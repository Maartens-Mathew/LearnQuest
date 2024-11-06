package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.ManageGoals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.learnquest.AppState.App;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.EqualSpacingItemDecoration;
import com.example.learnquest.R;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoalsListActivity extends AppCompatActivity{

    public static final String GOAL_LIST_ACTIVITY = "GoalListActivity";
    private RecyclerView rwGoalsList;
    private GoalAdapter adapter;
    private List<TrackProgressAssessmentData> entries;
    private GoalAdapter.GoalViewHolder selected;
    private Button btnUpdate, btnAdd, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_list);
        Thread thread = new Thread(this::getDatabaseData);
        thread.start();
    }


    private void setUp(List<TrackProgressAssessmentData> entries){
        btnAdd = findViewById(R.id.btnAddGoal);
        btnAdd.setOnClickListener(this::btnAddClicked);
        setUpRecyclerView(entries,v -> {
            Log.i(GOAL_LIST_ACTIVITY, "OnClick is triggering");
            if (selected != null) {
                selected.controls.setVisibility(View.GONE);
            }
            selected = (GoalAdapter.GoalViewHolder) rwGoalsList.findContainingViewHolder(v);
            if (selected != null) {
                selected.controls.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Thread thread = new Thread(this::getDatabaseData);
        thread.start();
//        try{
//            thread.start();
//            thread.join();
//        }catch(InterruptedException e){
//            e.printStackTrace();
//        }
//        setUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Thread thread = new Thread(this::getDatabaseData);
        thread.start();
    }

    public void btnAddClicked(View v){
        startActivity(new Intent(this, GroupAssessmentListActivity.class));
    }

    public void btnDeleteClicked(View v){
        Log.i(GOAL_LIST_ACTIVITY, "Delete has been clicked");
        Intent intent = new Intent(this, DeleteGoalActivity.class);
        intent.putExtra("data",selected.data);
        try{
            startActivity(intent);
        }
        catch (Exception e){
            Log.e(GOAL_LIST_ACTIVITY, e.getMessage());
            for (StackTraceElement ele : e.getStackTrace()){
                Log.e(GOAL_LIST_ACTIVITY, ele.toString());
            }
        }
    }

    public void btnUpdateClicked(View v){
        int pos = selected.getBindingAdapterPosition();
        Intent intent = new Intent(this, AdjustGoalActivity.class);
        TrackProgressAssessmentData data = entries.get(pos);
        intent.putExtra("data",data);
        startActivity(intent);
    }

    public void setUpRecyclerView(List<TrackProgressAssessmentData> entries, View.OnClickListener listener){
        rwGoalsList = findViewById(R.id.aga_goal_recyclerview);
        GoalAdapter adapter = new GoalAdapter(entries, listener);
        rwGoalsList.setAdapter(adapter);
        rwGoalsList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rwGoalsList.addItemDecoration(new EqualSpacingItemDecoration(5));
    }

    private void getDatabaseData(){
        Call<List<TrackProgressAssessmentData>> call = App.api.getAssessmentData(App.user.getUserID(), App.group.getGroupID());

        call.enqueue(new Callback<List<TrackProgressAssessmentData>>() {
            @Override
            public void onResponse(Call<List<TrackProgressAssessmentData>> call, Response<List<TrackProgressAssessmentData>> response) {
                if (response.isSuccessful()) {
                    // This runs on a background thread, so use runOnUiThread to update the UI
                    entries = Collections.synchronizedList(response.body());
                    // If you need to update the UI, use runOnUiThread
                    runOnUiThread(() -> {
                        // You can update UI elements here, like notifying an adapter or other UI actions
                        setUp(entries);
                    });
                } else {
                    // Handle error response
                    try {
                        Log.e("ADJUST_GOALS_LIST_ACTIVITY", response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<TrackProgressAssessmentData>> call, Throwable t) {
                // Handle failure (e.g., network errors)
                Log.e("ADJUST_GOALS_LIST_ACTIVITY", "Request failed", t);
            }
        });
    }

}