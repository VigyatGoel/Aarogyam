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
import com.vigyat.fitnessappprototype.databinding.ActivityLoginBinding;

import java.util.Objects;

public class Login extends AppCompatActivity {


    TextInputEditText editTextEmail, editTextPassword;
    AppCompatButton buttonLogin, googleSignInBtn;
    FirebaseAuth mAuth;

    ProgressBar progressBar;
    TextView registerNow, forgotPassword;

    private ImageView eye;

    ActivityLoginBinding loginBinding;


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
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        editTextEmail = loginBinding.email;
        editTextPassword = loginBinding.password;
        buttonLogin = loginBinding.LoginBtn;
        progressBar = loginBinding.progressBar;
        registerNow = loginBinding.registerNow;
        forgotPassword = loginBinding.forgotPassword;
        eye = loginBinding.cEye;
        googleSignInBtn = loginBinding.googleSignInBtn;


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

        forgotPassword.setOnClickListener(view -> {

            Intent intent = new Intent(getApplicationContext(), ResetPassword.class);
            startActivity(intent);

        });

        registerNow.setOnClickListener(v -> {

            Intent i1 = new Intent(getApplicationContext(), Register.class);
            startActivity(i1);

        });

        buttonLogin.setOnClickListener(v -> login());
    }

    public void login() {


        progressBar.setVisibility(View.VISIBLE);


        String email, password;
        email = String.valueOf(editTextEmail.getText());
        password = String.valueOf(editTextPassword.getText());

        if (TextUtils.isEmpty(email)) {
            progressBar.setVisibility(View.GONE);

            Toast.makeText(Login.this, "Email can't be empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            progressBar.setVisibility(View.GONE);

            Toast.makeText(Login.this, "Password can't be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {

                        Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.

                        Toast.makeText(Login.this, "Authentication failed.",
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