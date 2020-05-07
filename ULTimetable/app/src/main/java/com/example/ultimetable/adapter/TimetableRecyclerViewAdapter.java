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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TimetableRecyclerViewAdapter extends RecyclerView.Adapter<TimetableRecyclerViewViewHolder> {
    private Context context;
    private List<Course> item;
    private Course course;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user;
    private int weekday;

    public TimetableRecyclerViewAdapter(Context context, List<Course> item,int weekday){
        if(this.item==null){
            this.item=new ArrayList<>();
        }
        this.context=context;
        this.item=item;
        this.weekday=weekday;
        user= FirebaseAuth.getInstance().getCurrentUser();
    }


    @Override
    public TimetableRecyclerViewViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        FragmentTimetableItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_timetable_item, viewGroup, false);
        return new TimetableRecyclerViewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final TimetableRecyclerViewViewHolder viewHolder, int position) {
        FragmentTimetableItemBinding binding=DataBindingUtil.getBinding(viewHolder.itemView);
        binding.setCourse(item.get(position));
        binding.executePendingBindings();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                course=viewHolder.binding.getCourse();
                Bundle bundle=new Bundle();
                bundle.putInt("Weekday",weekday);
                bundle.putString("ClassId",course.getClassId());
                bundle.putString("Id",course.getCourseId());
                bundle.putString("Name",course.getCourseName());
                bundle.putString("Type",course.getClassType());
                bundle.putString("Time",course.getClassBegin());
                bundle.putString("Hour",course.getClassHour());
                bundle.putString("Classroom",course.getClassRoom());
                Navigation.findNavController(view).navigate(R.id.action_nav_timetable_to_edit_timetable,bundle);
            }
        });
    }
    @Override
    public int getItemCount() {
        return item.size();
    }
}
