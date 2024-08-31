package com.example.learnquest.QuizBank;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.learnquest.R;
import com.example.learnquest.Utils.database.SupabaseApi;
import com.example.learnquest.Utils.database.SupabaseClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void pressOnClick(View view) {
        // Create an instance of the Supabase API interface
        SupabaseApi api = SupabaseClient.getClient().create(SupabaseApi.class);

        // Fetch all TaggedQuizEntry records to get the tag IDs
        Call<List<TaggedQuizEntry>> taggedQuizEntryCall = api.getAllTaggedQuizEntries();
        taggedQuizEntryCall.enqueue(new Callback<List<TaggedQuizEntry>>() {
            @Override
            public void onResponse(Call<List<TaggedQuizEntry>> call, Response<List<TaggedQuizEntry>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TaggedQuizEntry> taggedEntries = response.body();
                    List<Integer> tagIDs = new ArrayList<>();

                    // Extract tag IDs from the TaggedQuizEntry entries
                    for (TaggedQuizEntry entry : taggedEntries) {
                        tagIDs.add(entry.getTagID());
                    }

                    // Fetch all tags to map IDs to names
                    Call<List<Tag>> tagCall = api.getAllTags();
                    tagCall.enqueue(new Callback<List<Tag>>() {
                        @Override
                        public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                List<Tag> tags = response.body();
                                // Map tag IDs to tag names
                                Map<Integer, String> tagIDToNameMap = new HashMap<>();
                                for (Tag tag : tags) {
                                    tagIDToNameMap.put(tag.getTagID(), tag.getTagName());
                                }

                                // Prepare a string to display the tag IDs and names
                                StringBuilder tagIDsString = new StringBuilder("Tag IDs and Names: ");
                                for (Integer id : tagIDs) {
                                    String tagName = tagIDToNameMap.get(id);
                                    if (tagName != null) {
                                        tagIDsString.append(id).append(" (").append(tagName).append("), ");
                                    } else {
                                        tagIDsString.append(id).append(" (Unknown Tag), ");
                                    }
                                }

                                // Remove the trailing comma and space from the string
                                if (tagIDsString.length() > 0) {
                                    tagIDsString.setLength(tagIDsString.length() - 2);
                                }

                                // Find the TextView and update it with the tag IDs and names string
                                TextView txtShow = findViewById(R.id.txtShow);
                                txtShow.setText(tagIDsString.toString());
                            } else {
                                Log.e("FetchTags", "Error fetching tags: Response is empty or unsuccessful");
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Tag>> call, Throwable t) {
                            Log.e("FetchTags", "Error fetching tags: " + t.getMessage());
                        }
                    });
                } else {
                    Log.e("FetchTaggedEntries", "Error fetching tagged quiz entries: Response is empty or unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<List<TaggedQuizEntry>> call, Throwable t) {
                Log.e("FetchTaggedEntries", "Error fetching tagged quiz entries: " + t.getMessage());
            }
        });
    }

}
