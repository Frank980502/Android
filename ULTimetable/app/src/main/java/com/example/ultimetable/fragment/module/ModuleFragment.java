package com.example.ultimetable.fragment.module;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ultimetable.R;
import com.example.ultimetable.adapter.TimetableRecyclerViewAdapter;
import com.example.ultimetable.bean.Course;
import com.example.ultimetable.bean.MyClass;
import com.example.ultimetable.bean.Student;
import com.example.ultimetable.databinding.FragmentTimetableListBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ModuleFragment extends Fragment {

    private List<String> courseIdList=new ArrayList<>();
    private List<MyClass> classList=new ArrayList<MyClass>();
    private FirebaseFirestore db;
    private FirebaseUser user;
    private String classId,classBegin,classHour,classType,classRoom;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_module, container, false);
        db= FirebaseFirestore.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();
        final TextView tv= root.findViewById(R.id.tv2);
        tv.setText(null);
        update(tv);
        final Spinner sp1 = root.findViewById(R.id.AllCourseId1);
        final Spinner sp2 = root.findViewById(R.id.AllCourseId2);
        courseIdList.add("NULL");
        db.collection("Course")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                courseIdList.add(document.getId());
                            }
                            ArrayAdapter<String> courseIdAdapter=new ArrayAdapter<>(requireActivity(),R.layout.support_simple_spinner_dropdown_item,courseIdList);
                            sp1.setAdapter(courseIdAdapter);
                            sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, final int position, final long id) {
                                    if(!courseIdList.get(position).equals("NULL")) {
                                        db.collection("Student")
                                                .document(Objects.requireNonNull(user.getEmail()).split("@")[0])
                                                .get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        Student student = documentSnapshot.toObject(Student.class);
                                                        assert student != null;
                                                        List<String> newModuleList = student.getStudentModule();
                                                        if(!newModuleList.contains(courseIdList.get(position))) {
                                                            //add
                                                            newModuleList.add(courseIdList.get(position));
                                                            student.setStudentModule(newModuleList);
                                                            db.collection("Student")
                                                                    .document(Objects.requireNonNull(user.getEmail()).split("@")[0])
                                                                    .set(student);
                                                            loadCourseData(courseIdList.get(position),"Monday");
                                                            loadCourseData(courseIdList.get(position),"Tuesday");
                                                            loadCourseData(courseIdList.get(position),"Wednesday");
                                                            loadCourseData(courseIdList.get(position),"Thursday");
                                                            loadCourseData(courseIdList.get(position),"Friday");
                                                            update(tv);
                                                            Toast.makeText(getContext(),"Add successfully!",Toast.LENGTH_LONG).show();
                                                        }else{
                                                            Toast.makeText(getContext(),"You have chosen this module!",Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            sp2.setAdapter(courseIdAdapter);
                            sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                                    if(!courseIdList.get(position).equals("NULL")) {
                                        db.collection("Student")
                                                .document(Objects.requireNonNull(user.getEmail()).split("@")[0])
                                                .get()
                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        Student student = documentSnapshot.toObject(Student.class);
                                                        assert student != null;
                                                        List<String> newModuleList = student.getStudentModule();
                                                        if(newModuleList.contains(courseIdList.get(position))) {
                                                            newModuleList.remove(courseIdList.get(position));
                                                            student.setStudentModule(newModuleList);
                                                            db.collection("Student")
                                                                    .document(Objects.requireNonNull(user.getEmail()).split("@")[0])
                                                                    .set(student);
                                                            update(tv);
                                                            Toast.makeText(getContext(),"Delete successfully!",Toast.LENGTH_LONG).show();
                                                        }else{
                                                            Toast.makeText(getContext(),"You don't choose this module!",Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                    }
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                        }
                    }
                });
        return root;
    }

    private void update(final TextView tv){
        tv.setText(null);
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
                                List<String> moduleList= new ArrayList<>();
                                assert stu != null;
                                int size=stu.getStudentModule().size();
                                for(int i=0;i<size;i++){
                                    moduleList.add(stu.getStudentModule().get(i));
                                    tv.append(moduleList.get(i)+"\n");
                                }
                            }
                        }
                    }
                });
    }

    private void loadCourseData(final String id, final String weekday){

        db.collection("Course")
                .document(id)
                .collection("Schedule")
                .document(weekday)
                .collection("Class")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int num=1;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                MyClass myclass=new MyClass();

                                classId=document.getId();
                                classBegin=document.getString("Begin");
                                classHour=document.getString("Hour");
                                classType=document.getString("Type");
                                classRoom=document.getString("Room");

                                myclass.setClassId(classId);
                                myclass.setClassBegin(classBegin);
                                myclass.setClassHour(classHour);
                                myclass.setClassType(classType);
                                myclass.setClassRoom(classRoom);

                                classList.add(myclass);
                                num++;
                            }
                            for(int classNum=0;classNum<classList.size();classNum++){
                                db.collection("Student")
                                        .document(user.getEmail().split("@")[0])
                                        .collection("Modules")
                                        .document(id)
                                        .collection("Schedule")
                                        .document(weekday)
                                        .collection("Class")
                                        .document("Class"+(classNum+1))
                                        .set(classList.get(classNum));
                            }
                            classList.clear();
                        }
                    }
                });
    }
}