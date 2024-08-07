package com.example.learnquest;

import android.graphics.Color;
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
import java.util.Random;

public class ViewProgessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_progess);
        try{
            LineChart lineChart = findViewById(R.id.lineChart);
            lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            LineData lineData = new LineData();
            int size = 5;
            Data data = new Data(0f,0f,0f);
            data.setValues();
            Data[] values = data.values;
            float[] idealMarks = new float[size];
            float[] weights = new float[size];
            float[] marksObtained = new float[size];
            //I don't know why but must have List<Entry> pointer
            List<Entry> currProgressData = new ArrayList<>(11);
            List<Entry> goalMarkData = new ArrayList<>(11);
            for (int i = 0; i < values.length; i++) {
                Entry markDesired = new Entry((float)i,values[i].markDesired);
                if (i < 3){
                    Entry actualMark = new Entry((float)i,values[i].markObtained);
                    currProgressData.add(actualMark);
                }
                else{
                    currProgressData.add(markDesired);
                }
                idealMarks[i] = values[i].markDesired;
                marksObtained[i] = values[i].markObtained;
                weights[i] = values[i].weight;
                goalMarkData.add(markDesired);
            }
            float currProg = calcCurrentProgress(idealMarks,weights,marksObtained,3);
            float idealMark = calcIdealMark(idealMarks,weights);
            String currProgOutput = String.format("%.0f",currProg*100.0f);
            String idealMarkOutput = String.format("%.0f",idealMark*100.0f);
            TextView lblCurrProgress = findViewById(R.id.lblCurrentProgress);
            TextView lblGoalMark = findViewById(R.id.lblGoalMark);
            lblCurrProgress.setText(currProgOutput + "%");
            lblGoalMark.setText(idealMarkOutput + "%");
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
            XAxis xAxis = lineChart.getXAxis();
            YAxis yAxisL = lineChart.getAxisLeft();
            YAxis yAxisR = lineChart.getAxisRight();
            yAxisR.setEnabled(false);
            yAxisL.setAxisMinimum(0.0f);
            yAxisL.setAxisMaximum(1.0f);
            xAxis.setAxisMinimum(0.0f);
            xAxis.setAxisMaximum((float)values.length-1);
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

    private class Data{

        private Data[] values;
        private float markDesired, markObtained, weight;

        public Data(float markDesired, float markObtained, float weight) {
            this.markDesired = markDesired;
            this.markObtained = markObtained;
            this.weight = weight;
        }

        public void setValues(){
            values = new Data[5];
            values[0] = new Data(0.7f,0.5f,0.2f*0.3f);
            values[1] = new Data(0.7f,0.65f,0.35f*0.3f);
            values[2] = new Data(0.7f,0.7f,0.35f*0.3f);
            values[3] = new Data(0.7f,0.8f,0.1f*0.3f);
            values[4] = new Data(0.7f,0.75f,0.7f);
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