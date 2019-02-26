package com.example.myapplicationfirebase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class NoteRepo {
    private NoteDao noteDao;
    private List<Note> allNotes;

    NoteRepo(Application myApp){
        AppDatabase db = AppDatabase.getDatabase(myApp);
        noteDao = db.noteDao();
        allNotes = noteDao.getAll();
    }

    List<Note> getAll(){
        return allNotes;
    }

    public void insert (Note note){
        new insertAsyncTask(noteDao).execute(note);
    }

    private static class insertAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao mAsyncTaskDao;

        insertAsyncTask(NoteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Note... params) {
            mAsyncTaskDao.insertAll(params[0]);
            return null;
        }
    }
}
