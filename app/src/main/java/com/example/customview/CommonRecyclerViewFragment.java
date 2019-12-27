package com.example.customview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class CommonRecyclerViewFragment extends Fragment {
    private FragmentAdapter adapter;

    public static Fragment getInstance(String aaaa) {
        Bundle bundle = new Bundle();
        bundle.putString("name", aaaa);
        CommonRecyclerViewFragment commonRecyclerViewFragment = new CommonRecyclerViewFragment();

        commonRecyclerViewFragment.setArguments(bundle);
        commonRecyclerViewFragment.name = aaaa;
        return commonRecyclerViewFragment;
    }


    String name = "";


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("FFF", name + ".isVisibleToUser==" + isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("FFF", name + ".onHiddenChanged==" + hidden);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("FFF", name + ".onCreate");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("FFF", name + ".onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("FFF", name + ".onResume");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (name.equals("Fragment1") || name.equals("Fragment2")) {
            View layout = LayoutInflater.from(this.getContext()).inflate(R.layout.activity_main2, container, false);
            TabLayout tabLayout = layout.findViewById(R.id.tabs_search);
            ViewPager viewPager = layout.findViewById(R.id.viewpager);
            viewPager.setOffscreenPageLimit(3);
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager, true);
            return layout;

        } else {
            TextView tv = new TextView(this.getContext());
            tv.setText(name);
            tv.setHeight(100);
            tv.setWidth(200);
            Log.e("FFF", name + ".onCreateView");
            return tv;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("FFF", name + ".onActivityCreated");
    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new FragmentAdapter(getChildFragmentManager());

        if (name.equals("Fragment1")) {
            //All (search)
            adapter.addFragment(CommonRecyclerViewFragment.getInstance("Fragment1111111Child1"), "Fragment1111111Child1");
            //Name(namesearch)
            adapter.addFragment(CommonRecyclerViewFragment.getInstance("Fragment1111111Child2"), "Fragment1111111Child2");
            //Artist(artist_name)
            adapter.addFragment(CommonRecyclerViewFragment.getInstance("Fragment1111111Child3"), "Fragment1111111Child3");
            //Album(album_name)
            adapter.addFragment(CommonRecyclerViewFragment.getInstance("Fragment1111111Child4"), "Fragment1111111Child4");
        } else {
            //All (search)
            adapter.addFragment(CommonRecyclerViewFragment.getInstance("Fragment22222222Child1"), "Fragment222222222Child1");
            //Name(namesearch)
            adapter.addFragment(CommonRecyclerViewFragment.getInstance("Fragment22222222Child2"), "Fragment222222222Child2");
            //Artist(artist_name)
            adapter.addFragment(CommonRecyclerViewFragment.getInstance("Fragment22222222Child3"), "Fragment222222222Child3");
            //Album(album_name)
            adapter.addFragment(CommonRecyclerViewFragment.getInstance("Fragment22222222Child4"), "Fragment222222222Child4");
        }
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
}
