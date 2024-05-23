package com.example.satvik;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter_Profile extends RecyclerView.Adapter<MyViewHollder> implements Filterable {

    private final Context context;
    private List<Supplier> items;
    private List<Supplier> filteredItems;

    public MyAdapter_Profile(Context context, List<Supplier> items) {
        this.context = context;
        this.items = items;
        this.filteredItems = new ArrayList<>(items);
    }

    public void setItems(List<Supplier> items) {
        this.items = items;
        this.filteredItems = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHollder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHollder(LayoutInflater.from(context).inflate(R.layout.item_view_profile,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHollder holder, int position) {
        holder.bind(filteredItems.get(position));
    }

    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Supplier> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(items);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Supplier item : items) {
                        if (item.getCategory().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.values != null) {
                    filteredItems.clear();
                    filteredItems.addAll((List<Supplier>) results.values);
                    notifyDataSetChanged();
                } else {
                    // Handle the case when results are null
                    Log.e("MyAdapter_Profile", "Filtered results are null");
                }
            }
        };
    }
}
