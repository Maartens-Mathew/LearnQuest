package com.example.learnquest.SetWeightings;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learnquest.R;
import com.example.learnquest.Utils.database.SupabaseApi;
import com.example.learnquest.Utils.database.SupabaseClient;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;




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
                Toast.makeText(this, "Clicked on Assessment[" + (index+1) + "]", Toast.LENGTH_LONG).show();
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


//    public void deleteAssessment(Assessment assessment) {
//        SupabaseApi api = SupabaseClient.getClient().create(SupabaseApi.class);
//
//        // Call the delete endpoint with the assessment's ID
//        Call<Void> call = api.deleteAssessment(assessment.getAssessmentID());
//
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(ListViewActivity.this, "Assessment deleted successfully from DB!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(ListViewActivity.this, "Failed to delete assessment from DB: " + response.message(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Toast.makeText(ListViewActivity.this, "Error deleting from DB: " + t.getMessage(), Toast.LENGTH_LONG).show();
//                t.printStackTrace();
//            }
//        });
//    }


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

//    public void onSaveClicked(View view) {//tyhis was workinhhhhh
//        List<Assessment> assessments = AssessmentManager.getInstance().getAssessments();
//
//        if (assessments.isEmpty()) {
//            Toast.makeText(this, "No assessments to save.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        SupabaseApi api = SupabaseClient.getClient().create(SupabaseApi.class);
//
//        for (Assessment assessment : assessments) {
//            Call<Assessment> call = api.addAssessment(assessment);
//
//            call.enqueue(new Callback<Assessment>() {
//                @Override
//                public void onResponse(Call<Assessment> call, Response<Assessment> response) {
//                    if (response.isSuccessful()) {
//
//
//                        Toast.makeText(ListViewActivity.this, "Assessment saved successfully!", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(ListViewActivity.this, "Failed to save assessment: " + response.message(), Toast.LENGTH_LONG).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Assessment> call, Throwable t) {
//                    Toast.makeText(ListViewActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
//                    Log.e("SaveAssessment", "Failure: ", t);
//                }
//            });
//        }
//    }

//    public void onSaveClicked(View view) {
//        // Create a test assessment
//        Assessment testAssessment = new Assessment("Test Assessment", "2024-08-29", 20.0f);
//
//        SupabaseApi api = SupabaseClient.getClient().create(SupabaseApi.class);
//        Call<Void> call = api.addAssessment(testAssessment);
//
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(ListViewActivity.this, "Assessment saved successfully!", Toast.LENGTH_SHORT).show();
//                } else {
//                    try {
//                        String errorBody = response.errorBody().string();
//                        Log.e("SaveAssessment", "Error body: " + errorBody);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Toast.makeText(ListViewActivity.this, "Failed to save assessment: " + response.message(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Toast.makeText(ListViewActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
//                t.printStackTrace();
//            }
//        });
//    }


//    public void onDeleteAllClicked(View view) {         //deletes based on filter. WORKIGN
//        // Specify the groupID you want to delete
//        int groupIDToDelete = 1;
//
//        // Create the API client
//        SupabaseApi api = SupabaseClient.getClient().create(SupabaseApi.class);
//
//        // Log the groupID being used for deletion
//        Log.d("DeleteAssessments", "Attempting to delete all assessments for groupID: " + groupIDToDelete);
//
//        // Make the API call to delete records with the correct filter syntax
//        Call<Void> deleteCall = api.deleteAssessmentsByGroupID("eq." + groupIDToDelete);
//
//        deleteCall.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                Log.d("DeleteAssessments", "Delete response code: " + response.code());
//                if (response.isSuccessful()) {
//                    Log.d("DeleteAssessments", "Successfully deleted assessments.");
//                    Toast.makeText(ListViewActivity.this, "All assessments for groupID " + groupIDToDelete + " deleted.", Toast.LENGTH_LONG).show();
//                } else {
//                    Log.e("DeleteAssessments", "Failed to delete assessments: " + response.message());
//                    try {
//                        String errorBody = response.errorBody().string();
//                        Log.e("DeleteAssessments", "Error body: " + errorBody);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Toast.makeText(ListViewActivity.this, "Failed to delete assessments: " + response.message(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Log.e("DeleteAssessments", "Error deleting assessments: " + t.getMessage());
//                Toast.makeText(ListViewActivity.this, "Error deleting assessments: " + t.getMessage(), Toast.LENGTH_LONG).show();
//                t.printStackTrace();
//            }
//        });
//    }
public void onSaveClicked(View view) {
    int groupIDToDelete = 1; // Update this as needed

    SupabaseApi api = SupabaseClient.getClient().create(SupabaseApi.class);

    // Step 1: Delete existing records for the specified group ID
    Call<Void> deleteCall = api.deleteAssessmentsByGroupID("eq." + groupIDToDelete);

    deleteCall.enqueue(new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            Log.d("DeleteAssessments", "Delete response code: " + response.code());
            if (response.isSuccessful()) {
                Log.d("DeleteAssessments", "Successfully deleted assessments.");
                Toast.makeText(ListViewActivity.this, "All assessments for groupID " + groupIDToDelete + " deleted.", Toast.LENGTH_LONG).show();

                // Step 2: After successful deletion, proceed to save new assessments
                saveAssessments(api);

            } else {
                Log.e("DeleteAssessments", "Failed to delete assessments: " + response.message());
                try {
                    String errorBody = response.errorBody().string();
                    Log.e("DeleteAssessments", "Error body: " + errorBody);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(ListViewActivity.this, "Failed to delete assessments: " + response.message(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            Log.e("DeleteAssessments", "Error deleting assessments: " + t.getMessage());
            Toast.makeText(ListViewActivity.this, "Error deleting assessments: " + t.getMessage(), Toast.LENGTH_LONG).show();
            t.printStackTrace();
        }
    });
}

    private void saveAssessments(SupabaseApi api) {
        List<Assessment> assessments = AssessmentManager.getInstance().getAssessments();

        if (assessments.isEmpty()) {
            Toast.makeText(this, "No assessments to save.", Toast.LENGTH_SHORT).show();
            return;
        }

        for (Assessment assessment : assessments) {
            Call<Assessment> call = api.addAssessment(assessment);

            call.enqueue(new Callback<Assessment>() {
                @Override
                public void onResponse(Call<Assessment> call, Response<Assessment> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ListViewActivity.this, "Assessment saved successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ListViewActivity.this, "Failed to save assessment: " + response.message(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Assessment> call, Throwable t) {
               //     Toast.makeText(ListViewActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("SaveAssessment", "Failure: ", t);
                }
            });
        }
    }



    }
