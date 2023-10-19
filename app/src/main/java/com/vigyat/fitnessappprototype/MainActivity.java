package com.vigyat.fitnessappprototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.Manifest;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    private static final int REQUEST_ACTIVITY_RECOGNITION_PERMISSION = 1;
    private static final int REQUEST_POST_NOTIFICATION_PERMISSION = 2;

    private LinearLayout exerciseLL, stepCounterLL;


    private LottieAnimationView exerciseLAV, counterLAV;
    private ImageView imageView;
    FirebaseAuth auth;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Check if the permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {
            // Request the ACTIVITY_RECOGNITION permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                    REQUEST_ACTIVITY_RECOGNITION_PERMISSION);
        }

//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
//                != PackageManager.PERMISSION_GRANTED) {
//            // Request the POST_NOTIFICATIONS permission
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
//                    REQUEST_POST_NOTIFICATION_PERMISSION);
//        }

        auth = FirebaseAuth.getInstance();

        user = auth.getCurrentUser();
        if (user == null) {
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
            finish();

        }

//        Intent serviceIntent = new Intent(this, StepCounterService.class);
//        startService(serviceIntent);


        imageView = findViewById(R.id.aarogyamImg);

        exerciseLL = findViewById(R.id.idLLExercise);
        stepCounterLL = findViewById(R.id.idLLstepCounter);
        exerciseLAV = findViewById(R.id.LAVExercise);
        stepCounterLL = findViewById(R.id.idLLstepCounter);

        resetStepCounter();

        Intent i = new Intent(getApplicationContext(), StepCounterService.class);
        startService(i);


        exerciseLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i1 = new Intent(getApplicationContext(), MentalHealth_inner.class);
                startActivity(i1);
            }
        });

        stepCounterLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), StepCounter.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_ACTIVITY_RECOGNITION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, continue with your app's functionality.

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Request the POST_NOTIFICATIONS permission
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.POST_NOTIFICATIONS},
                            REQUEST_POST_NOTIFICATION_PERMISSION);
                }
            } else {
                // Permission denied, handle accordingly (e.g., show a message or disable functionality).
            }
        }

        if (requestCode == REQUEST_POST_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can proceed with your task.
            } else {
                // Permission denied, handle it accordingly.
            }
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.Btn) {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



    public void resetStepCounter(){

        Calendar now = Calendar.getInstance();
        Calendar midnight = Calendar.getInstance();
        midnight.set(Calendar.HOUR_OF_DAY, 0);
        midnight.set(Calendar.MINUTE, 0);
        midnight.set(Calendar.SECOND, 0);

        if (now.after(midnight)) {
            midnight.add(Calendar.DATE, 1);
        }

        long initialDelay = midnight.getTimeInMillis() - now.getTimeInMillis();

        PeriodicWorkRequest clearSharedPreferencesWork = new PeriodicWorkRequest.Builder(
                ResetStepCounter.class,
                1, TimeUnit.DAYS
        )
                .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                .build();

        WorkManager.getInstance(this).enqueue(clearSharedPreferencesWork);
    }
}