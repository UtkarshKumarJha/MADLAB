package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    CheckBox cbPizza, cbBurger, cbPasta, cbFries;
    Button btnSubmitOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        cbPizza = findViewById(R.id.cbPizza);
        cbBurger = findViewById(R.id.cbBurger);
        cbPasta = findViewById(R.id.cbPasta);
        cbFries = findViewById(R.id.cbFries);
        btnSubmitOrder = findViewById(R.id.btnSubmitOrder);

        btnSubmitOrder.setOnClickListener(v -> {
            StringBuilder orderDetails = new StringBuilder();
            int totalCost = 0;

            // Check which items are selected and add their cost
            if (cbPizza.isChecked()) {
                orderDetails.append("Pizza : Rs. 200\n");
                totalCost += 200;
            }
            if (cbBurger.isChecked()) {
                orderDetails.append("Burger : Rs. 100\n");
                totalCost += 100;
            }
            if (cbPasta.isChecked()) {
                orderDetails.append("Pasta : Rs. 150\n");
                totalCost += 150;
            }
            if (cbFries.isChecked()) {
                orderDetails.append("French Fries : Rs. 80\n");
                totalCost += 80;
            }

            // Validation: Ensure at least one item is selected
            if (totalCost == 0) {
                Toast.makeText(this, "Please select at least one item to order.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Requirement: Lock the order (Disable checkboxes and button)
            cbPizza.setEnabled(false);
            cbBurger.setEnabled(false);
            cbPasta.setEnabled(false);
            cbFries.setEnabled(false);
            btnSubmitOrder.setEnabled(false);
            btnSubmitOrder.setText("Order Submitted");

            // Pass the details to the Summary Activity
            Intent intent = new Intent(MainActivity.this, OrderSummaryActivity.class);
            intent.putExtra("ORDER_DETAILS", orderDetails.toString());
            intent.putExtra("TOTAL_COST", String.valueOf(totalCost));
            startActivity(intent);
        });
    }
}