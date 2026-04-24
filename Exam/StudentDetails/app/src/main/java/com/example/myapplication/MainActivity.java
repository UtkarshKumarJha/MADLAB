package com.example.myapplication;

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

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        EditText etName = findViewById(R.id.etName);
        RadioGroup rgGender = findViewById(R.id.rgGender);
        Spinner spinnerCourse = findViewById(R.id.spinnerCourse);
        CheckBox cbHostel = findViewById(R.id.cbHostel);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        // Setup Spinner
        String[] courses = {"B.Tech", "M.Tech", "BBA", "MBA"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourse.setAdapter(adapter);

        btnSubmit.setOnClickListener(v -> {
            String name = etName.getText().toString();

            // Get Gender
            int selectedGenderId = rgGender.getCheckedRadioButtonId();
            String gender = "Not Specified";
            if (selectedGenderId != -1) {
                RadioButton selectedRb = findViewById(selectedGenderId);
                gender = selectedRb.getText().toString();
            }

            // Get Course
            String course = spinnerCourse.getSelectedItem().toString();

            // Get Checkbox status
            String hostelStatus = cbHostel.isChecked() ? "Yes" : "No";

            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
                return;
            }

            // Database Insert Operation
            long id = dbHelper.insertStudent(name, gender, course, hostelStatus);
            if (id != -1) {
                Toast.makeText(this, "Data Saved to SQLite", Toast.LENGTH_SHORT).show();
                
                // Send data to Page 2
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                intent.putExtra("ID", id); // Pass the ID for CRUD operations on next page
                intent.putExtra("NAME", name);
                intent.putExtra("GENDER", gender);
                intent.putExtra("COURSE", course);
                intent.putExtra("HOSTEL", hostelStatus);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Data Insertion Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
