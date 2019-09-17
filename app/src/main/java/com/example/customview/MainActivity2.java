package com.example.customview;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        TabLayout tabLayout = findViewById(R.id.tabs_search);
//
//        ViewPager viewPager = findViewById(R.id.viewpager);
//        viewPager.setOffscreenPageLimit(3);

        Log.e("FFF", "MainActivity2 started,TaskID--->"+getTaskId());
//        setupViewPager(viewPager);
//        tabLayout.setupWithViewPager(viewPager, true);
    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new FragmentAdapter(getSupportFragmentManager());
        //All (search)
        adapter.addFragment(CommonRecyclerViewFragment.getInstance("Fragment1"), "Fragment1");
        //Name(namesearch)
        adapter.addFragment(CommonRecyclerViewFragment.getInstance("Fragment2"), "Fragment2");
        //Artist(artist_name)
        adapter.addFragment(CommonRecyclerViewFragment.getInstance("Fragment3"), "Fragment3");
        //Album(album_name)
        adapter.addFragment(CommonRecyclerViewFragment.getInstance("Fragment4"), "Fragment4");
        viewPager.setAdapter(adapter);
    }

    private class FragmentAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("FFF", "MainActivity2---->onDestroy");
    }

    public void abc(View view) {
        Intent intent1 = new Intent(this, MainActivity3.class);

        startActivity(intent1);
    }
}
