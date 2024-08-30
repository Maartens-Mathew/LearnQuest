package com.example.learnquest.QuizBank;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.learnquest.R;
import com.example.learnquest.Utils.database.SupabaseApi;
import com.example.learnquest.Utils.database.SupabaseClient;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class quizBankHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_bank);

        // Fetch data and populate RecyclerView
        fetchDataAndPopulateRecyclerView();
    }

    // Fetch data from Supabase and populate RecyclerView
    private void fetchDataAndPopulateRecyclerView() {
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
                                List<QuizEntryWithTags> combinedList = combineData(quizEntries, tags);
                                populateRecyclerView(combinedList);
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


    private List<QuizEntryWithTags> combineData(List<QuizEntry> quizEntries, List<Tag> tags) {
        List<QuizEntryWithTags> combinedList = new ArrayList<>();

        for (QuizEntry quizEntry : quizEntries) {
            List<String> associatedTags = new ArrayList<>();
            for (Tag tag : tags) {
                if (tag.getGroupID() == quizEntry.getGroupID()) {
                    associatedTags.add(tag.getTagName());
                }
            }
            combinedList.add(new QuizEntryWithTags(
                    quizEntry.getQuizEntryID(),
                    quizEntry.getQuestion(),
                    quizEntry.getAnswer(),
                    quizEntry.getDescription(),
                    quizEntry.getGroupID(),
                    associatedTags
            ));
        }
        return combinedList;
    }


    // Populate the RecyclerView with combined data
    private void populateRecyclerView(List<QuizEntryWithTags> quizEntries) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        QuizAdapter adapter = new QuizAdapter(quizEntries);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
