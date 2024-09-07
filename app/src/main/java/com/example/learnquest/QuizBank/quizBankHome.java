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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class quizBankHome extends AppCompatActivity {

    Map<Short, QuizEntry> quizEntryMap = new ConcurrentHashMap<>();

    public List<TaggedQuiz> entries;


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

            Log.i("Custom",quizEntryMap.toString());
        }catch(InterruptedException e){
            System.out.println("Thread was interrupted.");
        }

        ExpandableListView expandableListView = findViewById(R.id.elvQuizEntries);
        QuizExpandableListAdapter quizAdapter = new QuizExpandableListAdapter(new ArrayList<>(quizEntryMap.values()));
        expandableListView.setAdapter(quizAdapter);






    }


    private void GetQuizQuestions(){
        Call<List<TaggedQuiz>> quizCall = App.api.getQuizEntries(new GetQuizEntriesRequest((short) App.groupID));
        Response<List<TaggedQuiz>> quizResponse = null;

        try{
            quizResponse = quizCall.execute();
        }catch(IOException e){
            System.out.println("Error: " + e.getMessage());

        }

        if (quizResponse.isSuccessful() && quizResponse.body() != null)
        {
            Response<List<TaggedQuiz>> finalQuizResponse = quizResponse;
            finalQuizResponse.body().forEach(this::addToList);
            Log.i("Custom",finalQuizResponse.body().toString());
        }
        else{
            Log.e("Custom", "Some error (don't know)");
        }

    }


    public void addToList(TaggedQuiz taggedQuiz){
       // Log.i("Custom",taggedQuiz.toString());
        QuizEntry entry = quizEntryMap.get(taggedQuiz.getQuizEntryID());

        if (entry == null)
            entry = new QuizEntry(taggedQuiz.getQuizEntryID(), taggedQuiz.getQuestion(), taggedQuiz.getAnswer(), taggedQuiz.getDescription());


       // Log.i("Custom",entry.toString());
        Tag tag = new Tag(taggedQuiz.getTagID(), taggedQuiz.getTagName());
        entry.addTag(tag);

        quizEntryMap.put(entry.getQuizEntryID(), entry);
      //  Log.i("Custom",quizEntryMap.toString());





    }

    // Fetch data from Supabase and populate RecyclerView


}
