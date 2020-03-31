package com.example.ultimetable.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.ultimetable.BR;

public class Course extends BaseObservable {
    private String courseId;
    private String courseName;
    private String classBegin;
    //private String classHour;
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
    /*
    @Bindable
    public Map getCourseSchedule() {
        return courseSchedule;
    }
    public void setCourseSchedule(Map<String,String> courseSchedule) {
        this.courseSchedule = courseSchedule;
    }
    @Bindable
    public String getMon() {
        return Mon;
    }
    public void setMon(String mon) {
        Mon = mon;
    }
    @Bindable
    public String getTue() {
        return Tue;
    }

    public void setTue(String tue) {
        Tue = tue;
    }
    @Bindable
    public String getWed() {
        return Wed;
    }
    public void setWed(String wed) {
        Wed = wed;
    }
    @Bindable
    public String getThu() {
        return Thu;
    }
    public void setThu(String thu) {
        Thu = thu;
    }
    @Bindable
    public String getFri() {
        return Fri;
    }
    public void setFri(String fri) {
        Fri = fri;
    }
    @Bindable
    public String[] getMonday() {
        return Monday;
    }
    public void setMonday(String[] monday) {
        Monday = monday;
    }
    @Bindable
    public String[] getTuesday() {
        return Tuesday;
    }
    public void setTuesday(String[] tuesday) {
        Tuesday = tuesday;
    }
    @Bindable
    public String[] getWednesday() {
        return Wednesday;
    }
    public void setWednesday(String[] wednesday) {
        Wednesday = wednesday;
    }
    @Bindable
    public String[] getThursday() {
        return Thursday;
    }
    public void setThursday(String[] thursday) {
        Thursday = thursday;
    }
    @Bindable
    public String[] getFriday() {
        return Friday;
    }
    public void setFriday(String[] friday) {
        Friday = friday;
    }
    @Bindable
    public String getOneClass() {
        return oneClass;
    }
    public void setOneClass(String oneClass) {
        this.oneClass = oneClass;
    }
    @Bindable
    public String[] getClassInfo() {
        return classInfo;
    }
    public void setClassInfo(String[] classInfo) {
        this.classInfo = classInfo;
    }
    @Bindable
    public String getClassBegin() {
        return classBegin;
    }
    public void setClassBegin(String classBegin) {
        this.classBegin = classBegin;
    }
    @Bindable
    public String getClassHour() {
        return classHour;
    }
    public void setClassHour(String classHour) {
        this.classHour = classHour;
    }
    @Bindable
    public String getClassType() {
        return classType;
    }
    public void setClassType(String classType) {
        this.classType = classType;
    }
    @Bindable
    public String getClassRoom() {
        return classRoom;
    }
    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public void setMon(Map<String,String> courseSchedule) {
        Mon=courseSchedule.get("Mon");
    }
    public void setTue(Map<String,String> courseSchedule) {
        Tue=courseSchedule.get("Tue");
    }
    public void setTWed(Map<String,String> courseSchedule) {
        Wed=courseSchedule.get("Wed");
    }
    public void setThu(Map<String,String> courseSchedule) {
        Thu=courseSchedule.get("Thu");
    }
    public void setFri(Map<String,String> courseSchedule) {
        Fri=courseSchedule.get("Fri");
    }

    public void setMonday(String Mon) {
        Monday=Mon.split(";");
    }
    public void setTuesday(String Tue) {
        Tuesday=Tue.split(";");
    }
    public void setWednesday(String Wed) {
        Wednesday=Wed.split(";");
    }
    public void setThursday(String Thu) {
        Thursday=Thu.split(";");
    }
    public void setFriday(String Fri) {
        Friday=Fri.split(";");
    }
    public void setClassInfo(String oneClass) {
        classInfo = oneClass.split("\\+");
    }

    public void setClassHour(String[] classInfo) {
        classHour = classInfo[1];
    }
    public void setClassType(String[] classInfo) {
        classType = classInfo[2];
    }
    public void setClassRoom(String[] classInfo) {
        classRoom = classInfo[3];
    }*/
}
