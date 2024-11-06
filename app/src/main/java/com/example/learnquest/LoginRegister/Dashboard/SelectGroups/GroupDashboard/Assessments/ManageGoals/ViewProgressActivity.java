package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.ManageGoals;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProgressActivity extends AppCompatActivity {

    private LineChart lnChrtProgress;
    private List<TrackProgressAssessmentData> entries;
    private float accumProj;
    private float accumDesired;
    private int stateType;
    private TextView lblDesiredFinal;
    private TextView lblProjectedFinal;
    private Button btnAdjustGoals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_progess);
        lblDesiredFinal = findViewById(R.id.avp_desired_final);
        lblProjectedFinal = findViewById(R.id.avp_projected_final);
        btnAdjustGoals = findViewById(R.id.avp_adjust_goals);
        lnChrtProgress = findViewById(R.id.avp_progress_chart);
        Thread thread = new Thread(this::getDatabaseData);
        thread.start();

//        btnAdjustGoals.setOnClickListener(
//                v -> {
//                    Intent intent = new Intent(this, AdjustGoalsListActivity.class);
//                    intent.putExtra("isViewProgress",true);
//                    startActivity(intent);
//                });
    }

    public void onBtnAdjustGoalsClicked(View v){
        Intent intent = new Intent(this, AdjustGoalsListActivity.class);
        intent.putExtra("isViewProgress",true);
        startActivity(intent);
    }

    private void setUpChart(){
        lnChrtProgress.getXAxis().setAxisMinimum(0f);
        lnChrtProgress.getAxisLeft().setAxisMinimum(0f);
        lnChrtProgress.getAxisRight().setAxisMinimum(0f);
        lnChrtProgress.invalidate();
    }

    public void onViewProgressBtnBackPressed(View v){
        getOnBackPressedDispatcher().onBackPressed();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Thread thread = new Thread(this::getDatabaseData);
        thread.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Thread thread = new Thread(this::getDatabaseData);
        thread.start();
    }

    private void doCode(List<TrackProgressAssessmentData> entries){
        this.entries = entries;
        createDataSets();
        lblDesiredFinal.setText(getResources().getString(R.string.desired_final_mark,
                String.format("%.0f",accumDesired)));
        lblProjectedFinal.setText(getResources().getString(R.string.projected_final_mark,
                String.format("%.0f",accumProj)));
        setUpChart();
        btnAdjustGoals.setOnClickListener(this::onBtnAdjustGoalsClicked);
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
        Call<List<TrackProgressAssessmentData>> call = App.api.getAssessmentData(App.user.getUserID(), App.group.getGroupID());

        call.enqueue(new Callback<List<TrackProgressAssessmentData>>() {
            @Override
            public void onResponse(Call<List<TrackProgressAssessmentData>> call, Response<List<TrackProgressAssessmentData>> response) {
                if (response.isSuccessful()) {
                    // This runs on a background thread, so use runOnUiThread to update the UI
                    List<TrackProgressAssessmentData> entries = Collections.synchronizedList(response.body());
                    entries.sort(comparator);
                    // If you need to update the UI, use runOnUiThread
                    runOnUiThread(() -> {
                        // You can update UI elements here, like notifying an adapter or other UI actions
                        doCode(entries);
                    });
                } else {
                    // Handle error response
                    try {
                        Log.e("ADJUST_GOALS_LIST_ACTIVITY", response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<TrackProgressAssessmentData>> call, Throwable t) {
                // Handle failure (e.g., network errors)
                Log.e("ADJUST_GOALS_LIST_ACTIVITY", "Request failed", t);
            }
        });
//        Call<List<TrackProgressAssessmentData>> call = App.api.getAssessmentData(0, 1);
//        Response<List<TrackProgressAssessmentData>> response = null;
//        try{
//            response = call.execute();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        if (response.isSuccessful()){
//            entries = Collections.synchronizedList(response.body());
//            entries.sort(comparator);
//        }
//        else{
//            try{
//                Log.e("TrackProgressFragment",response.errorBody().string());
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }
//        }
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