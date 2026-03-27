package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class ConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        TextView tvDetails = findViewById(R.id.tvDetails);
        Button btnConfirm = findViewById(R.id.btnConfirm);
        Button btnEdit = findViewById(R.id.btnEdit);

        // 1. Get Data from Intent
        String type = getIntent().getStringExtra("TYPE");
        String vNum = getIntent().getStringExtra("V_NUM");
        String rcNum = getIntent().getStringExtra("RC_NUM");

        // 2. Display Data
        tvDetails.setText("Vehicle Type: " + type + "\n\n" +
                "Vehicle No: " + vNum + "\n\n" +
                "RC No: " + rcNum);

        // 3. Handle Edit (Go Back)
        btnEdit.setOnClickListener(v -> {
            finish(); // Closes this activity and returns to MainActivity
        });

        // 4. Handle Confirm (Generate ID)
        btnConfirm.setOnClickListener(v -> {
            int serialNumber = new Random().nextInt(10000); // Random ID
            Toast.makeText(this, "Allotment Confirmed! Serial ID: " + serialNumber, Toast.LENGTH_LONG).show();

            // Optional: Close app or return to start after confirmation
            // finishAffinity();
        });
    }
}