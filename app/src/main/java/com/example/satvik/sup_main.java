package com.example.satvik;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public  class sup_main extends AppCompatActivity {

    private TextView go_to_addmenu;
    String suppMobile;

    String suppmob;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sup_main);


        go_to_addmenu = findViewById(R.id.add_menu);

        Intent i = getIntent();
        suppMobile = i.getStringExtra("mobile");
//        suppMobile = i.getStringExtra("mobilee");

        Toast.makeText(sup_main.this, "Supplier Login: "+suppMobile, Toast.LENGTH_SHORT).show();

//
//        Intent j = getIntent();
//        suppmob = j.getStringExtra("mobilee");
//        Toast.makeText(sup_main.this, "Supplier Login: "+suppmob, Toast.LENGTH_SHORT).show();

//        suppMobile = savedInstanceState.getString("mobile");
        go_to_addmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sup_main.this, add_menu.class);
                intent.putExtra("mobile1", suppMobile);
                startActivity(intent);
            }
        });

    }

}
