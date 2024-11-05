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
import com.example.learnquest.model.user.User;

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
            lblAssessmentName.setText(getResources().getString(R.string.assessment_name, data.getName()));
            lblWeighting.setText(getResources().getString(R.string.adjust_goals_weighting,
                    String.format("%.0f",data.getWeighting())));
            lblDueDate.setText(getResources().getString(R.string.due_date,
                    String.format("%tF",data.getDueDate())));
        }
    }

    public void btnAddGoalAddClicked(View v){
        if (edtMarkDesired.getText().toString().length() == 0){
            Toast.makeText(this, "Please enter a desired mark", Toast.LENGTH_SHORT).show();
            return;
        }
        Float markDesired = Float.parseFloat(edtMarkDesired.getText().toString());
        newGoal = new StudentAssessment(data.getAssessmentID(),"",markDesired,null, App.user.getUserID());
        Call<Void> assessmentCall = App.api.addGoal(newGoal);
        assessmentCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Log.i(ADD_GOAL_ACTIVITY,"successfully added Goal");
                    Toast.makeText(AddGoalActivity.this, "Added new Goal", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(AddGoalActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    Log.e(ADD_GOAL_ACTIVITY,"Something went wrong: " + response.message());
                    if (response.errorBody() != null){
                        Log.e(ADD_GOAL_ACTIVITY, String.valueOf(response.code()));
                        Log.e(ADD_GOAL_ACTIVITY,response.errorBody().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Toast.makeText(AddGoalActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.e(ADD_GOAL_ACTIVITY,throwable.getStackTrace().toString());
            }
        });
        getOnBackPressedDispatcher().onBackPressed();
    }

    public void btnAddGoalCancelClicked(View v){
        getOnBackPressedDispatcher().onBackPressed();
    }
}