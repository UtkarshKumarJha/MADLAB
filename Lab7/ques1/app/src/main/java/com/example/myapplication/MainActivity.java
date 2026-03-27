package com.example.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listViewApps;
    List<AppInfo> appList;
    PackageManager packageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewApps = findViewById(R.id.listViewApps);
        packageManager = getPackageManager();
        appList = new ArrayList<>();

        loadInstalledApps();

        // Custom Adapter for the ListView
        ArrayAdapter<AppInfo> adapter = new ArrayAdapter<AppInfo>(this, R.layout.list_item_app, appList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.list_item_app, parent, false);
                }
                AppInfo app = appList.get(position);
                ((ImageView) convertView.findViewById(R.id.appIcon)).setImageDrawable(app.icon);
                ((TextView) convertView.findViewById(R.id.appName)).setText(app.name);
                return convertView;
            }
        };

        listViewApps.setAdapter(adapter);

        // 1. Register ListView for Context Menu
        registerForContextMenu(listViewApps);
    }

    private void loadInstalledApps() {
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
            // Filter out some deep system processes if desired, but here we load all
            boolean isSystem = (packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
            String appName = packageManager.getApplicationLabel(packageInfo).toString();
            Drawable icon = packageManager.getApplicationIcon(packageInfo);
            appList.add(new AppInfo(appName, packageInfo.packageName, icon, isSystem));
        }
    }

    // 2. Create the Context Menu on Long Press
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        AppInfo selectedApp = appList.get(info.position);

        // Check specific permissions (Location & Camera)
        boolean hasCamera = packageManager.checkPermission(Manifest.permission.CAMERA, selectedApp.packageName) == PackageManager.PERMISSION_GRANTED;
        boolean hasLocation = packageManager.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, selectedApp.packageName) == PackageManager.PERMISSION_GRANTED;

        String appType = selectedApp.isSystemApp ? "System App" : "User Installed";
        String perms = "Perms: " + (hasCamera ? "Camera " : "") + (hasLocation ? "Location" : "");
        if (!hasCamera && !hasLocation) perms = "Perms: Standard";

        // Set Title with System/User info and Permissions
        menu.setHeaderTitle(selectedApp.name + "\n(" + appType + ") | " + perms);

        // Add menu options
        menu.add(0, 1, 0, "Open App");
        menu.add(0, 2, 0, "View Details");
        menu.add(0, 3, 0, "Uninstall");
    }

    // 3. Handle Context Menu Item Clicks
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        AppInfo selectedApp = appList.get(info.position);

        switch (item.getItemId()) {
            case 1: // Open App
                Intent launchIntent = packageManager.getLaunchIntentForPackage(selectedApp.packageName);
                if (launchIntent != null) {
                    startActivity(launchIntent);
                } else {
                    Toast.makeText(this, "App cannot be opened directly", Toast.LENGTH_SHORT).show();
                }
                return true;

            case 2: // View Details
                Intent detailIntent = new Intent(this, AppDetailsActivity.class);
                detailIntent.putExtra("PACKAGE_NAME", selectedApp.packageName);
                startActivity(detailIntent);
                return true;

            case 3: // Uninstall with Prompt
                if (selectedApp.isSystemApp) {
                    Toast.makeText(this, "Cannot uninstall a System App", Toast.LENGTH_SHORT).show();
                } else {
                    showUninstallPrompt(selectedApp.packageName);
                }
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    // Confirmation Prompt before Uninstall
    private void showUninstallPrompt(String packageName) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Uninstall")
                .setMessage("Are you sure you want to uninstall this application?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Intent intent = new Intent(Intent.ACTION_DELETE);
                    intent.setData(Uri.parse("package:" + packageName));
                    startActivity(intent);
                })
                .setNegativeButton("No", null)
                .show();
    }
}