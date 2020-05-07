package com.example.ultimetable.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.ultimetable.BR;

import java.util.List;

public class Student extends BaseObservable {
    private String studentId;
    private String studentName;
    private String studentEmail;
    private List<String> studentModule;

    public Student(){

    }
    @Bindable
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
        notifyPropertyChanged(BR.studentId);
    }
    @Bindable
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
        notifyPropertyChanged(BR.studentName);
    }
    @Bindable
    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
        notifyPropertyChanged(BR.studentEmail);
    }
    @Bindable
    public List<String> getStudentModule() {
        return studentModule;
    }

    public void setStudentModule(List<String> studentModule) {
        this.studentModule = studentModule;
        notifyPropertyChanged(BR.studentModule);
    }
}
