package com.vigyat.fitnessappprototype;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class GoogleSignInHelper {

    private final Context context;
    private final FirebaseAuth mAuth;
    private final GoogleSignInClient googleSignInClient;

    public GoogleSignInHelper(Context context) {
        this.context = context;
        FirebaseApp.initializeApp(context);
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(context, options);
        mAuth = FirebaseAuth.getInstance();
    }

    public void signInWithGoogle(ActivityResultLauncher<Intent> activityResultLauncher) {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        activityResultLauncher.launch(signInIntent);
    }

    public ActivityResultCallback<ActivityResult> getGoogleSignInResultHandler() {
        return result -> {
            if (result.getResultCode() == android.app.Activity.RESULT_OK) {
                Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                try {
                    GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                    AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
                    mAuth.signInWithCredential(authCredential).addOnCompleteListener(getAuthResultHandler());
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private OnCompleteListener<AuthResult> getAuthResultHandler() {
        return task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, "Signed in successfully!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, MainActivity.class);
                context.startActivity(i);
                ((android.app.Activity) context).finish();
            } else {
                Toast.makeText(context, "Something went wrong... " + task.getException(), Toast.LENGTH_SHORT).show();
            }
        };
    }
}