package com.example.customview.listview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class AdapterViewHolder<Item> extends RecyclerView.ViewHolder {
    public View itemView;

    public AdapterViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
    }


    public abstract void bindView(Item item);
}
