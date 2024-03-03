package com.usbbog.cbs_app.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.usbbog.cbs_app.R;

public class Casos extends AppCompatActivity {
    private static final String TAG = "Casos";

    CardView cvCasos;
    TextView txtTitulo,txtDescripcion, txtFecha;
    Button btnVerMas;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_casos);



    }



}