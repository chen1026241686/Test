package com.example.customview.listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerAdapter<Item> extends RecyclerView.Adapter<AdapterViewHolder<Item>> {

    private List<Item> data = new ArrayList<>();

    private View itemView;

    private Context context;

    private int layoutId;

    public RecyclerAdapter(Context context, List<Item> data, int layoutId) {
        this.context = context;
        if (data != null)
            this.data = data;
        this.layoutId = layoutId;
    }

    public void resetData(List<Item> data) {
        if (data == null) {
            this.data.clear();
        } else {
            this.data.clear();
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void resetData() {
        this.data.clear();
        notifyDataSetChanged();
    }

    public void addData(List<Item> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        itemView = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
        return onCreateViewHolder(itemView);
    }

    public abstract AdapterViewHolder onCreateViewHolder(View itemView);

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder adapterViewHolder, int i) {
        adapterViewHolder.bindView(data.get(i));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}
