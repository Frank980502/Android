package com.example.ultimetable.fragment.timetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.example.ultimetable.R;
import com.example.ultimetable.adapter.TimetableFragmentAdapter;
import com.example.ultimetable.databinding.FragmentTimetableBinding;

import java.util.ArrayList;
import java.util.List;


public class TimetableFragment extends Fragment {

    private TimetableFragmentAdapter adapter;
    private FragmentTimetableBinding binding;
    private List<String> titleList=new ArrayList<String>();
    private List<Fragment> fragments=new ArrayList<Fragment>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragments.add(new TimetableMondayFragment());
        fragments.add(new TimetableTuesdayFragment());
        fragments.add(new TimetableWednesdayFragment());
        fragments.add(new TimetableThursdayFragment());
        fragments.add(new TimetableFridayFragment());

        titleList.add("Mon");
        titleList.add("Tues");
        titleList.add("Wed");
        titleList.add("Thur");
        titleList.add("Fri");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_timetable, container, false);
        adapter = new TimetableFragmentAdapter(getChildFragmentManager(), fragments, titleList);
        ViewPager viewPager=binding.viewPager;
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.setAdapter(adapter);
        PagerTabStrip pagerTabStrip=binding.pagertab;
        pagerTabStrip.setTextSpacing(0);
        return binding.getRoot();
    }


}