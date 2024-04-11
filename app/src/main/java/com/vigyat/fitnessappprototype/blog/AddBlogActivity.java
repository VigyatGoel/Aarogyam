package com.vigyat.fitnessappprototype.blog;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.vigyat.fitnessappprototype.R;
import com.vigyat.fitnessappprototype.databinding.ActivityAddBlogBinding;

public class AddBlogActivity extends AppCompatActivity {

    ActivityAddBlogBinding addBlogBinding;
    Button shareBtn;
    TextInputEditText titleEt;
    TextInputEditText thoughtsEt;

    private ImageView addPhotoBtn;
    private ProgressBar progressBar;
    private ImageView imageView;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Blog");

    //Storage
    private StorageReference storageReference;

    private String currentUserId;
    private String currentUserName;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;

    // Activity Result Launcher
    ActivityResultLauncher<String> mTakePhoto;

    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_blog);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addBlogBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_blog);
        shareBtn = addBlogBinding.ShareBtn;
        titleEt = addBlogBinding.titleEt;


    }
}