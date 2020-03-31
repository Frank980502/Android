package com.example.ultimetable.adapter;

import androidx.recyclerview.widget.RecyclerView;
import com.example.ultimetable.databinding.CardNoteBinding;

public class NoteRecyclerViewViewHolder extends RecyclerView.ViewHolder{

    CardNoteBinding binding;

    public NoteRecyclerViewViewHolder(CardNoteBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
