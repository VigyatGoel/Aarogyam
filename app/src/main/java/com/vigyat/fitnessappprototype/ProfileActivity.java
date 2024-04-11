package com.vigyat.fitnessappprototype;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vigyat.fitnessappprototype.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {

    TextView userName, email, steps;
    ImageView profileImage;
    AppCompatButton logoutBtn;

    CardView editProfileCardView;
    ActivityProfileBinding profileBinding;

    FirebaseAuth auth;
    FirebaseUser user;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        profileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        userName = profileBinding.username;
        email = profileBinding.email;
        steps = profileBinding.steps;
        profileImage = profileBinding.profileImage;
        logoutBtn = profileBinding.LogoutBtn;
        editProfileCardView = profileBinding.editProfileCardView;

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        getUserDetails();
        int storedStepCount = getStepCountFromSharedPreference();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //private FloatingActionButton fab;
        // Initialize the step count to zero
        steps.setText(String.valueOf(storedStepCount));

        logoutBtn.setOnClickListener(v -> logout());

        editProfileCardView.setOnClickListener(v -> {
            Intent i = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(i);
        });
    }

    private void getUserDetails() {


        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            // Update the user's name
            String username = currentUser.getDisplayName();
            String userEmail = currentUser.getEmail();
            if (username != null) {
                userName.setText(username);
            } else {
                userName.setText("User");
            }

            if (userEmail != null) {
                email.setText(userEmail);
            } else {
                email.setText("email");
            }

            // Update the user's profile image
            if (currentUser.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(currentUser.getPhotoUrl())
                        .circleCrop()
                        .into(profileImage);

            }
        }
    }

    private void logout() {
        // Sign out from Firebase
        FirebaseAuth.getInstance().signOut();

        // Sign out from Google Sign-In
        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
            // Redirect to Login activity
            Intent i = new Intent(getApplicationContext(), Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });
    }

    private int getStepCountFromSharedPreference() {
        SharedPreferences sharedPreferences = getSharedPreferences("StepCounterPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("stepCount", 0);
    }

}