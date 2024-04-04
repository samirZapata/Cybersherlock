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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.usbbog.cbs_app.R;
import com.usbbog.cbs_app.modelHelper.AppData;
import com.usbbog.cbs_app.modelHelper.CasosHolder;
import com.usbbog.cbs_app.networking.ApiServices;
import com.usbbog.cbs_app.networking.RetrofitClient;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
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

        getCaseByName();

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



    }


    private void getCaseByName() {
        apiService = RetrofitClient.getClient().create(ApiServices.class);

        // Obtener el nombre del caso desde AppData
        String nombreCaso = AppData.getNombreCaso();

        // Realizar la llamada para obtener los datos del caso
        Call<ResponseBody> call = apiService.getCasoByNombreCaso(nombreCaso);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        // Convertir el cuerpo de la respuesta a String y mostrarlo en los componentes de la interfaz de usuario
                        String responseBody = response.body().string();

                        Gson gson = new Gson();
                        JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);

                        JsonObject caso = jsonObject.getAsJsonObject("caso");
                        String cName = caso.get("nombreCaso").getAsString();
                        String cAcosador = caso.get("acosador").getAsString();
                        String cFecha = caso.get("createdAt").getAsString();
                        String cTelefono = caso.get("telAcosador").getAsString();
                        String cDesc = caso.get("desc").getAsString();

                        JsonArray evidenciasArray = jsonObject.getAsJsonArray("evidencias");
                        for (JsonElement elemento : evidenciasArray) {
                            JsonObject evidencia = elemento.getAsJsonObject();
                            String cCif = evidencia.get("claveDeCifrado").getAsString();
                            cifrado.setText(cCif);
                        }

                        nombreCasos.setText(cName);
                        nombreAcosador.setText(cAcosador);
                        fechaCaso.setText(cFecha);
                        telAcosador.setText(cTelefono);
                        desc.setText(cDesc);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // Manejar errores de respuesta
                    Log.i(TAG, "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Manejar errores de red
                Log.e(TAG, "Error de red: " + t.getMessage(), t);
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