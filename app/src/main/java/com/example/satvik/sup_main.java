package com.example.satvik;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class sup_main extends AppCompatActivity {

    private TextView go_to_addmenu;
    private ImageView go_to_suppliers_setting;
    private TextView go_to_registered_students;
    private View go_to_new_orders;
    private View go_to_delete_menu;

    private String suppMobile;
    private String Suppliers_name;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sup_main);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        go_to_addmenu = findViewById(R.id.add_menu);
        go_to_registered_students = findViewById(R.id.view_registered_students);
        go_to_suppliers_setting = findViewById(R.id._vector_ek6);
        go_to_new_orders = findViewById(R.id.rectangle_26);
        go_to_delete_menu = findViewById(R.id.rectangle_26_ek2);

        Intent i = getIntent();
        suppMobile = i.getStringExtra("mobile");

        Toast.makeText(sup_main.this, "Supplier Login: " + suppMobile, Toast.LENGTH_SHORT).show();

        // Retrieve and store the supplier's name
        getSupplierName(suppMobile);

        go_to_addmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sup_main.this, add_menu.class);
                intent.putExtra("mobile1", suppMobile);
                intent.putExtra("supplier_name", Suppliers_name);
                startActivity(intent);
            }
        });

        go_to_registered_students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sup_main.this, view_registered_students.class);
                intent.putExtra("mobile1", suppMobile);
                intent.putExtra("supplier_name", Suppliers_name);
                startActivity(intent);
            }
        });

        go_to_suppliers_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sup_main.this, Suppliers_setting.class);
                intent.putExtra("supmobile", suppMobile);
                intent.putExtra("supplier_name", Suppliers_name);
                startActivity(intent);
            }
        });

        go_to_new_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sup_main.this, NewOrderActivity.class);
                intent.putExtra("supmobile", suppMobile);
                intent.putExtra("supplier_name", Suppliers_name);
                startActivity(intent);
            }
        });

        go_to_delete_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sup_main.this, DeleteMenuActivity.class);
                intent.putExtra("Sup_mobile", suppMobile);
                intent.putExtra("supplier_name", Suppliers_name);
                startActivity(intent);
            }
        });
    }

    private void getSupplierName(String mobile) {
        db.collection("suppliers_acc").document(mobile).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Suppliers_name = document.getString("fullname");
                                // Log or handle the retrieved supplier name as needed
                                Toast.makeText(sup_main.this, "Supplier Name: " + Suppliers_name, Toast.LENGTH_SHORT).show();
                            } else {
                                Suppliers_name = "No such supplier";
                                Toast.makeText(sup_main.this, Suppliers_name, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Suppliers_name = "Error getting supplier name";
                            Toast.makeText(sup_main.this, Suppliers_name, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
