package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.ManageGoals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteGoalActivity extends AppCompatActivity {

    public static final String DELETE_GOAL_ACTIVITY = "DeleteGoalActivity";
    private TrackProgressAssessmentData data;
    private TextView lblAssessmentName, lblWeighting, lblMarkDesired, lblMarkObtained, lblDueDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_goal);
        lblAssessmentName = findViewById(R.id.lblDeleteGoalAssessmentName);
        lblDueDate = findViewById(R.id.lblDeleteGoalDueDate);
        lblWeighting = findViewById(R.id.lblDeleteGoalWeighting);
        lblMarkDesired = findViewById(R.id.lblDeleteGoalMarkDesired);
        lblMarkObtained = findViewById(R.id.lblDeleteGoalMarkObtained);
        Intent intent = getIntent();
        if (intent != null){
            data = (TrackProgressAssessmentData) intent.getExtras().get("data");
            lblAssessmentName.setText("Assessment Name: " + data.getName());
            lblDueDate.setText("Due Date: " + data.getDate_due().toString());
            lblWeighting.setText("Weighting: "+ data.getWeight());
            lblMarkDesired.setText("Mark Desired: "+data.getIdeal_mark());
            if (data.getMark_obtained() != null){
                lblMarkObtained.setVisibility(View.VISIBLE);
                lblMarkObtained.setText("Mark Obtained: " + data.getMark_obtained());
            }
            else{
                lblMarkObtained.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void onBtnCancelClicked(View v){
        getOnBackPressedDispatcher().onBackPressed();
    }

    public void onBtnDeleteClicked(View v){
        Call<Void> deleteCall = App.api.deleteGoal("eq." + 0, "eq." + data.getAssessment_id());
        deleteCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Log.i(DELETE_GOAL_ACTIVITY, "Delete worked successfully");
                    Toast.makeText(DeleteGoalActivity.this, "Goal Successfully Deleted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.e(DELETE_GOAL_ACTIVITY,"Update Failed: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Log.e(DELETE_GOAL_ACTIVITY,"Error: "+ throwable.getStackTrace());
            }
        });
        getOnBackPressedDispatcher().onBackPressed();
    }
}