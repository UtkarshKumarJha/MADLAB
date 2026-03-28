package com.example.myapplication; // Change this to your package name!

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialize Views
        EditText etInputData = findViewById(R.id.etInputData);
        RadioGroup rgOptions = findViewById(R.id.rgOptions);
        Spinner spinnerItems = findViewById(R.id.spinnerItems);
        CheckBox cbConfirm = findViewById(R.id.cbConfirm);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        // 2. Setup Spinner Data
        String[] dropdownData = {"Item A", "Item B", "Item C"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dropdownData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItems.setAdapter(adapter);

        // 3. Handle Button Click
        btnSubmit.setOnClickListener(v -> {
            // --- EXTRACT DATA ---
            String textData = etInputData.getText().toString().trim();
            String dropdownSelection = spinnerItems.getSelectedItem().toString();
            String checkStatus = cbConfirm.isChecked() ? "Yes" : "No";

            // Extract Radio Button safely (prevents crash if none selected)
            String radioSelection = "None";
            int selectedId = rgOptions.getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton selectedRb = findViewById(selectedId);
                radioSelection = selectedRb.getText().toString();
            }

            // --- VALIDATION ---
            if (textData.isEmpty()) {
                etInputData.setError("This field is required!");
                Toast.makeText(this, "Please enter data", Toast.LENGTH_SHORT).show();
                return; // Stop execution here
            }

            // --- PASS DATA TO NEXT ACTIVITY ---
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("TEXT_KEY", textData);
            intent.putExtra("RADIO_KEY", radioSelection);
            intent.putExtra("SPINNER_KEY", dropdownSelection);
            intent.putExtra("CHECK_KEY", checkStatus);
            startActivity(intent);
        });
    }
}