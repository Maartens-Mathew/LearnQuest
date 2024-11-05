package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.ManageGoals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnquest.AppState.App;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.ManageGoals.TrackProgressAssessmentData;
import com.example.learnquest.R;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdjustGoalActivity extends AppCompatActivity {

    private TrackProgressAssessmentData data;
    private EditText edtMarkDesired, edtMarkObtained;
    private TextView lblAssessmentName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_goal);
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey("data")){
            data = (TrackProgressAssessmentData) getIntent().getExtras().get("data");
            edtMarkDesired = findViewById(R.id.edtDesiredMark);
            edtMarkObtained = findViewById(R.id.edtMarkObtained);
            lblAssessmentName = findViewById(R.id.lblAssessmentName);
            lblAssessmentName.setText(getResources().getString(R.string.assessment_name,data.getName()));
            edtMarkDesired.setText(String.format("%.0f",data.getIdeal_mark()));
            edtMarkObtained.setText(String.format("%.0f",data.getMark_obtained()));
        }
    }


    public void onAdjustGoalBtnBackPressed(View view){
        getOnBackPressedDispatcher().onBackPressed();
    }

    public void btnUpdateConfirmClicked(View view){
        if (edtMarkObtained.getText().toString().length() == 0 && edtMarkObtained.getText().toString().length() == 0){
            Toast.makeText(this, "Please enter desired and obtained marks", Toast.LENGTH_SHORT).show();
            return;
        }
        double newIdealMark = Double.parseDouble(edtMarkDesired.getText().toString());
        double newMarkObtained = Double.parseDouble(edtMarkObtained.getText().toString());
        Map<String, Object> body = new HashMap<>();
        if (data.getIdeal_mark() != newIdealMark){
            data.setIdeal_mark(newIdealMark);
            body.put("idealMark",data.getIdeal_mark());
        }
        if (data.getMark_obtained() != newMarkObtained){
            data.setMark_obtained(newMarkObtained);
            body.put("markObtained", data.getMark_obtained());
        }
        Call<Void> updateCall = App.api.updateGoal("eq." + 0, "eq." + data.getAssessment_id(),body);
        updateCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Log.i("AdjustGoalActivity","Update Successful");
                    Toast.makeText(AdjustGoalActivity.this,"Updated successfully",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(AdjustGoalActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    Log.e("AdjustGoalActivity","Update Failed: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Toast.makeText(AdjustGoalActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.e("AdjustGoalActivity","Error: "+ throwable.getStackTrace());
            }
        });
        getOnBackPressedDispatcher().onBackPressed();
    }
}