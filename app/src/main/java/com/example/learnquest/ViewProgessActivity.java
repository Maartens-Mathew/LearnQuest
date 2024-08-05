package com.example.learnquest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;

public class ViewProgessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_progess);
        TextView tx = findViewById(R.id.lblGoalMark);
        tx.setOnClickListener(view ->{
            Database ds = new Database();
        });
//        try{
//            LineChart lineChart = findViewById(R.id.lineChart);
//            lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//            LineData lineData = new LineData();
//            Random random = new Random();
//            int size = 5;
//            float[] idealMarks = new float[size];
//            float[] weights = new float[size];
//            float[] marksObtained = new float[size];
//            //I don't know why but must have List<Entry> pointer
//            List<Entry> currProgressData = new ArrayList<>(30);
//            List<Entry> goalMarkData = new ArrayList<>(11);
//            float remWeight = 1.0f;
//            float weightToInsert;
//            for (int i = 0; i < size; i++) {
//                idealMarks[i] = getValueInclusive(random);
//                marksObtained[i] = getValueInclusive(random);
//                do{
//                    weightToInsert = getWeight(random);
//                } while (remWeight - weightToInsert < 0.0f);
//                weights[i] = weightToInsert;
//                Entry e = new Entry();
//                e.setX((float)i);
//                if (i <= 2){
//                    e.setY(marksObtained[i]);
//                }
//                else{
//                    e.setY(idealMarks[i]);
//                }
//                Entry e1 = new Entry((float)i, idealMarks[i]);
//                Log.i("MainActivity",e.toString());
//                Log.i("MainActivity","e1: "+e1.toString());
//                currProgressData.add(e);
//                goalMarkData.add(e1);
//            }
//            float currProg = calcCurrentProgress(idealMarks,weights,marksObtained,3);
//            float idealMark = calcIdealMark(idealMarks,weights);
//            String currProgOutput = String.format("%.0f",currProg*100.0f);
//            String idealMarkOutput = String.format("%.0f",idealMark*100.0f);
//            TextView lblCurrProgress = findViewById(R.id.lblCurrentProgress);
//            TextView lblGoalMark = findViewById(R.id.lblGoalMark);
//            lblCurrProgress.setText(currProgOutput + "%");
//            lblGoalMark.setText(idealMarkOutput + "%");
//            LineDataSet ds = new LineDataSet(currProgressData,"current");
//            LineDataSet dx = new LineDataSet(goalMarkData, "ideal");
//            ds.setColor(Color.RED);
//            dx.setColor(Color.BLUE);
//            dx.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//            ds.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//            ds.setDrawValues(false);
//            dx.setDrawValues(false);
//            lineData.addDataSet(ds);
//            lineData.addDataSet(dx);
//            lineChart.setData(lineData);
//            lineChart.invalidate();
//        }
//        catch (Exception e){
//            if (e.getMessage() != null){
//                Log.e("MainActivity", e.getMessage());
//            }
//            for (StackTraceElement element : e.getStackTrace()){
//                Log.e("MainActivity",e.toString());
//            }
//        }
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


    private float[] w = {0.1f,0.1f,0.2f,0.3f,0.3f};

    private float getWeight(Random r){
        return w[r.nextInt(w.length)];
    }

    private float getValueInclusive(Random r){
        float result = r.nextFloat();
        if (1.0-result < 0.00001f){
            result = 1.0f;
        }
        return result;
    }
}