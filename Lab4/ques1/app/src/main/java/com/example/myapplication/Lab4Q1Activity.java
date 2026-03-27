package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;

public class Lab4Q1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab4_q1);

        Button btnSimple = findViewById(R.id.btnSimple);
        ToggleButton toggleBtn = findViewById(R.id.toggleBtn);

        // 1. Handle Normal Button Click
        btnSimple.setOnClickListener(v -> {
            showCustomToast("Button Clicked!", android.R.drawable.ic_dialog_email);
        });

        // 2. Handle Toggle Button Click
        toggleBtn.setOnClickListener(v -> {
            if (toggleBtn.isChecked()) {
                showCustomToast("Toggle is ON", android.R.drawable.ic_lock_idle_alarm);
            } else {
                showCustomToast("Toggle is OFF", android.R.drawable.ic_delete);
            }
        });
    }

    // Helper method to show the custom toast
    private void showCustomToast(String message, int imageResId) {
        // 1. Inflate the custom layout
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_layout, null);

        // 2. Set the Image and Text
        ImageView image = layout.findViewById(R.id.toastImage);
        TextView text = layout.findViewById(R.id.toastText);

        image.setImageResource(imageResId); // Set the different image
        text.setText(message);

        // 3. Create and show the Toast
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout); // Set the custom view
        toast.show();
    }
}