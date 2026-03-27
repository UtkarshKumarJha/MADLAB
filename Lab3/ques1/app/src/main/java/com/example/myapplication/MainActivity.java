package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);

        // Set up the adapter to switch between fragments
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0: return new ArtistsFragment(); // Tab 1: List
                    case 1: return new AlbumsFragment();  // Tab 2: Grid
                    case 2: return new SongsFragment();   // Tab 3: Table
                    default: return new ArtistsFragment();
                }
            }

            @Override
            public int getCount() {
                return 3; // We have 3 Tabs
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0: return "Artists";
                    case 1: return "Albums";
                    case 2: return "Songs";
                    default: return null;
                }
            }
        });

        // Link the Tab Layout to the ViewPager
        tabLayout.setupWithViewPager(viewPager);
    }
}