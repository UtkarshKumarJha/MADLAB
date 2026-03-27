package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;

public class AlbumsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Make sure this points to the XML file containing the <GridView>
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        GridView gridView = view.findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter());
        return view;
    }
    // Simple Adapter to show images in grid
    private class ImageAdapter extends BaseAdapter {
        // Using system icons for demo. Replace with R.drawable.your_dog_image
        private int[] imageIds = {
                android.R.drawable.ic_menu_camera, android.R.drawable.ic_menu_gallery,
                android.R.drawable.ic_menu_compass, android.R.drawable.ic_menu_call,
                android.R.drawable.ic_menu_camera, android.R.drawable.ic_menu_gallery
        };

        @Override
        public int getCount() { return imageIds.length; }

        @Override
        public Object getItem(int position) { return null; }

        @Override
        public long getItemId(int position) { return 0; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(getActivity());
                imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(imageIds[position]);
            return imageView;
        }
    }
}