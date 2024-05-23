
package com.example.satvik;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImageView;
        TextView itemNameTextView;
        TextView itemPriceTextView;

        Context context;
        Button go_to_cart; // Reference to the RelativeLayout
        private String contact;
        public String user_mob= MainActivity.usermobile;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext(); // Assign the context to the instance variable

            itemImageView = itemView.findViewById(R.id.food_item_img);
            itemNameTextView = itemView.findViewById(R.id.food_item_name);
            itemPriceTextView = itemView.findViewById(R.id.food_item_price);
            go_to_cart = itemView.findViewById(R.id.order);

            go_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Handle navigation here
                        // For example, start a new activity
                        Intent intent = new Intent(context, Bill.class);
                        intent.putExtra("contact", contact); // Pass contact information
                        intent.putExtra("user_mob", user_mob);
                        context.startActivity(intent);
                        Toast.makeText(context, "dnvkn", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }




