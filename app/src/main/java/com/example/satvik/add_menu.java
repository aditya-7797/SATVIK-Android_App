package com.example.satvik;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class add_menu extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText itemName;
    private EditText itemPrice;
    private Button addItem;
    private ImageView itemImage;
    private Uri imageUri;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db;
    String suppMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_menu);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        Intent i = getIntent();
        suppMobile = i.getStringExtra("mobile1");
        Toast.makeText(add_menu.this, "Supplier Login: " + suppMobile, Toast.LENGTH_SHORT).show();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding item...");

        itemName = findViewById(R.id.item_name);
        itemPrice = findViewById(R.id.price);
        addItem = findViewById(R.id.add_menu_button);
        itemImage = findViewById(R.id.item_image);

        itemImage.setOnClickListener(v -> openFileChooser());

        addItem.setOnClickListener(v -> {
            String name = itemName.getText().toString().trim();
            String price = itemPrice.getText().toString().trim();

            if (name.isEmpty() || price.isEmpty() || imageUri == null) {
                Toast.makeText(add_menu.this, "Please fill all the details and select an image.", Toast.LENGTH_SHORT).show();
            } else {
                uploadData(name, price, imageUri.toString());
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(itemImage);
        }
    }

    private void uploadData(String name, String price, String imageUrl) {
        progressDialog.show();

        if (currentUser != null) {

            CollectionReference suppliersAccCollectionRef = db.collection("suppliers_acc");

            DocumentReference supplierDocRef = suppliersAccCollectionRef.document(suppMobile);

            supplierDocRef.collection("menu")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        int itemCount = queryDocumentSnapshots.size();
                        String itemId = "item" + (itemCount + 1);

                        Map<String, Object> menuItem = new HashMap<>();
                        menuItem.put("name", name);
                        menuItem.put("price", price);
                        menuItem.put("imageUrl", imageUrl);

                        supplierDocRef.collection("menu").document(itemId)
                                .set(menuItem)
                                .addOnSuccessListener(documentReference -> {
                                    progressDialog.dismiss();
                                    Toast.makeText(add_menu.this, "Item added successfully.", Toast.LENGTH_SHORT).show();
                                    itemName.setText("");
                                    itemPrice.setText("");
                                    itemImage.setImageResource(R.drawable.rectangle_16); // Set to your default image resource
                                    imageUri = null; // Clear the image URI
                                })
                                .addOnFailureListener(e -> {
                                    progressDialog.dismiss();
                                    Log.e("Firestore Error", "Error adding document", e);
                                    Toast.makeText(add_menu.this, "Failed to add item to Firestore.", Toast.LENGTH_SHORT).show();
                                });
                    })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Log.e("Firestore Error", "Error getting document count", e);
                        Toast.makeText(add_menu.this, "Failed to get item count from Firestore.", Toast.LENGTH_SHORT).show();
                    });

        } else {
            progressDialog.dismiss();
            Toast.makeText(add_menu.this, "User not authenticated.", Toast.LENGTH_SHORT).show();
            Log.e("Authentication Error", "User not authenticated.");
        }
    }
}
