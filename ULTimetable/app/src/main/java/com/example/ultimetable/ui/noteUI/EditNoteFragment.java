package com.example.ultimetable.ui.noteUI;


import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.ultimetable.R;
import com.example.ultimetable.adapter.NoteRecyclerViewAdapter;
import com.example.ultimetable.bean.Note;
import com.example.ultimetable.databinding.FragmentNoteEditBinding;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditNoteFragment extends Fragment {


    public EditNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final FragmentNoteEditBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_edit, container, false);
        final Integer position=getArguments().getInt("position");
        String id=getArguments().getString("Id");
        String title=getArguments().getString("Title");
        String description=getArguments().getString("Description");
        final Note note=new Note(id,title,description);
        binding.setNote(note);
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                /*
                note.setTitle(binding.title.getText().toString());
                note.setDescription(binding.description.getText().toString());
                binding.setNote(note);
                //NoteRecyclerViewAdapter mAdapter=new NoteRecyclerViewAdapter();
                Bundle bundle=new Bundle();
                bundle.putInt("position",position);
                bundle.putString("Id",note.getId());
                bundle.putString("Title",note.getTitle());
                bundle.putString("Description",note.getDescription());
                */
                Navigation.findNavController(v).navigate(R.id.action_edit_note_to_nav_note);
            }
        });
        return binding.getRoot();
    }

}
