package com.vigyat.fitnessappprototype;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Meditation extends AppCompatActivity {
    CountDownTimer countDownTimer;
    ProgressBar progressBar;
    FloatingActionButton btn;
    TextView timerTextView;
    boolean isTimerRunning = false;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);

        progressBar = findViewById(R.id.progressBar);
        timerTextView = findViewById(R.id.timerTextView);
        btn = findViewById(R.id.btn);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.meditation_music);

        btn.setOnClickListener(v -> {

            if (isTimerRunning) {

                btn.setImageResource(R.drawable.ic_play);
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                // Pause the timer
                countDownTimer.cancel();

            } else {
                btn.setImageResource(R.drawable.ic_pause);

                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.meditation_music);
                mediaPlayer.start();

                startTimer();
            }
            isTimerRunning = !isTimerRunning;
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            progressBar.setVisibility(View.GONE);

        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(300000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int totalTime = 300000;
                // Calculate the remaining time in seconds
                long remainingSeconds = millisUntilFinished / 1000;

                // Update the TextView with the remaining time in "mm:ss" format
                timerTextView.setText(String.format("%02d:%02d", remainingSeconds / 60, remainingSeconds % 60));

                // Calculate and update the progress based on the elapsed time
                long elapsedTime = totalTime - millisUntilFinished;
                int currentProgress = (int) (elapsedTime * 100 / totalTime);

                progressBar.setProgress(currentProgress);
            }

            @Override
            public void onFinish() {
                // Set the progress to 100% when the timer finishes
                progressBar.setProgress(100);
            }
        }.start();
        progressBar.setProgress(0);
    }
}
