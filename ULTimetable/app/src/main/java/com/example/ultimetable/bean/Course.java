package com.example.ultimetable.bean;

import android.annotation.SuppressLint;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.ultimetable.BR;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Course extends BaseObservable implements Comparable<Course>{
    private String courseId;
    private String courseName;
    private String classId;
    private String classBegin;
    private String classHour;
    private String classType;
    private String classRoom;

    public Course(){
    }
    public Course(String courseId,String courseName, String classBegin, String classType,String classRoom){
        this.courseId=courseId;
        this.courseName=courseName;
        this.classBegin=classBegin;
        this.classType=classType;
        this.classRoom=classRoom;
    }
    public Course(String courseId,String courseName, String classBegin, String classHour, String classType,String classRoom){
        this.courseId=courseId;
        this.courseName=courseName;
        this.classBegin=classBegin;
        this.classHour=classHour;
        this.classType=classType;
        this.classRoom=classRoom;
    }
    @Bindable
    public String getCourseId() {
        return courseId;
    }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
        notifyPropertyChanged(BR.courseId);
    }
    @Bindable
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
        notifyPropertyChanged(BR.courseName);
    }
    @Bindable
    public String getClassBegin() {
        return classBegin;
    }

    public void setClassBegin(String classBegin) {
        this.classBegin = classBegin;
        notifyPropertyChanged(BR.classBegin);
    }
    @Bindable
    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
        notifyPropertyChanged(BR.classType);
    }
    @Bindable
    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
        notifyPropertyChanged(BR.classRoom);
    }
    @Bindable
    public String getClassHour() {
        return classHour;
    }

    public void setClassHour(String classHour) {
        this.classHour = classHour;
        notifyPropertyChanged(BR.classHour);
    }
    @Bindable
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
        notifyPropertyChanged(BR.classId);
    }

    @Override
    public int compareTo(Course o) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        boolean flag = false;
        try {
            Date d1=sdf.parse(this.getClassBegin());
            Date d2=sdf.parse(o.getClassBegin());
            if (d1.getTime() > d2.getTime()) {
                flag = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (flag) {
            return 1;
        } else {
            return -1;
        }
    }
}
