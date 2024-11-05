package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.ManageGoals;

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
import java.util.Comparator;
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
        rwGoalsList = findViewById(R.id.rwAdjustGoalsList);
        Thread thread = new Thread(this::databaseCall);
        try{
            thread.start();
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        setUpRecyclerView(this::Listener);

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
            entries.sort(comparator);
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

    private final Comparator<TrackProgressAssessmentData> comparator = (o1, o2) -> {
        java.util.Date d1 = o1.getDate_due();
        java.util.Date d2 = o2.getDate_due();
        return d1.compareTo(d2);
    };

    private void Listener(View v){
        int pos = rwGoalsList.findContainingViewHolder(v).getBindingAdapterPosition();
        GoalAdapter.GoalViewHolder gvh = (GoalAdapter.GoalViewHolder)rwGoalsList.findContainingViewHolder(v);
        TrackProgressAssessmentData selected = gvh.data;
        Intent newIntent = new Intent(this, AdjustGoalActivity.class);
        newIntent.putExtra("data",selected);
        startActivity(newIntent);
    }

    private void setUpRecyclerView(View.OnClickListener listener){
        GoalAdapter adapter = new GoalAdapter(entries, this, listener);
        rwGoalsList.setAdapter(adapter);
        rwGoalsList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Thread thread = new Thread(this::databaseCall);
        try{
            thread.start();
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        setUpRecyclerView(this::Listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Thread thread = new Thread(this::databaseCall);
        try{
            thread.start();
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        setUpRecyclerView(this::Listener);
    }

}