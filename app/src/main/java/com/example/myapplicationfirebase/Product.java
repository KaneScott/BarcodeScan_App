package com.example.myapplicationfirebase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Product implements Comparable<Product> {
    @PrimaryKey(autoGenerate = true)
    public int pid;

    @ColumnInfo(name = "price")
    public double price;

    @Embedded
    public Store store;

//    @ColumnInfo(name = "store_name")
//    public String storeName;

    @ColumnInfo(name = "prodID")
    public String prodID;

    public Product(Store store, String prodID, double price) {
        this.price = price;
        this.store = store;
        this.prodID = prodID;
    }

    @Override
    public int compareTo(Product product) {
        if (price > product.price)
            return 1;
        if (price < product.price)
            return -1;
        else
            return 0;
    }
}
