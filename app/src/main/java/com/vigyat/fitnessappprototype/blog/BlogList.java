package com.vigyat.fitnessappprototype.blog;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.vigyat.fitnessappprototype.R;
import com.vigyat.fitnessappprototype.databinding.ActivityBlogListBinding;

public class BlogList extends AppCompatActivity {

    RecyclerView recyclerView;
    ActivityBlogListBinding blogListBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_blog_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        blogListBinding = DataBindingUtil.setContentView(this, R.layout.activity_blog_list);
        recyclerView = blogListBinding.blogListRecyclerView;


    }
}