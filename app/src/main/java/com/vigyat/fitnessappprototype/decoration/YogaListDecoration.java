package com.vigyat.fitnessappprototype.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class YogaListDecoration extends RecyclerView.ItemDecoration {

    private final int space;

    public YogaListDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.right = space;
        outRect.bottom = space;
        outRect.left = space;

        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space;
        }
    }
}
