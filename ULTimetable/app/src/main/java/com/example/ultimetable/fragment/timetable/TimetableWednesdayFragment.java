package com.example.ultimetable.fragment.timetable;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ultimetable.R;
import com.example.ultimetable.adapter.TimetableRecyclerViewAdapter;
import com.example.ultimetable.bean.MyClass;
import com.example.ultimetable.bean.Student;
import com.example.ultimetable.databinding.FragmentTimetableListBinding;
import com.example.ultimetable.bean.Course;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {/@link OnListFragmentInteractionListener}
 * interface.
 */
public class TimetableWednesdayFragment extends Fragment {

    private List<Course> courseList=new ArrayList<Course>();
    private TimetableRecyclerViewAdapter adapter;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private FragmentTimetableListBinding binding;
    private String courseName;

    public TimetableWednesdayFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_timetable_list, container, false);
        db= FirebaseFirestore.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();
        showData();
        return binding.getRoot();
    }

    private void showData(){
        final List<String> moduleList=new ArrayList<String>();
        courseList.clear();
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
                                int size=stu.getStudentModule().size();
                                for(int i=0;i<size;i++){
                                    moduleList.add(stu.getStudentModule().get(i));
                                }

                                for(int i=0;i<moduleList.size();i++){
                                    final int j=i;
                                    final String str;
                                    final Map<String, String> name = new HashMap<>();

                                    if(moduleList.get(i)==null || moduleList.get(i).equals("")){
                                        str=" ";
                                    }else{
                                        str=moduleList.get(i);
                                    }

                                    db.collection("Course")
                                            .document(str)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot document = task.getResult();
                                                        if (document.exists()) {
                                                            courseName=document.getString("courseName");
                                                            name.put(moduleList.get(j),courseName);
                                                        }
                                                    }
                                                }
                                            });

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
                                                            course.setCourseName(name.get(moduleList.get(j)));
                                                            course.setClassId(document.getString("classId"));
                                                            course.setClassBegin(document.getString("classBegin"));
                                                            course.setClassHour(document.getString("classHour"));
                                                            course.setClassType(document.getString("classType"));
                                                            course.setClassRoom(document.getString("classRoom"));
                                                            courseList.add(course);
                                                        }
                                                        Collections.sort(courseList);
                                                        RecyclerView recyclerView = binding.recyclerViewMonday;
                                                        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
                                                        adapter = new TimetableRecyclerViewAdapter(getActivity(), courseList,3);
                                                        recyclerView.setAdapter(adapter);
                                                    }
                                                }
                                            });
                                }
                            }
                        }
                    }
                });
    }
}
