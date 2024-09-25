package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.learnquest.R;

public class AdjustGoalActivity extends AppCompatActivity {

    private TrackProgressAssessmentData data;
    private EditText edtMarkDesired, edtMarkObtained;
    private TextView lblAssessmentName;
    private AppCompatButton btnUpdateConfirm;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_goal);
        if (savedInstanceState != null && savedInstanceState.containsKey("pos")){
            pos = savedInstanceState.getInt("pos");
            data = GoalLogic.data.get(pos);
            edtMarkDesired = findViewById(R.id.edtDesiredMark);
            edtMarkObtained = findViewById(R.id.edtMarkObtained);
            btnUpdateConfirm = findViewById(R.id.btnConfirmUpdateGoal);
            lblAssessmentName = findViewById(R.id.lblAssessmentName);
            lblAssessmentName.setText(data.getName());
            edtMarkDesired.setText(data.getIdeal_mark().toString());
            edtMarkObtained.setText(data.getMark_obtained().toString());
            btnUpdateConfirm.setOnClickListener(this::btnUpdateConfirmClicked);
        }
    }

    public void btnUpdateConfirmClicked(View view){
        view = null;
        data.setIdeal_mark(Double.parseDouble(edtMarkDesired.getText().toString()));
        data.setMark_obtained(Double.parseDouble(edtMarkObtained.getText().toString()));
        Intent intent = new Intent(this, GoalsListActivity.class);
        intent.putExtra("stateType",1);
        startActivity(intent);
        //TODO:Update this info to database
    }
}