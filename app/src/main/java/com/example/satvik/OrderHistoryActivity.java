package com.example.satvik;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderHistoryMenuAdapter adapter;
    private List<OrderHistoryMenuItem> menuItemList;
    private FirebaseFirestore firestore;
    String User_No;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        menuItemList = new ArrayList<>();

        Intent i = getIntent();
        User_No = i.getStringExtra("user_No");

        firestore = FirebaseFirestore.getInstance();
        loadMenuItems();

        adapter = new OrderHistoryMenuAdapter(menuItemList, this);
        recyclerView.setAdapter(adapter);
    }

    private void loadMenuItems() {
        String userMobileNumber = User_No ;  // Replace with actual user mobile number
        CollectionReference ordersRef = firestore.collection("users")
                .document(userMobileNumber)
                .collection("ordering_history");

        ordersRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    OrderHistoryMenuItem menuItem = document.toObject(OrderHistoryMenuItem.class);
                    Toast.makeText(this, " "+menuItem.getSuppliers_Contact(), Toast.LENGTH_SHORT).show();
                    menuItemList.add(menuItem);
                }
                adapter.notifyDataSetChanged();
            } else {
                // Handle the error
            }
        });
    }
}
