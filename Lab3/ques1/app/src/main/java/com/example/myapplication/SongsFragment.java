package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class SongsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // ERROR WAS HERE: You were inflating "activity_albums_fragment"
        // FIX: Inflate "fragment_songs" (which we will define below)
        return inflater.inflate(R.layout.fragment_songs, container, false);
    }
}