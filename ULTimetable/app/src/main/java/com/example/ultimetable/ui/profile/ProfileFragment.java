package com.example.ultimetable.ui.profile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.ultimetable.R;
import com.example.ultimetable.databinding.FragmentProfileBinding;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //profileViewModel =new ViewModelProvider(this).get(ProfileViewModel.class);

        FragmentProfileBinding binding= DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog1();
            }
        });
        binding.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog2();
            }
        });
        binding.btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog3();
            }
        });
        binding.btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog4();
            }
        });
        binding.btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog5();
            }
        });
        binding.btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog6();
            }
        });
        binding.btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog7();
            }
        });
        return binding.getRoot();
    }

    private void showDialog1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setIcon(R.drawable.picture);
        builder.setTitle("Edit User Picture");
        builder.setMessage("(Picture Upload Component)");
        builder.setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface1, int i) {

                    }
                });
        AlertDialog dialog1 = builder.create();
        dialog1.show();
    }



    private void showDialog2() {
        final EditText et2 = new EditText(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setIcon(R.drawable.picture);
        builder.setTitle("Edit First Name");
        builder.setView(et2);
        builder.setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onClick(DialogInterface dialogInterface2, int which) {
                        String input = et2.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getApplicationContext(), "Can not be NULL" + input, Toast.LENGTH_LONG).show();
                        }
                        else {
                            Intent intent = new Intent();
                            intent.putExtra("content", input);
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog dialog2 = builder.create();
        dialog2.show();
    }

    private void showDialog3() {
        final EditText et3 = new EditText(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setIcon(R.drawable.picture);
        builder.setTitle("Edit Last Name");
        builder.setView(et3);
        builder.setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onClick(DialogInterface dialogInterface3, int which) {
                        String input = et3.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getApplicationContext(), "Can not be NULL" + input, Toast.LENGTH_LONG).show();
                        }
                        else {
                            Intent intent = new Intent();
                            intent.putExtra("content", input);
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog dialog3 = builder.create();
        dialog3.show();
    }


    private void showDialog4() {
        final EditText et4 = new EditText(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setIcon(R.drawable.picture);
        builder.setTitle("Edit Email Address");
        builder.setView(et4);
        builder.setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onClick(DialogInterface dialogInterface4, int which) {
                        String input = et4.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getApplicationContext(), "Can not be NULL" + input, Toast.LENGTH_LONG).show();
                        }
                        else {
                            Intent intent = new Intent();
                            intent.putExtra("content", input);
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog dialog4 = builder.create();
        dialog4.show();
    }

    private void showDialog5() {
        final EditText et5 = new EditText(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setIcon(R.drawable.picture);
        builder.setTitle("Edit City/Town");
        builder.setView(et5);
        builder.setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onClick(DialogInterface dialogInterface5, int which) {
                        String input = et5.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getApplicationContext(), "Can not be NULL" + input, Toast.LENGTH_LONG).show();
                        }
                        else {
                            Intent intent = new Intent();
                            intent.putExtra("content", input);
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog dialog5 = builder.create();
        dialog5.show();
    }


    private void showDialog6() {
        final EditText et6 = new EditText(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setIcon(R.drawable.picture);
        builder.setTitle("Edit Country/Region");
        builder.setView(et6);
        builder.setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onClick(DialogInterface dialogInterface6, int which) {
                        String input = et6.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getApplicationContext(), "Can not be NULL" + input, Toast.LENGTH_LONG).show();
                        }
                        else {
                            Intent intent = new Intent();
                            intent.putExtra("content", input);
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog dialog6 = builder.create();
        dialog6.show();
    }

    private void showDialog7() {
        final EditText et7 = new EditText(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setIcon(R.drawable.picture);
        builder.setTitle("Edit Your Discription");
        builder.setView(et7);
        builder.setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onClick(DialogInterface dialogInterface7, int which) {
                        String input = et7.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getApplicationContext(), "Can not be NULL" + input, Toast.LENGTH_LONG).show();
                        }
                        else {
                            Intent intent = new Intent();
                            intent.putExtra("content", input);
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog dialog7 = builder.create();
        dialog7.show();
    }
}