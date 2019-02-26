package com.example.myapplicationfirebase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class StoreClickListener implements View.OnClickListener {
    private final Store store;
    private static final String TAG = "MyApplicationFirebase";
    public StoreClickListener(Store s){
        store = s;
    }

    @Override
    public void onClick(View view) {
        Log.e(TAG, "STORE CLICKED: "+store.name);
        Intent intent = new Intent(view.getContext(), StoreScreen.class);
        Bundle b = new Bundle();
        intent.putExtra("store", store.name);
        view.getContext().startActivity(intent);
    }
}
