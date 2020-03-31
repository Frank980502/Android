package com.example.ultimetable.ui.noteUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ultimetable.adapter.NoteRecyclerViewAdapter;
import com.example.ultimetable.databinding.FragmentNoteBinding;
import com.example.ultimetable.bean.Note;
import com.example.ultimetable.R;
import com.example.ultimetable.databinding.FragmentNoteEditBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class NoteFragment extends Fragment {

    private View root;
    private List<Note> noteList=new ArrayList<Note>();
    private NoteViewModel noteViewModel;
    private NoteRecyclerViewAdapter adapter;
    private FirebaseFirestore db;
    private Note note1,note2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        //db = FirebaseFirestore.getInstance();
        //root = inflater.inflate(R.layout.fragment_note, container, false);
        noteList.clear();
        note1 = new Note("1", "CS4082", "exam");
        note2 = new Note("2", "CS4084", "exam");
        noteList.add(note1);
        noteList.add(note2);

        FragmentNoteBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note, container, false);
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new NoteRecyclerViewAdapter(getActivity(), noteList);
        //adapter.onItemDatasChanged();
        recyclerView.setAdapter(adapter);
        return binding.getRoot();

    }
}

