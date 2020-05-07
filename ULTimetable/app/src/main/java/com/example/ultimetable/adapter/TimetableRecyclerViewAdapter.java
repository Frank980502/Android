package com.example.ultimetable.adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ultimetable.MainActivity;
import com.example.ultimetable.MapsActivity;
import com.example.ultimetable.R;
import com.example.ultimetable.databinding.FragmentTimetableItemBinding;
import com.example.ultimetable.bean.Course;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
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
    public void onBindViewHolder(@NonNull final TimetableRecyclerViewViewHolder viewHolder, final int position) {
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

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(final View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(context);

                //options to display in dialog
                String[] options={"Delete","Map"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(which==0){
                            removeData(position);
                        }
                        if(which==1){
                            course=viewHolder.binding.getCourse();
                            Intent intent = new Intent(context, MapsActivity.class);
                            intent.putExtra("Room",course.getClassRoom());
                            context.startActivity(intent);
                        }
                    }
                }).create().show();
                return false;
            }
        });
    }
    @Override
    public int getItemCount() {
        return item.size();
    }

    public void removeData(int position) {
        String day = null;
        if(weekday==1) day="Monday";
        if(weekday==2) day="Tuesday";
        if(weekday==3) day="Wednesday";
        if(weekday==4) day="Thursday";
        if(weekday==5) day="Friday";
        db.collection("Student").document(user.getEmail().split("@")[0])
                .collection("Modules")
                .document(item.get(position).getCourseId())
                .collection("Schedule")
                .document(day)
                .collection("Class")
                .document(item.get(position).getClassId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
        item.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0,item.size());
    }
}
