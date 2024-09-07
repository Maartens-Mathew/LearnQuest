package com.example.learnquest.QuizBank;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;
import com.example.learnquest.Utils.database.GetQuizEntriesRequest;
import com.example.learnquest.Utils.database.SupabaseApi;
import com.example.learnquest.Utils.database.SupabaseClient;
import com.example.learnquest.model.wrappers.TaggedQuiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class quizBankHome extends AppCompatActivity {

    Map<Integer, QuizEntry> quizEntryMap = new ConcurrentHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_bank);

        //For dummy testing purposes, this should be set when a group is selected in the dashboard.
        App.groupID = 1;

        Thread thread = new Thread(this::GetQuizQuestions);

        try {
            thread.join();
        }catch(InterruptedException e){
            System.out.println("Thread was interrupted.");
        }

        populateRecyclerView();
    }


    private void GetQuizQuestions(){
        Call<List<TaggedQuiz>> quizCall = App.api.getQuizEntries(new GetQuizEntriesRequest((short) App.groupID));

        Response<List<TaggedQuiz>> quizResponse = null;

        try{
            quizResponse = quizCall.execute();
        }catch (IOException e){
            //If database did not connect (it should)
            Log.e("Database error", "Could not connect");
        }

        if (quizResponse.isSuccessful()) {
            List<TaggedQuiz> entries = quizResponse.body();
            entries.forEach(this::addToList);
            Log.i("Success", entries.toString());
        }else
            Log.e("Database error", "Something went wrong.");


    }


    private void addToList(TaggedQuiz taggedQuiz){
        QuizEntry entry = quizEntryMap.get(taggedQuiz.getQuizEntryID());
        if (entry == null)
            entry = new QuizEntry(taggedQuiz.getQuizEntryID(), taggedQuiz.getQuestion(), taggedQuiz.getAnswer(), taggedQuiz.getDescription());

        Tag tag = new Tag(taggedQuiz.getTagID(), taggedQuiz.getTagName());
        entry.addTag(tag);

    }

    // Fetch data from Supabase and populate RecyclerView

    // Populate the RecyclerView with combined data
    private void populateRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        QuizAdapter adapter = new QuizAdapter(new ArrayList<>(quizEntryMap.values()));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
