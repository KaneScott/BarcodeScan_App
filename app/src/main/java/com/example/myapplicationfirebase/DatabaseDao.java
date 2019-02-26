package com.example.myapplicationfirebase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DatabaseDao {
    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Query("SELECT * FROM productimage")
    List<ProductImage> getAllProductImages();

    @Query("SELECT * FROM product WHERE pid IN (:userIds)")
    List<Product> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM product where prodID LIKE :prodID")
    List<Product> findByProdID(String prodID);

    @Query("SELECT * FROM productimage where prodID like :prodID")
    ProductImage findProductImageByID(String prodID);

    @Query("SELECT * FROM store where name like :name")
    Store findStoreByName(String name);

    @Insert
    void insertAll(Store... stores);

    @Insert
    void insertAll(ProductImage... prodImages);

    @Insert
    void insertAll(Product... prods);

    @Delete
    void delete(Product prod);

    @Query("DELETE FROM product ")
    void deleteAll();

}
