package com.example.satvik;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter_Profile extends RecyclerView.Adapter<MyViewHollder> {


    Context context;
    List<item> items;

    public MyAdapter_Profile(Context context, List<item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHollder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHollder(LayoutInflater.from(context).inflate(R.layout.item_view_profile,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHollder holder, int position) {
        holder.nameView.setText(items.get(position).getName());
        holder.address_view.setText(items.get(position).getAddress());
        holder.category_view.setText(items.get(position).getCategory());
        holder.imageView.setImageResource(items.get(position).getProfile_image());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
