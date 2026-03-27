package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;

public class ArtistsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Create a ListView programmatically (or use XML)
        ListView listView = new ListView(getActivity());

        // Data from the manual's image
        String[] countries = {"American Samoa", "El Salvador", "Saint Helena", "Saint Kitts and Nevis", "Saint Lucia", "Samoa", "Saudi Arabia"};

        // Basic Adapter to display the list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                countries
        );

        listView.setAdapter(adapter);
        return listView;
    }
}