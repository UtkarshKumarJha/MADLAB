package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinnerVehicle = findViewById(R.id.spinnerVehicle);
        EditText etVehicleNumber = findViewById(R.id.etVehicleNumber);
        EditText etRcNumber = findViewById(R.id.etRcNumber);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        // 1. Populate the Spinner with Data
        String[] vehicleTypes = {"Car", "Bike", "Truck", "Electric Scooter"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                vehicleTypes
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVehicle.setAdapter(adapter);

        // 2. Handle Submit Button
        btnSubmit.setOnClickListener(v -> {
            String type = spinnerVehicle.getSelectedItem().toString();
            String vNum = etVehicleNumber.getText().toString();
            String rcNum = etRcNumber.getText().toString();

            if(vNum.isEmpty() || rcNum.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Pass data to the Confirmation Activity
                Intent intent = new Intent(MainActivity.this, ConfirmActivity.class);
                intent.putExtra("TYPE", type);
                intent.putExtra("V_NUM", vNum);
                intent.putExtra("RC_NUM", rcNum);
                startActivity(intent);
            }
        });
    }
}