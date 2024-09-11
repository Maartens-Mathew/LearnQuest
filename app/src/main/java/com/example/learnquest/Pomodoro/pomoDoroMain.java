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
    private Button startBreakButton;
    private Button stopBreakButton;
    private CountDownTimer countDownTimer;
    private final long POMODORO_TIME = 25 * 60 * 1000; // 25 minutes
    private final long BREAK_TIME = 5 * 60 * 1000; // 5 minutes
    private long timeLeftInMillis = POMODORO_TIME;
    private boolean isTimerRunning = false;
    private boolean isBreakMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pomodoro_view);

        circularProgressBar = findViewById(R.id.circularProgressBar);
        timerText = findViewById(R.id.timerText);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        startBreakButton = findViewById(R.id.btnBreak);
        stopBreakButton = findViewById(R.id.btnStopBreak);

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
                handleBreakButtonsVisibility(false); // Reset button visibility after finishing
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

    public void StartBreakOnClick(View view) {
        // Stop any current timer
        if (isTimerRunning) {
            countDownTimer.cancel();
            isTimerRunning = false;
        }

        // Set the break time and start a new timer
        timeLeftInMillis = BREAK_TIME;
        startTimer();

        // Set break mode to true and update UI
        isBreakMode = true;
        handleBreakButtonsVisibility(true);
    }

    public void stopBreakOnClick(View view) {
        // Stop the break timer and start the pomodoro timer again
        if (isTimerRunning) {
            countDownTimer.cancel();
            isTimerRunning = false;
        }

        // Reset the timer to the pomodoro time
        timeLeftInMillis = POMODORO_TIME;
        startTimer();

        // Set break mode to false and update UI
        isBreakMode = false;
        handleBreakButtonsVisibility(false);
    }

    private void handleBreakButtonsVisibility(boolean inBreakMode) {
        if (inBreakMode) {
            startBreakButton.setVisibility(View.GONE); // Hide the Start Break button
            stopBreakButton.setVisibility(View.VISIBLE); // Show the Stop Break button
        } else {
            startBreakButton.setVisibility(View.VISIBLE); // Show the Start Break button
            stopBreakButton.setVisibility(View.GONE); // Hide the Stop Break button
        }
    }
}
