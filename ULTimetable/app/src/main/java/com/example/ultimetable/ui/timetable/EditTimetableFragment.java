package com.example.ultimetable.ui.timetable;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.ultimetable.R;
import com.example.ultimetable.bean.Course;
import com.example.ultimetable.databinding.FragmentTimetableEditBinding;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class EditTimetableFragment extends Fragment {

    public EditTimetableFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentTimetableEditBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_timetable_edit, container, false);
        String id=getArguments().getString("Id");
        String name=getArguments().getString("Name");
        String type=getArguments().getString("Type");
        String time=getArguments().getString("Time");
        String classroom=getArguments().getString("Classroom");
        Course course=new Course();
        course.setCourseId(id);
        course.setCourseName(name);
        course.setClassType(type);
        course.setClassBegin(time);
        course.setClassRoom(classroom);

        binding.setCourse(course);
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_edit_timetable_to_nav_timetable);
            }
        });
        return binding.getRoot();
    }
}
