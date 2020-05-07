package com.example.ultimetable.fragment.timetable;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.ultimetable.R;
import com.example.ultimetable.bean.Course;
import com.example.ultimetable.databinding.FragmentTimetableEditBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Objects;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class EditTimetableFragment extends Fragment {

    private int newHour,newMinute;
    private FragmentTimetableEditBinding binding;
    private FirebaseFirestore db;
    private FirebaseUser user;

    public EditTimetableFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_timetable_edit, container, false);
        db= FirebaseFirestore.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();
        final String classId=getArguments().getString("ClassId");
        final String id=getArguments().getString("Id");
        final String name=getArguments().getString("Name");
        final String type=getArguments().getString("Type");
        final String time=getArguments().getString("Time");
        final String hour=getArguments().getString("Hour");
        final String classroom=getArguments().getString("Classroom");
        final int weekday=getArguments().getInt("Weekday");

        final Course course=new Course();

        course.setCourseId(id);
        course.setClassId(classId);
        course.setCourseName(name);
        course.setClassType(type);
        course.setClassHour(hour);
        course.setClassBegin(time);
        course.setClassRoom(classroom);

        binding.setCourse(course);

        if(type.equals("LEC")) binding.courseType.setSelection(0,true);
        if(type.equals("LAB")) binding.courseType.setSelection(1,true);
        if(type.equals("TUT")) binding.courseType.setSelection(2,true);

        if(hour.equals("1")) binding.courseHour.setSelection(0,true);
        if(hour.equals("2")) binding.courseHour.setSelection(1,true);
        if(hour.equals("3")) binding.courseHour.setSelection(2,true);
        if(hour.equals("4")) binding.courseHour.setSelection(3,true);


        binding.courseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final Course newCourse=binding.getCourse();
                String[] types = getResources().getStringArray(R.array.spinner_type);
                newCourse.setClassType(types[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.courseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Course newCourse=binding.getCourse();
                newHour=Integer.parseInt(newCourse.getClassBegin().split(":")[0]);
                newMinute=Integer.parseInt(newCourse.getClassBegin().split(":")[1]);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                if((String.valueOf(hourOfDay).length()==1)) {
                                    if((String.valueOf(minute).length()==1)){
                                        newCourse.setClassBegin("0"+hourOfDay+":"+"0"+minute);
                                    }else {
                                        newCourse.setClassBegin("0"+hourOfDay+":"+minute);
                                    }
                                }else{
                                    if((String.valueOf(minute).length()==1)){
                                        newCourse.setClassBegin(hourOfDay+":"+"0"+minute);
                                    }else {
                                        newCourse.setClassBegin(hourOfDay+":"+minute);
                                    }
                                }
                            }
                        }, newHour, newMinute, true);
                timePickerDialog.show();
            }
        });

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Course newCourse = new Course();
                newCourse.setCourseName(name);
                newCourse.setCourseId(id);
                newCourse.setClassId(classId);
                newCourse.setClassType(binding.courseType.getSelectedItem().toString());
                newCourse.setClassHour(binding.courseHour.getSelectedItem().toString());
                newCourse.setClassBegin(binding.courseTime.getText().toString());
                newCourse.setClassRoom(binding.courseRoom.getText().toString());

                switch (weekday) {
                    case 1:
                        db.collection("Student")
                                .document(user.getEmail().split("@")[0])
                                .collection("Modules")
                                .document(id)
                                .collection("Schedule")
                                .document("Monday")
                                .collection("Class")
                                .document(classId)
                                .set(newCourse);
                        break;
                    case 2:
                        db.collection("Student")
                                .document(user.getEmail().split("@")[0])
                                .collection("Modules")
                                .document(id)
                                .collection("Schedule")
                                .document("Tuesday")
                                .collection("Class")
                                .document(classId)
                                .set(newCourse);
                        break;
                    case 3:
                        db.collection("Student")
                                .document(user.getEmail().split("@")[0])
                                .collection("Modules")
                                .document(id)
                                .collection("Schedule")
                                .document("Wednesday")
                                .collection("Class")
                                .document(classId)
                                .set(newCourse);
                        break;
                    case 4:
                        db.collection("Student")
                                .document(user.getEmail().split("@")[0])
                                .collection("Modules")
                                .document(id)
                                .collection("Schedule")
                                .document("Thursday")
                                .collection("Class")
                                .document(classId)
                                .set(newCourse);
                        break;
                    case 5:
                        db.collection("Student")
                                .document(user.getEmail().split("@")[0])
                                .collection("Modules")
                                .document(id)
                                .collection("Schedule")
                                .document("Friday")
                                .collection("Class")
                                .document(classId)
                                .set(newCourse);
                        break;
                }
                Navigation.findNavController(v).navigate(R.id.action_edit_timetable_to_nav_timetable);
            }
        });
        return binding.getRoot();
    }
}
