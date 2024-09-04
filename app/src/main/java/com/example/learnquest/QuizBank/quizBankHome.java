package com.example.learnquest.QuizBank;

import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;
import com.example.learnquest.Utils.database.SupabaseApi;
import com.example.learnquest.Utils.database.SupabaseClient;
import com.example.learnquest.model.quiz.QuizEntry;
import com.example.learnquest.model.resultWrappers.TaggedQuiz;

import java.io.IOException;  // Added to handle IOException for errorBody
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
      //  fetchDataAndPopulateList();
       // make();

        new Thread(() -> {
            MathewMethod();
        }).start();

        Log.e("Test","Test");
    }

    //Global variables then I can see them
    List<QuizEntry> quizEntries = new ArrayList<>();
    Call<List<TaggedQuiz>> entryCall;
    Response<List<TaggedQuiz>> entryResponse;

    public void MathewMethod(){
        entryCall = App.api.getQuizEntries(new GetQuizEntriesRequest((short)1));
       entryResponse = null;

        try{
            entryResponse = entryCall.execute();
        }catch(IOException e){
            System.out.println("Connection to database not working.");
        }

        if (entryResponse.isSuccessful()) {
            List<QuizEntry> quizEntries = entryResponse.body().stream().map(taggedQuiz -> {
                return new QuizEntry(taggedQuiz.quizEntryID, taggedQuiz.question, taggedQuiz.answer, taggedQuiz.description, App.groupID, taggedQuiz.isValidated);
            }).collect(Collectors.toList());

            this.quizEntries = quizEntries;
          //  buildUI(quizEntries);
        }
        else
            System.out.println("Something went wrong.");

        try {
            Log.i("Info", entryResponse.errorBody().string().toString());
        }catch(Exception ignored){}
    }

    private void make() {
        Call<List<TaggedQuiz>> entryCall = App.api.getQuizEntries(new GetQuizEntriesRequest((short)1));
        List<QuizEntry> quizEntries = null;

        Response<List<TaggedQuiz>> entryResponse = null;
            //Lemme just dropm y code here cool
        try{


            //I have your branch. I'll try bring over the code and get it working from my side. That fine?
            //okay just commit this version of it rn..

            entryResponse = entryCall.execute();
        }catch(IOException e){
            //If something goes wrong in the connection (if connected, won't go here)

            return; //Don't want program to continue
        }

        if (entryResponse.isSuccessful()){
            //Now go from TaggedQuiz to QuizEntry

            //Stream API
            quizEntries = entryResponse.body().stream().map(this::DatabaseObjToEntry).collect(Collectors.toList());

            quizEntries.forEach(System.out::println);

            //Not quite sure yet how to put tags into list. Lol we can figure that out later
            // m,ain thing is jsut connetign to fucntion rn awe
        }else{
            //Error occured (error 400,404, etc)
            try {
                System.out.println(entryResponse.errorBody().string().toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }


        /**Reponse code
         *
         *
         *   if (response.isSuccessful() && response.body() != null) {
         *                     Toast.makeText(quizBankHome.this, "Data fetched successfully", Toast.LENGTH_LONG).show();
         *                     List<QuizEntryWithTags> taggedQuizEntries = response.body();
         *                     processQuizEntries(taggedQuizEntries);
         *                 } else {
         *                     try {
         *                         String errorMessage = "Failed to fetch data. Response code: " + response.code() + " - " + response.message();
         *                         if (response.errorBody() != null) {
         *                             errorMessage += "\nError body: " + response.errorBody().string();
         *                         }
         *                         Toast.makeText(quizBankHome.this, errorMessage, Toast.LENGTH_LONG).show();
         *                         Log.e("Make", errorMessage);
         *                     } catch (IOException e) {
         *                         Toast.makeText(quizBankHome.this, "Error reading error body: " + e.getMessage(), Toast.LENGTH_LONG).show();
         *                         Log.e("Make", "Error reading error body: " + e.getMessage());
         *                     }
         *                 }
         *
         *
         *
         *                 Failure code
         *                                 Toast.makeText(quizBankHome.this, "Error fetching data: " + t.getMessage(), Toast.LENGTH_LONG).show();
         *                 Log.e("Make", "Error fetching data: " + t.getMessage());
         *
         *
         *
         *
         */



    }

    public QuizEntry DatabaseObjToEntry(TaggedQuiz item){
        return new QuizEntry(item.quizEntryID,item.question,item.answer,item.description,App.groupID,item.isValidated);
    }


   /** private void fetchDataAndPopulateList() {
        SupabaseApi api = SupabaseClient.getClient().create(SupabaseApi.class);

        // Create request object with group ID
        GetQuizEntriesRequest request = new GetQuizEntriesRequest((short) App.groupID);

        // Fetch TaggedQuizEntries asynchronously
        Call<List<QuizEntryWithTags>> quizEntryCall = api.getQuizEntries(request);
        quizEntryCall.enqueue(new Callback<List<QuizEntryWithTags>>() {
            @Override
            public void onResponse(Call<List<QuizEntryWithTags>> call, Response<List<QuizEntryWithTags>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<QuizEntryWithTags> taggedQuizEntries = response.body();
                    Log.d("FetchQuizEntries", "Received data: " + taggedQuizEntries.toString());
                    processQuizEntries(taggedQuizEntries);
                } else {
                    try {
                        Log.e("FetchQuizEntries", "Failed to fetch quiz entries. Response code: " + response.code() + " - " + response.message());
                        if (response.errorBody() != null) {
                            Log.e("FetchQuizEntries", "Error body: " + response.errorBody().string());
                        }
                    } catch (IOException e) {
                        Log.e("FetchQuizEntries", "Error reading error body: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<QuizEntryWithTags>> call, Throwable t) {
                Log.e("FetchQuizEntries", "Error fetching quiz entries: " + t.getMessage());
            }
        });
    }**/





    // Process the fetched TaggedQuizEntries and populate the ExpandableListView
    private void processQuizEntries(List<QuizEntryWithTags> taggedQuizEntries) {
        for (QuizEntryWithTags entry : taggedQuizEntries) {
            QuizEntryWithTags quizEntryWithTags = new QuizEntryWithTags(
                    entry.getQuizEntryID(),
                    entry.getQuestion(),
                    entry.getAnswer(),
                    entry.getDescription(),
                    1,false// Using App.groupID for the current group
            );

            quizQuestions.add(entry.getQuestion());
            quizData.put(entry.getQuestion(), quizEntryWithTags);
        }

        // Populate the ExpandableListView with combined data
        populateExpandableListView();
    }

    // Populate the ExpandableListView with processed data
    private void populateExpandableListView() {
        expandableListAdapter = new QuizExpandableListAdapter(quizQuestions, quizData);
        expandableListView.setAdapter(expandableListAdapter);
    }
}
