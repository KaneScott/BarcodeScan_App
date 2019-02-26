package com.example.myapplicationfirebase;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
   private NoteViewModel vModel;
   private static final String TAG = "MyApplicationFirebase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupDB();
        findViewById(R.id.read_barcode).setOnClickListener(this);
//        NoteDao dao = db.noteDao();
//        Note note = new Note("Doritos", "New World", "1234567890128");
//        dao.insertAll(note);

    }


    private void setupDB() {
        ProductDatabase productDB = Room.databaseBuilder(getApplicationContext(), ProductDatabase.class, "ProductDatabase").build();
        HandlerThread ht = new HandlerThread("MyHandlerThread");
        ht.start();
        new Handler(ht.getLooper()).post(new Runnable() {
            @Override
            public void run(){
                ProductImage doritoImage = new ProductImage("1234567890128", R.drawable.doritosnachocheese,
                        "Doritos corn chips are full volume, full flavour and full on tooth rattling crunch. Doritos…for the bold. ",
                        "Doritos - Cheese Supreme Corn Chips ");
                Store newWorld = new Store("New+World", "A wonderful store located in the NEW WORLD", "New World", 4, R.drawable.newworld);
                Store paknsave = new Store("PAK'nSAVE", "Where you have to pack if you want to save", "PAK'nSAVE", 4, R.drawable.paknsave);
                Store fourSquare = new Store("Four Square",
                        "Four Square is a chain of supermarkets in New Zealand that was founded by John Heaton Barker. It has 280 stores throughout New Zealand, mostly in small towns. Some Four Square supermarkets are also operating in Australia.",
                        "Four Square", 3, R.drawable.four_square);
                Store shitStore = new Store("Shite+Store", "A store where everything is shit and the prices are sky high", "Shite Store", 1, R.drawable.notfound);
                Store countDown = new Store("Countdown", "Where you are always counting down the savings", "Countdown", 3, R.drawable.countdown);
                Product doritos = new Product(newWorld, "1234567890128", 2.56);
                Product doritoscd = new Product(countDown, "1234567890128", 3.10);
                Product doritosp = new Product(paknsave, "1234567890128", 2.60);
                Product dortios_fs = new Product(fourSquare, "1234567890128", 4.60);
                Product doritos_ss = new Product(shitStore, "1234567890128", 29.60);
                DatabaseDao mdao = ProductDatabase.getDatabase(getApplicationContext()).dbDao();
                ProductDatabase.getDatabase(getApplicationContext()).clearAllTables();
                mdao.insertAll(doritos);
                mdao.insertAll(newWorld);
                mdao.insertAll(doritoImage);
                mdao.insertAll(countDown);
                mdao.insertAll(paknsave);
                mdao.insertAll(shitStore);
                mdao.insertAll(doritoscd);
                mdao.insertAll(doritosp);
                mdao.insertAll(doritos_ss);
                mdao.insertAll(fourSquare);
                mdao.insertAll(dortios_fs);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.read_barcode){
            Intent intent = new Intent(this, CameraPreview.class);
            startActivity(intent);
        }
    }
}
