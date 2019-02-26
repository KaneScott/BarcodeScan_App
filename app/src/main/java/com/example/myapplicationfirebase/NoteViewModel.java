package com.example.myapplicationfirebase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepo repo;
    private List<Note> allNotes;

    public NoteViewModel (Application app){
        super(app);
        repo = new NoteRepo(app);
        allNotes = repo.getAll();
    }

    List<Note> getAllNotes(){return allNotes;}

    public void insert(Note note){repo.insert(note);}
}
