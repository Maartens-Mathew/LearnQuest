package com.example.learnquest.LoginRegister.Dashboard.SelectGroups.GroupDashboard.Quiz.takingQuiz;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learnquest.QuizBank.QuizEntry;
import com.example.learnquest.R;
import com.example.learnquest.AppState.App;

import java.util.List;

public class QuizNavigationActivity extends AppCompatActivity {

    private TextView questionTextView, questionCounterTextView;
    private TextView answerTextView; // Corrected variable name to be more accurate
    private Button toggleAnswerButton;
    private ImageButton prevButton, nextButton;

    private List<QuizEntry> quizEntries;
    private int currentIndex = 0;
    private boolean isAnswerVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_take);
        LinearLayout questionBox = findViewById(R.id.questionBox);

        // Load and start fade-in animation
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        questionBox.startAnimation(fadeIn);

        // Initialize views
        questionTextView = findViewById(R.id.questionTextView);
        questionCounterTextView = findViewById(R.id.questionCounterTextView);
        answerTextView = findViewById(R.id.answerEditText);
        toggleAnswerButton = findViewById(R.id.btnUnhide);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);

        App.groupID = 1; // Set group ID as needed

        quizEntries = (List<QuizEntry>) getIntent().getSerializableExtra("quizEntries");

        if (quizEntries == null) {
            Toast.makeText(this, "Failed to load quiz entries.", Toast.LENGTH_SHORT).show();
        } else if (quizEntries.isEmpty()) {
            Toast.makeText(this, "No Questions Available!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Quiz Loaded Successfully!", Toast.LENGTH_LONG).show();
            updateQuestion(); // Load the first question
        }


        // Toggle button click listener
        toggleAnswerButton.setOnClickListener(v -> toggleAnswer());

        // Navigation buttons
        prevButton.setOnClickListener(v -> navigateQuestion(-1));
        nextButton.setOnClickListener(v -> navigateQuestion(1));
        Log.d("QuizEntriesDebug", "Received entries count: " + (quizEntries != null ? quizEntries.size() : "null"));

    }

    private void toggleAnswer() {
        Animation scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        LinearLayout questionBox = findViewById(R.id.questionBox);
        questionBox.startAnimation(scaleUp);

        if (isAnswerVisible) {
            answerTextView.setText(""); // Clear the answer
            toggleAnswerButton.setText("Show Answer");
        } else {
            QuizEntry currentEntry = quizEntries.get(currentIndex);
            answerTextView.setText(currentEntry.getAnswer()); // Show answer
            toggleAnswerButton.setText("Hide Answer");
        }
        isAnswerVisible = !isAnswerVisible;
    }

    private void navigateQuestion(int direction) {
        if (quizEntries == null || quizEntries.isEmpty()) return;

        currentIndex += direction;
        if (currentIndex < 0) currentIndex = quizEntries.size() - 1;
        else if (currentIndex >= quizEntries.size()) currentIndex = 0;

        updateQuestion();
    }

    private void updateQuestion() {
        QuizEntry currentEntry = quizEntries.get(currentIndex);
        questionTextView.setText(currentEntry.getQuestion());

        String counterText = "Question " + (currentIndex + 1) + " of " + quizEntries.size();
        questionCounterTextView.setText(counterText);

        answerTextView.setText("");  // Clear answer box
        toggleAnswerButton.setText("Show Answer");  // Reset button text
        isAnswerVisible = false;  // Ensure answer is hidden by default
    }
}
