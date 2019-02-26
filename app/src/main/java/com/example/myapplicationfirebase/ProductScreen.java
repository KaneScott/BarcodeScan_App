package com.example.myapplicationfirebase;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class ProductScreen extends AppCompatActivity {
    private static final String TAG = "MyApplicationFirebase";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_screen);
        Bundle b = getIntent().getExtras();
        String prodID = b.getString("id");
        Log.e(TAG, "Product ID passed: " + prodID);

        final View parentView = findViewById(R.id.mainContent);
        final ImageView imgView = findViewById(R.id.product_image);
        final TextView productName = findViewById(R.id.product_name);
        final LinearLayout linearLayout = findViewById(R.id.product_price_list);
        final RelativeLayout relLayout = findViewById(R.id.mainContent);
        final CardView cardView = findViewById(R.id.card_viewProduct);
        final TextView productDescription = findViewById(R.id.product_description);
        final Button scanButton = findViewById(R.id.read_barcode);

        //TASK TO GET PRODUCT IMAGE FROM PASSED SERIAL NUMBER
        class ChangeImageTask implements Runnable {
            private String info;
            private volatile ProductImage pdImage;

            ChangeImageTask(String str) {
                info = str;
            }

            public void run() {
                DatabaseDao dao = ProductDatabase.getDatabase(getApplicationContext()).dbDao();
                pdImage = dao.findProductImageByID(info);
            }
        }
        ChangeImageTask task = new ChangeImageTask(prodID);
        Thread t = new Thread(task);
        t.start();
        ProductImage pdImage = null;
        try {
            t.join();
            pdImage = task.pdImage;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (pdImage != null) {

            //SET IMAGE TO PRODUCT IMAGE. SCALE PRODUCT IMAGE AND DESCRIPTION TEXT
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

                    lp = (RelativeLayout.LayoutParams) productDescription.getLayoutParams();
                    percentWidth = width * .75;
                    percentHeight = height *.15;
                    lp.width = (int) percentWidth;
                    lp.height = (int) percentHeight;
                    productDescription.setLayoutParams(lp);
                    productDescription.setMovementMethod(new ScrollingMovementMethod());

                    percentWidth = width *.75;
                    lp = (RelativeLayout.LayoutParams) cardView.getLayoutParams();
                    lp.width = (int) percentWidth;
                    cardView.setLayoutParams(lp);

                }
            });
            imgView.setImageResource(pdImage.image_fp);
            productName.setText(pdImage.name);
            productDescription.setText(pdImage.description);

        }

        //SETUP BUTTON LISTENER
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.read_barcode) {
                    Intent intent = new Intent(ProductScreen.this, CameraPreview.class);
                    startActivity(intent);
                }
            }
        });

        //SETUP TASK TO FIND PRODUCTS MATCHING SERIAL NUMBER
        class SetupPricesTask implements Runnable {
            private String info;
            private volatile List<Product> prodList;

            SetupPricesTask(String str) {
                info = str;
            }

            public void run() {
                DatabaseDao dao = ProductDatabase.getDatabase(getApplicationContext()).dbDao();
                prodList = dao.findByProdID(info);
            }
        }
        SetupPricesTask pricesTask = new SetupPricesTask(prodID);
        Thread t2 = new Thread(pricesTask);
        t2.start();
        List<Product> prodList = null;
        try {
            t2.join();
            prodList = pricesTask.prodList;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //SETUP PRICE LIST WITH STORE NAMES
        if (prodList != null) {
            String prices = "";
            ArrayList<Product> productArray = new ArrayList<Product>();
            productArray.addAll(prodList);
            Collections.sort(productArray);
            LinearLayout linLayout =  findViewById(R.id.product_price_list);
            LinearLayout linLayoutRight = findViewById(R.id.product_price_list_right);
            if (linLayout.getChildCount() > 0)
                linLayout.removeAllViews();
            int i = 0;
            if (productArray.size() > 5)
                for (int j = 0; j<5; j++) {
                    Product p = productArray.get(j);
                    TextView tv = new TextView(this);
                    String pr = String.format("%.2f", p.price);
                    String styledText = "<font color=#000000>$" + pr + "</font>\t\t\t\t" + "<u><font color=#4449ab>"+p.store.name+"</font></u>";
                    tv.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                    tv.setId(j);
                    if((j & 1) == 0)
                        linLayout.addView(tv);
                    else
                        linLayoutRight.addView(tv);
                    tv.setClickable(true);
                    tv.setOnClickListener(new StoreClickListener(p.store));
                    tv.setTextAppearance(R.style.productPrices);
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tv.getLayoutParams();
                    lp.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    lp.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    tv.setLayoutParams(lp);
                }
            else
                for (Product p : productArray) {
                    TextView tv = new TextView(this);
                    String pr = String.format("%.2f", p.price);
                    String styledText = "<font color=#000000>$" + pr + "</font>\t\t\t\t" + "<u><font color=#4449ab>"+p.store.name+"</font></u>";
                    tv.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
                    tv.setId(i);
                    if((i & 1) == 0)
                        linLayout.addView(tv);
                    else
                        linLayoutRight.addView(tv);
                    tv.setClickable(true);
                    tv.setOnClickListener(new StoreClickListener(p.store));
                    tv.setTextAppearance(R.style.productPrices);
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tv.getLayoutParams();
                    lp.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    lp.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    tv.setLayoutParams(lp);
                    i++;
                }
        }


    }

}
