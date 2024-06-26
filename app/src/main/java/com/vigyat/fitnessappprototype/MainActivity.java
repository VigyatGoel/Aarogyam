package com.vigyat.fitnessappprototype;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vigyat.fitnessappprototype.blog.BlogList;
import com.vigyat.fitnessappprototype.databinding.ActivityMainBinding;
import com.vigyat.fitnessappprototype.decoration.SpacesItemDecorationTipsCard;
import com.vigyat.fitnessappprototype.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    private static final int REQUEST_ACTIVITY_RECOGNITION_PERMISSION = 1;
    private static final int REQUEST_POST_NOTIFICATION_PERMISSION = 2;


    private CardView mentalHealthCard, stepsCard, blogCard;

    private ImageView profileImage;
    private TextView welcomeText, stepsTV;

    private RecyclerView tipsRecyclerView;
    private TipsCardViewAdapter tipsViewAdapter;
    FirebaseAuth auth;

    FirebaseUser user;

    ActivityMainBinding mainBinding;

    private MainViewModel viewModel;

    private BroadcastReceiver stepCountReceiver;

    @RequiresApi(api = Build.VERSION_CODES.Q)
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

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);


        auth = FirebaseAuth.getInstance();

        user = auth.getCurrentUser();
        if (user == null) {
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
            finish();
        } else {
            viewModel.setUserData(user);
        }

        scheduleResetStepCounter();


        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        blogCard = mainBinding.blogCard;


        stepsCard = mainBinding.stepsCard;
        mentalHealthCard = mainBinding.mentalHealthCard;
        welcomeText = mainBinding.welcomeText;
        tipsRecyclerView = mainBinding.tipRecyclerView;
        profileImage = mainBinding.profileImage;
        stepsTV = mainBinding.stepTV;

        int storedStepCount = getStepCountFromSharedPreference();
        viewModel.setStepCount(storedStepCount);

        stepCountReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int stepCount = intent.getIntExtra("stepCount", 0);
                viewModel.setStepCount(stepCount);

            }
        };
        registerReceiver(stepCountReceiver, new IntentFilter("com.vigyat.fitnessappprototype.STEP_COUNT_CHANGED"), Context.RECEIVER_NOT_EXPORTED);

        viewModel.getStepCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer stepCount) {
                stepsTV.setText(String.valueOf(stepCount));
            }
        });

        updateUI();


        Intent i = new Intent(getApplicationContext(), StepCounterService.class);
        startService(i);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        tipsRecyclerView.setLayoutManager(layoutManager);
        List<String> cardData = getCardData();


        tipsViewAdapter = new TipsCardViewAdapter(cardData);
        tipsRecyclerView.setAdapter(tipsViewAdapter);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        tipsRecyclerView.addItemDecoration(new SpacesItemDecorationTipsCard(spacingInPixels));


        mentalHealthCard.setOnClickListener(v -> {

            Intent i1 = new Intent(MainActivity.this, MentalHealth_inner.class);
            startActivity(i1);
        });

        stepsCard.setOnClickListener(v -> {

            Intent i2 = new Intent(MainActivity.this, StepCounter.class);
            startActivity(i2);
        });

        blogCard.setOnClickListener(v -> {
            Intent i12 = new Intent(MainActivity.this, BlogList.class);
            startActivity(i12);
        });

        profileImage.setOnClickListener(v -> profileImageClick());

    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
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


    private List<String> getCardData() {
        List<String> cardData = new ArrayList<>();
        cardData.add("Stay hydrated");
        cardData.add("Get regular exercise");
        cardData.add("Eat a balanced diet");
        cardData.add("Take breaks during work");
        cardData.add("Get a good night's sleep");
        cardData.add("Practice mindfulness and meditation");
        cardData.add("Maintain a positive attitude");
        cardData.add("Avoid excessive caffeine");
        cardData.add("Limit screen time");
        cardData.add("Connect with loved ones");
        return cardData;
    }

    private void scheduleResetStepCounter() {
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(false)
                .build();

        PeriodicWorkRequest resetStepCounterRequest =
                new PeriodicWorkRequest.Builder(ResetStepCounter.class, 24, TimeUnit.HOURS)
                        .setInitialDelay(getDelayUntilMidnight(), TimeUnit.MILLISECONDS)
                        .setConstraints(constraints)
                        .build();

        WorkManager.getInstance(getApplicationContext()).enqueue(resetStepCounterRequest);
    }

    private long getDelayUntilMidnight() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis() - System.currentTimeMillis();
    }

    private void updateUI() {
        viewModel.getUserData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {

                FirebaseUser currentUser = auth.getCurrentUser();
                if (firebaseUser != null) {
                    // Update the user's name
                    String username = firebaseUser.getDisplayName();
                    if (username != null) {
                        welcomeText.setText(username);
                    } else {
                        welcomeText.setText("User");
                    }

                    // Update the user's profile image
                    if (firebaseUser.getPhotoUrl() != null) {
                        Glide.with(MainActivity.this)
                                .load(currentUser.getPhotoUrl())
                                .circleCrop()
                                .into(profileImage);
                    }
                }
            }
        });
    }


    private void profileImageClick() {
        Intent i = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(i);
    }

    private int getStepCountFromSharedPreference() {
        SharedPreferences sharedPreferences = getSharedPreferences("StepCounterPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("stepCount", 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(stepCountReceiver);
    }
}