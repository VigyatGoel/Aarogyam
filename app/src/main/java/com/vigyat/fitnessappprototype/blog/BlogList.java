package com.vigyat.fitnessappprototype.blog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.StorageReference;
import com.vigyat.fitnessappprototype.R;
import com.vigyat.fitnessappprototype.databinding.ActivityBlogListBinding;

import java.util.ArrayList;
import java.util.List;

public class BlogList extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser user;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Blog");
    private StorageReference storageReference;
    private List<Blog> blogList;

    BlogAdapter blogAdapter;


    FloatingActionButton addBlogBtn;


    RecyclerView recyclerView;
    ActivityBlogListBinding blogListBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_blog_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        blogListBinding = DataBindingUtil.setContentView(this, R.layout.activity_blog_list);
        recyclerView = blogListBinding.blogListRecyclerView;
        addBlogBtn = blogListBinding.addBLogBtn;

        addBlogBtn.setOnClickListener(v -> {
            Intent i = new Intent(BlogList.this, AddBlogActivity.class);
            startActivity(i);
        });

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        blogList = new ArrayList<>();


    }

    @Override
    protected void onStart() {
        super.onStart();

        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                blogList.clear(); // Clear the blogList before adding blogs

                for(QueryDocumentSnapshot blogs : queryDocumentSnapshots){

                    Blog blog = blogs.toObject(Blog.class);
                    blogList.add(blog);
                }

                blogAdapter = new BlogAdapter(BlogList.this, blogList);
                recyclerView.setAdapter(blogAdapter);
                blogAdapter.notifyDataSetChanged();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.e("BlogList", "Error loading blogs", e);


                Toast.makeText(BlogList.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}