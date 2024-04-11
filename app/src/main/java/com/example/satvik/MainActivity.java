package com.example.satvik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView go_to_profile;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        go_to_profile=findViewById(R.id.non_veg);
        go_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,profile.class);
                startActivity(intent);
            }
        });

        //fab =  findViewById(R.id.fab);


        RecyclerView recyclerView = findViewById(R.id.recyclerview_profile);

        List<item> items = new ArrayList<>();
        items.add(new item("Aditya Shinde","Katraj","serves all food",R.drawable.proimage));
        items.add(new item("Aditya Bhendavdekar","Katraj","serves all food",R.drawable.proimage));
        items.add(new item("Aditya Vaidya","Katraj","serves all food",R.drawable.proimage));
        items.add(new item("Aditya Lad","Katraj","serves all food",R.drawable.proimage));
        items.add(new item("Aditya Nalawde","Katraj","serves all food",R.drawable.proimage));
        items.add(new item("Aditya Narayan","Katraj","serves all food",R.drawable.proimage));
        items.add(new item("Aditya Maske","Katraj","serves all food",R.drawable.proimage));
        items.add(new item("Aditya Shinde","Katraj","serves all food",R.drawable.proimage));
        items.add(new item("Aditya Bhendavdekar","Katraj","serves all food",R.drawable.proimage));
        items.add(new item("Aditya Vaidya","Katraj","serves all food",R.drawable.proimage));
        items.add(new item("Aditya Lad","Katraj","serves all food",R.drawable.proimage));
        items.add(new item("Aditya Nalawde","Katraj","serves all food",R.drawable.proimage));
        items.add(new item("Aditya Narayan","Katraj","serves all food",R.drawable.proimage));
        items.add(new item("Aditya Maske","Katraj","serves all food",R.drawable.proimage));



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter( new MyAdapter_Profile(getApplicationContext(),items));
    }

}