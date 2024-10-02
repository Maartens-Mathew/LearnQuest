package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ViewProgressActivity extends AppCompatActivity {

    private LineChart lnChrtProgress;
    private List<TrackProgressAssessmentData> entries;
    private float accumProj;
    private float accumDesired;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_progess);
        TextView lblDesiredFinal = findViewById(R.id.avp_desired_final);
        TextView lblProjectedFinal = findViewById(R.id.avp_projected_final);
        Button btnAdjustGoals = findViewById(R.id.avp_adjust_goals);
        lnChrtProgress = findViewById(R.id.avp_progress_chart);
        Thread thread = new Thread(this::getDatabaseData);
        try{
            thread.start();
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        createDataSets();
        lblDesiredFinal.setText(getResources().getString(R.string.desired_final_mark,
                String.format("%.0f",accumDesired)));
        lblProjectedFinal.setText(getResources().getString(R.string.projected_final_mark,
                String.format("%.0f",accumProj)));
        setUpChart();
        GoalLogic.data = entries;
        btnAdjustGoals.setOnClickListener(
                v -> {
                    Intent intent = new Intent(this, GoalsListActivity.class);
                    intent.putExtra("stateType",1);
                    startActivity(intent);
                });
    }

    private void setUpChart(){
        lnChrtProgress.getXAxis().setAxisMinimum(0f);
        lnChrtProgress.getAxisLeft().setAxisMinimum(0f);
        lnChrtProgress.getAxisRight().setAxisMinimum(0f);
        lnChrtProgress.invalidate();
    }

    private void createDataSets(){
        List<Entry> desiredDataEntries = new ArrayList<>();
        List<Entry> currentDataEntries = new ArrayList<>();
        List<Entry> projectedDataEntries = new ArrayList<>();
        accumProj = 0f;
        accumDesired = 0f;
        for (int i = 0; i < entries.size(); i++){
            TrackProgressAssessmentData entry = entries.get(i);
            if (entry.getMark_obtained() != null){
                accumProj += entry.getMark_obtained()*(entry.getWeight()/100);
                currentDataEntries.add(new Entry(i*1f, accumProj));
                accumDesired += entry.getIdeal_mark()*(entry.getWeight()/100);
                desiredDataEntries.add(new Entry(i*1f, accumDesired));
            }
            else{
                if (projectedDataEntries.size() == 0){
                    projectedDataEntries.add(new Entry((i-1)*1f,accumProj));
                }
                accumProj += entry.getIdeal_mark()*(entry.getWeight()/100);
                accumDesired += entry.getIdeal_mark()*(entry.getWeight()/100);
                projectedDataEntries.add(new Entry(i*1f, accumProj));
                desiredDataEntries.add(new Entry(i*1f, accumDesired));
            }
        }
        LineDataSet desiredMarkData = new LineDataSet(desiredDataEntries, "Ideal marks");
        LineDataSet currentMarkData = new LineDataSet(currentDataEntries, "current marks");
        LineDataSet projectMarkData = new LineDataSet(projectedDataEntries, "projected marks");
        desiredMarkData.setColor(getResources().getColor(R.color.purple_200, getResources().newTheme()));
        currentMarkData.setColor(getResources().getColor(R.color.teal_200, getResources().newTheme()));
        projectMarkData.setColor(getResources().getColor(R.color.red, getTheme().getResources().newTheme()));
        currentMarkData.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        desiredMarkData.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        projectMarkData.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        currentMarkData.setCubicIntensity(0.15f);
        desiredMarkData.setCubicIntensity(0.15f);
        projectMarkData.setCubicIntensity(0.15f);
        projectMarkData.enableDashedLine(10f,5f,0f);
        lnChrtProgress.setData(new LineData(desiredMarkData, currentMarkData, projectMarkData));
    }

    private void getDatabaseData(){
        //ensure we have the correct userID and groupID
        //use userID & groupId
        Call<List<TrackProgressAssessmentData>> call = App.api.get_assessment_data(0, 1);
        Response<List<TrackProgressAssessmentData>> response = null;
        try{
            response = call.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if (response.isSuccessful()){
            entries = Collections.synchronizedList(response.body());
            entries.sort(comparator);
        }
        else{
            try{
                Log.e("TrackProgressFragment",response.errorBody().string());
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private final Comparator<TrackProgressAssessmentData> comparator = new Comparator<TrackProgressAssessmentData>() {
        @Override
        public int compare(TrackProgressAssessmentData o1, TrackProgressAssessmentData o2) {
            java.util.Date d1 = o1.getDate_due();
            java.util.Date d2 = o2.getDate_due();
            return d1.compareTo(d2);
        }
    };
}