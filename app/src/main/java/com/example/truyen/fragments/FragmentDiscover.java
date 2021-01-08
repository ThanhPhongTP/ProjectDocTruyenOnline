package com.example.truyen.fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.truyen.R;
import com.example.truyen.adapter.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class FragmentDiscover extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        checkTheme();
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        setControl(view);
        addTabs();

        return view;
    }

    @Override
    public void onResume() {
//        removeTablayout();
//        addTabs();
        super.onResume();
    }

    private void removeTablayout(){
        FragmentManager fm = getChildFragmentManager();
        FragmentAdapter fragmentAdapter = new FragmentAdapter(fm);
        int nCount = fragmentAdapter.getCount();
        for(int i = nCount - 1; i >= 0; --i) {
            fragmentAdapter.destroyItem(viewPager, i, fragmentAdapter.getItem(i));
            tabLayout.removeTabAt(i);
        }
        tabLayout.invalidate();
        fragmentAdapter.notifyDataSetChanged();
    }


    private void checkTheme() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getContext());
        if (sharedPreferences.contains("THEME")) {
            int nID = sharedPreferences.getInt("THEME", R.style.ProjectDocTruyenOnline_Light);
            requireActivity().setTheme(nID);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void addTabs() {
        FragmentManager fm = getChildFragmentManager();
        FragmentAdapter fragmentAdapter = new FragmentAdapter(fm);
        fragmentAdapter.add(new FragmentHistory(), "Lịch sử");
        fragmentAdapter.add(new FragmentFllow(), "Ưa thích");
        fragmentAdapter.add(new FragmentDownload(), "Tải xuống");
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setBackground(null);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.setSaveEnabled(false);
        //set non Swipe for viewPager
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    private void setControl(View view) {
        viewPager = view.findViewById(R.id.viewDiscover);
        tabLayout = view.findViewById(R.id.tabDiscover);
    }
}