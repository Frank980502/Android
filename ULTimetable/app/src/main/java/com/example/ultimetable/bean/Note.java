package com.example.ultimetable.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.ultimetable.BR;

public class Note extends BaseObservable {

    private String id,title,description;

    public Note(){
    }

    public Note(String id,String title,String description){
        this.id=id;
        this.title=title;
        this.description=description;
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
}

