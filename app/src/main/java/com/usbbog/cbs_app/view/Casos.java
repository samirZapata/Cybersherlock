package com.usbbog.cbs_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.usbbog.cbs_app.R;

public class Casos extends AppCompatActivity {
    private static final String TAG = "Casos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casos);

        Log.d(TAG, "SE INICIO LA ACTIVIDAD");
    }
}