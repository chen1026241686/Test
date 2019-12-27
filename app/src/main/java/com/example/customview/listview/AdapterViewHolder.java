package com.example.customview.listview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

public abstract class AdapterViewHolder<Item> extends RecyclerView.ViewHolder {
    public View itemView;

    public AdapterViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
    }


    public abstract void bindView(Item item);
}
