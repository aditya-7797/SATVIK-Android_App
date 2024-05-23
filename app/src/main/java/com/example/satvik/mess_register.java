package com.example.satvik;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class mess_register extends AppCompatActivity {

    private EditText nameEditText, mobileNoEditText;
    private Button registerButton;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_register);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        nameEditText = findViewById(R.id.name);
        mobileNoEditText = findViewById(R.id.mobile_no);
        registerButton = findViewById(R.id.register);

        Intent i = getIntent();
        String contact = i.getStringExtra("contact");
        Toast.makeText(this, contact, Toast.LENGTH_SHORT).show();

        // Set OnClickListener for register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve name and mobile number
                String name = nameEditText.getText().toString().trim();
                String mobileNo = mobileNoEditText.getText().toString().trim();

                // Increment the counter for the next customer
                db.collection("suppliers_acc")
                        .document(contact)
                        .collection("registered_mess")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                int customerCount = queryDocumentSnapshots.size() + 1;
                                String customerId = "Customer" + customerCount;

                                // Create a new document with the generated ID
                                db.collection("suppliers_acc")
                                        .document(contact)
                                        .collection("registered_mess")
                                        .document(customerId)
                                        .set(new Student(name, mobileNo))
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(mess_register.this, "Student registered successfully",
                                                        Toast.LENGTH_SHORT).show();
                                                // Clear EditText fields after successful registration
                                                nameEditText.setText("");
                                                mobileNoEditText.setText("");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(mess_register.this, "Error registering student",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
            }
        });



    }
}
