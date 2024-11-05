package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.ManageGoals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnquest.AppState.App;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.EqualSpacingItemDecoration;
import com.example.learnquest.R;
import com.example.learnquest.model.assessment.Assessment;
import com.example.learnquest.model.group.Group;
import com.example.learnquest.model.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupAssessmentListActivity extends AppCompatActivity {

    public static final String GROUP_ASSESSMENT_ACTIVITY = "GroupAssessmentActivity";
    private List<Assessment> assessments;
    private RecyclerView rwAssessmentList;
    private TextView lblAssesmentAddInfo;
    private AssessmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_assessment_list);
        assessments = new ArrayList<>();

        App.user = User.demoUser();
        App.group = Group.demoGroup();
        rwAssessmentList = findViewById(R.id.rwAssessmentList);
        lblAssesmentAddInfo = findViewById(R.id.lblInfoDisplay);
        databaseCall();

    }

    public void onGroupAssessmentBtnBackPressed(View v){
        getOnBackPressedDispatcher().onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(GROUP_ASSESSMENT_ACTIVITY,"onResume() activated");
        databaseCall();
        if (assessments == null){
            lblAssesmentAddInfo.setText("No assessment to add as Goal");
            rwAssessmentList.setVisibility(View.GONE);
        }
        else{
            rwAssessmentList.setVisibility(View.VISIBLE);
            setUpRecyclerView();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(GROUP_ASSESSMENT_ACTIVITY,"onRestart() activated");
        databaseCall();

        if (assessments == null) {
            lblAssesmentAddInfo.setText("No assessment to add as Goal");
            rwAssessmentList.setVisibility(View.GONE);
        } else {
            rwAssessmentList.setVisibility(View.VISIBLE);
            setUpRecyclerView();
        }
    }

    private void setUpRecyclerView(){
        adapter = new AssessmentAdapter(assessments,v ->{
            Intent intent = new Intent(GroupAssessmentListActivity.this, AddGoalActivity.class);
            int pos = rwAssessmentList.findContainingViewHolder(v).getBindingAdapterPosition();
            intent.putExtra("assessment",assessments.get(pos));
            intent.putExtra("pos",pos);
            startActivity(intent);
        });
        rwAssessmentList.setAdapter(adapter);
        rwAssessmentList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rwAssessmentList.addItemDecoration(new EqualSpacingItemDecoration(5));
    }

    public void databaseCall(){
        Call<List<Assessment>> assessmentCall = App.api.getAvailableAssessments(App.user.getUserID(), App.group.getGroupID());

        assessmentCall.enqueue(new Callback<List<Assessment>>() {
            @Override
            public void onResponse(Call<List<Assessment>> call, Response<List<Assessment>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    assessments.addAll(response.body());
                    onAssessments();
                }
                else{
                    if (!response.isSuccessful() && response.body() == null){
                        runOnUiThread(() -> Toast.makeText(GroupAssessmentListActivity.this, "There are no assessments to add as goals", Toast.LENGTH_SHORT).show());
                        onNoAssessments();
                    }

                    if (!response.isSuccessful()){
                        try {
                            Log.e("Custom", response.errorBody().string());
                        } catch (IOException e) {
                            Log.e("Custom","Something happened.");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Assessment>> call, Throwable throwable) {
                runOnUiThread(() -> Toast.makeText(GroupAssessmentListActivity.this, "Could not connect to database successfully.", Toast.LENGTH_SHORT).show());
            }
        });
    }

    public void onNoAssessments(){

        rwAssessmentList.setVisibility(View.GONE);
        lblAssesmentAddInfo.setText("No assessment to add as Goal");
    }

    public void onAssessments(){
        rwAssessmentList.setVisibility(View.VISIBLE);
        setUpRecyclerView();
    }
}