package com.example.learnquest.QuizBank;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learnquest.AppState.App;
import com.example.learnquest.R;
import com.example.learnquest.Utils.database.QuizCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class quizBankHome extends AppCompatActivity {

    public List<QuizEntry> entries;
    private QuizExpandableListAdapter quizAdapter;
    private ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_bank);

        entries = new ArrayList<>();
        App.groupID = 1;
        App.setApplicationContext(getApplicationContext());

        expandableListView = findViewById(R.id.elvQuizEntries);
        quizAdapter = new QuizExpandableListAdapter(entries);
        expandableListView.setAdapter(quizAdapter);

        loadQuizQuestions();

        expandableListView.setGroupIndicator(null);
    }

    // Method to load quiz questions
    private void loadQuizQuestions() {
        Thread thread = new Thread(this::GetQuizQuestions);
        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            Log.e("ThreadError", "Thread was interrupted.");
        }
    }

    private void GetQuizQuestions() {
        Call<List<QuizEntry>> call = App.api.getQuizEntries(App.groupID); // Replace 1 with actual groupID
        Response<List<QuizEntry>> response;

        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (response.isSuccessful()) {
            entries = Collections.synchronizedList(response.body());
            runOnUiThread(() -> {
                quizAdapter.updateData(entries);
                quizAdapter.notifyDataSetChanged();
            });
            Log.i("Custom", entries.toString());
        } else {
            Log.i("Custom", "Could not parse");
            try {
                Log.e("Custom", response.errorBody().string());
            } catch (IOException ignored) {
            }
        }
    }

    public void pressOnClick(View view) {
        Intent intent = new Intent(this, quizNewInsertAct.class);
        startActivityForResult(intent, 1);  // Use startActivityForResult to listen for a result
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Reload quiz questions after a new entry is added
            Toast.makeText(this, "New quiz entry added. Refreshing data.", Toast.LENGTH_SHORT).show();
            loadQuizQuestions();
        }
    }
}
