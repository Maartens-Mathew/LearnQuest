package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AdjustGoalsListActivity extends AppCompatActivity {

    private List<TrackProgressAssessmentData> entries;
    private RecyclerView rwGoalsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_goals_list);
        setUpRecyclerView(v ->{
            int pos = rwGoalsList.findContainingViewHolder(v).getAdapterPosition();
            GoalAdapter.GoalViewHolder gvh = (GoalAdapter.GoalViewHolder)rwGoalsList.findContainingViewHolder(v);
            TrackProgressAssessmentData selected = gvh.data;
            Intent newIntent = new Intent(this, AdjustGoalActivity.class);
            newIntent.putExtra("data",selected);
            startActivity(newIntent);
        });
    }


    public void setUpRecyclerView(View.OnClickListener listener){
        rwGoalsList = findViewById(R.id.aga_goal_recyclerview);
        GoalAdapter adapter = new GoalAdapter(entries, this, listener);
        rwGoalsList.setAdapter(adapter);
        rwGoalsList.setLayoutManager(new LinearLayoutManager(this));
    }


    private void getDatabaseData(){
        //ensure we have the correct userID and groupID
        //use userID & groupId
        Call<List<TrackProgressAssessmentData>> call = App.api.getAssessmentData(0, 1);
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