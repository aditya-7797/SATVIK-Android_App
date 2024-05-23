package com.example.satvik;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class create_account extends AppCompatActivity {

    private EditText etFullName, etMobileNo, etPassword, etConfirmPassword;
    private Button btnCreateAccount;
    private ProgressBar progressBar;

    private Button goto_main_activity;


    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_acc);
        
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        etFullName = findViewById(R.id.create_fullname);
        etMobileNo = findViewById(R.id.mobile_no);
        etPassword = findViewById(R.id.password_);
        etConfirmPassword = findViewById(R.id.confirm_password);
        progressBar = findViewById(R.id.progressBar);

        btnCreateAccount = findViewById(R.id.button1); // Correct button ID

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(create_account.this,MainActivity.class);
                startActivity(intent);
                registerUser();

                // Register user when this button is clicked
            }
        });
    }


    private void registerUser() {
        String fullName = etFullName.getText().toString().trim();
        String mobileNo = etMobileNo.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (validateInputs(fullName, mobileNo, password, confirmPassword)) {
            progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(mobileNo + "@mobile.mo", password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Toast.makeText(create_account.this, "Account created successfully", Toast.LENGTH_SHORT).show();

                                // Save user details to Firestore
                                saveUserDetails(fullName, mobileNo);

                                // Clear EditText fields
                                clearInputFields();

                            } else {
                                Toast.makeText(create_account.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void clearInputFields() {
        etFullName.setText("");
        etMobileNo.setText("");
        etPassword.setText("");
        etConfirmPassword.setText("");
    }


    private boolean validateInputs(String fullName, String mobileNo, String password, String confirmPassword) {
        if (fullName.isEmpty() || mobileNo.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidMobileNo(mobileNo)) {
            Toast.makeText(this, "Invalid mobile number", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isValidMobileNo(String mobileNo) {
        return mobileNo.length() == 10;
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    private void saveUserDetails(String fullName, String mobileNo) {
        Map<String, Object> user = new HashMap<>();
        user.put("fullname", fullName);  // String datatype
        user.put("mobile_no", mobileNo);  // String datatype

        db.collection("users").document(mobileNo)
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(create_account.this, "User details saved successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(create_account.this, "Error saving user details", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
