package com.example.learnquest.QuizBank;

import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;
import com.example.learnquest.Utils.database.SupabaseApi;
import com.example.learnquest.Utils.database.SupabaseClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class quizBankHome extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private QuizExpandableListAdapter expandableListAdapter;
    private List<String> quizQuestions;
    private HashMap<String, QuizEntryWithTags> quizData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_bank);

        // For dummy testing purposes, this should be set when a group is selected in the dashboard.
        App.groupID = 1;

        expandableListView = findViewById(R.id.expandableListView);

        // Initialize data structures
        quizQuestions = new ArrayList<>();
        quizData = new HashMap<>();

        // Fetch data and populate ExpandableListView
        fetchDataAndPopulateList();
    }

    private void fetchDataAndPopulateList() {
        SupabaseApi api = SupabaseClient.getClient().create(SupabaseApi.class);

        // Fetch QuizEntries
        Call<List<QuizEntry>> quizEntryCall = api.getAllQuizEntries();
        quizEntryCall.enqueue(new Callback<List<QuizEntry>>() {
            @Override
            public void onResponse(Call<List<QuizEntry>> call, Response<List<QuizEntry>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<QuizEntry> quizEntries = response.body();

                    // Fetch Tags
                    Call<List<Tag>> tagCall = api.getAllTags();
                    tagCall.enqueue(new Callback<List<Tag>>() {
                        @Override
                        public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                List<Tag> tags = response.body();
                                combineData(quizEntries, tags);
                                populateExpandableListView();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Tag>> call, Throwable t) {
                            Log.e("FetchTags", "Error fetching tags: " + t.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<QuizEntry>> call, Throwable t) {
                Log.e("FetchQuizEntries", "Error fetching quiz entries: " + t.getMessage());
            }
        });
    }

    // Combine QuizEntry and Tag data
    private void combineData(List<QuizEntry> quizEntries, List<Tag> tags) {
        for (QuizEntry quizEntry : quizEntries) {
            List<String> associatedTags = new ArrayList<>();
            for (Tag tag : tags) {
                if (tag.getGroupID() == quizEntry.getGroupID()) {
                    associatedTags.add(tag.getTagName());
                }
            }

            QuizEntryWithTags quizEntryWithTags = new QuizEntryWithTags(
                    quizEntry.getQuizEntryID(),
                    quizEntry.getQuestion(),
                    quizEntry.getAnswer(),
                    quizEntry.getDescription(),
                    quizEntry.getGroupID(),
                    associatedTags
            );

            quizQuestions.add(quizEntry.getQuestion());
            quizData.put(quizEntry.getQuestion(), quizEntryWithTags);
        }
    }

    // Populate the ExpandableListView with combined data
    private void populateExpandableListView() {
        expandableListAdapter = new QuizExpandableListAdapter(quizQuestions, quizData);
        expandableListView.setAdapter(expandableListAdapter);
    }
}
