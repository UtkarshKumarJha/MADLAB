package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);

        // Set up the adapter
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0: return new TopStoriesFragment();
                    case 1: return new SportsNewsFragment(); // Renamed to avoid conflict with Q2
                    case 2: return new EntertainmentFragment();
                    default: return new TopStoriesFragment();
                }
            }

            @Override
            public int getCount() {
                return 3; // 3 Tabs
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0: return "Top Stories";
                    case 1: return "Sports";
                    case 2: return "Entertainment";
                    default: return null;
                }
            }
        });

        tabLayout.setupWithViewPager(viewPager);
    }
}