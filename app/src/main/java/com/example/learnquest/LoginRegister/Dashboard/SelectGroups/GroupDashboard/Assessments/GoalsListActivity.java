package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class GoalsListActivity extends AppCompatActivity{

    private RecyclerView rwGoalsList;
    private GoalAdapter adapter;

    private int stateType;
    private GoalAdapter.GoalViewHolder selected;
    private AppCompatButton btnUpdate, btnAdd, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_list);
        if (savedInstanceState != null && savedInstanceState.containsKey("stateType")){
            stateType = savedInstanceState.getInt("stateType");
            if (stateType == 0){
                Thread thread = new Thread(this::getDatabaseData);
                try{
                    thread.start();
                    thread.join();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                LinearLayout controls = findViewById(R.id.aga_controls);
                controls.setVisibility(View.VISIBLE);
                btnAdd = findViewById(R.id.btnAddGoal);
                btnUpdate = findViewById(R.id.btnUpdateGoal);
                btnDelete = findViewById(R.id.btnDeleteGoal);
                btnUpdate.setOnClickListener(this::btnUpdateClicked);
                setUpRecyclerView(v -> selected = (GoalAdapter.GoalViewHolder) rwGoalsList.findContainingViewHolder(v));
            }
            else{
                setUpRecyclerView(v ->{
                    int pos = rwGoalsList.findContainingViewHolder(v).getAdapterPosition();
                    Intent intent = new Intent(this, AdjustGoalActivity.class);
                    intent.putExtra("pos",pos);
                    startActivity(intent);
                });
            }
        }

    }

    public void btnAddClicked(View v){

    }

    public void btnDeleteClicked(View v){
        if (selected == null) return;
        int pos = selected.getAdapterPosition();
        TrackProgressAssessmentData data = GoalLogic.data.remove(pos);
        rwGoalsList.getAdapter().notifyItemRemoved(pos);
        //TODO:delete data from database
    }

    public void btnUpdateClicked(View v){
        if (selected == null) return;
        int pos = selected.getAdapterPosition();
        Intent intent = new Intent(this, AdjustGoalActivity.class);
        intent.putExtra("pos",pos);
        TrackProgressAssessmentData data = GoalLogic.data.get(pos);
        startActivity(intent);
    }

    public void setUpRecyclerView(View.OnClickListener listener){
        rwGoalsList = findViewById(R.id.aga_goal_recyclerview);
        GoalAdapter adapter = new GoalAdapter(GoalLogic.data, this);
        rwGoalsList.setAdapter(adapter);
        rwGoalsList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getDatabaseData(){
        //ensure we have the correct userID and groupID
        //use userID & groupId
        Call<List<TrackProgressAssessmentData>> call = App.api.get_assessment_data(0, 1);
        Response<List<TrackProgressAssessmentData>> response = null;
        try{
            response = call.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if (response.isSuccessful()){
            GoalLogic.data = Collections.synchronizedList(response.body());
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