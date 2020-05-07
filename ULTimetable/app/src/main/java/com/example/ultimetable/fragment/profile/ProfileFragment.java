package com.example.ultimetable.fragment.profile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.ultimetable.R;
import com.example.ultimetable.bean.Student;
import com.example.ultimetable.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class ProfileFragment extends Fragment {

    private FirebaseFirestore db;
    private FirebaseUser user;
    private FragmentProfileBinding binding;
    private Student stu;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        db= FirebaseFirestore.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();

        stu=new Student();
        stu.setStudentName(user.getDisplayName());
        stu.setStudentId(user.getEmail().split("@")[0]);
        stu.setStudentEmail(user.getEmail());
        binding.setStudent(stu);

        binding.editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog1();
            }
        });

        return binding.getRoot();
    }

    private void showDialog1() {
        final EditText et = new EditText(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Name");
        builder.setView(et);
        builder.setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onClick(DialogInterface dialogInterface5, int which) {
                        String input = et.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getApplicationContext(), "Can not be NULL" + input, Toast.LENGTH_LONG).show();
                        }
                        else {
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(input)
                                    .build();
                            user.updateProfile(profileUpdates);
                            stu.setStudentName(input);
                            db.collection("Student")
                                    .document(user.getEmail().split("@")[0])
                                    .update("studentName",input);
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

/*
    private void showDialog2() {
        final EditText et = new EditText(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Email Address");
        builder.setView(et);
        builder.setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onClick(DialogInterface dialogInterface6, int which) {
                        String input = et.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getApplicationContext(), "Can not be NULL" + input, Toast.LENGTH_LONG).show();
                        }
                        else {
                            user.updateEmail(input);
                            stu.setStudentId(input.split("@")[0]);
                            stu.setStudentEmail(input);
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
 */
}