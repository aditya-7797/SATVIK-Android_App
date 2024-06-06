package com.example.satvik;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DeleteMenuActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DeleteMenuAdapter deleteMenuAdapter;
    private List<DeleteMenuItem> menuItems;
    private FirebaseFirestore db;
    private String suppliersMobileNo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_menu);

        Intent i = getIntent();
        suppliersMobileNo= i.getStringExtra("Sup_mobile");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        menuItems = new ArrayList<>();
        deleteMenuAdapter = new DeleteMenuAdapter(this, menuItems, suppliersMobileNo);
        recyclerView.setAdapter(deleteMenuAdapter);

        db = FirebaseFirestore.getInstance();

        db.collection("suppliers_acc")
                .document(suppliersMobileNo)
                .collection("menu")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        DeleteMenuItem menuItem = document.toObject(DeleteMenuItem.class);
                        menuItem.setDocumentId(document.getId()); // Assumes that DeleteMenuItem has a setDocumentId method
                        menuItems.add(menuItem);
                    }
                    deleteMenuAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(DeleteMenuActivity.this, "Error loading data", Toast.LENGTH_SHORT).show();
                });
    }
}
