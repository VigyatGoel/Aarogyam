package com.vigyat.fitnessappprototype;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ResetStepCounter extends Worker {


    public ResetStepCounter(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("StepCounterPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("stepCount", 0);
        editor.apply();

        return Result.success();
    }
}
