package com.example.learnquest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
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
            LineData lineData = new LineData();
            Random random = new Random();
            //I don't know why but must have List<Entry> pointer
            List<Entry> entries = new ArrayList<>(30);
            ArrayList<String> strings = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Entry e = new Entry((float)i,random.nextFloat());
                strings.add(i,"r");
                Log.i("MainActivity",strings.get(i));
                Log.i("MainActivity",e.toString());
                entries.add(e);
            }
            LineDataSet ds = new LineDataSet(entries,"random Data");
            ds.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            lineData.addDataSet(ds);
            lineChart.setData(lineData);
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
}