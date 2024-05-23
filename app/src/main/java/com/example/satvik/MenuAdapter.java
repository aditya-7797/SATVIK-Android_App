package com.example.satvik;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private List<MenuItem2> menuItems;
    private Context context; // Add context variable

    public MenuAdapter(List<MenuItem2> menuItems, Context context) {
        this.menuItems = menuItems;
        this.context = context; // Initialize context
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_view, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuItem2 menuItem = menuItems.get(position);
        holder.bind(menuItem); // Pass context here
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    static class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImageView;
        TextView itemNameTextView;
        TextView itemPriceTextView;
        Button order;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.food_item_img);
            itemNameTextView = itemView.findViewById(R.id.food_item_name);
            itemPriceTextView = itemView.findViewById(R.id.food_item_price);
            order = itemView.findViewById(R.id.order);
        }

        public void bind(MenuItem2 menuItem) {
            itemNameTextView.setText(menuItem.getItemName());
            itemPriceTextView.setText(menuItem.getItemPrice());
            itemImageView.setImageResource(menuItem.getImageResource());
            order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), Bill.class);
                    intent.putExtra("menu_item", itemNameTextView.getText().toString());
                    intent.putExtra("item_price", itemPriceTextView.getText().toString());

                    v.getContext().startActivity(intent);
                    Toast.makeText(v.getContext(), "Order Clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
