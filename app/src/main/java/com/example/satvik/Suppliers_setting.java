package com.example.satvik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Suppliers_setting extends AppCompatActivity {

    private View go_to_about_us;

    private View go_to_report_problem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppliers_setting);



        String sup_mobile;
        String sup_name;

        Intent i = getIntent();
        sup_name = i.getStringExtra("supplier_name");
        sup_mobile = i.getStringExtra("Sup_mobile");


        TextView userNameTextView = findViewById(R.id.supplier_name);
        TextView userContactTextView = findViewById(R.id.supplier_mobile);

        userNameTextView.setText(sup_name);
        userContactTextView.setText(sup_mobile);


        Button go_to_login = findViewById(R.id.go_to_login);
        go_to_about_us=findViewById(R.id.rectangle_24_ek4);
        go_to_report_problem = findViewById(R.id.rectangle_24_ek5);

        go_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Suppliers_setting.this, login.class);
                startActivity(intent);
            }
        });

        go_to_about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Suppliers_setting.this, About_us.class);
                startActivity(intent);
            }
        });

        go_to_report_problem.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v){
                Intent intent = new Intent(Suppliers_setting.this, Report_Problem.class);
                startActivity(intent);
            }
        });
    }
}

