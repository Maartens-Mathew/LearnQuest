package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Assessments.ManageAssessments;

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

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;
import com.example.learnquest.model.assessment.Assessment;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListViewActivity extends AppCompatActivity {
    private ArrayAdapter<Assessment> adapter;
    private int selectedIndex = -1;
    private List<Assessment> assessments;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        assessments = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, assessments);

        ListView lstAssessments = findViewById(R.id.lstAssessments);
        lstAssessments.setAdapter(adapter);

        lstAssessments.setOnItemClickListener((adapterView, view, index, id) -> {
            if(index >= 0){
                selectedIndex = index;
                Toast.makeText(this, "Clicked on Assessment[" + index + "]", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Clicked, but no-one home.", Toast.LENGTH_LONG).show();
            }
        });

        pieChart = findViewById(R.id.pieChart);
    }

    public void onAddClicked(View view) {
        showAddAssessmentDialog();
    }

    public void onDeleteClicked(View view) {
        if(selectedIndex >= 0) {
            assessments.remove(selectedIndex);
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

        etDate.setOnClickListener(v -> showDatePicker(etDate));

        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = etName.getText().toString().trim();
            String dateString = etDate.getText().toString().trim();

            // Validate input
            if (name.isEmpty() || dateString.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_LONG).show();
                return;
            }

            Float weighting;
            try {
                weighting = Float.parseFloat(etWeighting.getText().toString().trim());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Weighting must be a valid number.", Toast.LENGTH_LONG).show();
                return;
            }

            if (getTotalWeighting() + weighting > 100) {
                Toast.makeText(this, "Total weighting exceeds 100.", Toast.LENGTH_LONG).show();
                return;
            }

            Date dueDate = parseDate(dateString);
            if (dueDate == null) {
                Toast.makeText(this, "Invalid date format. Use dd/MM/yyyy.", Toast.LENGTH_LONG).show();
                return;
            }

            Assessment assessment = new Assessment(dueDate, App.group.getGroupID(), name, weighting);
            assessments.add(assessment);
            adapter.notifyDataSetChanged();
            updatePieChart();
            Toast.makeText(this, "Added assessment.", Toast.LENGTH_LONG).show();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void showDatePicker(EditText etDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, dayOfMonth) -> etDate.setText(dayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear),
                year, month, day);
        datePickerDialog.show();
    }

    private Date parseDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            return null; // Return null for invalid date formats
        }
    }

    private int getTotalWeighting() {
        int total = 0;
        for (Assessment assessment : assessments) {
            total += assessment.getWeighting();
        }
        return total;
    }

    private void updatePieChart() {
        List<PieEntry> entries = new ArrayList<>();
        for (Assessment assessment : assessments) {
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

}
