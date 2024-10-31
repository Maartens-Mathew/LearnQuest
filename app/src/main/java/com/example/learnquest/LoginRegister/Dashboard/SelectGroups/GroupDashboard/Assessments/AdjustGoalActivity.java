package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.learnquest.AppState.App;
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
    private AppCompatButton btnUpdateConfirm;
    private int pos;
    private int state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_goal);
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey("pos")){
            pos = getIntent().getExtras().getInt("pos");
            data = GoalLogic.data.get(pos);
            edtMarkDesired = findViewById(R.id.edtDesiredMark);
            edtMarkObtained = findViewById(R.id.edtMarkObtained);
            btnUpdateConfirm = findViewById(R.id.btnConfirmUpdateGoal);
            lblAssessmentName = findViewById(R.id.lblAssessmentName);
            lblAssessmentName.setText(data.getName());
            edtMarkDesired.setText(data.getIdeal_mark().toString());
            edtMarkObtained.setText(data.getMark_obtained().toString());
            btnUpdateConfirm.setOnClickListener(this::btnUpdateConfirmClicked);
            if (getIntent().getExtras().containsKey("stateType")){
                state = getIntent().getExtras().getInt("stateType");
            }
            else{
                state = 0;
            }
        }
    }



    public void onBtnBackPressed(View view){
        Intent intent = new Intent(this, GoalsListActivity.class);
        intent.putExtra("stateType",1);
        startActivity(intent);
    }

    public void btnUpdateConfirmClicked(View view){
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
                }
                else{
                    Log.e("AdjustGoalActivity","Update Failed: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Log.e("AdjustGoalActivity","Error: "+ throwable.getStackTrace());
            }
        });
        Intent intent = new Intent(this, GoalsListActivity.class);
        intent.putExtra("stateType",1);
        startActivity(intent);
        //TODO:Update this info to database
    }
}