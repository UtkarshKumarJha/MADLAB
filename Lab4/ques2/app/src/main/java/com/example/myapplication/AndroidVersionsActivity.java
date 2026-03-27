package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AndroidVersionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_versions);

        Button btnPie = findViewById(R.id.btnPie);
        Button btnOreo = findViewById(R.id.btnOreo);
        Button btnNougat = findViewById(R.id.btnNougat);

        // Set Click Listeners
        // Replace 'android.R.drawable...' with 'R.drawable.your_actual_image_name'
        btnPie.setOnClickListener(v ->
                showVersionToast("Android 9.0 Pie", android.R.drawable.ic_menu_camera));

        btnOreo.setOnClickListener(v ->
                showVersionToast("Android 8.0 Oreo", android.R.drawable.ic_menu_compass));

        btnNougat.setOnClickListener(v ->
                showVersionToast("Android 7.0 Nougat", android.R.drawable.ic_menu_gallery));
    }

    // Helper method to create and show the custom toast
    private void showVersionToast(String versionName, int iconResId) {
        // 1. Inflate the layout (convert XML to View)
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_android_version, null);

        // 2. Set the Image and Text dynamically
        ImageView img = layout.findViewById(R.id.imgVersion);
        TextView text = layout.findViewById(R.id.tvVersionName);

        img.setImageResource(iconResId);
        text.setText(versionName);

        // 3. Create and Show Toast
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout); // Important: Set the custom view!
        toast.show();
    }
}