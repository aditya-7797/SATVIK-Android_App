package com.example.satvik;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHollder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView nameView,address_view,category_view;



    public MyViewHollder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.image_profile);
        nameView = itemView.findViewById(R.id.madhavi_bhide_ek2);
        address_view = itemView.findViewById(R.id.upper_indiranagar);
        category_view = itemView.findViewById(R.id.serves_western_maharashtrian_food);
    }
}
