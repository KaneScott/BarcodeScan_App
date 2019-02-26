package com.example.myapplicationfirebase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteItemView;

        private NoteViewHolder(View itemView){
            super(itemView);
            noteItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater inflater;
    private List<Note> notes; //Cached copy of notes
    NoteListAdapter(Context context) { inflater = LayoutInflater.from(context); }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int pos){
        if(notes != null){
            Note current = notes.get(pos);
            holder.noteItemView.setText(current.productName);
        }
        else{
            //If data not ready yet
            holder.noteItemView.setText("No data");
        }
    }

    void setNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(notes != null)
            return notes.size();
        else
            return 0;
    }


}
