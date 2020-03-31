package com.example.ultimetable.ui.timetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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

    private TimetableViewModel timetableViewModel;
    private TimetableFragmentAdapter adapter;
    private FragmentTimetableBinding binding;
    //private TimetableRecyclerViewAdapter adapter2;
    private View root;
    //private List<Course> courseList=new ArrayList<Course>();
    //private FirebaseFirestore db;
    //private Course course1,course2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        timetableViewModel =new ViewModelProvider(this).get(TimetableViewModel.class);
        root = inflater.inflate(R.layout.fragment_timetable, container, false);

        List<Fragment> fragments=new ArrayList<Fragment>();
        fragments.add(new TimetableMondayFragment());
        fragments.add(new TimetableTuesdayFragment());
        fragments.add(new TimetableWednesdayFragment());
        fragments.add(new TimetableThursdayFragment());
        fragments.add(new TimetableFridayFragment());

        adapter = new TimetableFragmentAdapter(getChildFragmentManager(), fragments);
        //设定适配器
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_timetable, container, false);
        ViewPager viewPager=binding.viewPager;
        PagerTabStrip pagerTabStrip=binding.pagertab;
        pagerTabStrip.setTextSpacing(0);
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.setAdapter(adapter);
        return binding.getRoot();

    }
}