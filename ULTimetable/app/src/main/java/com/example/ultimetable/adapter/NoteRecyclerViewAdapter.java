package com.example.ultimetable.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

import java.util.List;

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewViewHolder> {
    private Context context;
    private List<Note> itemData;

    public NoteRecyclerViewAdapter(Context context, List<Note> itemData){
        this.context=context;
        this.itemData=itemData;
    }



    @Override
    public NoteRecyclerViewViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        CardNoteBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.card_note, viewGroup, false);
        return new NoteRecyclerViewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteRecyclerViewViewHolder viewHolder, final int position) {

        viewHolder.binding.setNote(itemData.get(position));
        final Note note=viewHolder.binding.getNote();
        viewHolder.binding.executePendingBindings();
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putInt("position",position);
                bundle.putString("ID",note.getId());
                bundle.putString("Title",note.getTitle());
                bundle.putString("Description",note.getDescription());
                Navigation.findNavController(view).navigate(R.id.action_nav_note_to_edit_note,bundle);
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                Log.d("111","222");
                return false;
            }
        });

    }
    @Override
    public int getItemCount() {
        return itemData.size();
    }


    //移除数据
    public void removeData(int position) {
        itemData.remove(position);
        notifyItemRemoved(position);
    }

    //新增数据
    public void addData(int position,Note note) {
        itemData.add(position, note);
        notifyItemInserted(position);
    }

    //更改某个位置的数据
    public void changeData(int position,Note note) {
        itemData.set(position, note);
        notifyItemChanged(position);
    }

    public void onItemDatasChanged(List<Note> newItemData){
        this.itemData = newItemData;
        notifyDataSetChanged();
    }

    protected void onItemRangeChanged(List<Note> newItemData, int positionStart, int itemCount)
    {
        this.itemData = newItemData;
        notifyItemRangeChanged(positionStart,itemCount);
    }

    protected void onItemRangeInserted(List<Note> newItemData, int positionStart, int itemCount)
    {
        this.itemData = newItemData;
        notifyItemRangeInserted(positionStart,itemCount);
    }

    protected void onItemRangeRemoved(List<Note> newItemData, int positionStart, int itemCount)
    {
        this.itemData = newItemData;
        notifyItemRangeRemoved(positionStart,itemCount);
    }
}
