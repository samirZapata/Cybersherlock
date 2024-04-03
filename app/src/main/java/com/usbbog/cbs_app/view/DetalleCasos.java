package com.usbbog.cbs_app.view;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.usbbog.cbs_app.R;
import com.usbbog.cbs_app.modelHelper.AppData;
import com.usbbog.cbs_app.modelHelper.CasosHolder;
import com.usbbog.cbs_app.networking.ApiServices;
import com.usbbog.cbs_app.networking.RetrofitClient;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/*
                    __/\\\\\\\\\\\\\____/\\\\____________/\\\\____/\\\\\\\\\_____
                     _\/\\\/////////\\\_\/\\\\\\________/\\\\\\__/\\\///////\\\___
                      _\/\\\_______\/\\\_\/\\\//\\\____/\\\//\\\_\///______\//\\\__
                       _\/\\\\\\\\\\\\\/__\/\\\\///\\\/\\\/_\/\\\___________/\\\/___
                        _\/\\\/////////____\/\\\__\///\\\/___\/\\\________/\\\//_____
                         _\/\\\_____________\/\\\____\///_____\/\\\_____/\\\//________
                          _\/\\\_____________\/\\\_____________\/\\\___/\\\/___________
                           _\/\\\_____________\/\\\_____________\/\\\__/\\\\\\\\\\\\\\\_
                            _\///______________\///______________\///__\///////////////__
*
* */


public class DetalleCasos extends AppCompatActivity {

    private String TAG = "CASOS: ";
    private ApiServices apiService;

    TextView nombreCasos, nombreAcosador, fechaCaso, telAcosador, desc, cifrado;
    Button btnNuevoCaso, btnExportar;
    CardView cPerfil;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detalle_casos);

        String nombreCaso = AppData.getNombreCaso();
        String uri = AppData.getFileUri();

        //HOOKS
        nombreCasos = findViewById(R.id.idCaso);
        nombreAcosador = findViewById(R.id.nombreAcosador);
        fechaCaso = findViewById(R.id.fechaCaso);
        telAcosador = findViewById(R.id.telAcosado);
        desc = findViewById(R.id.descCaso);
        cifrado = findViewById(R.id.cifrado);
        //____________________________________
        btnNuevoCaso = findViewById(R.id.btnNuevoCaso);
        btnExportar = findViewById(R.id.btnExportar);

        //-------------------------------------
        cPerfil = findViewById(R.id.cPerfil);
        //END HOOKS


        btnNuevoCaso.setOnClickListener((View view)->{
            Intent goNuevoCaso = new Intent(DetalleCasos.this, Nuevo_caso.class);
            startActivity(goNuevoCaso);
            finish();
        });

        btnExportar.setOnClickListener((View view)->{

        });

        cPerfil.setOnClickListener((View view)->{
            Intent goPerfil = new Intent(DetalleCasos.this, Perfil.class);
            startActivity(goPerfil);
        });

        getCaseByName();

    }


    private void getCaseByName() {

        apiService = RetrofitClient.getClient().create(ApiServices.class);

        String nombreCaso = AppData.getNombreCaso();
        // Realizar la llamada para obtener los datos del caso
        Call<CasosHolder> call = apiService.getCasoByNombreCaso(nombreCaso);
        call.enqueue(new Callback<CasosHolder>() {
            @Override
            public void onResponse(Call<CasosHolder> call, Response<CasosHolder> response) {
                if (response.isSuccessful()) {
                    CasosHolder caso = response.body();
                    // Mostrar los datos del caso en los componentes de la interfaz de usuario
                    nombreCasos.setText(caso.getNombreCaso());
                    nombreAcosador.setText(caso.getAcosador());
                    //fechaCaso.setText(caso.getFecha()); // Asegúrate de tener un campo de fecha en CasosHolder
                    telAcosador.setText(caso.getTelAcosador());
                    desc.setText(caso.getDesc());
                    cifrado.setText(String.valueOf(caso.isCifrado())); // Convertir booleano a String
                } else {
                    Log.i(TAG, "No se pudo obtener ningun dato");
                }
            }

            @Override
            public void onFailure(Call<CasosHolder> call, Throwable t) {
                Log.i(TAG, Objects.requireNonNull(t.getMessage()));
            }
        });
    }



    private void downloadFile(Context context, Uri uri, String displayName) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setAllowedOverRoaming(false);
        request.setTitle(displayName);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, displayName);

        downloadManager.enqueue(request);
    }



}