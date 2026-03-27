package com.example.myapplication;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Tag for filtering logs in Logcat
    private static final String TAG = "LifecycleDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Triggered when the activity is first created
        Log.d(TAG, "onCreate invoked");
        Toast.makeText(this, "onCreate invoked", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Triggered when the activity becomes visible
        Log.d(TAG, "onStart invoked");
        Toast.makeText(this, "onStart invoked", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Triggered when the user can interact with the app
        Log.d(TAG, "onResume invoked");
        Toast.makeText(this, "onResume invoked", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Triggered when the activity is partially obscured or about to stop
        Log.d(TAG, "onPause invoked");
        Toast.makeText(this, "onPause invoked", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Triggered when the activity is no longer visible
        Log.d(TAG, "onStop invoked");
        Toast.makeText(this, "onStop invoked", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // Triggered when the activity is stopped and starting again
        Log.d(TAG, "onRestart invoked");
        Toast.makeText(this, "onRestart invoked", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Triggered before the activity is destroyed
        Log.d(TAG, "onDestroy invoked");
        Toast.makeText(this, "onDestroy invoked", Toast.LENGTH_SHORT).show();
    }
}