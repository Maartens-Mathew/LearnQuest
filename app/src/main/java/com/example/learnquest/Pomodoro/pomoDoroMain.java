package com.example.learnquest.Pomodoro;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learnquest.R;

public class pomoDoroMain extends AppCompatActivity {
    private CircularProgressBar circularProgressBar;
    private TextView timerText;
    private Button startButton;
    private Button pauseButton;
    private CountDownTimer countDownTimer;
    private final long POMODORO_TIME = 25 * 60 * 1000; // 25 minutes
    private long timeLeftInMillis = POMODORO_TIME;
    private boolean isTimerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pomodoro_view);

        circularProgressBar = findViewById(R.id.circularProgressBar);
        timerText = findViewById(R.id.timerText);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });
    }

    private void startTimer() {
        if (isTimerRunning) {
            return; // Prevent starting a new timer if one is already running
        }

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateUI();
            }

            @Override
            public void onFinish() {
                timerText.setText("Time's Up!");
                circularProgressBar.setProgress(0);
                isTimerRunning = false;
                startButton.setEnabled(true); // Enable the start button again
            }
        }.start();

        isTimerRunning = true; // Set the timer running state to true
        startButton.setEnabled(false); // Disable the start button while timer is running
        pauseButton.setEnabled(true); // Enable the pause button
    }

    private void pauseTimer() {
        if (!isTimerRunning) {
            return; // Prevent pausing if the timer isn't running
        }

        countDownTimer.cancel();
        isTimerRunning = false; // Set the timer running state to false
        startButton.setEnabled(true); // Enable the start button
        pauseButton.setEnabled(false); // Disable the pause button
    }
    private void updateUI() {
        int progress = (int) ((timeLeftInMillis / (float) POMODORO_TIME) * 100);
        circularProgressBar.setProgress(progress);

        // Calculate remaining time in minutes and seconds
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);

        // Update the timer text in the CircularProgressBar
        circularProgressBar.setTimerText(timeLeftFormatted);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
