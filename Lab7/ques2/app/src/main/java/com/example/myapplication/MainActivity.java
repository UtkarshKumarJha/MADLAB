package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageView displayedImage;
    TextView tvImageCaption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        LinearLayout myMenuView = findViewById(R.id.myMenuView);
        displayedImage = findViewById(R.id.displayedImage);
        tvImageCaption = findViewById(R.id.tvImageCaption);

        // Set click listener on the custom "My Menu" view
        myMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Instantiate PopupMenu anchored to the clicked view
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);

                // 2. Inflate the menu resource
                popupMenu.getMenuInflater().inflate(R.menu.popup_my_menu, popupMenu.getMenu());

                // 3. Handle item clicks
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        if (id == R.id.item_image1) {
                            // Update the ImageView and Text
                            displayedImage.setImageResource(android.R.drawable.ic_menu_camera); // Using built-in camera icon for Image 1
                            tvImageCaption.setText("Showing: Image - 1");

                            // Display the Toast
                            Toast.makeText(MainActivity.this, "Image - 1 is displayed!", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        else if (id == R.id.item_image2) {
                            // Update the ImageView and Text
                            displayedImage.setImageResource(android.R.drawable.ic_menu_gallery); // Using built-in gallery icon for Image 2
                            tvImageCaption.setText("Showing: Image - 2");

                            // Display the Toast
                            Toast.makeText(MainActivity.this, "Image - 2 is displayed!", Toast.LENGTH_SHORT).show();
                            return true;
                        }

                        return false;
                    }
                });

                // 4. Show the PopupMenu
                popupMenu.show();
            }
        });
    }
}