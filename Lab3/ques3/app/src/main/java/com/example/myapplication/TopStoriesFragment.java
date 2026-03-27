package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class TopStoriesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText("Breaking News: \n\n1. Android 15 Released!\n2. Stock Markets hit all time high.\n3. Weather forecast for tomorrow.");
        textView.setTextSize(20);
        textView.setPadding(30, 30, 30, 30);
        return textView;
    }
}