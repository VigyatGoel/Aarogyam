package com.vigyat.fitnessappprototype;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class YogaListAdapter extends RecyclerView.Adapter<YogaListAdapter.MyViewHolder> {

    private List<YogaListModalClass> yogaList;
    private Context context;

    public YogaListAdapter(List<YogaListModalClass> yogaList, Context context) {
        this.yogaList = yogaList;
        this.context = context;
    }

    @NonNull
    @Override
    public YogaListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_yoga,parent,false);
        return new YogaListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull YogaListAdapter.MyViewHolder holder, int position) {

        YogaListModalClass yoga = yogaList.get(position);
        holder.textView.setText(yoga.getYogaName());
        holder.imageView.setImageResource(yoga.getYogaImage());


        holder.yogaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Yoga.class);

                intent.putExtra("yoga_name", yoga.getYogaName());
                intent.putExtra("yoga_image", yoga.getYogaImage());
                intent.putExtra("yoga_url",yoga.getYogaUrl());
                intent.putExtra("yoga_benefits",yoga.getyogaBenefits());
                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return yogaList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        ConstraintLayout yogaLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.yogaImage);
            textView = itemView.findViewById(R.id.yogaName);
            yogaLayout = itemView.findViewById(R.id.yogaLayout);

        }
    }
}