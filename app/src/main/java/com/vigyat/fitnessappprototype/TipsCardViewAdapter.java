package com.vigyat.fitnessappprototype;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TipsCardViewAdapter extends RecyclerView.Adapter<TipsCardViewAdapter.ViewHolder> {

    private List<String> mData;

    public TipsCardViewAdapter(List<String> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tips_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = mData.get(position);
        holder.cardTextView.setText(item);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cardTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardTextView = itemView.findViewById(R.id.cardTextView);
        }
    }
}
