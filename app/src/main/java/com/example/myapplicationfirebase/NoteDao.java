package com.example.myapplicationfirebase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    List<Note> getAll();

    @Query("SELECT * FROM note WHERE uid IN (:userIds)")
    List<Note> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM note where prodID LIKE :prodID")
    Note findByProdID(String prodID);


    @Query("SELECT * FROM note where product_name LIKE :product AND " +
            "store_name Like :store LIMIT 1")
    Note findByStore(String product, String store);

    @Insert
    void insertAll(Note... notes);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note")
    void deleteAll();
}
