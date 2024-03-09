package com.usbbog.cbs_app.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.usbbog.cbs_app.R;

public class Pasos_importar extends AppCompatActivity {
    Button btnListos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasos_importar);

        btnListos = findViewById(R.id.btn_listo);

        btnListos.setOnClickListener((View v)->{
            Intent i = new Intent(Pasos_importar.this, Nuevo_caso.class);
            startActivity(i);
        });
    }

}
