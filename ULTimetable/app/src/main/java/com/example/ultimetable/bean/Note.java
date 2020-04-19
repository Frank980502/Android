package com.example.ultimetable.bean;

import android.annotation.SuppressLint;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.ultimetable.BR;
import com.google.firebase.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Note extends BaseObservable {

    private String id,cd,rd,title,description;
    private Timestamp creationDate;
    private Boolean state;

    public Note(){
    }

    public Note(String id,String title,String description,Boolean state){
        this.id=id;
        this.title=title;
        this.description=description;
        this.creationDate=new Timestamp(new Date());
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm");
        this.cd=sdf.format(creationDate.toDate());
        this.state=state;
    }

    public Note(String id,String title,String description,String rd,Boolean state) {
        this.id=id;
        this.title=title;
        this.description=description;
        this.creationDate=new Timestamp(new Date());
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm");
        this.cd=sdf.format(creationDate.toDate());
        this.rd=rd;
        this.state=state;
    }

    @Bindable
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id=id;
        notifyPropertyChanged(BR.id);
    }
    @Bindable
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
        notifyPropertyChanged(BR.title);
    }
    @Bindable
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description=description;
        notifyPropertyChanged(BR.description);
    }
    @Bindable
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
        notifyPropertyChanged(BR.creationDate);
    }
    @Bindable
    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
        notifyPropertyChanged(BR.cd);
    }
    @Bindable
    public String getRd() {
        return rd;
    }

    public void setRd(String rd) {
        this.rd = rd;
        notifyPropertyChanged(BR.rd);
    }
    @Bindable
    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
        notifyPropertyChanged(BR.state);
    }
}

