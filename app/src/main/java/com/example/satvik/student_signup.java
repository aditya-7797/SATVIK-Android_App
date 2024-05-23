package com.example.satvik;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class student_signup extends AppCompatActivity {
    private TextView go_to_create_acc;
    private TextView go_to_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_signup);

        go_to_create_acc = findViewById(R.id.createacc);
        go_to_login = findViewById(R.id._already_a_member__login_here);


        go_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(student_signup.this, login.class);
                startActivity(intent);
            }
        });

        go_to_create_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(student_signup.this, create_account.class);
                startActivity(intent);
            }
        });
    }
}
