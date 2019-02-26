package com.example.myapplicationfirebase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Product.class, Store.class, ProductImage.class}, version = 1, exportSchema = false)
public abstract class ProductDatabase extends RoomDatabase {
    public abstract DatabaseDao dbDao();
    private static volatile ProductDatabase INSTANCE;



    private static RoomDatabase.Callback appDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    static ProductDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    //INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app_database").allowMainThreadQueries().build();
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ProductDatabase.class, "product_database").build();
                }
            }
        }
        return INSTANCE;
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final DatabaseDao mDao;

        PopulateDbAsync(ProductDatabase db) {
            mDao = db.dbDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
//            mDao.deleteAll();
//            ProductImage doritoImage = new ProductImage("1234567890128", R.drawable.doritosnachocheese, "Tasty cheese doritos", "Doritos");
//            Store newWorld = new Store("2 Crombie Street", "A wonderful store located in the NEW WORLD", "New World", 4, "drawable/newworld.jpg");
//            Product doritos = new Product(newWorld, "1234567890128", 2.56);
//            mDao.insertAll(doritos);
//            mDao.insertAll(newWorld);
//            mDao.insertAll(doritoImage);
            return null;
        }
    }
}
