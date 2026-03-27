package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class SportsNewsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText("Sports Updates: \n\n1. Real Madrid wins the match.\n2. NBA Finals starting soon.\n3. F1: Hamilton creates history.");
        textView.setTextSize(20);
        textView.setPadding(30, 30, 30, 30);
        return textView;
    }
}