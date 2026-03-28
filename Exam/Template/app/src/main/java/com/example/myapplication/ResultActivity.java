package com.example.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result); // Make sure you create this XML!

        TextView tvSummary = findViewById(R.id.tvSummary); // Make sure you have this ID in XML

        // 1. Receive Data
        String tData = getIntent().getStringExtra("TEXT_KEY");
        String rData = getIntent().getStringExtra("RADIO_KEY");
        String sData = getIntent().getStringExtra("SPINNER_KEY");
        String cData = getIntent().getStringExtra("CHECK_KEY");

        // 2. Display Data
        String finalOutput = "Text: " + tData + "\n" +
                "Radio: " + rData + "\n" +
                "Spinner: " + sData + "\n" +
                "Checked: " + cData;
        tvSummary.setText(finalOutput);
    }

    // --- OPTIONS MENU BOILERPLATE ---

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Option 1: Inflate from XML (Best practice)
        // getMenuInflater().inflate(R.menu.my_menu, menu);

        // Option 2: Add programmatically (Quickest in an exam if you forget XML syntax)
        menu.add(0, 1, 0, "Action 1");
        menu.add(0, 2, 0, "Action 2");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) { // Or R.id.action_1 if using XML
            Toast.makeText(this, "Action 1 Clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == 2) {
            Toast.makeText(this, "Action 2 Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}