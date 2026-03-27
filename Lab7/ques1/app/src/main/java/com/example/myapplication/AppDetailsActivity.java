package com.example.myapplication;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

public class AppDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_details);

        ImageView ivIcon = findViewById(R.id.detailAppIcon);
        TextView tvName = findViewById(R.id.detailAppName);
        TextView tvInfo = findViewById(R.id.detailAppInfo);

        String packageName = getIntent().getStringExtra("PACKAGE_NAME");
        PackageManager pm = getPackageManager();

        try {
            // Get App Info, Version, and Permissions
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            ApplicationInfo appInfo = pm.getApplicationInfo(packageName, 0);

            // Set Icon and Name
            ivIcon.setImageDrawable(pm.getApplicationIcon(appInfo));
            tvName.setText(pm.getApplicationLabel(appInfo).toString());

            // Get Version
            String version = packageInfo.versionName != null ? packageInfo.versionName : "Unknown";

            // Approximate Size from APK file length
            File file = new File(appInfo.sourceDir);
            long sizeInMB = file.length() / (1024 * 1024);

            // Get Requested Permissions
            StringBuilder permissions = new StringBuilder();
            if (packageInfo.requestedPermissions != null) {
                for (String perm : packageInfo.requestedPermissions) {
                    // Clean up the string to just show the permission name (e.g., android.permission.CAMERA -> CAMERA)
                    String shortPerm = perm.substring(perm.lastIndexOf('.') + 1);
                    permissions.append("- ").append(shortPerm).append("\n");
                }
            } else {
                permissions.append("No special permissions requested.");
            }

            // Display Information
            String details = "Package: " + packageName + "\n\n" +
                    "Version: " + version + "\n\n" +
                    "Approx. Size: " + sizeInMB + " MB\n\n" +
                    "Permissions:\n" + permissions.toString();

            tvInfo.setText(details);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            tvInfo.setText("Error loading application details.");
        }
    }
}