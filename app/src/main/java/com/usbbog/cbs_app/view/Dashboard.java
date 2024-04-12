package com.usbbog.cbs_app.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.usbbog.cbs_app.R;
import com.usbbog.cbs_app.modelHelper.AppData;

import java.util.ArrayList;

import HelperClasses.HomeAdapter.ConsejosAdapter;
import HelperClasses.HomeAdapter.ConsejosHelperClass;
import HelperClasses.HomeAdapter.EvidenciasAdapter;
import HelperClasses.HomeAdapter.EvidenciasHelperClass;

public class Dashboard extends AppCompatActivity {

    Button btnCasos, btnNuevoCaso;
    TextView perfil, txtVerTodo;
    RecyclerView rcConsejos, rcEvidencias;
    RecyclerView.Adapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        //START HOOKS----------------------------------------
        btnCasos = findViewById(R.id.btnCasos);
        btnNuevoCaso = findViewById(R.id.btnEvidencias);
        perfil = findViewById(R.id.txtLeterD);
        txtVerTodo = findViewById(R.id.btnVerTodaEvidencias);
        rcConsejos = findViewById(R.id.rcConsejos);
        rcEvidencias = findViewById(R.id.rcEvidencias);
        //END HOOKS-------------------------------------------


        rcTips();
        rcEvidencias();

        //-----------------------------------------------------

        btnCasos.setOnClickListener((View view)->{
            Intent goCasos = new Intent(Dashboard.this, Casos.class);
            startActivity(goCasos);
        });

        btnNuevoCaso.setOnClickListener((View view)->{
            Intent goNuevoCaso = new Intent(Dashboard.this, Nuevo_caso.class);
            startActivity(goNuevoCaso);
        });

        perfil.setOnClickListener((View view)->{
            Intent goPerfil = new Intent(Dashboard.this, Perfil.class);
            startActivity(goPerfil);
        });

        txtVerTodo.setOnClickListener((View view)->{
            Intent goEvidencias = new Intent(Dashboard.this, Casos.class);
            startActivity(goEvidencias);
        });
    }


    private void rcTips() {

        rcConsejos.setHasFixedSize(true);
        rcConsejos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<ConsejosHelperClass> consejos = new ArrayList<>();

        consejos.add(new ConsejosHelperClass("Titulo 1", "Esto es una prueba de descripción escrita"));
        consejos.add(new ConsejosHelperClass("Titulo 2", "Esto es una prueba de descripción escrita"));
        consejos.add(new ConsejosHelperClass("Titulo 3", "Esto es una prueba de descripción escrita"));

        adapter = new ConsejosAdapter(consejos);

        rcConsejos.setAdapter(adapter);
    }


    private void rcEvidencias() {
        rcEvidencias.setHasFixedSize(true);
        rcEvidencias.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ArrayList<EvidenciasHelperClass> evidencias = new ArrayList<>();

        evidencias.add(new EvidenciasHelperClass("15/02/2024", "15sd6s5d168s4ds8d65s8d75", R.drawable.ic_clip_b));
        evidencias.add(new EvidenciasHelperClass("12/02/2024", "sd8s9d856sd89sd85s6d8s9d", R.drawable.ic_clip_b));
        evidencias.add(new EvidenciasHelperClass("14/02/2024", "sd7s45d87d5s4ds57s54ds74", R.drawable.ic_clip_b));
        evidencias.add(new EvidenciasHelperClass("16/02/2024", "754s4ds54d5sd45sd45sd45s", R.drawable.ic_clip_b));

        adapter = new EvidenciasAdapter(evidencias);
        rcEvidencias.setAdapter(adapter);


    }


    private void gotLetter(){
        String letter = AppData.getNombre();
        perfil.setText(letter);
    }


}