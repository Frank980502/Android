package com.example.ultimetable.fragment.timetable;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.example.ultimetable.R;
import com.example.ultimetable.adapter.TimetableFragmentAdapter;
import com.example.ultimetable.adapter.TimetableRecyclerViewAdapter;
import com.example.ultimetable.bean.Course;
import com.example.ultimetable.bean.Student;
import com.example.ultimetable.databinding.FragmentTimetableBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;


public class TimetableFragment extends Fragment {

    private TimetableFragmentAdapter adapter;
    private FragmentTimetableBinding binding;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private List<Course> courseList=new ArrayList<Course>();
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
        db= FirebaseFirestore.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_timetable, container, false);
        adapter = new TimetableFragmentAdapter(getChildFragmentManager(), fragments, titleList);
        final ViewPager viewPager=binding.viewPager;
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.setAdapter(adapter);
        PagerTabStrip pagerTabStrip=binding.pagertab;
        pagerTabStrip.setTextSpacing(0);
        Calendar calendar = Calendar.getInstance();
        int weekday=calendar.get(Calendar.DAY_OF_WEEK)-1;
        if(weekday==0||weekday==6) weekday=1;
        viewPager.setCurrentItem(weekday-1);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(viewPager.getCurrentItem()){
                    case 0: courseList.clear();
                            db.collection("Student")
                                    .document(Objects.requireNonNull(user.getEmail()).split("@")[0])
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {

                                                DocumentSnapshot document = task.getResult();
                                                assert document != null;
                                                if (document.exists()) {
                                                    Student stu=document.toObject(Student.class);
                                                    final List<String> moduleList= new ArrayList<>();
                                                    assert stu != null;
                                                    final int size=stu.getStudentModule().size();
                                                    for(int i=0;i<size;i++){
                                                        moduleList.add(stu.getStudentModule().get(i));
                                                    }
                                                    for(int i = 0; i<moduleList.size(); i++){
                                                        final int j=i;
                                                        final String str;
                                                        if(moduleList.get(i)==null || moduleList.get(i).equals("")){
                                                            str=" ";
                                                        }else{
                                                            str=moduleList.get(i);
                                                        }
                                                        db.collection("Student")
                                                                .document(user.getEmail().split("@")[0])
                                                                .collection("Modules")
                                                                .document(str)
                                                                .collection("Schedule")
                                                                .document("Monday")
                                                                .collection("Class")
                                                                .get()
                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                        if (task.isSuccessful()) {
                                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                Course course=new Course();
                                                                                course.setCourseId(moduleList.get(j));
                                                                                course.setClassId(document.getString("classId"));
                                                                                course.setClassBegin(document.getString("classBegin"));
                                                                                course.setClassHour(document.getString("classHour"));
                                                                                course.setClassType(document.getString("classType"));
                                                                                course.setClassRoom(document.getString("classRoom"));
                                                                                courseList.add(course);
                                                                            }
                                                                            Collections.sort(courseList);

                                                                            if(j==moduleList.size()-1){
                                                                                List<String> infoList=new ArrayList<>();
                                                                                String info=" ";
                                                                                for(int i=0;i<courseList.size();i++){
                                                                                    if(i+1<courseList.size()){
                                                                                        if(courseList.get(i).getClassBegin().equals(courseList.get(i+1).getClassBegin())){
                                                                                            infoList.add(courseList.get(i).getCourseId()+"'s "+courseList.get(i).getClassType()+" and "+courseList.get(i+1).getCourseId()+"'s "+courseList.get(i+1).getClassType()+" on Monday.");
                                                                                        }
                                                                                    }
                                                                                }
                                                                                for(int i=0;i<infoList.size();i++){
                                                                                    info+="\n"+infoList.get(i);
                                                                                }
                                                                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                                                builder.setTitle("Conflict Report");
                                                                                if(info.length()<5) info="No conflict!";
                                                                                builder.setMessage(info);
                                                                                AlertDialog dialog = builder.create();
                                                                                dialog.show();
                                                                            }
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                }
                                            }
                                        }
                                    });
                            break;
                    case 1: courseList.clear();
                            db.collection("Student")
                                    .document(Objects.requireNonNull(user.getEmail()).split("@")[0])
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {

                                                DocumentSnapshot document = task.getResult();
                                                assert document != null;
                                                if (document.exists()) {
                                                    Student stu=document.toObject(Student.class);
                                                    final List<String> moduleList= new ArrayList<>();
                                                    assert stu != null;
                                                    final int size=stu.getStudentModule().size();
                                                    for(int i=0;i<size;i++){
                                                        moduleList.add(stu.getStudentModule().get(i));
                                                    }
                                                    for(int i = 0; i<moduleList.size(); i++){
                                                        final int j=i;
                                                        final String str;
                                                        if(moduleList.get(i)==null || moduleList.get(i).equals("")){
                                                            str=" ";
                                                        }else{
                                                            str=moduleList.get(i);
                                                        }
                                                        db.collection("Student")
                                                                .document(user.getEmail().split("@")[0])
                                                                .collection("Modules")
                                                                .document(str)
                                                                .collection("Schedule")
                                                                .document("Tuesday")
                                                                .collection("Class")
                                                                .get()
                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                        if (task.isSuccessful()) {
                                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                Course course=new Course();
                                                                                course.setCourseId(moduleList.get(j));
                                                                                course.setClassId(document.getString("classId"));
                                                                                course.setClassBegin(document.getString("classBegin"));
                                                                                course.setClassHour(document.getString("classHour"));
                                                                                course.setClassType(document.getString("classType"));
                                                                                course.setClassRoom(document.getString("classRoom"));
                                                                                courseList.add(course);
                                                                            }
                                                                            Collections.sort(courseList);

                                                                            if(j==moduleList.size()-1){
                                                                                List<String> infoList=new ArrayList<>();
                                                                                String info=" ";
                                                                                for(int i=0;i<courseList.size();i++){
                                                                                    if(i+1<courseList.size()){
                                                                                        if(courseList.get(i).getClassBegin().equals(courseList.get(i+1).getClassBegin())){
                                                                                            infoList.add(courseList.get(i).getCourseId()+"'s "+courseList.get(i).getClassType()+" and "+courseList.get(i+1).getCourseId()+"'s "+courseList.get(i+1).getClassType()+" on Tuesday.");
                                                                                        }
                                                                                    }
                                                                                }
                                                                                for(int i=0;i<infoList.size();i++){
                                                                                    info+="\n"+infoList.get(i);
                                                                                }
                                                                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                                                builder.setTitle("Conflict Report");
                                                                                if(info.length()<5) info="No conflict!";
                                                                                builder.setMessage(info);
                                                                                AlertDialog dialog = builder.create();
                                                                                dialog.show();
                                                                            }
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                }
                                            }
                                        }
                                    });
                            break;
                    case 2: courseList.clear();
                            db.collection("Student")
                                    .document(Objects.requireNonNull(user.getEmail()).split("@")[0])
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {

                                                DocumentSnapshot document = task.getResult();
                                                assert document != null;
                                                if (document.exists()) {
                                                    Student stu=document.toObject(Student.class);
                                                    final List<String> moduleList= new ArrayList<>();
                                                    assert stu != null;
                                                    final int size=stu.getStudentModule().size();
                                                    for(int i=0;i<size;i++){
                                                        moduleList.add(stu.getStudentModule().get(i));
                                                    }
                                                    for(int i = 0; i<moduleList.size(); i++){
                                                        final int j=i;
                                                        final String str;
                                                        if(moduleList.get(i)==null || moduleList.get(i).equals("")){
                                                            str=" ";
                                                        }else{
                                                            str=moduleList.get(i);
                                                        }
                                                        db.collection("Student")
                                                                .document(user.getEmail().split("@")[0])
                                                                .collection("Modules")
                                                                .document(str)
                                                                .collection("Schedule")
                                                                .document("Wednesday")
                                                                .collection("Class")
                                                                .get()
                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                        if (task.isSuccessful()) {
                                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                Course course=new Course();
                                                                                course.setCourseId(moduleList.get(j));
                                                                                course.setClassId(document.getString("classId"));
                                                                                course.setClassBegin(document.getString("classBegin"));
                                                                                course.setClassHour(document.getString("classHour"));
                                                                                course.setClassType(document.getString("classType"));
                                                                                course.setClassRoom(document.getString("classRoom"));
                                                                                courseList.add(course);
                                                                            }
                                                                            Collections.sort(courseList);

                                                                            if(j==moduleList.size()-1){
                                                                                List<String> infoList=new ArrayList<>();
                                                                                String info=" ";
                                                                                for(int i=0;i<courseList.size();i++){
                                                                                    if(i+1<courseList.size()){
                                                                                        if(courseList.get(i).getClassBegin().equals(courseList.get(i+1).getClassBegin())){
                                                                                            infoList.add(courseList.get(i).getCourseId()+"'s "+courseList.get(i).getClassType()+" and "+courseList.get(i+1).getCourseId()+"'s "+courseList.get(i+1).getClassType()+" on Wednesday.");
                                                                                        }
                                                                                    }
                                                                                }
                                                                                for(int i=0;i<infoList.size();i++){
                                                                                    info+="\n"+infoList.get(i);
                                                                                }
                                                                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                                                builder.setTitle("Conflict Report");
                                                                                if(info.length()<5) info="No conflict!";
                                                                                builder.setMessage(info);
                                                                                AlertDialog dialog = builder.create();
                                                                                dialog.show();
                                                                            }
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                }
                                            }
                                        }
                                    });
                            break;
                    case 3: courseList.clear();
                            db.collection("Student")
                            .document(Objects.requireNonNull(user.getEmail()).split("@")[0])
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {

                                        DocumentSnapshot document = task.getResult();
                                        assert document != null;
                                        if (document.exists()) {
                                            Student stu=document.toObject(Student.class);
                                            final List<String> moduleList= new ArrayList<>();
                                            assert stu != null;
                                            final int size=stu.getStudentModule().size();
                                            for(int i=0;i<size;i++){
                                                moduleList.add(stu.getStudentModule().get(i));
                                            }
                                            for(int i = 0; i<moduleList.size(); i++){
                                                final int j=i;
                                                final String str;
                                                if(moduleList.get(i)==null || moduleList.get(i).equals("")){
                                                    str=" ";
                                                }else{
                                                    str=moduleList.get(i);
                                                }
                                                db.collection("Student")
                                                        .document(user.getEmail().split("@")[0])
                                                        .collection("Modules")
                                                        .document(str)
                                                        .collection("Schedule")
                                                        .document("Thursday")
                                                        .collection("Class")
                                                        .get()
                                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                if (task.isSuccessful()) {
                                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                                        Course course=new Course();
                                                                        course.setCourseId(moduleList.get(j));
                                                                        course.setClassId(document.getString("classId"));
                                                                        course.setClassBegin(document.getString("classBegin"));
                                                                        course.setClassHour(document.getString("classHour"));
                                                                        course.setClassType(document.getString("classType"));
                                                                        course.setClassRoom(document.getString("classRoom"));
                                                                        courseList.add(course);
                                                                    }
                                                                    Collections.sort(courseList);

                                                                    if(j==moduleList.size()-1){
                                                                        List<String> infoList=new ArrayList<>();
                                                                        String info=" ";
                                                                        for(int i=0;i<courseList.size();i++){
                                                                            if(i+1<courseList.size()){
                                                                                if(courseList.get(i).getClassBegin().equals(courseList.get(i+1).getClassBegin())){
                                                                                    infoList.add(courseList.get(i).getCourseId()+"'s "+courseList.get(i).getClassType()+" and "+courseList.get(i+1).getCourseId()+"'s "+courseList.get(i+1).getClassType()+" on Thursday.");
                                                                                }
                                                                            }
                                                                        }
                                                                        for(int i=0;i<infoList.size();i++){
                                                                            info+="\n"+infoList.get(i);
                                                                        }
                                                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                                        builder.setTitle("Conflict Report");
                                                                        if(info.length()<5) info="No conflict!";
                                                                        builder.setMessage(info);
                                                                        AlertDialog dialog = builder.create();
                                                                        dialog.show();
                                                                    }
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                }
                            });
                            break;
                    case 4: courseList.clear();
                            db.collection("Student")
                                    .document(Objects.requireNonNull(user.getEmail()).split("@")[0])
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {

                                                DocumentSnapshot document = task.getResult();
                                                assert document != null;
                                                if (document.exists()) {
                                                    Student stu=document.toObject(Student.class);
                                                    final List<String> moduleList= new ArrayList<>();
                                                    assert stu != null;
                                                    final int size=stu.getStudentModule().size();
                                                    for(int i=0;i<size;i++){
                                                        moduleList.add(stu.getStudentModule().get(i));
                                                    }
                                                    for(int i = 0; i<moduleList.size(); i++){
                                                        final int j=i;
                                                        final String str;
                                                        if(moduleList.get(i)==null || moduleList.get(i).equals("")){
                                                            str=" ";
                                                        }else{
                                                            str=moduleList.get(i);
                                                        }
                                                        db.collection("Student")
                                                                .document(user.getEmail().split("@")[0])
                                                                .collection("Modules")
                                                                .document(str)
                                                                .collection("Schedule")
                                                                .document("Friday")
                                                                .collection("Class")
                                                                .get()
                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                        if (task.isSuccessful()) {
                                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                Course course=new Course();
                                                                                course.setCourseId(moduleList.get(j));
                                                                                course.setClassId(document.getString("classId"));
                                                                                course.setClassBegin(document.getString("classBegin"));
                                                                                course.setClassHour(document.getString("classHour"));
                                                                                course.setClassType(document.getString("classType"));
                                                                                course.setClassRoom(document.getString("classRoom"));
                                                                                courseList.add(course);
                                                                            }
                                                                            Collections.sort(courseList);

                                                                            if(j==moduleList.size()-1){
                                                                                List<String> infoList=new ArrayList<>();
                                                                                String info=" ";
                                                                                for(int i=0;i<courseList.size();i++){
                                                                                    if(i+1<courseList.size()){
                                                                                        if(courseList.get(i).getClassBegin().equals(courseList.get(i+1).getClassBegin())){
                                                                                            infoList.add(courseList.get(i).getCourseId()+"'s "+courseList.get(i).getClassType()+" and "+courseList.get(i+1).getCourseId()+"'s "+courseList.get(i+1).getClassType()+" on Friday.");
                                                                                        }
                                                                                    }
                                                                                }
                                                                                for(int i=0;i<infoList.size();i++){
                                                                                    info+="\n"+infoList.get(i);
                                                                                }
                                                                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                                                builder.setTitle("Conflict Report");
                                                                                if(info.length()<5) info="No conflict!";
                                                                                builder.setMessage(info);
                                                                                AlertDialog dialog = builder.create();
                                                                                dialog.show();
                                                                            }
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                }
                                            }
                                        }
                                    });
                            break;
                }
            }
        });
        return binding.getRoot();
    }
}