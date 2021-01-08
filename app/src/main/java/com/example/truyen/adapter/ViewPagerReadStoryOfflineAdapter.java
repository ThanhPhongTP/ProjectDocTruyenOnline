package com.example.truyen.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.truyen.fragments.ReadFragmentOffline;
import com.example.truyen.models.Chapter;

import java.util.List;

public class ViewPagerReadStoryOfflineAdapter extends FragmentStatePagerAdapter {
    List<Chapter> chapterArrayList;
    Chapter chapter;
    public ViewPagerReadStoryOfflineAdapter(@NonNull FragmentManager fm, List<Chapter> chapterArrayList, Chapter chapter) {
        super(fm);
        this.chapterArrayList = chapterArrayList;
        this.chapter = chapter;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Log.d("itefffmview",chapterArrayList.get(position).getTitle()+"");

        return ReadFragmentOffline.newInstance(position +1);
    }
    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
    @Override
    public int getCount() {
//        return Integer.MAX_VALUE;
        return chapterArrayList.size();

    }
}