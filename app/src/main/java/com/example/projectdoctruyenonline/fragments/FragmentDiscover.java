package com.example.projectdoctruyenonline.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.projectdoctruyenonline.R;
import com.example.projectdoctruyenonline.adapter.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

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
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        setControl(view);
        addTabs();
        return view;
    }

    @Override
    public void onStart() {
        Log.d("aaaa", "1");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("aaaa", "2");
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("aaaa", "3");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Log.d("aaaa", "4");
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        Log.d("aaaa", "5");
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        Log.d("aaaa", "6");
        super.onDestroy();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void addTabs() {
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager());
        fragmentAdapter.add(new FragmentHistory(), "Lịch sử");
        fragmentAdapter.add(new FragmentFllow(), "Theo dõi");
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setBackground(null);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
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