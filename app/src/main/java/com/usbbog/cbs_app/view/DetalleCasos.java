package com.usbbog.cbs_app.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.usbbog.cbs_app.R;
import com.usbbog.cbs_app.modelHelper.AppData;
import com.usbbog.cbs_app.networking.ApiServices;
import com.usbbog.cbs_app.networking.RetrofitClient;

import java.io.File;
import java.io.IOException;

import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
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
    ImageView btnClose;

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
        btnClose = findViewById(R.id.btnCloseD);
        getCaseByName();

        //-------------------------------------
        cPerfil = findViewById(R.id.cPerfil);
        //END HOOKS


        btnNuevoCaso.setOnClickListener((View view)->{
            Intent goNuevoCaso = new Intent(DetalleCasos.this, Nuevo_caso.class);
            startActivity(goNuevoCaso);
            finish();
        });

        cPerfil.setOnClickListener((View view)->{
            Intent goPerfil = new Intent(DetalleCasos.this, Perfil.class);
            startActivity(goPerfil);
        });

        btnClose.setOnClickListener((View view)->{
            Intent goDash = new Intent(DetalleCasos.this, Dashboard.class);
            startActivity(goDash);
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

                        //OBTENER DATOS DE CASOS
                        JsonObject caso = jsonObject.getAsJsonObject("caso");
                        String id = caso.get("_id").getAsString();
                        String cName = caso.get("nombreCaso").getAsString();
                        String cAcosador = caso.get("acosador").getAsString();
                        String cFecha = caso.get("createdAt").getAsString();
                        String cTelefono = caso.get("telAcosador").getAsString();
                        String cDesc = caso.get("desc").getAsString();

                        //OBTENER DATOS DE EVIDENCIAS
                        JsonArray evidenciasArray = jsonObject.getAsJsonArray("evidencias");
                        String fileName = "";
                        for (JsonElement elemento : evidenciasArray) {
                            JsonObject evidencia = elemento.getAsJsonObject();
                            String cCif = evidencia.get("claveDeCifrado").getAsString();
                            fileName = evidencia.get("filename").getAsString();
                            cifrado.setText(cCif);
                        }

                        nombreCasos.setText(cName);
                        nombreAcosador.setText(cAcosador);
                        fechaCaso.setText(cFecha);
                        telAcosador.setText(cTelefono);
                        desc.setText(cDesc);

                        String finalFileName = fileName;
                        btnExportar.setOnClickListener((View view)->{
                            downloadFile(DetalleCasos.this, id, finalFileName);
                        });

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



    private void downloadFile(Context context, String casoId, String nombreArchivo) {
        // Crear instancia de ApiService
        apiService = RetrofitClient.getClient().create(ApiServices.class);

        // Realizar la llamada para descargar el archivo
        Call<ResponseBody> call = apiService.descargarArchivo(casoId, nombreArchivo);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Guardar el archivo en la carpeta de descargas
                    saveFileToDownloads(context, response.body(), nombreArchivo);
                } else {
                    // Manejar respuesta no exitosa
                    Toast.makeText(context, "Error al descargar el archivo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Manejar error de red
                Toast.makeText(context, "Upss... parece que no tienes conexion a internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveFileToDownloads(Context context, ResponseBody body, String nombreArchivo) {
        try {
            // Obtener el directorio de descargas
            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            // Crear un archivo en el directorio de descargas con el nombre proporcionado
            File file = new File(downloadsDir, nombreArchivo);

            // Escribir el cuerpo de la respuesta en el archivo
            BufferedSink bufferedSink = Okio.buffer(Okio.sink(file));
            bufferedSink.writeAll(body.source());
            bufferedSink.close();

            if (file.exists() && file.length() > 0) {
                // Mostrar mensaje de descarga completada
                Intent goOk = new Intent(DetalleCasos.this, Ok.class);
                startActivity(goOk);
            } else {
                Intent goBad = new Intent(DetalleCasos.this, Bad.class);
                startActivity(goBad);
            }

        } catch (IOException e) {
            e.printStackTrace();
            // Manejar error al guardar el archivo
            Toast.makeText(context, "Upss... parece que no tienes conexion a internet", Toast.LENGTH_SHORT).show();
        }
    }


}