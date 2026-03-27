package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TableActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        TextView tvTableName = findViewById(R.id.tvTableName);
        TextView tvTableReg = findViewById(R.id.tvTableReg);

        // Get data from Page 1 and put in Table
        tvTableName.setText(getIntent().getStringExtra("NAME"));
        tvTableReg.setText(getIntent().getStringExtra("REG_NO"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.page2_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_next) {
            startActivity(new Intent(TableActivity.this, DetailsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}