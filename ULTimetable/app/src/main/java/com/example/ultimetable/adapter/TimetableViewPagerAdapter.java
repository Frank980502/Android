package com.example.ultimetable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.PagerAdapter;

import com.example.ultimetable.bean.Course;

import java.util.List;

public class TimetableViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<Course> itemData;
    private int variableId;
    private int layoutId;
    private LayoutInflater layoutInflater;
    private String[] mTitles;

    public TimetableViewPagerAdapter(Context context, List<Course> itemData,String[] mTitles,int variableId,int layoutId,LayoutInflater layoutInflater){
        this.context=context;
        this.itemData=itemData;
        this.variableId=variableId;
        this.layoutId=layoutId;
        this.layoutInflater=layoutInflater;
        this.mTitles=mTitles;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewDataBinding binding=DataBindingUtil.inflate(layoutInflater,layoutId,container,true);
        binding.setVariable(variableId,itemData.get(position));
        return binding;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container,position,object); 这一句要删除，否则报错
        //ViewDataBinding binding=(ViewDataBinding)object;
        //container.removeView(binding.getRoot());
    }

    @Override
    public int getCount() {
        return itemData.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        ViewDataBinding binding=(ViewDataBinding)object;
        return view == binding.getRoot();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
