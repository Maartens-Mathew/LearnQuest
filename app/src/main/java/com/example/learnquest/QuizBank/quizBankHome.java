package com.example.learnquest.QuizBank;

import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

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

public class quizBankHome extends AppCompatActivity {

    public List<QuizEntry> entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_bank);
        entries = new ArrayList<>();

        //For dummy testing purposes, this should be set when a group is selected in the dashboard.
        App.groupID = 1;
        App.setApplicationContext(getApplicationContext());


        Thread thread = new Thread(this::GetQuizQuestions);


        try {
            thread.start();
            thread.join();
        }catch(InterruptedException e){
            System.out.println("Thread was interrupted.");
        }

        ExpandableListView expandableListView = findViewById(R.id.elvQuizEntries);
        QuizExpandableListAdapter quizAdapter = new QuizExpandableListAdapter(entries);
        expandableListView.setAdapter(quizAdapter);
        // Test deleting quizEntryID = 4
       // deleteQuizEntry((short) 4);


    }

    void deleteQuizEntry(Short quizEntryID) {
        // Convert the ID to the correct filter format
        String filterParam = "eq." + quizEntryID;
        Call<Void> deleteCall = App.api.deleteQuizEntry(filterParam);

        deleteCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i("Custom", "Entry deleted successfully");
                    // Remove entry from the local map and update the UI
                    quizEntryMap.remove(quizEntryID);
                    updateUI();
                } else {
                    // Log the error details
                    Log.e("Custom", "Failed to delete entry: HTTP " + response.code() + " " + response.message());
                    try {
                        if (response.errorBody() != null) {
                            Log.e("Custom", "Error body: " + response.errorBody().string());
                        }
                    } catch (IOException e) {
                        Log.e("Custom", "Error reading error body: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Custom", "Error deleting entry: " + t.getMessage());
                t.printStackTrace();  // Log the stack trace to help identify the issue
            }
        });
    }

    private void GetQuizQuestions()  {

        Call<List<QuizEntry>> call = App.api.getQuizEntries4(1);
        Response<List<QuizEntry>> response = null;

        try{
            response = call.execute();
        }catch(IOException e){
            e.printStackTrace();
            return;
        }

        if (response.isSuccessful())
        {
            entries = Collections.synchronizedList(response.body());
            Log.i("Custom",entries.toString());
        }
        else{
            Log.i("Custom", "Could not parse");
            try {
                Log.e("Custom", response.errorBody().string());
            }catch(IOException ignored){}
        }

    }
// Update the UI after deleting
    private void updateUI() {
        QuizExpandableListAdapter quizAdapter = new QuizExpandableListAdapter(new ArrayList<>(quizEntryMap.values()));
        ExpandableListView expandableListView = findViewById(R.id.elvQuizEntries);
        expandableListView.setAdapter(quizAdapter);
    }


}
