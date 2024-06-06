package com.example.satvik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class setting extends AppCompatActivity {
        private TextView  go_to_login;

        private  TextView go_to_Supplier_SignUp;

        private View go_to_ordering_history;

        private View go_to_about_us;

    private View go_to_report_problem;

    private View go_to_ordering_help;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

    go_to_login= findViewById(R.id.go_to_login);
    go_to_Supplier_SignUp= findViewById(R.id.join_as_supplier);
    go_to_ordering_history=findViewById(R.id.rectangle_24_ek1);
    go_to_about_us=findViewById(R.id.rectangle_24_ek4);
        go_to_report_problem = findViewById(R.id.rectangle_24_ek5);
        go_to_ordering_help = findViewById(R.id.rectangle_24_ek2);



        Intent intu = getIntent();
        String user_No = intu.getStringExtra("Users_No");

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

    go_to_ordering_history.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(setting.this,OrderHistoryActivity.class);
            intent.putExtra("user_No",user_No);
            startActivity(intent);
        }
    });

    go_to_about_us.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(setting.this, About_us.class);
            startActivity(intent);
        }
    });

        go_to_report_problem.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v){
                Intent intent = new Intent(setting.this, Report_Problem.class);
                startActivity(intent);
            }
        });

        go_to_ordering_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(setting.this,Ordering_Help.class);
                startActivity(intent);
            }
        });
    }
}
