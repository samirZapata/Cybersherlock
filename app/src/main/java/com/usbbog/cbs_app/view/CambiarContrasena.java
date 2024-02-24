package com.usbbog.cbs_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.usbbog.cbs_app.R;

public class CambiarContrasena extends AppCompatActivity {

    ImageView btnBack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cambiar_contrasena);

        //HOOKS START------------------------------------
            btnBack = findViewById(R.id.btnBackThree);
        //HOOKS END--------------------------------------



        btnBack.setOnClickListener((View view)->{
            Intent a = new Intent(CambiarContrasena.this, CambiarContrasena.class);
            startActivity(a);
        });
    }
}