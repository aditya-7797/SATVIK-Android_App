package com.example.satvik;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String usermobile;
    private ImageView go_to_setting;
    private EditText editTextSearch;
    private RecyclerView recyclerView;
    private MyAdapter_Profile adapter;
    private String Mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        go_to_setting = findViewById(R.id.mpagesetting);
        editTextSearch = findViewById(R.id.editTextSearch);
        recyclerView = findViewById(R.id.recyclerview_profile);

        Intent intu = getIntent();
        Mobile = intu.getStringExtra("Mobile");

        if (Mobile != null) {
            Toast.makeText(MainActivity.this, Mobile, Toast.LENGTH_SHORT).show();
            usermobile = Mobile;
        } else {
            Toast.makeText(MainActivity.this, "No mobile number provided", Toast.LENGTH_SHORT).show();
        }

        go_to_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, setting.class);
                intent.putExtra("Users_No", usermobile);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter_Profile(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Supplier> items = new ArrayList<>();

        db.collection("suppliers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Supplier supplier = document.toObject(Supplier.class);
                                supplier.setMobile(document.getString("mobile"));
                                items.add(supplier);
                            }
                            Log.d("MainActivity", "Items size: " + items.size());
                            if (!items.isEmpty()) {
                                adapter.setItems(items);

                                editTextSearch.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        adapter.getFilter().filter(s);
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {}
                                });
                            } else {
                                Toast.makeText(MainActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                                Log.e("MainActivity", "No data available");
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                            Log.e("MainActivity", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
