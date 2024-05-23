package com.example.satvik;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class MyViewHollder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView nameView, addressView, categoryView;
    RelativeLayout connectLayout; // Reference to the RelativeLayout
    Context context;

    // Add field for contact information
    private String contact;
    private String profile_fullname;

    private String profile_address;

    private String profile_category;

    public String user_mob= MainActivity.usermobile;

    public MyViewHollder(@NonNull View itemView) {
        super(itemView);

        context = itemView.getContext(); // Get the context

        imageView = itemView.findViewById(R.id.image_profile);
        nameView = itemView.findViewById(R.id.madhavi_bhide_ek2);
        addressView = itemView.findViewById(R.id.upper_indiranagar);
        categoryView = itemView.findViewById(R.id.serves_western_maharashtrian_food);

        // Reference to the RelativeLayout
        connectLayout = itemView.findViewById(R.id.group_6);

        // Set OnClickListener to the RelativeLayout
        connectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // Handle navigation here
                    // For example, start a new activity
                    Intent intent = new Intent(context, profile.class);

                    intent.putExtra("contact", contact); // Pass contact information
                    intent.putExtra("profile_fullname", profile_fullname); // Pass contact
                    // information
                    intent.putExtra("profile_address", profile_address); // Pass contact information
                    intent.putExtra("profile_category", profile_category); // Pass contact information
                    intent.putExtra("user_mob",user_mob);


                    context.startActivity(intent);
                }
            }
        });
    }

    // Your existing bind method
    public void bind(Supplier supplier) {
        nameView.setText(supplier.getFullname());
        addressView.setText(supplier.getAddress());
        categoryView.setText(supplier.getCategory());
//        Picasso.get().load(supplier.getImageUrl()).into(imageView);

        // Set contact information
        contact = supplier.getMobile();
        profile_fullname= supplier.getFullname();
        profile_address= supplier.getAddress();
        profile_category= supplier.getCategory();
    }
}
