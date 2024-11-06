package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.ManageGoals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.learnquest.AppState.App;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.EqualSpacingItemDecoration;
import com.example.learnquest.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdjustGoalsListActivity extends AppCompatActivity {

    public static final String ADJUST_GOALS_LIST_ACTIVITY = "AdjustGoalsListActivity";
    private List<TrackProgressAssessmentData> entries;
    private RecyclerView rwGoalsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_goals_list);
        rwGoalsList = findViewById(R.id.rwAdjustGoalsList);
        Thread thread = new Thread(this::databaseCall);
        thread.start();
//        try{
//            thread.start();
//            thread.join();
//        }catch(InterruptedException e){
//            e.printStackTrace();
//        }

//        setUpRecyclerView(v ->{
//            int pos = rwGoalsList.findContainingViewHolder(v).getBindingAdapterPosition();
//            GoalAdapter.GoalViewHolder gvh = (GoalAdapter.GoalViewHolder)rwGoalsList.findContainingViewHolder(v);
//            TrackProgressAssessmentData selected = gvh.data;
//            Intent newIntent = new Intent(this, AdjustGoalActivity.class);
//            newIntent.putExtra("data",selected);
//            startActivity(newIntent);
//        });
    }

    public void onAdjustGoalsListActivityBtnBackPressed(View v){
        getOnBackPressedDispatcher().onBackPressed();
    }

    private void databaseCall(){
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
                        setUpRecyclerView(entries, AdjustGoalsListActivity.this::Listener);
                    });
                } else {
                    // Handle error response
                    try {
                        Log.e(ADJUST_GOALS_LIST_ACTIVITY, response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<TrackProgressAssessmentData>> call, Throwable t) {
                // Handle failure (e.g., network errors)
                Log.e(ADJUST_GOALS_LIST_ACTIVITY, "Request failed", t);
            }
        });
    }

    private void Listener(View v){
        int pos = rwGoalsList.findContainingViewHolder(v).getBindingAdapterPosition();
        GoalAdapter.GoalViewHolder gvh = (GoalAdapter.GoalViewHolder)rwGoalsList.findContainingViewHolder(v);
        TrackProgressAssessmentData selected = gvh.data;
        Intent newIntent = new Intent(this, AdjustGoalActivity.class);
        newIntent.putExtra("data",selected);
        startActivity(newIntent);
    }

    private void setUpRecyclerView(List<TrackProgressAssessmentData> entries, View.OnClickListener listener){
        GoalAdapter adapter = new GoalAdapter(entries, listener);
        rwGoalsList.setAdapter(adapter);
        rwGoalsList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        rwGoalsList.addItemDecoration(new EqualSpacingItemDecoration(5));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Thread thread = new Thread(this::databaseCall);
        thread.start();
//        try{
//            thread.start();
//            thread.join();
//        }catch(InterruptedException e){
//            e.printStackTrace();
//        }
//        setUpRecyclerView(this::Listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Thread thread = new Thread(this::databaseCall);
        thread.start();
//        try{
//            thread.start();
//            thread.join();
//        }catch(InterruptedException e){
//            e.printStackTrace();
//        }
//        setUpRecyclerView(this::Listener);
    }

}