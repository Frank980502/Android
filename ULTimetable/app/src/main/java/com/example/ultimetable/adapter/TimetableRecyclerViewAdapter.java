package com.example.ultimetable.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ultimetable.R;
import com.example.ultimetable.databinding.FragmentTimetableItemBinding;
import com.example.ultimetable.bean.Course;

import java.util.List;

public class TimetableRecyclerViewAdapter extends RecyclerView.Adapter<TimetableRecyclerViewViewHolder> {
    private Context context;
    private List<Course> itemData;

    public TimetableRecyclerViewAdapter(Context context, List<Course> itemData){
        this.context=context;
        this.itemData=itemData;
    }


    @Override
    public TimetableRecyclerViewViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        FragmentTimetableItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.fragment_timetable_item, viewGroup, false);
        return new TimetableRecyclerViewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TimetableRecyclerViewViewHolder viewHolder, int position) {
        viewHolder.binding.setCourse(itemData.get(position));
        viewHolder.binding.executePendingBindings();
        final Course course=viewHolder.binding.getCourse();
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("Id",course.getCourseId());
                bundle.putString("Name",course.getCourseName());
                bundle.putString("Type",course.getClassType());
                bundle.putString("Time",course.getClassBegin());
                bundle.putString("Classroom",course.getClassRoom());
                Navigation.findNavController(view).navigate(R.id.action_nav_timetable_to_edit_timetable,bundle);
            }
        });
    }
    @Override
    public int getItemCount() {
        return itemData.size();
    }
}
