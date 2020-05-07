package com.example.ultimetable.adapter;

import androidx.recyclerview.widget.RecyclerView;
import com.example.ultimetable.databinding.FragmentTimetableItemBinding;

public class TimetableRecyclerViewViewHolder extends RecyclerView.ViewHolder{

    FragmentTimetableItemBinding binding;

    public TimetableRecyclerViewViewHolder(FragmentTimetableItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
