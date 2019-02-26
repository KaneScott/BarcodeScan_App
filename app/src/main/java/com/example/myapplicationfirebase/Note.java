package com.example.myapplicationfirebase;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Note  {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "product_name")
    public String productName;

    @ColumnInfo(name = "store_name")
    public String storeName;

    @ColumnInfo(name = "prodID")
    public String prodID;

    public Note(String productName, String storeName, String prodID){
        this.productName = productName;
        this.storeName = storeName;
        this.prodID = prodID;
    }

}
