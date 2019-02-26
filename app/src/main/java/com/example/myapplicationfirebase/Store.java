package com.example.myapplicationfirebase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Store {
    @PrimaryKey(autoGenerate = true)
    public int sid;

    @ColumnInfo (name = "location")
    public String location;

    @ColumnInfo (name = "description")
    public String description;

    @ColumnInfo (name = "name")
    public String name;

    @ColumnInfo (name = "rating")
    public int rating;

    @ColumnInfo (name = "image_fp")
    public int image_fp;

    public Store(String location, String description, String name, int rating, int image_fp) {
        this.location = location;
        this.description = description;
        this.name = name;
        this.rating = rating;
        this.image_fp = image_fp;
    }
}
