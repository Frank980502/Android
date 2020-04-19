package com.example.ultimetable.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ultimetable.R;
import com.example.ultimetable.databinding.CardNoteBinding;
import com.example.ultimetable.bean.Note;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewViewHolder>{
    private Context context;
    private List<Note> item;
    private Note note;
    private FirebaseFirestore db;
    private FirebaseUser user;
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public NoteRecyclerViewAdapter(Context context, List<Note> item){
        if(this.item==null){
            this.item=new ArrayList<>();
        }
        this.context=context;
        this.item=item;
        user= FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public NoteRecyclerViewViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        CardNoteBinding binding = DataBindingUtil.inflate(inflater, R.layout.card_note, viewGroup, false);
        return new NoteRecyclerViewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteRecyclerViewViewHolder viewHolder, final int position) {
        CardNoteBinding binding=DataBindingUtil.getBinding(viewHolder.itemView);
        binding.setNote(item.get(position));
        binding.executePendingBindings();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                note=viewHolder.binding.getNote();
                Bundle bundle=new Bundle();
                bundle.putInt("Type",2);// Update
                bundle.putString("ID",note.getId());
                bundle.putString("Title",note.getTitle());
                bundle.putString("Description",note.getDescription());
                bundle.putString("ReminderDate",note.getRd());
                bundle.putBoolean("State",note.getState());
                Navigation.findNavController(view).navigate(R.id.action_nav_note_to_edit_note,bundle);
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(context);

                //options to display in dialog
                String[] options={"Delete"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(which==0){
                            removeData(position);
                        }
                    }
                }).create().show();
                return false;
            }
        });

    }
    @Override
    public int getItemCount() {
        return item.size();
    }


    public void removeData(int position) {

            db.collection("Student").document(user.getEmail().split("@")[0])
                    .collection("Note")
                    .document(item.get(position).getId())
                    .delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
            item.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(0,item.size());
        }
}
