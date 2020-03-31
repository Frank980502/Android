package com.example.ultimetable.ui.noteUI;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ultimetable.bean.Note;

public class NoteViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Note> mNote;

    public NoteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is note fragment");
        mNote = new MutableLiveData<Note>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}