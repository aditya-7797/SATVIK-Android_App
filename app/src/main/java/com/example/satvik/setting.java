package com.example.satvik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class setting extends AppCompatActivity {
        private TextView  go_to_login;
        private TextView go_to_signUp;

        private  TextView go_to_Supplier_SignUp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

    go_to_login= findViewById(R.id.go_to_login);
    go_to_Supplier_SignUp= findViewById(R.id.join_as_supplier);

    go_to_login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(setting.this,login.class);
            startActivity(intent);
        }
    });

    go_to_Supplier_SignUp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(setting.this,create_account_supplier.class);
            startActivity(intent);
        }
    });
    }
}
