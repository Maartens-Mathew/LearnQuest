package com.example.learnquest.SetWeightings;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learnquest.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {
    private ArrayAdapter<Assessment> adapter;
    private int selectedIndex = -1;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        // Initialize the adapter with assessments from AssessmentManager
        List<Assessment> assessments = AssessmentManager.getInstance().getAssessments();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, assessments);

        ListView lstAssessments = findViewById(R.id.lstAssessments);
        lstAssessments.setAdapter(adapter);

        lstAssessments.setOnItemClickListener((adapterView, view, index, id) -> {
            if (index >= 0) {
                selectedIndex = index;
                Toast.makeText(this, "Clicked on Assessment[" + index + "]", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Clicked, but no assessment selected.", Toast.LENGTH_LONG).show();
            }
        });

        pieChart = findViewById(R.id.pieChart);
        updatePieChart();
    }

    public void onAddClicked(View view) {
        showAddAssessmentDialog();


    }





    public void onDeleteClicked(View view) {
        if (selectedIndex >= 0) {
            AssessmentManager.getInstance().removeAssessment(selectedIndex);
            adapter.notifyDataSetChanged();
            updatePieChart();
            Toast.makeText(this, "Deleted assessment.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "No assessment selected.", Toast.LENGTH_LONG).show();
        }
        selectedIndex = -1;
    }

    private void showAddAssessmentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_assessment, null);
        builder.setView(dialogView);

        EditText etName = dialogView.findViewById(R.id.etName);
        EditText etWeighting = dialogView.findViewById(R.id.etWeighting);
        EditText etDate = dialogView.findViewById(R.id.etDate);

        etDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year1, month1, dayOfMonth) -> etDate.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1),
                    year, month, day);
            datePickerDialog.show();
        });

        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = etName.getText().toString();
            String date = etDate.getText().toString();
            int weighting;

            try {
                weighting = Integer.parseInt(etWeighting.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid weighting.", Toast.LENGTH_LONG).show();
                return;
            }

            if (getTotalWeighting() + weighting > 100) {
                Toast.makeText(this, "Total weighting exceeds 100.", Toast.LENGTH_LONG).show();
            } else {
                Assessment assessment = new Assessment(name, date, weighting);
                AssessmentManager.getInstance().addAssessment(assessment);
                adapter.notifyDataSetChanged();
                updatePieChart();
                Toast.makeText(this, "Added assessment.", Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private int getTotalWeighting() {
        int total = 0;
        for (Assessment assessment : AssessmentManager.getInstance().getAssessments()) {
            total += assessment.getWeighting();
        }
        return total;
    }

    private void updatePieChart() {
        List<PieEntry> entries = new ArrayList<>();
        for (Assessment assessment : AssessmentManager.getInstance().getAssessments()) {
            entries.add(new PieEntry(assessment.getWeighting(), assessment.getName()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Assessments");

        // Generate random colors for each entry
        List<Integer> colors = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            colors.add(getRandomColor());
        }
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate();
    }

    // Method to generate a random color
    private int getRandomColor() {
        // Generate random RGB values
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        return (0xff << 24) | (r << 16) | (g << 8) | b; // ARGB format
    }

    public void onSaveClicked(View view) {
        // Implement your save logic here (e.g., save assessments to a database or file)
        Toast.makeText(this, "Save functionality not implemented yet.", Toast.LENGTH_SHORT).show();
    }
}
