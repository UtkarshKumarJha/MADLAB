package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EventDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new EventDBHelper(this);
        EditText etName = findViewById(R.id.etName);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields for registration", Toast.LENGTH_SHORT).show();
                return;
            }

            if(db.registerUser(name, email, password)) {
                Toast.makeText(this, "Registration Successful! You can now login.", Toast.LENGTH_SHORT).show();
                etName.setText(""); // Clear name field for convenience
            } else {
                Toast.makeText(this, "Registration failed. Email might already exist.", Toast.LENGTH_SHORT).show();
            }
        });

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            if(db.checkLogin(email, password)) {
                // Save email to SharedPreferences for easy access later
                SharedPreferences.Editor editor = getSharedPreferences("UserPrefs", MODE_PRIVATE).edit();
                editor.putString("LOGGED_IN_EMAIL", email);
                editor.apply();

                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();

                // Redirect based on role: admin@gmail.com goes to EventManageActivity, others to StudentEventActivity
                Intent intent;
                intent = new Intent(this, EventManageActivity.class);
                startActivity(intent);
                finish(); // Prevent going back to login screen
            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
