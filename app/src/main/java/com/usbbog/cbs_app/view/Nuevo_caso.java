package com.usbbog.cbs_app.view;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE;
import static android.provider.Telephony.Mms.Part.TEXT;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.usbbog.cbs_app.R;
import com.usbbog.cbs_app.modelHelper.AppData;
import com.usbbog.cbs_app.networking.Network;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;


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

public class Nuevo_caso extends AppCompatActivity {
    Button btnpasos, btnimportar, btnSelectFile;
    ImageView btnBack;
    EditText txtFecha, txtPersonaje, txtWhastapp, txtDescripcion;
    TextView lblNomCaso, lblFecha;
    private TextInputLayout textInputLayout;
    private TextInputEditText archivoAdjunto;

    //private static final int PICK_FILE_REQUEST_CODE = 123;
    private static final int REQUEST_CODE_SELECT_FILE = 1;
    private Network network = new Network();
    // private String createCaseUrl = network.getApiCreateCase();
    private String createCase = network.getGetUrlForCreateCase();
    final String TAG="CASOS: ";
    Retrofit retrofit;
    CaseApi caseApi;


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nuevo_caso);

        btnBack = findViewById(R.id.btnBack);
        btnpasos = findViewById(R.id.btn_pasos);
        btnimportar = findViewById(R.id.btn_importar);
        lblNomCaso = findViewById(R.id.lblNomCaso);
        lblFecha = findViewById(R.id.lblFecha);
        txtPersonaje = findViewById(R.id.edtPersonaje);
        txtWhastapp = findViewById(R.id.edtPhone);
        txtDescripcion = findViewById(R.id.edtDescribe);
        textInputLayout = findViewById(R.id.textInputLayout);
        archivoAdjunto = findViewById(R.id.archivoAdjunto);
        textInputLayout = findViewById(R.id.textInputLayout);

        String currentMail = AppData.getCorreo();
        String lFecha = obtenerFechaActual();
        lblFecha.setText(lFecha);


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(createCase) //endpoint
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        caseApi = retrofit.create(CaseApi.class);



        btnBack.setOnClickListener((View view) -> {
            Intent i = new Intent(Nuevo_caso.this, Dashboard.class);
            startActivity(i);
        });

        btnpasos.setOnClickListener((View view) -> {
            Intent i = new Intent(Nuevo_caso.this, Pasos_importar.class);
            startActivity(i);
        });

        btnimportar.setOnClickListener(v -> {
            if (campos()) {
                selecionarFile();
            }
        });
    }


    /**
     * Genera un código alfanumérico aleatorio de longitud 8
     *
     * @return Una cadena que representa el código alfanumérico aleatorio generado
     */
    public static String generarCodigoAleatorio() {
        String CARACTERES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int LONGITUD = 8;
        Random RANDOM = new SecureRandom();

        StringBuilder sb = new StringBuilder(LONGITUD);
        for (int i = 0; i < LONGITUD; i++) {
            sb.append(CARACTERES.charAt(RANDOM.nextInt(CARACTERES.length())));

        }
        return sb.toString();
    }


    private String obtenerFechaActual() {
        // Obtener la instancia del calendario
        Calendar calendario = Calendar.getInstance();

        // Obtener la fecha actual como objeto Date
        Date fecha = calendario.getTime();

        // Formatear la fecha como string (por ejemplo: "dd/MM/yyyy HH:mm:ss")
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String fechaFormateada = formatoFecha.format(fecha);

        return fechaFormateada;
    }



    /**
     * Este método se encarga de enviar el archivo seleccionado y los datos adicionales al servidor
     * Crea un objeto RequestBody con el archivo y los datos adicionales
     * Luego, realiza una llamada a la API utilizando Retrofit para cargar el archivo y los datos
     * */
    private void envioFilealServidor(File file, String fileName, String nombreCaso, String acosador, String telAcosador, String desc, String createdBy) throws IOException{


        MediaType mediaType;
        try{
            mediaType = MediaType.parse("application/octet-stream");
            //mediaType = MediaType.get($this)
        }catch (IllegalArgumentException ignored){
            mediaType = MediaType.get("application/octet-stream");
        }
        RequestBody requestBody = RequestBody.create(mediaType, file);
        // Crear RequestBody para los demás campos
        RequestBody requestBodyNombreCaso = RequestBody.create(null, nombreCaso);
        RequestBody requestBodyAcosador = RequestBody.create(null, acosador);
        RequestBody requestBodyTelAcosador = RequestBody.create(null, telAcosador);
        RequestBody requestBodyDesc = RequestBody.create(null, desc);
        RequestBody requestBodyCreatedBy = RequestBody.create(null, createdBy);


        MultipartBody.Part filepart = MultipartBody.Part.createFormData("evidencias", fileName, requestBody);
        Call<String> call = caseApi.uploadFile(requestBodyNombreCaso, requestBodyAcosador, requestBodyTelAcosador, requestBodyDesc, requestBodyCreatedBy, filepart);


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()){
                    Log.e("UPLOAD_FAILED", "Respuesta no exitosa del servido " + response.message());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("UPLOAD_FAILED", "Fallo la conexión con el servidor "+ t.getLocalizedMessage());
            }
        });
    }


    /*Esta interfaz define el endpoint de la API que se utiliza para cargar el archivo y los datos
     */
    interface CaseApi{
        @Multipart
        @POST("api/cases/")
        Call<String> uploadFile(
                @Part("nombreCaso") RequestBody nombreCaso,
                @Part("acosador") RequestBody acosador,
                @Part("telAcosador") RequestBody telAcosador,
                @Part("desc") RequestBody desc,
                @Part("createdBy") RequestBody createdBy,
                @Part MultipartBody.Part evidencias
                //@PartMap() Map<String, RequestBody> evidencias
        );

    }

    /**
    * Este método se llama cuando se hace clic en el botón "Seleccionar archivo"
    * Invoca al método selecionarFile()
    * */
    public void selecionarFile(View view) {
        selecionarFile();
    }

    /*
    *  Este método abre el selector de archivos del sistema para que el usuario pueda elegir un archivo
    *  Inicia una nueva actividad con el Intent ACTION_GET_CONTENT
    * */
    private void selecionarFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
    }

    /*  Este método se ejecuta cuando se obtiene un resultado de una actividad iniciada previamente
     *  En este caso, se maneja el resultado de la selección de archivo
     *  Si se seleccionó un archivo correctamente, se llama al método importarfileDispositivo() con el URI del archivo
     * */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SELECT_FILE) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    // Result OK (user has picked a file)
                    if (data != null && data.getData() != null) {
                        Uri uri = data.getData();
                        importarfileDispositivo(uri);
                    } else {
                        //rror_selecting_file, Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "ERROR SELECCIONANDO EL ARCHIVO");
                    }
                    break;

                case Activity.RESULT_CANCELED:// User cancelled the action
                    Log.i(TAG, "OPERACION CANCELADA");
                    break;
                default:
                    Log.w("ExternalStorage", "Unknown resultCode");
                    break;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


/*
*   Este método se encarga de copiar el archivo seleccionado al caché de la aplicación
*   Luego, llama al método envioFilealServidor() para enviar el archivo y los datos al servidor
* */
    private void importarfileDispositivo(Uri uri){
        ContentResolver resolver = getContentResolver();

        // Obtener el mapa de tipos MIME para obtener la extensión del archivo
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Obtener la extensión del archivo a partir de la URI
        String extension = mimeTypeMap.getExtensionFromMimeType(resolver.getType(uri));

        try (ParcelFileDescriptor pfd = resolver.openFileDescriptor(uri, "r")){

            // Crear un archivo temporal en la carpeta caché del dispositivo
            File file = new File(getCacheDir(), System.currentTimeMillis() + '.'+ extension);

            // Abrir flujos de entrada y salida para leer y escribir bytes
            InputStream inputStream = new FileInputStream(pfd.getFileDescriptor());
            OutputStream outputStream= new FileOutputStream(file);

            // Generar un nombre aleatorio para el caso y asignárselo al label correspondiente
            String nomCasoAux = Nuevo_caso.generarCodigoAleatorio();
            // Establecer el texto en el TextView
            lblNomCaso.setText(nomCasoAux);
            String nombreCaso = nomCasoAux;

            //Asigno los valores de los campos del formulario a las variables
            String fecha = obtenerFechaActual();
            lblFecha.setText(fecha);
            String acosador = txtPersonaje.getText().toString();
            String telAcosador = txtWhastapp.getText().toString();
            String desc = txtDescripcion.getText().toString();
            String createdBy = AppData.getCorreo();

            RequestBody nombreCasorq = RequestBody.create(TEXT, MediaType.parse(nombreCaso));
            RequestBody acosadorrq = RequestBody.create(TEXT, MediaType.parse(acosador));
            RequestBody telAcosadorrq = RequestBody.create(TEXT, MediaType.parse(telAcosador));
            RequestBody desrq = RequestBody.create(TEXT, MediaType.parse(desc));
            RequestBody createdByrq = RequestBody.create(TEXT, MediaType.parse(createdBy));

            RequestBody fileBody = RequestBody.create(MediaType.parse(MEDIA_TYPE), file);
            MultipartBody.Part filepart = MultipartBody.Part.createFormData("evidencias", file.getName(), fileBody);


            // Leer bytes del flujo de entrada y escribirlos en el flujo de salida hasta finalizar
            byte[] buffer = new byte[4 * 1024];
            int byteRead;
            while((byteRead = inputStream.read(buffer))!= -1){
                outputStream.write(buffer, 0, byteRead);
            }
            outputStream.flush();
            inputStream.close();
            outputStream.close();


            // Obtener el nombre del archivo adjunto y mostrarlo en pantalla
            String nombreArchivo = new File(uri.getPath()).getName();
            archivoAdjunto.setText(nombreArchivo);


            //Envio archivo al servidor junto con los demas datos
            envioFilealServidor(file.getAbsoluteFile(), file.getName(), nombreCaso, acosador, telAcosador, desc, createdBy);
            AppData.setNombreCaso(nombreCaso);
            AppData.setPersonaje(acosador);
            AppData.setFecha(fecha);
            AppData.setFileUri(uri);


        } catch (IOException | SecurityException e) {
            e.printStackTrace();
            //Toast.makeText(this, R.string.error_importing_file, Toast.LENGTH_SHORT).show();
            Log.i(TAG, "ERROR SELECCIONANDO EL ARCHIVO");
        } finally {
            Intent goDetalleCaso = new Intent(Nuevo_caso.this, DetalleCasos.class);
            startActivity(goDetalleCaso);
            finish();

        }
    }


    /*
    *   Este método valida que todos los campos de entrada estén completos
    *   Muestra un error si algún campo está vacío
    *   Retorna true si todos los campos están completos, de lo contrario, retorna false
    * */

    private boolean campos(){
        String fecha = obtenerFechaActual();
        String personaje = txtPersonaje.getText().toString();
        String whatsapp = txtWhastapp.getText().toString();
        String descrip = txtDescripcion.getText().toString();

        if(personaje.isEmpty()){
            showError(txtPersonaje, "Debe ingresar el nombre de la persona que envia los mensajes");
            return false;
        }

        if (whatsapp.isEmpty()){
            showError(txtWhastapp, "Debe ingresar el número de celular con quién chatear");
            return false;
        }

        if(descrip.isEmpty()){
            showError(txtDescripcion, "Debe ingresar una breve descripción de la conversación");
            return false;
        }
        return true;
    }

    /*
    *   Este método muestra un mensaje de error en un campo de entrada de texto
    *   Se utiliza para indicar al usuario que falta ingresar información en un campo
    * */
    private void showError(TextView field, String message){
        field.setError(message);
        field.requestFocus();
    }
}