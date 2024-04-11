package com.vigyat.fitnessappprototype;

import static com.vigyat.fitnessappprototype.R.drawable.ic_eye_hide;
import static com.vigyat.fitnessappprototype.R.drawable.ic_eye_open;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.vigyat.fitnessappprototype.databinding.ActivityRegisterBinding;

import java.util.Objects;

public class Register extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword, editTextUserName;
    AppCompatButton buttonReg, googleSignInBtn;
    FirebaseAuth mAuth;

    ProgressBar progressBar;
    TextView loginNow;
    ImageView eye;
    ActivityRegisterBinding registerBinding;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        registerBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        editTextUserName = registerBinding.username;
        editTextEmail = registerBinding.email;
        editTextPassword = registerBinding.password;
        buttonReg = registerBinding.RegisterBtn;
        progressBar = registerBinding.progressBar;
        loginNow = registerBinding.loginNow;
        eye = registerBinding.cEye;
        googleSignInBtn = registerBinding.googleSignInBtn;

        googleSignIn();


        Intent i = new Intent(getApplicationContext(), StepCounterService.class);
        stopService(i);

        eye.setOnClickListener(v -> {
            if (editTextPassword.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                eye.setImageResource(ic_eye_hide);
            } else {
                editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                eye.setImageResource(ic_eye_open);
            }
            editTextPassword.setSelection(Objects.requireNonNull(editTextPassword.getText()).length());
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            v.requestFocus();
        });

        loginNow.setOnClickListener(v -> {

            Intent i1 = new Intent(getApplicationContext(), Login.class);
            startActivity(i1);
            finish();
        });

        buttonReg.setOnClickListener(v -> register());
    }

    public void register() {

        progressBar.setVisibility(View.VISIBLE);

        String username, email, password;
        username = String.valueOf(editTextUserName.getText());
        email = String.valueOf(editTextEmail.getText());
        password = String.valueOf(editTextPassword.getText());

        if (TextUtils.isEmpty(email)) {
            progressBar.setVisibility(View.GONE);


            Toast.makeText(Register.this, "Email can't be empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            progressBar.setVisibility(View.GONE);

            Toast.makeText(Register.this, "Password can't be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(username)) {
            progressBar.setVisibility(View.GONE);

            Toast.makeText(Register.this, "Username can't be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Register.this, task -> {
                    progressBar.setVisibility(View.GONE);

                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        // Update user's profile with the provided name
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .build();

                        assert user != null;
                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(updateTask -> {
                                    if (updateTask.isSuccessful()) {
                                        // Profile updated successfully
                                        Toast.makeText(Register.this, "Registered successfully to Aarogyam", Toast.LENGTH_SHORT).show();
                                        Intent i1 = new Intent(Register.this, MainActivity.class);
                                        startActivity(i1);
                                    } else {
                                        // Failed to update profile
                                        Toast.makeText(Register.this, "Failed to update profile.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(Register.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });


    }

    public void googleSignIn() {
        GoogleSignInHelper googleSignInHelper = new GoogleSignInHelper(this);
        ActivityResultLauncher<Intent> googleSignInLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), googleSignInHelper.getGoogleSignInResultHandler());
        googleSignInBtn.setOnClickListener(v -> googleSignInHelper.signInWithGoogle(googleSignInLauncher));
    }
}