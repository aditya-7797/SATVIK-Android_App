package com.example.satvik;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class NewOrderActivity extends AppCompatActivity {
    private static final String TAG = "NewOrderActivity";

    private RecyclerView recyclerView;
    private NewOrderAdapter newOrderAdapter;
    private List<NewOrder> newOrderList;

    private String suppliersContact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_orders);

        Intent i = getIntent();
        suppliersContact = i.getStringExtra("supmobile");
        Toast.makeText(this, suppliersContact, Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.new_order_recyclers_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        newOrderList = new ArrayList<>();
        newOrderAdapter = new NewOrderAdapter(newOrderList, this, suppliersContact);
        recyclerView.setAdapter(newOrderAdapter);

        loadNewOrdersFromDatabase();
    }

    private void loadNewOrdersFromDatabase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("suppliers_acc")
                .document(suppliersContact)
                .collection("new_orders")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String itemName = document.getString("itemName");
                            int quantity = document.getLong("quantity").intValue();
                            String userName = document.getString("userName");
                            String userContact = document.getString("userMobileNumber");
                            String documentId = document.getId();

                            NewOrder newOrder = new NewOrder(itemName, quantity, userName, userContact, documentId);
                            newOrderList.add(newOrder);
                        }
                        newOrderAdapter.notifyDataSetChanged();
                    } else {
                        Log.e(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }
}
