package com.example.learnquest;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class ViewProgressActivity extends AppCompatActivity {

    private LineChart lineChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_progess);
        try{
            setUpChart();
            LineData lineData = new LineData();
            int size = 5;
            DummyData dummyData = new DummyData();
            DummyData[] values = dummyData.values;
            float[] idealMarks = new float[size];
            float[] weights = new float[size];
            float[] marksObtained = new float[size];
            float accumIdeal = 0f;
            float accumCurr = 0f;
            List<Entry> currProgressData = new ArrayList<>(11);
            List<Entry> goalMarkData = new ArrayList<>(11);
            for (int i = 0; i < values.length; i++) {
                accumIdeal += values[i].markDesired*values[i].weight;
                Entry markDesired = new Entry((float)i,accumIdeal);
                if (i < 3){
                    accumCurr += values[i].markObtained*values[i].weight;
                    Entry actualMark = new Entry((float)i,accumCurr);
                    currProgressData.add(actualMark);
                }
                else{
                    accumCurr += values[i].markDesired*values[i].weight;
                    Entry currProg = new Entry((float)i,accumCurr);
                    currProgressData.add(currProg);
                }
                idealMarks[i] = values[i].markDesired;
                marksObtained[i] = values[i].markObtained;
                weights[i] = values[i].weight;
                goalMarkData.add(markDesired);
            }
            TextView lblCurrProgress = findViewById(R.id.lblCurrentProgress);
            TextView lblGoalMark = findViewById(R.id.lblGoalMark);
            lblCurrProgress.setText(getResources().getString(R.string.current_mark, String.format("%.0f",accumCurr*100.0f)));
            lblGoalMark.setText(getResources().getString(R.string.goal_mark,String.format("%.0f",accumIdeal*100.0f)));
            LineDataSet ds = new LineDataSet(currProgressData,"current");
            LineDataSet dx = new LineDataSet(goalMarkData, "ideal");
            dx.setColor(R.color.teal_200);
            ds.setColor(R.color.purple_500);
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
        catch (Exception e){
            if (e.getMessage() != null){
                Log.e("MainActivity", e.getMessage());
            }
            for (StackTraceElement element : e.getStackTrace()){
                Log.e("MainActivity",e.toString());
            }
        }
    }

    private void setUpChart(){
        lineChart = findViewById(R.id.lineChart);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setAxisMinimum(0f);
        lineChart.getAxisLeft().setAxisMinimum(0f);
        lineChart.getAxisRight().setEnabled(false);
    }

    private class DummyData {

        private DummyData[] values;
        private float markDesired, markObtained, weight;

        public DummyData(float markDesired, float markObtained, float weight) {
            this.markDesired = markDesired;
            this.markObtained = markObtained;
            this.weight = weight;
        }

        public DummyData(){
            setValues();
        }

        public void setValues(){
            values = new DummyData[5];
            values[0] = new DummyData(0.7f,0.5f,0.2f*0.3f);
            values[1] = new DummyData(0.7f,0.65f,0.35f*0.3f);
            values[2] = new DummyData(0.7f,0.7f,0.35f*0.3f);
            values[3] = new DummyData(0.7f,0.8f,0.1f*0.3f);
            values[4] = new DummyData(0.7f,0.75f,0.7f);
        }
    }

    public float calcCurrentProgress(float[] idealMark, float[] weights, float[] markObtained, int n){
        float currProgress = 0.0f;
        for (int i = 0; i < n; i++) {
            currProgress += markObtained[i]*weights[i];
        }
        for (int i = n; i < idealMark.length; i++){
            currProgress += idealMark[i]*weights[i];
        }
        return  currProgress;
    }

    public float calcIdealMark(float[] idealMark, float[] weights){
        float totalIdealMark = 0.0f;
        for (int i = 0; i < idealMark.length; i++) {
            totalIdealMark += idealMark[i]*weights[i];
        }
        return totalIdealMark;
    }
}