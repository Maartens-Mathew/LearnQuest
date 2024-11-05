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
        btnAdd = findViewById(R.id.btnAddGoal);
        btnUpdate = findViewById(R.id.btnUpdateGoal);
        btnDelete = findViewById(R.id.btnDeleteGoal);
        btnAdd.setOnClickListener(this::btnAddClicked);
        btnUpdate.setOnClickListener(this::btnUpdateClicked);
        btnDelete.setOnClickListener(this::btnDeleteClicked);
        setUpRecyclerView(v -> {
            CardView c = (CardView)v ;
            Log.i(GOAL_LIST_ACTIVITY, "OnClick is triggering");
            if (selected == null){
                selected = (GoalAdapter.GoalViewHolder) rwGoalsList.findContainingViewHolder(c);
               // c.setBackgroundColor(getResources().getColor(R.color.teal_200, getResources().newTheme()));
                c.setBackgroundColor(Color.RED);
                Log.i(GOAL_LIST_ACTIVITY,"Color of CardView: " + c.getCardBackgroundColor().toString());
            }
            else{
                //selected.cardView.setBackgroundColor(getResources().getColor(R.color.narsi_teal, getResources().newTheme()));
                selected.cardView.setBackgroundColor(Color.BLUE);
                selected = (GoalAdapter.GoalViewHolder) rwGoalsList.findContainingViewHolder(c);
                c.setBackgroundColor(Color.RED);
                //c.setBackgroundColor(getResources().getColor(R.color.teal_200, getResources().newTheme()));
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
        btnAdd.setOnClickListener(this::btnAddClicked);
        btnUpdate.setOnClickListener(this::btnUpdateClicked);
        btnDelete.setOnClickListener(this::btnDeleteClicked);
        setUpRecyclerView(v -> {
            if (selected == null){
                selected = (GoalAdapter.GoalViewHolder) rwGoalsList.findContainingViewHolder(v);
                v.setBackgroundColor(getResources().getColor(R.color.teal_200, getResources().newTheme()));
            }
            else{
                selected.cardView.setBackgroundColor(getResources().getColor(R.color.narsi_teal, getResources().newTheme()));
                selected = (GoalAdapter.GoalViewHolder) rwGoalsList.findContainingViewHolder(v);
                v.setBackgroundColor(getResources().getColor(R.color.teal_200, getResources().newTheme()));
            }
        });
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
        btnAdd.setOnClickListener(this::btnAddClicked);
        btnUpdate.setOnClickListener(this::btnUpdateClicked);
        btnDelete.setOnClickListener(this::btnDeleteClicked);
        setUpRecyclerView(v -> {
            if (selected == null){
                selected = (GoalAdapter.GoalViewHolder) rwGoalsList.findContainingViewHolder(v);
                ((CardView) v).setCardBackgroundColor(Color.parseColor("#FF03DAC5"));
                //v.setBackgroundColor(getResources().getColor(R.color.teal_200, getResources().newTheme()));
            }
            else{
                selected.cardView.setCardBackgroundColor(Color.parseColor("#00796B"));
                //selected.cardView.setCardBackgroundColor(getResources().getColor(R.color.narsi_teal, getResources().newTheme()));
                selected = (GoalAdapter.GoalViewHolder) rwGoalsList.findContainingViewHolder(v);
                ((CardView) v).setCardBackgroundColor(Color.parseColor("#FF03DAC5"));
                //v.setBackgroundColor(getResources().getColor(R.color.teal_200, getResources().newTheme()));
            }
        });
    }

    public void btnAddClicked(View v){
        startActivity(new Intent(this, GroupAssessmentListActivity.class));
    }

    public void btnDeleteClicked(View v){
        if (selected == null){
            Toast.makeText(this,"Please select a goal", Toast.LENGTH_LONG).show();
        }
        else{
            Intent intent = new Intent(this, DeleteGoalActivity.class);
            intent.putExtra("data",selected.data);
            startActivity(intent);
        }
    }

    public void btnUpdateClicked(View v){
        if (selected == null){
            Toast.makeText(this,"Please select a goal", Toast.LENGTH_LONG).show();
            return;
        }
        int pos = selected.getBindingAdapterPosition();
        Intent intent = new Intent(this, AdjustGoalActivity.class);
        intent.putExtra("pos",pos);
        TrackProgressAssessmentData data = entries.get(pos);
        intent.putExtra("data",data);
        startActivity(intent);
    }

    public void setUpRecyclerView(View.OnClickListener listener){
        rwGoalsList = findViewById(R.id.aga_goal_recyclerview);
        GoalAdapter adapter = new GoalAdapter(entries, this, listener);
        rwGoalsList.setAdapter(adapter);
        rwGoalsList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rwGoalsList.addItemDecoration(new EqualSpacingItemDecoration(5));
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