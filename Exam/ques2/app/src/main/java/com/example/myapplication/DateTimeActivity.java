package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.PopupMenu;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.SummaryActivity;

import java.util.Calendar;

public class DateTimeActivity extends AppCompatActivity {
    String selectedDate = "Not Selected";
    String selectedTime = "Not Selected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);

        Button btnPickDate = findViewById(R.id.btnPickDate);
        Button btnPickTime = findViewById(R.id.btnPickTime);
        Button btnShowPopup = findViewById(R.id.btnShowPopup);

        // Get data from Page 3 to carry it forward
        String topic = getIntent().getStringExtra("TOPIC");
        String guide = getIntent().getStringExtra("GUIDE");

        // Date Picker
        btnPickDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, day) -> {
                selectedDate = day + "/" + (month + 1) + "/" + year;
                btnPickDate.setText("Date: " + selectedDate);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        // Time Picker
        btnPickTime.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new TimePickerDialog(this, (view, hour, minute) -> {
                selectedTime = hour + ":" + minute;
                btnPickTime.setText("Time: " + selectedTime);
            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
        });

        // Popup Menu Logic
        btnShowPopup.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(DateTimeActivity.this, v);
            popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_summary) {
                    Intent intent = new Intent(DateTimeActivity.this, SummaryActivity.class);
                    // Pass Page 3 AND Page 4 data
                    intent.putExtra("TOPIC", topic);
                    intent.putExtra("GUIDE", guide);
                    intent.putExtra("DATE", selectedDate);
                    intent.putExtra("TIME", selectedTime);
                    startActivity(intent);
                    return true;
                }
                return false;
            });
            popup.show();
        });
    }
}