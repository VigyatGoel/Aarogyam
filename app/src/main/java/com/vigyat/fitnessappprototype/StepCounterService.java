package com.vigyat.fitnessappprototype;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.vigyat.fitnessappprototype.viewmodel.MainViewModel;


public class StepCounterService extends Service implements SensorEventListener {


    private SensorManager sensorManager;
    private Sensor stepDetectorSensor;
    private int stepCount = 0;

    private MainViewModel viewModel;


    @Override
    public void onCreate() {
        super.onCreate();


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        SharedPreferences sharedPreferences = getSharedPreferences("StepCounterPrefs", Context.MODE_PRIVATE);
        stepCount = sharedPreferences.getInt("stepCount", 0);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotification();
        startCounting();
        return START_STICKY; // Service will be restarted if it gets terminated by the system
    }

    private void startCounting() {

        if (stepDetectorSensor == null) {
            Toast.makeText(this, "Step detector sensor not found", Toast.LENGTH_SHORT).show();
        } else {
            sensorManager.registerListener(this, stepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {

            // Increment the step count for each step detected
            stepCount++;
            saveStepCountToSharedPreference(stepCount);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        sensorManager.unregisterListener(this);
    }

    private void saveStepCountToSharedPreference(int stepCount) {
        SharedPreferences sharedPreferences = getSharedPreferences("StepCounterPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stepCount", stepCount);
        editor.apply();

        // Send a broadcast to notify the UI about the step count update
        Intent intent = new Intent("step_count_updated");
        intent.putExtra("stepCount", stepCount);
        sendBroadcast(intent);
    }

    private void createNotification() {
        // Create a notification channel (required for Android Oreo and above)
        NotificationChannel channel = new NotificationChannel("step_counter_channel",
                "Step Counter Channel",
                NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "step_counter_channel")
                .setSmallIcon(R.drawable.ic_app_logo)
                .setContentTitle("Step Counter")
                .setContentText("Counting Steps")
                .setPriority(NotificationCompat.PRIORITY_MAX);

        // Build the notification and show it
        Notification notification = builder.build();
        startForeground(1, notification);
    }

}
