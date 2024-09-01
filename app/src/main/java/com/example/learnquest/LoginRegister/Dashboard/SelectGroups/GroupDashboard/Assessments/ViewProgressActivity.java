package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learnquest.R;
import com.example.learnquest.Utils.database.SupabaseApi;
import com.example.learnquest.Utils.database.SupabaseClient;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProgressActivity extends AppCompatActivity {

    private LineChart lineChart;
    private LineData lineData;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_progess);
        //TODO: must get userID from login screen
        Intent intent = getIntent();
        userID = intent.getExtras().getInt("userID");
        setUpChart();
        lineData = new LineData();
        interactingWithDatabase();
    }

    private void interactingWithDatabase(){
        /*
          I did all of this including the creation of the new classes to get the data from the database.
          This way of doing it is the way shown to me by ChatGPT. Its very inefficient and there must be a better
          way of doing this. I feel like we should just combine the Assessment and StudentAsssessment tables together
          I don't really understand why they are separate. It's very difficult to get the relational data through a Select
          query so had to just get all the data from both tables using api calls. I also added methods to the api interface
          to better do that. I also added a @Query term which is the userID. I'm not sure if the api call will filter it
          correctly so that it gets only the student assessments with that userID. I also had to get the weightings from
          the Assessment table so I had to search the whole assessment table to get a specific weighting for each student
          assesssment of hte specified user which is very inefficient.

          This also relies on the activity being started with the userID of the user passed to it
        */
        SupabaseApi api = SupabaseClient.getClient().create(SupabaseApi.class);
        Call<List<StudentAssessment>> studentAssessmentsCall = api.getStudentAssessments(Integer.toString(userID));
        studentAssessmentsCall.enqueue(new Callback<List<StudentAssessment>>() {
            @Override
            public void onResponse(Call<List<StudentAssessment>> call, Response<List<StudentAssessment>> response) {
                if (response.isSuccessful() && response.body() != null){
                    List<StudentAssessment> studentAssessments = response.body();
                    Call<List<Assessment>> assessments = api.getAssessments();
                    assessments.enqueue(new Callback<List<Assessment>>() {
                        @Override
                        public void onResponse(Call<List<Assessment>> call, Response<List<Assessment>> response) {
                            if (response.isSuccessful() && response.body() != null){
                                List<Assessment> assessments = response.body();
                                List<ChartData> chartDataList = new ArrayList<>();
                                for(StudentAssessment sa : studentAssessments){
                                    ChartData cd = new ChartData();
                                    cd.setIdealMark(sa.getIdealMark());
                                    cd.setMarkObtained(sa.getMarkObtained());
                                    for(Assessment assessment : assessments){
                                        if (sa.getAssessmentID() == assessment.getAssessmentID()){
                                            cd.setWeighting(assessment.getWeighting());
                                            break;
                                        }
                                    }
                                    chartDataList.add(cd);
                                }
                                addChartData(chartDataList);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Assessment>> call, Throwable throwable) {
                            Log.e("ViewProgressActivity","Call 2: error getting a response from database");
                        }
                    });
                }
                else{
                    Log.e("ViewProgressActivity","Call 1: error getting a response from database");
                }
            }

            @Override
            public void onFailure(Call<List<StudentAssessment>> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    private void setUpChart(){
        lineChart = findViewById(R.id.lineChart);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setAxisMinimum(0f);
        lineChart.getAxisLeft().setAxisMinimum(0f);
    }

    private void addChartData(List<ChartData> chartData){
        List<Entry> currProgressData = new ArrayList<>();
        List<Entry> goalMarkData = new ArrayList<>();
        float accumIdeal = 0f;
        float accumCurr = 0f;
        for (int i = 0; i < chartData.size(); i++){
            ChartData cd = chartData.get(i);
            if (cd.getMarkObtained() != 0f){
                accumCurr += cd.getMarkObtained();
                currProgressData.add(new Entry(i*0f,accumCurr));
                accumIdeal += cd.getIdealMark();
                goalMarkData.add(new Entry(i*0f,accumIdeal));
            }
            else{
                accumIdeal += cd.getIdealMark();
                accumCurr += cd.getIdealMark();
                currProgressData.add(new Entry(i*0f,accumCurr));
                goalMarkData.add(new Entry(i*0f,accumIdeal));
            }
        }

        TextView lblCurrProgress = findViewById(R.id.lblCurrentProgress);
        TextView lblGoalMark = findViewById(R.id.lblGoalMark);
        lblCurrProgress.setText(getResources().getString(R.string.current_mark, String.format("%.0f",accumCurr*100.0f)));
        lblGoalMark.setText(getResources().getString(R.string.goal_mark,String.format("%.0f",accumIdeal*100.0f)));
        LineDataSet ds = new LineDataSet(currProgressData,"current");
        LineDataSet dx = new LineDataSet(goalMarkData, "ideal");
        dx.setColor(Color.RED);
        ds.setColor(Color.BLUE);
        dx.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        ds.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        ds.setDrawValues(false);
        dx.setDrawValues(false);
        lineData.addDataSet(ds);
        lineData.addDataSet(dx);
        lineChart.setData(lineData);
        ds.setCubicIntensity(0.15f);
        dx.setCubicIntensity(0.15f);
        lineChart.invalidate();
    }
}