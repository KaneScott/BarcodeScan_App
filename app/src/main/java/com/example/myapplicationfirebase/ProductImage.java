package com.example.myapplicationfirebase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ProductImage {
    @PrimaryKey(autoGenerate = true)
    public int piid;

    @ColumnInfo(name = "prodId")
    public String prodId;

    @ColumnInfo(name = "image_fp")
    public int image_fp;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "name")
    public String name;


    public ProductImage(String prodId, int image_fp, String description, String name) {
        this.prodId = prodId;
        this.image_fp = image_fp;
        this.description = description;
        this.name = name;
    }
}
