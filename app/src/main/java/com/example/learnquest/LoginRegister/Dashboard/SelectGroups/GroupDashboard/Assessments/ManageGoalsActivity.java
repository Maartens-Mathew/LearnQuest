package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ManageGoalsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_goals);
        recyclerView = findViewById(R.id.recyclerView);
        Thread thread = new Thread(this::getDatabaseData);
        try{
            thread.start();
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        GoalAdapter adapter = new GoalAdapter(GoalLogic.data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getDatabaseData() {
        //ensure we have the correct userID and groupID
        //use userID & groupId
        Call<List<TrackProgressAssessmentData>> call = App.api.get_assessment_data(0, 1);
        Response<List<TrackProgressAssessmentData>> response = null;
        try {
            response = call.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response.isSuccessful()) {
            GoalLogic.data = Collections.synchronizedList(response.body());
        } else {
            try {
                Log.e("TrackProgressFragment", response.errorBody().string());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}