package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.addValidation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.AppState.App;
import com.example.learnquest.QuizBank.QuizEntry;
import com.example.learnquest.QuizBank.Tag;
import com.example.learnquest.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.AppState.App;
import com.example.learnquest.ExternalTools.ExpandableRecyclerView.models.ExpandableGroup;
import com.example.learnquest.R;
import com.example.learnquest.Utils.database.DatabaseRunnable;
import com.example.learnquest.Utils.database.GetQuizEntriesRequest;
import com.example.learnquest.Utils.database.QuizCallback;
import com.example.learnquest.Utils.database.SupabaseApi;
import com.example.learnquest.Utils.database.SupabaseClient;
import com.example.learnquest.model.wrappers.TaggedQuiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class quizValidHome extends AppCompatActivity {
    private RecyclerView recyclerView;
    private QuizEntryAdapter adapter;
    private Spinner spinnerTags;
    private List<QuizEntry> entries; // Data for RecyclerView
    private QuizRecyclerViewAdapter recyclerAdapter; // Adapter for RecyclerView
    private int selectedGroupId = 1; // Default selected group ID
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

        App.groupID =1;

        // Fetch the quiz entries for a group (replace with the actual groupID)
        //fetchQuizQuestions(App.groupID);

        // Fetch tags by group ID (for Spinner)

        // Trigger quiz question fetching based on selected groupID in Spinner

        Runnable runnable = () ->{

            fetchQuizQuestions(App.groupID);
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