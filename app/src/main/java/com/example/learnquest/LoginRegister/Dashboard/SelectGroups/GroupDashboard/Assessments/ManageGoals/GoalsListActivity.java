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
        try{
            thread.start();
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        setUp();
//        if (selected == null){
//            selected = (GoalAdapter.GoalViewHolder) rwGoalsList.findContainingViewHolder(v);
//            selected.controls.setVisibility(View.VISIBLE);
//        }
//        else{
//            selected.controls.setVisibility(View.GONE);
//            selected = (GoalAdapter.GoalViewHolder) rwGoalsList.findContainingViewHolder(v);
//            selected.controls.setVisibility(View.VISIBLE);
//        }


    }


    private void setUp(){
        btnAdd = findViewById(R.id.btnAddGoal);
        btnAdd.setOnClickListener(this::btnAddClicked);
        setUpRecyclerView(v -> {
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
        try{
            thread.start();
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        setUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Thread thread = new Thread(this::getDatabaseData);
        try{
            thread.start();
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        setUp();
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
            Log.e(GOAL_LIST_ACTIVITY, e.getStackTrace().toString());
        }
    }

    public void btnUpdateClicked(View v){
        int pos = selected.getBindingAdapterPosition();
        Intent intent = new Intent(this, AdjustGoalActivity.class);
        TrackProgressAssessmentData data = entries.get(pos);
        intent.putExtra("data",data);
        startActivity(intent);
    }

    public void setUpRecyclerView(View.OnClickListener listener){
        rwGoalsList = findViewById(R.id.aga_goal_recyclerview);
        GoalAdapter adapter = new GoalAdapter(entries, listener);
        rwGoalsList.setAdapter(adapter);
        rwGoalsList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rwGoalsList.addItemDecoration(new EqualSpacingItemDecoration(5));
    }

    private void getDatabaseData(){
        Call<List<TrackProgressAssessmentData>> call = App.api.getAssessmentData(App.user.getUserID(),
                App.group.getGroupID());
        Response<List<TrackProgressAssessmentData>> response = null;
        try{
            response = call.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if (response.isSuccessful()){
            entries = Collections.synchronizedList(response.body());
        }
        else{
            try{
                Log.e("TrackProgressFragment",response.errorBody().string());
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}