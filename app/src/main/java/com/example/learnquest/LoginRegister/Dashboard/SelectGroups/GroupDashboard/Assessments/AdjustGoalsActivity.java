package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AdjustGoalsActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_goals);
        Thread thread = new Thread(this::getDatabaseData);
        try{
            thread.start();
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_display, GoalsListFragment.newInstance())
                .commit();

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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.card_goal){
            int dataPos = GoalLogic.goalList.findContainingViewHolder(v).getAdapterPosition();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_display, AdjustGoalsFragment.newInstance(dataPos))
                    .commit();
        }
        else{
            GoalLogic.goalList.getAdapter().notifyDataSetChanged();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_display, GoalsListFragment.newInstance())
                    .commit();
        }
    }
}