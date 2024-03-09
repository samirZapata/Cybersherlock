package com.usbbog.cbs_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.usbbog.cbs_app.R;

public class WMiddle extends AppCompatActivity {


    Button btnListo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_wmiddle);

        btnListo = findViewById(R.id.btnCheck);

        btnListo.setOnClickListener((View view)->{
            Intent i = new Intent(WMiddle.this, Dashboard.class);
            this.startActivity(i);
        });
    }
}