package com.vigyat.fitnessappprototype;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.vigyat.fitnessappprototype.databinding.ActivityStepCounterBinding;

public class StepCounter extends AppCompatActivity {

    private TextView stepGoalTV;
    private int stepsGoal;
    private TextView stepsTV;
    private ProgressBar progressBar;
    ActivityStepCounterBinding stepCounterBinding;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);

        stepCounterBinding = DataBindingUtil.setContentView(this, R.layout.activity_step_counter);


        stepGoalTV = stepCounterBinding.stepGoal;
        progressBar = stepCounterBinding.progressBar2;

        stepsTV = stepCounterBinding.TVSteps;

        int storedStepCount = getStepCountFromSharedPreference();

        //private FloatingActionButton fab;
        // Initialize the step count to zero
        stepsTV.setText(String.valueOf(storedStepCount));

        // Register the BroadcastReceiver to listen for step count updates
        IntentFilter filter = new IntentFilter("step_count_updated");
        registerReceiver(stepCountUpdateReceiver, filter, Context.RECEIVER_NOT_EXPORTED);


        Spinner spinnerStepGoal = findViewById(R.id.spinnerStepGoal);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.step_goal_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStepGoal.setAdapter(adapter);

        spinnerStepGoal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedStepGoal = (String) parentView.getItemAtPosition(position);
                // Convert the selectedStepGoal to an integer and set it as the stepsGoal
                stepsGoal = Integer.parseInt(selectedStepGoal);
                stepGoalTV.setText("" + stepsGoal);
                updateProgressBar(storedStepCount, stepsGoal, progressBar);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    private int getStepCountFromSharedPreference() {
        SharedPreferences sharedPreferences = getSharedPreferences("StepCounterPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("stepCount", 0);
    }

    private void updateProgressBar(int currentStepCount, int goal, ProgressBar progressBar) {
        int stepCounterProgress = (int) (((float) currentStepCount / goal) * 100);
        progressBar.setProgress(stepCounterProgress);
    }

    private final BroadcastReceiver stepCountUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("step_count_updated")) {
                // Handle the step count update here
                int updatedStepCount = intent.getIntExtra("stepCount", 0);
                stepsTV.setText(String.valueOf(updatedStepCount));
                updateProgressBar(updatedStepCount, stepsGoal, progressBar);
            }
        }
    };

    protected void onDestroy() {
        super.onDestroy();

        // Unregister the BroadcastReceiver when the activity is destroyed
        unregisterReceiver(stepCountUpdateReceiver);
    }

}