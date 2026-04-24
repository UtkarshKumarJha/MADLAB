package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class EventManageActivity extends AppCompatActivity {
    EventDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_manage);

        db = new EventDBHelper(this);
        EditText etSerial = findViewById(R.id.etSerialNo);
        EditText etName = findViewById(R.id.etEventName);
        EditText etDate = findViewById(R.id.etDate);
        EditText etTime = findViewById(R.id.etTime);
        EditText etLocation = findViewById(R.id.etLocation);

        // Date Picker
        etDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, day) -> {
                etDate.setText(day + "/" + (month + 1) + "/" + year);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        // Time Picker
        etTime.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new TimePickerDialog(this, (view, hour, min) -> {
                etTime.setText(hour + ":" + String.format("%02d", min));
            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false).show();
        });

        findViewById(R.id.btnInsertEvent).setOnClickListener(v -> {
            String serialStr = etSerial.getText().toString().trim();
            String name = etName.getText().toString().trim();
            String date = etDate.getText().toString().trim();
            String time = etTime.getText().toString().trim();
            String location = etLocation.getText().toString().trim();

            if (serialStr.isEmpty() || name.isEmpty() || date.isEmpty() || time.isEmpty() || location.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int serialNo = Integer.parseInt(serialStr);

            if (db.eventExists(serialNo)) {
                Toast.makeText(this, "Event Serial exists! You can only Update this event.", Toast.LENGTH_SHORT).show();
            } else {
                if (db.insertEvent(serialNo, name, date, time, location)) {
                    Toast.makeText(this, "Event Created!", Toast.LENGTH_SHORT).show();
                    clearFields(etSerial, etName, etDate, etTime, etLocation);
                }
            }
        });

        findViewById(R.id.btnUpdateEvent).setOnClickListener(v -> {
            String serialStr = etSerial.getText().toString().trim();
            String name = etName.getText().toString().trim();
            String date = etDate.getText().toString().trim();
            String time = etTime.getText().toString().trim();
            String location = etLocation.getText().toString().trim();

            if (serialStr.isEmpty()) {
                etSerial.setError("Required");
                return;
            }

            int serialNo = Integer.parseInt(serialStr);

            if (!db.eventExists(serialNo)) {
                Toast.makeText(this, "Event not found! Use 'Create' for new serial numbers.", Toast.LENGTH_SHORT).show();
            } else {
                if (db.updateEvent(serialNo, name, date, time, location)) {
                    Toast.makeText(this, "Event Updated!", Toast.LENGTH_SHORT).show();
                    clearFields(etSerial, etName, etDate, etTime, etLocation);
                }
            }
        });

        findViewById(R.id.btnGoToStudent).setOnClickListener(v -> {
            startActivity(new Intent(this, StudentEventActivity.class));
        });
    }

    private void clearFields(EditText... fields) {
        for (EditText f : fields) f.setText("");
    }
}
