package com.example.satvik;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class profile extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        Intent i = getIntent();
        String contact = i.getStringExtra("contact");
        String profile_fullname = i.getStringExtra("profile_fullname");
        String profile_address = i.getStringExtra("profile_address");
        String profile_category = i.getStringExtra("profile_category");
        String user_mobile = i.getStringExtra("user_mob");

        // Update TextViews with dynamic data
        TextView profileNameTextView = findViewById(R.id.profile_name);
        profileNameTextView.setText(profile_fullname);

        TextView profileAddressTextView = findViewById(R.id.profile_address);
        profileAddressTextView.setText(profile_address);

        TextView profileCategoryTextView = findViewById(R.id.profile_category);
        profileCategoryTextView.setText(profile_category);

        TextView contactTextView = findViewById(R.id.contact);
        contactTextView.setText(contact);

        // For demonstration purposes, you can display the received data in toasts
        Toast.makeText(profile.this, "Supplier Login: " + contact, Toast.LENGTH_SHORT).show();
        Toast.makeText(profile.this, "Supplier Login: " + profile_fullname, Toast.LENGTH_SHORT).show();
        Toast.makeText(profile.this, "Supplier Login: " + profile_category, Toast.LENGTH_SHORT).show();
        Toast.makeText(profile.this, "Supplier Login: " + profile_address, Toast.LENGTH_SHORT).show();
        Toast.makeText(profile.this, "Student Login: " + user_mobile, Toast.LENGTH_SHORT).show();

        // Check if contact is null
        if (contact != null) {
            // Assuming you have a reference to the Firestore database
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Create a reference to the menu subcollection for the supplier
            CollectionReference menuRef = db.collection("suppliers_acc").document(contact).collection("menu");

            // Initialize an empty list to hold menu items
            List<MenuItem2> menuItems = new ArrayList<>();

            // Query the menu subcollection to retrieve menu items
            menuRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            // Get data from each document in the menu subcollection
                            String itemName = documentSnapshot.getString("name");
                            String itemPrice = documentSnapshot.getString("price");

                            // Create a MenuItem object and add it to the list
                            menuItems.add(new MenuItem2(itemName, itemPrice));
                        }

                        // Initialize RecyclerView and adapter
                        RecyclerView recyclerView = findViewById(R.id.recyclerview_profile);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(profile.this); // Use profile.this as context
                        recyclerView.setLayoutManager(layoutManager);
                        MenuAdapter adapter = new MenuAdapter(menuItems, profile.this); // Pass profile.this as context

                        recyclerView.setAdapter(adapter);

                    })
                    .addOnFailureListener(e -> {
                        // Handle any errors
                        Log.e(TAG, "Error fetching menu items: ", e);
                    });
        } else {
            Log.e(TAG, "Contact is null");
        }

        Button register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(profile.this, mess_register.class);
                i.putExtra("contact", contact);
                startActivity(i);
            }
        });
    }
}
