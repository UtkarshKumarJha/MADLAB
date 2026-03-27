package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        TextView tvSummary = findViewById(R.id.tvFinalSummary);
        Button btnBack = findViewById(R.id.btnBack);

        String date = getIntent().getStringExtra("DATE");
        String zone = getIntent().getStringExtra("ZONE");

        // Logic to determine deployment string based on Zone selected
        String deploymentText = "";
        if (zone != null) {
            switch (zone) {
                case "North": deploymentText = "4 men are deployed"; break;
                case "South": deploymentText = "3 men are deployed"; break;
                case "East":  deploymentText = "2 men are deployed"; break;
                case "West":  deploymentText = "4 women are deployed"; break;
            }
        }

        String summary = "Date: " + date + "\n\n" +
                "Zone: " + zone + "\n\n" +
                "Status: " + deploymentText;

        tvSummary.setText(summary);

        // Close this page to go back
        btnBack.setOnClickListener(v -> finish());
    }
}