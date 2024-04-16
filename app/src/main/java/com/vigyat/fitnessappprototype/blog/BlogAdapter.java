package com.vigyat.fitnessappprototype.blog;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vigyat.fitnessappprototype.R;

import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.BlogViewHolder> {

    Context context;
    List<Blog> blogList;

    public BlogAdapter(Context context, List<Blog> blogList) {
        this.context = context;
        this.blogList = blogList;
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.blog_row,
                        parent,
                        false
                );
        return new BlogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogViewHolder holder, int position) {

        try {
            Blog currentBlog = blogList.get(position);
            holder.title.setText(currentBlog.getTitle());
            holder.thoughts.setText(currentBlog.getThoughts());
            holder.name.setText(currentBlog.getUserName());

            String imageUrl = currentBlog.getImageUrl();

            String timeAgo = (String) DateUtils.getRelativeTimeSpanString(currentBlog.getTimeAdded().getSeconds() * 1000);

            holder.dateAdded.setText(timeAgo);
            Glide.with(context).load(imageUrl).fitCenter().into(holder.image);


        } catch (Exception e) {
            Log.e("BlogAdapterTag", "Error binding view holder", e);
        }

    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }


    static class BlogViewHolder extends RecyclerView.ViewHolder {

        public TextView title, thoughts, dateAdded, name;
        public ImageView image;


        public BlogViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.blog_row_title);
            thoughts = itemView.findViewById(R.id.blog_row_thoughts);
            image = itemView.findViewById(R.id.blogImage);
            dateAdded = itemView.findViewById(R.id.blog_row_date);
            name = itemView.findViewById(R.id.blog_row_username);
        }
    }
}
