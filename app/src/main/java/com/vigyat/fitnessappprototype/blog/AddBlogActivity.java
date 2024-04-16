package com.vigyat.fitnessappprototype.blog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vigyat.fitnessappprototype.R;
import com.vigyat.fitnessappprototype.databinding.ActivityAddBlogBinding;

import java.util.Date;

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
    protected void onStart() {
        super.onStart();
        user = firebaseAuth.getCurrentUser();
    }
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
        thoughtsEt = addBlogBinding.thoughtsEt;
        imageView = addBlogBinding.postImageView;
        addPhotoBtn = addBlogBinding.postCameraButton;
        progressBar = addBlogBinding.postProgressBar;

        progressBar.setVisibility(View.INVISIBLE);

        storageReference = FirebaseStorage.getInstance().getReference();

        firebaseAuth = FirebaseAuth.getInstance();

        if(user != null){

            currentUserId = user.getUid();
            currentUserName = user.getDisplayName();
        }

        mTakePhoto = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri o) {
                imageView.setImageURI(o);
                imageUri = o;
            }
        });

        addPhotoBtn.setOnClickListener(v -> mTakePhoto.launch("image/*"));

        shareBtn.setOnClickListener(v -> shareBlog());



    }

    private void shareBlog(){

        String title, thoughts;
        title = String.valueOf(titleEt.getText());
        thoughts = String.valueOf(thoughtsEt.getText());

        progressBar.setVisibility(View.VISIBLE);

        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(thoughts) && imageUri != null){

            final StorageReference filePath = storageReference
                    .child("blog_images")
                    .child("image_"+ Timestamp.now().getSeconds());

            filePath.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    String imageUrl = uri.toString();

                                    Blog blog = new Blog();
                                    blog.setTitle(title);
                                    blog.setThoughts(thoughts);
                                    blog.setImageUrl(imageUrl);
                                    blog.setTimeAdded(new Timestamp(new Date()));
                                    blog.setUserName(currentUserName);
                                    blog.setUserId(currentUserId);


                                    collectionReference.add(blog).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {

                                            progressBar.setVisibility(View.INVISIBLE);
                                             Intent i = new Intent(AddBlogActivity.this, BlogList.class);
                                             startActivity(i);
                                             finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(AddBlogActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(AddBlogActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

        }
        else{

            progressBar.setVisibility(View.INVISIBLE);
        }

    }
}