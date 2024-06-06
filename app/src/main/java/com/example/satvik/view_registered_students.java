package com.example.satvik;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class view_registered_students extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_registered_students);

        Intent i = getIntent();
        String suppMobile = i.getStringExtra("mobile1");
        Toast.makeText(view_registered_students.this, suppMobile, Toast.LENGTH_SHORT).show();

        if (suppMobile != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference menuRef = db.collection("suppliers_acc").document(suppMobile).collection("registered_mess");

            List<RegisteredStudent> registeredStudentList = new ArrayList<>();

            menuRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    String name = documentSnapshot.getString("name");
                    String mobileNo = documentSnapshot.getString("mobileNo");

                    registeredStudentList.add(new RegisteredStudent(name, mobileNo));
                }

                RecyclerView recyclerView = findViewById(R.id.recycler_view);
                LinearLayoutManager layoutManager = new LinearLayoutManager(view_registered_students.this);
                recyclerView.setLayoutManager(layoutManager);
                RegisteredStudentAdapter adapter = new RegisteredStudentAdapter(registeredStudentList, view_registered_students.this);
                recyclerView.setAdapter(adapter);

            }).addOnFailureListener(e -> {
                Log.e(TAG, "Error fetching registered students: ", e);
            });
        } else {
            Log.e(TAG, "Contact is null");
        }
    }
}
