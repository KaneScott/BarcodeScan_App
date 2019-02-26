package com.example.myapplicationfirebase;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.method.ScrollingMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.regex.Pattern;

public class StoreScreen extends AppCompatActivity {
    private static final String TAG = "MyApplicationFirebase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_screen);

        Bundle b = getIntent().getExtras();
        String storeName = b.getString("store");
        Log.e(TAG, "Product ID passed: " + storeName);

        final View parentView = findViewById(R.id.store_screen_main);
        final TextView storeNameView = findViewById(R.id.store_name);
        final TextView storeDescriptionView = findViewById(R.id.store_description);
        final TextView storeLocationView = findViewById(R.id.store_location);
        final TextView storeRating = findViewById(R.id.store_rating);
        final CardView cardView = findViewById(R.id.card_viewStore);
        final ImageView imgView = findViewById(R.id.store_image);
        // ScrollView scrollView = findViewById(R.id.store_description_scroll);
        final Button scanButton = findViewById(R.id.read_barcode);

        //SETUP BUTTON LISTENER
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.read_barcode) {
                    Intent intent = new Intent(StoreScreen.this, CameraPreview.class);
                    startActivity(intent);
                }
            }
        });

        //TASK TO GET PRODUCT IMAGE FROM PASSED SERIAL NUMBER
        class StoreTask implements Runnable {
            private String info;
            private volatile Store store;

            StoreTask(String str) {
                info = str;
            }

            public void run() {
                DatabaseDao dao = ProductDatabase.getDatabase(getApplicationContext()).dbDao();
                store = dao.findStoreByName(info);
            }
        }
        StoreTask task = new StoreTask(storeName);
        Thread t = new Thread(task);
        t.start();
        Store store = null;
        try {
            t.join();
            store = task.store;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (store != null) {
            imgView.post(new Runnable() {
                @Override
                public void run() {
                    int height = parentView.getHeight();
                    int width = parentView.getWidth();
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) imgView.getLayoutParams();
                    double percentHeight = height * .25;
                    double percentWidth = width * .50;
                    lp.height = (int) percentHeight;
                    lp.width = (int) percentWidth;
                    imgView.setLayoutParams(lp);

                    lp = (RelativeLayout.LayoutParams) storeDescriptionView.getLayoutParams();
                    percentWidth = width * .75;
                    percentHeight = height * .15;
                    lp.width = (int) percentWidth;
                    lp.height = (int) percentHeight;
                    storeDescriptionView.setLayoutParams(lp);
                    storeDescriptionView.setMovementMethod(new ScrollingMovementMethod());

                    percentWidth = width *.75;
                    lp = (RelativeLayout.LayoutParams) cardView.getLayoutParams();
                    lp.width = (int) percentWidth;
                    cardView.setLayoutParams(lp);
                }
            });
            storeNameView.setText(store.name);
            imgView.setImageResource(store.image_fp);
            storeDescriptionView.setText(store.description);
            storeRating.setText(store.rating + "/5 STAR RATING");

            Pattern pat = Pattern.compile(getResources().getString(R.string.open_google_maps));
            storeLocationView.setLinkTextColor(ContextCompat.getColor(this, R.color.appDarkComplementary));
            Linkify.addLinks(storeLocationView, pat, "https://www.google.com/maps/search/?api=1&query=" + store.name);
        }

    }
}
