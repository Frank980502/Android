package com.example.ultimetable.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.ultimetable.BR;

public class MyClass {

    private String classBegin;
    private String classHour;
    private String classType;
    private String classRoom;
    private String classId;

    public MyClass() {
    }

    public MyClass(String classBegin, String classHour, String classType, String classRoom,String classId) {
        this.classBegin=classBegin;
        this.classHour=classHour;
        this.classType=classType;
        this.classRoom=classRoom;
        this.classId=classId;
    }

    public String getClassBegin() {
        return classBegin;
    }

    public void setClassBegin(String classBegin) {
        this.classBegin = classBegin;
    }

    public String getClassHour() {
        return classHour;
    }

    public void setClassHour(String classHour) {
        this.classHour = classHour;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}



