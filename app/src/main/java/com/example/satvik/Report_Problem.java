package com.example.satvik;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Report_Problem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_problem);

        TextView emailTextView = findViewById(R.id.emailTextView);
        emailTextView.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "satvik0246@gmail.com", null));
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        });
    }
}