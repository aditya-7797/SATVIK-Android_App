package com.example.satvik;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class supplier_register extends AppCompatActivity {

    private EditText addressEditText, categoryEditText;
    private Button submitButton;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supplier_register);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        addressEditText = findViewById(R.id.address);
        categoryEditText = findViewById(R.id.category);
        submitButton = findViewById(R.id.supplier_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerSupplier();
            }
        });
    }

    private void registerSupplier() {
        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get mobile number from Firebase Auth
        String mobile = currentUser.getPhoneNumber();

        // Fetch full name from suppliers_acc collection
        db.collection("suppliers_acc").document(mobile)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String fullName = documentSnapshot.getString("fullname");
                        String address = addressEditText.getText().toString().trim();
                        String category = categoryEditText.getText().toString().trim();

                        // Check if all fields are filled
                        if (address.isEmpty() || category.isEmpty()) {
                            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Store data in suppliers collection
                        Map<String, Object> supplierData = new HashMap<>();
                        supplierData.put("fullname", fullName);
                        supplierData.put("mobile_no", mobile);
                        supplierData.put("address", address);
                        supplierData.put("category", category);

                        db.collection("suppliers").document(mobile)
                                .set(supplierData)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(supplier_register.this, "Supplier registered successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(supplier_register.this, sup_main.class);
                                    startActivity(intent);
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(supplier_register.this, "Error registering supplier: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Toast.makeText(supplier_register.this, "Supplier data not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(supplier_register.this, "Error fetching supplier data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
