package com.example.ultimetable.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TimetableFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private List<String> titleList=new ArrayList<String>();

    public TimetableFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments=fragments;
        titleList.add("Mon");
        titleList.add("Tues");
        titleList.add("Wed");
        titleList.add("Thur");
        titleList.add("Fri");
    }

    @Override
    public Fragment getItem(int arg0) {
        return mFragments.get(arg0);
    }

    @Override
    public int getCount() {
        return mFragments.size();

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
