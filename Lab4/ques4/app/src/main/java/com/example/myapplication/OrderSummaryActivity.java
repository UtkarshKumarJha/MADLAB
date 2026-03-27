package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class OrderSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        TextView tvOrderDetails = findViewById(R.id.tvOrderDetails);
        TextView tvTotalCost = findViewById(R.id.tvTotalCost);

        // Get Data from Intent
        String details = getIntent().getStringExtra("ORDER_DETAILS");
        String total = getIntent().getStringExtra("TOTAL_COST");

        // Display Data
        tvOrderDetails.setText("Items Ordered:\n\n" + details);
        tvTotalCost.setText("Total Amount: Rs. " + total);
    }
}