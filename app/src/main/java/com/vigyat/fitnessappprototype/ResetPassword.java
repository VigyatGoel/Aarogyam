package com.vigyat.fitnessappprototype;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.vigyat.fitnessappprototype.databinding.ActivityResetPasswordBinding;

public class ResetPassword extends AppCompatActivity {

    Button resetBtn;
    TextInputEditText userEmail;
    FirebaseAuth auth;

    ActivityResetPasswordBinding resetPasswordBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        resetPasswordBinding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password);
        userEmail = resetPasswordBinding.email;
        resetBtn = resetPasswordBinding.ResetBtn;

        auth = FirebaseAuth.getInstance();

        resetBtn.setOnClickListener(v -> resetPassword());
    }

    public void resetPassword() {


        String emailAddress = userEmail.getText().toString();

        if (TextUtils.isEmpty(emailAddress)) {

            Toast.makeText(ResetPassword.this, "Email cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ResetPassword.this, "Email sent successfully.", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(ResetPassword.this, "Email not sent. Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }
}