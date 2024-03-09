package com.usbbog.cbs_app.view;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.usbbog.cbs_app.R;
import com.usbbog.cbs_app.networking.Network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Nuevo_caso extends AppCompatActivity {
    Button btnpasos, btnimportar;
    ImageView btnBack;
    EditText txtFecha, txtPersonaje, txtWhastapp, txtDescripcion;
    private static final int PICK_FILE_REQUEST_CODE = 123;
    Network network = new Network();
    String createCaseUrl = network.getUrlForCreateCase();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(createCaseUrl)
            .build();

    CaseApi caseApi = retrofit.create(CaseApi.class);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_caso);

        btnBack = findViewById(R.id.btnBack);
        btnpasos = findViewById(R.id.btn_pasos);
        btnimportar = findViewById(R.id.btn_importar);
        txtFecha = findViewById(R.id.edtDate);
        txtPersonaje = findViewById(R.id.edtPersonaje);
        txtWhastapp = findViewById(R.id.edtPhone);
        txtDescripcion = findViewById(R.id.edtDescribe);

        btnBack.setOnClickListener((View view)->{
            Intent i = new Intent(Nuevo_caso.this, Dashboard.class);
            startActivity(i);
        });

        btnpasos.setOnClickListener((View view)->{
            Intent i = new Intent(Nuevo_caso.this, Pasos_importar.class);
            startActivity(i);
        });

        btnimportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (campos()) {
                    envioZipalServidor();
                    elegirFile();
                }
            }
        });
    }

    //Metodo solicita la carga de los archivos ZIP al servidor utilizando Retrofit
    private void envioZipalServidor(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(createCaseUrl) //url del servidor
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        CaseApi caseApi = retrofit.create(CaseApi.class);

        Map<String, RequestBody> files = new HashMap<>();

        Call<String> call = caseApi.uploadZip(files);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                if (response.isSuccessful()){
                    String serverResponse = response.body();
                    Log.d("UPLOAD_SUCCESS", "Servidor respondio con código " + response.code() + ": " + serverResponse);
                }else {
                    Log.e("UPLOAD_FAILED", "Respuesta no exitosa del servidor " + response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("UPLOAD_FAILED", "Fallo la conexión con el servidor: " + t.getLocalizedMessage());
            }
        });
    }

    //Interfase CaseApi
    interface CaseApi{
        @Multipart
        @POST("upload")
        Call<String>uploadZip(@PartMap() Map<String, RequestBody> files);
    }

    //Metodo para selecionar el archivo zip
    private void elegirFile(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("aplication/zip");
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if(data != null){
                Uri uri = data.getData();
                if (uri != null){
                    importarZipFile(uri);
                }
            }
        }
    }

    //metodo que importa el archivo Zip
    private void importarZipFile(Uri uri){
        try {
            DocumentFile documentFile = DocumentFile.fromSingleUri(this, uri);
            if (documentFile != null && documentFile.isFile()){
                traerYEnviarZipalServer(documentFile);
                Toast.makeText(this, "El Archivo ZIP se importo con exito", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Seleccione un archivo ZIP válido", Toast.LENGTH_SHORT).show();
            }
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Error al importar el archivo", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para buscar el archivo en el directorio y extraerlo
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void traerYEnviarZipalServer(DocumentFile zipFile) throws IOException{
        try (ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(getContentResolver().openInputStream(zipFile.getUri())))){
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null){
                File outputFile = new File(getFilesDir(), entry.getName());
                if(entry.isDirectory()){
                    //si es zip crea el directorio
                    if(!outputFile.exists() && !outputFile.mkdirs()){
                        throw new IOException("No se puede crear el directorio " + outputFile.getAbsolutePath());
                    }
                }else {
                    //Crea el archivo si esta dentro de ZIP
                    try (FileOutputStream outputStream = new FileOutputStream(outputFile)){
                        byte[] buffer = new byte[1024];
                        int len;
                        while((len = zipInputStream.read(buffer)) != -1){
                            outputStream.write(buffer, 0, len);
                        }
                    }
                    //Convierte el archivo extraido en un RequestBody
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), outputFile);
                    extraerFileToBackend(requestBody, outputFile.getName());

                }
                zipInputStream.closeEntry();
            }
        }
    }

    //Método para enviar cada archivo individual al servidor que se ha extraido del archivo ZIP
    private void extraerFileToBackend(RequestBody requestBody, String fileName){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(createCaseUrl)
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        CaseApi caseApi = retrofit.create(CaseApi.class);

        Map<String, RequestBody> files = new HashMap<>();
        files.put(fileName, requestBody);

        Call<String> call = caseApi.uploadZip(files);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                if (!response.isSuccessful()){
                    Log.e("UPLOAD_FAILED", "Respuesta no exitosa del servidor " + response.message());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("UPLOAD_FAILED", "Fallo la conexión con el servidor " + t.getLocalizedMessage());
            }
        });
    }

    /*private void addFilesToMap(String name, RequestBody body){
        Map<String, RequestBody> files = new HashMap<>();
        files.put(name, body);
        envioZipalServidor(files);
    }*/


    //Metodo de validación de campos
    private boolean campos(){
        boolean validar = true;
        String fecha = txtFecha.getText().toString();
        String personaje = txtPersonaje.getText().toString();
        String whatsapp = txtWhastapp.getText().toString();
        String descrip = txtDescripcion.getText().toString();
        if(fecha.isEmpty()){
            txtFecha.setError("Debe ingresar la fecha de hoy");
            return false;
        }if(personaje.isEmpty()){
            txtFecha.setError("Debe ingresar el nombre de la persona que envia los mensajes");
            return false;
        }if(whatsapp.isEmpty()){
            txtFecha.setError("Debe ingresar el numero de celular con quien chatea");
            return false;
        }if(descrip.isEmpty()){
            txtFecha.setError("Debe ingresar una breve descripcion de la conversacion");
            return false;
        }
        return validar;
    }
}
