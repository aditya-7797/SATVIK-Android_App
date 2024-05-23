package com.example.satvik;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.UserProfileChangeRequest;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class create_account_supplier extends AppCompatActivity {
    private EditText fullNameEditText, mobileNoEditText, passwordEditText, confirmPasswordEditText, addressEditText, categoryEditText;
    private Button createAccountButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_supplier);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        fullNameEditText = findViewById(R.id.create_fullname_supplier);
        mobileNoEditText = findViewById(R.id.mobile_no_supplier);
        passwordEditText = findViewById(R.id.password_supplier);
        confirmPasswordEditText = findViewById(R.id.confirm_password_supplier);
        addressEditText = findViewById(R.id.address);
        categoryEditText = findViewById(R.id.category);
        createAccountButton = findViewById(R.id.create_supplier_button);
        progressBar = findViewById(R.id.progressBar);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullNameEditText.getText().toString().trim();
                String mobileNo = mobileNoEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();
                String address = addressEditText.getText().toString().trim();
                String category = categoryEditText.getText().toString().trim();

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(create_account_supplier.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(mobileNo + "@example.com", password)
                        .addOnCompleteListener(create_account_supplier.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(fullName)
                                            .build();
                                    user.updateProfile(profileUpdates);

                                    // Store fullname and mobile_no in suppliers_acc collection
                                    Map<String, Object> supplierAcc = new HashMap<>();
                                    supplierAcc.put("fullname", fullName);
                                    supplierAcc.put("mobile_no", mobileNo);

                                    db.collection("suppliers_acc").document(mobileNo).set(supplierAcc)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    // Store additional information in suppliers collection
                                                    Map<String, Object> supplier = new HashMap<>();
                                                    supplier.put("fullname", fullName);
                                                    supplier.put("address", address);
                                                    supplier.put("category", category);
                                                    supplier.put("mobile", mobileNo);

                                                    db.collection("suppliers").document(mobileNo).set(supplier)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    progressBar.setVisibility(View.GONE);
                                                                    Toast.makeText(create_account_supplier.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(create_account_supplier.this,sup_main.class);
                                                                    intent.putExtra("mobile", mobileNo);
                                                                    startActivity(intent);
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    progressBar.setVisibility(View.GONE);
                                                                    Toast.makeText(create_account_supplier.this, "Error creating account", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressBar.setVisibility(View.GONE);
                                                    Toast.makeText(create_account_supplier.this, "Error creating account", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(create_account_supplier.this, "Error creating account", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
