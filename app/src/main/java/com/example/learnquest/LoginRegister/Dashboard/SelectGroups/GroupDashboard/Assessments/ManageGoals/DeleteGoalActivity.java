package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.ManageGoals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
        Button btnDelete = findViewById(R.id.btnDeleteGoalDelete);
        Button btnCancel = findViewById(R.id.btnDeleteGoalCancel);
        btnDelete.setOnClickListener(this::onBtnDeleteClicked);
        btnCancel.setOnClickListener(this::onBtnCancelClicked);
        Intent intent = getIntent();
        if (intent != null){
            data = (TrackProgressAssessmentData) intent.getExtras().get("data");
            lblAssessmentName.setText(getResources().getString(R.string.assessment_name,data.getName()));
            lblDueDate.setText(getResources().getString(R.string.due_date, String.format("%tF",data.getDate_due())));
            lblWeighting.setText(getResources().
                    getString(R.string.adjust_goals_weighting, String.format("%.0f",data.getWeight())));
            lblMarkDesired.setText(getResources()
                    .getString(R.string.adjust_goals_mark_desired, String.format("%.0f",data.getIdeal_mark())));
            if (data.getMark_obtained() != null){
                lblMarkObtained.setVisibility(View.VISIBLE);
                lblMarkObtained.setText(getResources()
                        .getString(R.string.adjust_goals_mark_obtained, String.format("%.0f",data.getMark_obtained())));
            }
            else{
                lblMarkObtained.setVisibility(View.INVISIBLE);
            }
        }
        else{
            getOnBackPressedDispatcher().onBackPressed();
        }
    }

    public void onBtnCancelClicked(View v){
        getOnBackPressedDispatcher().onBackPressed();
    }

    public void onBtnDeleteClicked(View v){
        Call<Void> deleteCall = App.api.deleteGoal("eq." + App.user.getUserID(), "eq." + data.getAssessment_id());
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