package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TicketDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_details);

        TextView tvTicketInfo = findViewById(R.id.tvTicketInfo);
        Button btnBack = findViewById(R.id.btnBack);

        // Get Data
        String source = getIntent().getStringExtra("SOURCE");
        String dest = getIntent().getStringExtra("DEST");
        String date = getIntent().getStringExtra("DATE");
        String type = getIntent().getStringExtra("TYPE");

        // Display Data
        String info = "From: " + source + "\n" +
                "To: " + dest + "\n" +
                "Date: " + date + "\n" +
                "Type: " + type;

        tvTicketInfo.setText(info);

        btnBack.setOnClickListener(v -> finish());
    }
}