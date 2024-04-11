package com.vigyat.fitnessappprototype;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    private List<GovtHelpLineModal> helpLineList;
    private Context context;

    public MyAdapter(List<GovtHelpLineModal> helpLineList, Context context) {
        this.helpLineList = helpLineList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_helpline,parent,false);
         return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        GovtHelpLineModal helpLine = helpLineList.get(position);
        holder.textView.setText(helpLine.getHelplineName());
        holder.imageView.setImageResource(helpLine.getImage());
        holder.btn.setOnClickListener(v -> {
            String url = helpLine.getUrl();
            Uri webpage = Uri.parse(url);

            Intent intent = new Intent(Intent.ACTION_VIEW,webpage);
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return helpLineList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        Button btn;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
             imageView = itemView.findViewById(R.id.govtImg);
             textView = itemView.findViewById(R.id.textView);
             btn = itemView.findViewById(R.id.btn);
        }
    }
}