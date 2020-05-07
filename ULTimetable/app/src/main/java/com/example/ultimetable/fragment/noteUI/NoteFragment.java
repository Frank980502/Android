package com.example.ultimetable.fragment.noteUI;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ultimetable.adapter.NoteRecyclerViewAdapter;
import com.example.ultimetable.databinding.FragmentNoteBinding;
import com.example.ultimetable.bean.Note;
import com.example.ultimetable.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NoteFragment extends Fragment {

    private List<Note> noteList=new ArrayList<Note>();
    private NoteRecyclerViewAdapter adapter;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private FragmentNoteBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        //noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_note, container, false);
        db= FirebaseFirestore.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();

        showData();
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                String maxId;
                if(noteList.size()!=0){
                    maxId=noteList.get(noteList.size()-1).getId();
                }else{
                    maxId="Note0";
                }
                bundle.putInt("Type",1);//1 代表 ADD
                bundle.putString("MaxId",maxId);
                Navigation.findNavController(v).navigate(R.id.action_nav_note_to_edit_note,bundle);
            }
        });
        return binding.getRoot();
    }

    private void showData(){
        noteList.clear();
        db.collection("Student")
                .document(user.getEmail().split("@")[0])
                .collection("Note")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Note note=new Note();
                                note.setId(document.getString("id"));
                                note.setCreationDate(document.getTimestamp("creationDate"));
                                note.setCd(document.getString("cd"));
                                note.setTitle(document.getString("title"));
                                note.setDescription(document.getString("description"));
                                note.setRd(document.getString("rd"));
                                note.setState(document.getBoolean("state"));
                                noteList.add(note);
                                RecyclerView recyclerView = binding.recyclerView;
                                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
                                adapter = new NoteRecyclerViewAdapter(getActivity(), noteList);
                                recyclerView.setAdapter(adapter);
                            }
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}

