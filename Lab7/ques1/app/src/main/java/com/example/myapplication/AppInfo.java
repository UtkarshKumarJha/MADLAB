package com.example.myapplication;

import android.graphics.drawable.Drawable;

public class AppInfo {
    String name;
    String packageName;
    Drawable icon;
    boolean isSystemApp;

    public AppInfo(String name, String packageName, Drawable icon, boolean isSystemApp) {
        this.name = name;
        this.packageName = packageName;
        this.icon = icon;
        this.isSystemApp = isSystemApp;
    }
}