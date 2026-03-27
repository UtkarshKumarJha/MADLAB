package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;

public class NetworkToggleActivity extends AppCompatActivity {

    ImageView imgNetwork;
    ToggleButton toggleNetwork;
    Button btnChangeMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_toggle);

        imgNetwork = findViewById(R.id.imgNetwork);
        toggleNetwork = findViewById(R.id.toggleNetwork);
        btnChangeMode = findViewById(R.id.btnChangeMode);

        // 1. Set Initial State (Wi-Fi)
        updateUI(false);

        // 2. Listener for the ToggleButton
        toggleNetwork.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateUI(isChecked);
        });

        // 3. Listener for the "Change Mode" Button
        btnChangeMode.setOnClickListener(v -> {
            // Get current state, flip it, and set it back to the toggle
            boolean currentState = toggleNetwork.isChecked();
            toggleNetwork.setChecked(!currentState); // This triggers the listener above automatically
        });
    }

    // Helper function to update Image and Toast based on state
    private void updateUI(boolean isMobileData) {
        if (isMobileData) {
            // State: Mobile Data
            imgNetwork.setImageResource(android.R.drawable.ic_menu_call); // Replace with your Data icon
            Toast.makeText(this, "Mode: Mobile Data", Toast.LENGTH_SHORT).show();
        } else {
            // State: Wi-Fi
            imgNetwork.setImageResource(android.R.drawable.ic_menu_wifi); // Replace with your Wi-Fi icon (or generic system icon)
            Toast.makeText(this, "Mode: Wi-Fi", Toast.LENGTH_SHORT).show();
        }
    }
}