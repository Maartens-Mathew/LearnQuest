package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupAssessmentListActivity extends AppCompatActivity {

    public static final String GROUP_ASSESSMENT_ACTIVITY = "GroupAssessmentActivity";
    private List<Assessment> assessments;

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

    }

    public void databaseCall(){
        Call<List<Assessment>> assessmentCall = App.api.getAssessments();
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