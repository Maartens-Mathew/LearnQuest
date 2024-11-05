package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.addValidation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.AppState.App;
import com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.QuizBank.QuizEntry;
import com.example.learnquest.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class quizValidHome extends AppCompatActivity {
    private RecyclerView recyclerView;
    private QuizEntryAdapter adapter;
    private Spinner spinnerTags;
    private List<QuizEntry> entries; // Data for RecyclerView
    private QuizRecyclerViewAdapter recyclerAdapter; // Adapter for RecyclerView
    private int selectedGroupId = 1; // Default selected group ID



    private static final int REQUEST_CODE_EDIT = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.validate_quiz);
        entries = new ArrayList<>();
        // Initialize RecyclerView
        // recyclerView = findViewById(R.id.recycler_view_quiz_entries);
        setupRecyclerView();

        // Check if recyclerView is null
        if (recyclerView == null) {
            Log.e("Initialization Error", "RecyclerView is null. Please check your layout file.");
        //    Toast.makeText(this,"RecyclerView is null. Please check your layout file", Toast.LENGTH_LONG).show();
            return;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        App.setApplicationContext(getApplicationContext());

        App.group.setGroupID(1);

        // Fetch the quiz entries for a group (replace with the actual groupID)
        //fetchQuizQuestions(App.groupID);

        // Fetch tags by group ID (for Spinner)

        // Trigger quiz question fetching based on selected groupID in Spinner

        Runnable runnable = () ->{

            fetchQuizQuestions(App.group.getGroupID());
        };

        Thread thread = new Thread(runnable);


        try{
            thread.start();
            thread.join();
        }catch(InterruptedException ignored){};


    }
    // Setup RecyclerView
    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_quiz_entries);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new QuizRecyclerViewAdapter(entries);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK && data != null) {
            QuizEntry updatedEntry = (QuizEntry) data.getSerializableExtra("quizEntry");
            if (updatedEntry != null) {
                updateEntryInList(updatedEntry);
            } else {
                Toast.makeText(this, "No data received", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateEntryInList(QuizEntry updatedEntry) {
        for (int i = 0; i < entries.size(); i++) {
            Log.d("Debug", "Comparing IDs: " + entries.get(i).getQuizEntryID() + " and " + updatedEntry.getQuizEntryID());
            if (entries.get(i).getQuizEntryID().equals(updatedEntry.getQuizEntryID()) ) {
                entries.set(i, updatedEntry);
                recyclerAdapter.notifyItemChanged(i);
                Toast.makeText(quizValidHome.this, "WE FOUND ONE " + i, Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Log.d("Debug", "No matching quizEntryID found");
    }



    private void fetchQuizQuestions(Integer groupId) {
        Call<List<QuizEntry>> call = App.api.getWaitingQuizEntries(groupId);
        Response<List<QuizEntry>> response = null;


        try {
            response = call.execute(); // Synchronous call
        } catch (IOException e) {
            Log.e("API Error", "Error: " + e.getMessage());
            Toast.makeText(quizValidHome.this, "Error loading quiz entries", Toast.LENGTH_SHORT).show();
        }

// Process the response outside of the try-catch block
        if (response != null && response.isSuccessful() && response.body() != null) {
            entries.clear();
            entries.addAll(response.body());
           recyclerAdapter.notifyDataSetChanged();
        } else {
            if (response != null) {
                Log.e("API Error", "Response Code: " + response.code() + ", Message: " + response.message());
                Toast.makeText(quizValidHome.this, "Failed to load quiz entries", Toast.LENGTH_SHORT).show();

                // Handle null body scenario
                if (response.body() == null) {
                    entries.clear(); // Optionally clear the list if needed
                } else {
                    entries.addAll(response.body()); // Only add if response.body() is not null
                }
                recyclerAdapter.notifyDataSetChanged();
            }
        }

    }




}