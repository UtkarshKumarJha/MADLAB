package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class EntertainmentFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText("Entertainment Buzz: \n\n1. New Marvel movie trailer out!\n2. Oscar nominations announced.\n3. Top 10 songs of the week.");
        textView.setTextSize(20);
        textView.setPadding(30, 30, 30, 30);
        return textView;
    }
}