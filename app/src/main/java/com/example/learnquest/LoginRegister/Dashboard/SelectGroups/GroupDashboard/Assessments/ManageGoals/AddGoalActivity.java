package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.ManageGoals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;
import com.example.learnquest.model.assessment.Assessment;
import com.example.learnquest.model.assessment.StudentAssessment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddGoalActivity extends AppCompatActivity {

    public static final String ADD_GOAL_ACTIVITY = "AddGoalActivity";
    private Assessment data;
    private StudentAssessment newGoal;
    private int pos;
    private EditText edtMarkDesired;
    private TextView lblAssessmentName, lblWeighting, lblDueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);
        lblAssessmentName = findViewById(R.id.lblAddGoalAssessmentName);
        lblWeighting = findViewById(R.id.lblAddGoalWeighting);
        lblDueDate = findViewById(R.id.lblAddGoalDueDate);
        edtMarkDesired = findViewById(R.id.edtAddGoalMarkDesired);
        Intent intent = getIntent();
        if (intent != null){
            data = (Assessment) intent.getExtras().get("assessment");
            pos = intent.getExtras().getInt("pos");
            lblAssessmentName.setText("Assessment: " + data.getName());
            lblWeighting.setText("Weighting: " + data.getWeighting());
            lblDueDate.setText("Due Date: "+ data.getDueDate());
        }
    }

    public void btnAddClicked(View v){
        Float markDesired = Float.parseFloat(edtMarkDesired.getText().toString());
        GroupAssessmentListActivity.adapter.remove(pos);
        newGoal = new StudentAssessment(data.getAssessmentID(),"",null,markDesired,null);
        Call<Void> assessmentCall = App.api.addGoal(newGoal);
        assessmentCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Log.i(ADD_GOAL_ACTIVITY,"successfully added Goal");
                    Toast.makeText(AddGoalActivity.this, "Added new Goal", Toast.LENGTH_LONG).show();
                }
                else{
                    Log.e(ADD_GOAL_ACTIVITY,"Something went wrong: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Log.e(ADD_GOAL_ACTIVITY,throwable.getStackTrace().toString());
            }
        });
        getOnBackPressedDispatcher().onBackPressed();
    }

    public void btnCancelClicked(View v){
        getOnBackPressedDispatcher().onBackPressed();
    }
}