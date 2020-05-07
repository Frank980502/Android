package com.example.ultimetable.fragment.noteUI;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TimePicker;

import com.example.ultimetable.AlarmService;
import com.example.ultimetable.R;
import com.example.ultimetable.bean.Note;
import com.example.ultimetable.databinding.FragmentNoteEditBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class EditNoteFragment extends Fragment {

    private int year,month,day,hour,minute;
    private int newYear,newMonth,newDay,newHour,newMinute;
    private int request=0;
    private String dateStr,timeStr;
    private String id,newId;

    private Note note,note2;

    private FirebaseFirestore db;
    private FirebaseUser user;

    private FragmentNoteEditBinding binding;

    public EditNoteFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_note_edit, container, false);
        db= FirebaseFirestore.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();

        Calendar calendar = Calendar.getInstance();
        year     = calendar.get(Calendar.YEAR);
        month    = calendar.get(Calendar.MONTH);
        day      = calendar.get(Calendar.DATE);
        hour     = calendar.get(Calendar.HOUR);
        minute   = calendar.get(Calendar.MINUTE);

        initView();
        return binding.getRoot();
    }
    //Add
    private void initView(){
        assert getArguments() != null;
        int type = getArguments().getInt("Type");
        if (type ==1){
            String maxId = getArguments().getString("MaxId");
            assert maxId != null;
            newId="Note"+(Integer.parseInt(maxId.substring(4))+1);
            note=new Note(newId,null,null,null,false);
            binding.setNote(note);

            showReminderDate();

            binding.saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    note2=new Note(newId,binding.title.getText().toString(),binding.description.getText().toString(),binding.reminderDate.getText().toString(),false);
                    if(binding.reminderSwitch.isChecked()) note2.setState(true);
                    db.collection("Student").document(Objects.requireNonNull(user.getEmail()).split("@")[0]).collection("Note").document(newId)
                            .set(note2);

                    @SuppressLint("SimpleDateFormat")
                    final SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    long triggerAtMillis= 0;
                    try {
                        triggerAtMillis = sdf.parse(note2.getRd()).getTime();
                        if(triggerAtMillis>=System.currentTimeMillis()){
                            Intent intent = new Intent(getActivity(), AlarmService.class);
                            intent.setAction(AlarmService.ACTION_ALARM);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            PendingIntent pi = PendingIntent.getService(getActivity(),request++,intent,0);
                            AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                            int type = AlarmManager.RTC_WAKEUP;
                            manager.setExact(type,triggerAtMillis, pi);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Navigation.findNavController(v).navigate(R.id.action_edit_note_to_nav_note);

                }
            });
        }else if(type ==2){
            id=getArguments().getString("ID");
            String title = getArguments().getString("Title");
            String description = getArguments().getString("Description");
            String rd = getArguments().getString("ReminderDate");
            Boolean state=getArguments().getBoolean("State");
            note=new Note(id, title, description, rd,state);
            if(note.getState()) binding.reminderSwitch.setChecked(true);
            binding.setNote(note);
            showReminderDate();
            binding.saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    note2=new Note(id,binding.title.getText().toString(),binding.description.getText().toString(),binding.reminderDate.getText().toString(),false);
                    if(binding.reminderSwitch.isChecked()) note2.setState(true);
                    db.collection("Student").document(Objects.requireNonNull(user.getEmail()).split("@")[0]).collection("Note").document(id)
                            .set(note2)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                }
                            });

                    @SuppressLint("SimpleDateFormat")
                    final SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    long triggerAtMillis= 0;
                    try {
                        triggerAtMillis = sdf.parse(note2.getRd()).getTime();
                        if(triggerAtMillis>=System.currentTimeMillis()){
                            Intent intent = new Intent(getActivity(), AlarmService.class);
                            intent.setAction(AlarmService.ACTION_ALARM);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            PendingIntent pi = PendingIntent.getService(getActivity(),request++,intent,0);
                            AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                            int type = AlarmManager.RTC_WAKEUP;
                            manager.setExact(type,triggerAtMillis, pi);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Navigation.findNavController(v).navigate(R.id.action_edit_note_to_nav_note);
                }
            });
        }
    }

    private void showReminderDate(){
        binding.reminderSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    if(note.getRd()!=null && !note.getRd().equals("")){
                        binding.reminderSwitch.setChecked(true);
                    }else {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int y, int m, int d) {
                                        newYear = y;
                                        newMonth = m;
                                        newDay = d;
                                        dateStr = d + "/" + (m + 1) + "/" + y;
                                        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                                                new TimePickerDialog.OnTimeSetListener() {

                                                    @Override
                                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                        newHour = hourOfDay;
                                                        newMinute = minute;
                                                        if((String.valueOf(hourOfDay).length()==1)) {
                                                            if((String.valueOf(minute).length()==1)){
                                                                timeStr="0"+hourOfDay+":"+"0"+minute;
                                                            }else {
                                                                timeStr="0"+hourOfDay+":"+minute;
                                                            }
                                                        }else{
                                                            if((String.valueOf(minute).length()==1)){
                                                                timeStr=hourOfDay+":"+"0"+minute;
                                                            }else {
                                                                timeStr=hourOfDay+":"+minute;
                                                            }
                                                        }
                                                        dateStr=dateStr+" "+timeStr;
                                                        note.setRd(dateStr);
                                                        binding.reminderSwitch.setChecked(true);
                                                    }
                                                }, hour, minute, true);
                                        timePickerDialog.show();
                                    }
                                }, year, month, day);
                        datePickerDialog.show();
                        binding.reminderSwitch.setChecked(true);
                        if(timeStr==null){
                            dateStr=null;
                            binding.reminderSwitch.setChecked(false);
                        }
                    }
                }else{
                    note.setRd(null);
                    binding.reminderSwitch.setChecked(false);
                    dateStr=null;
                }
            }
        });

        binding.reminderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(note.getRd()!=null && note.getRd()!=""){
                    newYear=Integer.parseInt(note.getRd().split(" ")[0].split("/")[2]);
                    newMonth=Integer.parseInt(note.getRd().split(" ")[0].split("/")[1])-1;
                    newDay=Integer.parseInt(note.getRd().split(" ")[0].split("/")[0]);
                    newHour=Integer.parseInt(note.getRd().split(" ")[1].split(":")[0]);
                    newMinute=Integer.parseInt(note.getRd().split(" ")[1].split(":")[1]);
                }
                DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int y, int m, int d) {
                                newYear = y;
                                newMonth = m;
                                newDay = d;
                                dateStr = d + "/" + (m + 1) + "/" + y;
                                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                                        new TimePickerDialog.OnTimeSetListener() {

                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                newHour = hourOfDay;
                                                newMinute = minute;
                                                if((String.valueOf(hourOfDay).length()==1)) {
                                                    if((String.valueOf(minute).length()==1)){
                                                        timeStr="0"+hourOfDay+":"+"0"+minute;
                                                    }else {
                                                        timeStr="0"+hourOfDay+":"+minute;
                                                    }
                                                }else{
                                                    if((String.valueOf(minute).length()==1)){
                                                        timeStr=hourOfDay+":"+"0"+minute;
                                                    }else {
                                                        timeStr=hourOfDay+":"+minute;
                                                    }
                                                }
                                                dateStr=dateStr+" "+timeStr;
                                                note.setRd(dateStr);
                                            }
                                        }, newHour, newMinute, true);
                                timePickerDialog.show();
                            }
                        }, newYear, newMonth, newDay);
                datePickerDialog.show();
            }
        });
    }

}
