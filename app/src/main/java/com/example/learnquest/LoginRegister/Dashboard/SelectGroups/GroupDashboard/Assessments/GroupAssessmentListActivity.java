package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupAssessmentListActivity extends AppCompatActivity {

    public static final String GROUP_ASSESSMENT_ACTIVITY = "GroupAssessmentActivity";
    private List<Assessment> assessments;
    private List<StudentAssessment> assessmentsToFilter;
    private RecyclerView rwAssessmentList;
    private TextView lblAssesmentAddInfo;
    public static AssessmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_assessment_list);
        Thread thread = new Thread(this::databaseCall);
        try{
            thread.start();
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        filter();
        rwAssessmentList = findViewById(R.id.rwAssessmentList);
        lblAssesmentAddInfo = findViewById(R.id.lblInfoDisplay);
        if (assessments == null){
            rwAssessmentList.setVisibility(View.GONE);
            lblAssesmentAddInfo.setText("No assessment to add as Goal");
        }
        else{
            rwAssessmentList.setVisibility(View.VISIBLE);
            setUpRecyclerView();
        }
    }
    public void filter(){
        for (int i = 0; i < assessments.size(); i++){
            Assessment a = assessments.get(i);
            for (StudentAssessment s : assessmentsToFilter){
                if (a.getAssessmentID() == s.getAssessmentID()){
                    assessments.remove(a);
                }
            }
        }
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
        filter();
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
        Thread thread = new Thread(this::databaseCall);
        try{
            thread.start();
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        filter();
        if (assessments == null){
            lblAssesmentAddInfo.setText("No assessment to add as Goal");
            rwAssessmentList.setVisibility(View.GONE);
        }
        else{
            rwAssessmentList.setVisibility(View.VISIBLE);
            setUpRecyclerView();
        }
    }

    private void setUpRecyclerView(){
        adapter = new AssessmentAdapter(assessments,v ->{
            Intent intent = new Intent(v.getContext(), AddGoalActivity.class);
            int pos = rwAssessmentList.findContainingViewHolder(v).getBindingAdapterPosition();
            intent.putExtra("assessment",assessments.get(pos));
            intent.putExtra("pos",pos);
            startActivity(intent);
        });
        rwAssessmentList.setAdapter(adapter);
        rwAssessmentList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
    }

    public void databaseCall(){
        Call<List<Assessment>> assessmentCall = App.api.getAssessments();
        Call<List<StudentAssessment>> studentAssessmentCall = App.api.getStudentAssessments(App.userID);
        studentAssessmentCall.enqueue(new Callback<List<StudentAssessment>>() {
            @Override
            public void onResponse(Call<List<StudentAssessment>> call, Response<List<StudentAssessment>> response) {
                if (response.isSuccessful()){
                    assessmentsToFilter = Collections.synchronizedList(response.body());
                }
                else{
                    Log.e(GROUP_ASSESSMENT_ACTIVITY, "something went wrong");
                }
            }

            @Override
            public void onFailure(Call<List<StudentAssessment>> call, Throwable throwable) {
                Log.e(GROUP_ASSESSMENT_ACTIVITY,throwable.getStackTrace().toString());
            }
        });
        assessmentCall.enqueue(new Callback<List<Assessment>>() {
            @Override
            public void onResponse(Call<List<Assessment>> call, Response<List<Assessment>> response) {
                if (response.isSuccessful()){
                    assessments = Collections.synchronizedList(response.body());
                }
                else{
                    Log.e(GROUP_ASSESSMENT_ACTIVITY,"something went wrong");
                }
            }
            @Override
            public void onFailure(Call<List<Assessment>> call, Throwable throwable) {
                Log.e(GROUP_ASSESSMENT_ACTIVITY,throwable.getStackTrace().toString());
            }
        });
    }
}