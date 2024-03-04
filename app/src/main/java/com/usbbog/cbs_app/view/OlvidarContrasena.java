package com.usbbog.cbs_app.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.usbbog.cbs_app.R;
import com.usbbog.cbs_app.modelHelper.Holder;
import com.usbbog.cbs_app.modelHelper.MailHolder;
import com.usbbog.cbs_app.networking.Network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
*                                .-"``"-.
                                /______; \
                               {_______}\|
                               (/ a a \)(_)
                               (.-.).-.)
  _______________________ooo__(    ^    )___________________________
 /                             '-.___.-'                            \
|    RECOPILEMOS PRUEBAS, HAGAMOS JUSTICIA                           |
 \________________________________________ooo_______________________/
                               |_  |  _|
                               \___|___/
                               {___|___}
                                |_ | _|
                                /-'Y'-\
                               (__/ \__)
* */
public class OlvidarContrasena extends AppCompatActivity {


    Button btnNext;
    ImageView btnBack;
    EditText txtCorreo;

    private Network url = new Network();
    private String apiUrl = ""; //url.getApiResetPassword("");
    private RequestQueue requestQueue;
    boolean checkComplete, hasSentCode = false;
    Holder mailHolder = new Holder();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_olvidar_contrasena);

        //HOOKS START-----------------------------------------
        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNextOne);
        txtCorreo = findViewById(R.id.edtRecuperarCorreo);
        //HOOKS END-------------------------------------------

        //apiUrl = url.getApiResetPassword(txtCorreo.getText().toString());
        apiUrl = url.getApiResetPassword("");

        btnBack.setOnClickListener((View view)->{
            Intent a = new Intent(OlvidarContrasena.this, Login.class);
            startActivity(a);
        });




        txtCorreo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                apiUrl = url.getApiResetPassword(s.toString()); //SE OBTIENE LA URL DINAMICA
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });



        btnNext.setOnClickListener((View view)->{
            completeChecks();
            if(!hasSentCode){
                resetPassword(apiUrl);
                hasSentCode = true;
            }
            MailHolder.getInstance().setHolder(mailHolder.setCorreo(txtCorreo.getText().toString()));
            System.out.println("CORREO ENVIADO: " + mailHolder.getCorreo());
            Intent b = new Intent(OlvidarContrasena.this, VerificacionOTP.class);
            startActivity(b);
        });

    }



    /**
     * Clase HeaderUtil
     *
     * Encapsula un método para analizar los cabeceros de respuesta HTTP y devolverlos como un mapa de Strings a listas de Strings.
     */
    public class HeaderUtil {
        /**
         * parseHeaders
         *
         * Divide la representación en texto plano de los cabeceros HTTP en nombre y valor, y los devuelve como un mapa de nombres a listas de valores.
         *
         * @param headerString Representación en texto plano de los cabeceros HTTP
         * @return Mapa de nombres de cabeceros a listas de sus respectivos valores
         */
        public Map<String, List<String>> parseHeaders(String headerString) {
            // Compila un patrón regular que coincide con la forma de los cabeceros individuales
            Pattern pattern = Pattern.compile("(\\w+): (\\S.*)$", Pattern.MULTILINE);

            // Busca todos los cabeceros que coincidan con el patrón
            Matcher matcher = pattern.matcher(headerString);

            // Crea un diccionario vacío para almacenar los cabeceros
            Map<String, List<String>> headers = new LinkedHashMap<>();

            // Iterar sobre todos los cabeceros encontrados
            while (matcher.find()) {
                // Extrae el nombre y el valor del cabecero actual
                String name = matcher.group(1);
                String value = matcher.group(2);

                // Si el nombre del cabecero no existe en el diccionario, crea una lista vacía y la asocia con él
                List<String> values = headers.computeIfAbsent(name, k -> new ArrayList<>());

                // Añade el valor actual al conjunto de valores asociados con el nombre del cabecero
                values.add(value);
            }

            // Devuelve el diccionario de cabeceros
            return headers;
        }
    }

    private void resetPassword(String baseUrl) {
        Log.i("URL ROUTE: ", baseUrl.substring(baseUrl.indexOf("/api")));

        if(!checkComplete){
            Log.w("Network request", "No se han completado las comprobaciones.");
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    HeaderUtil headerUtil = new HeaderUtil();
                    Map<String, List<String>> headers = headerUtil.parseHeaders(response);
                    String statusLine = headers.get("Status").get(0);
                    String[] parts = statusLine.split(" ");
                    if (parts.length > 0 && (parts[0].equalsIgnoreCase("200") || parts[1].equalsIgnoreCase("ok"))) {
                        Toast.makeText(OlvidarContrasena.this, "Código enviado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(OlvidarContrasena.this, "¡Correo incorrecto!", Toast.LENGTH_SHORT).show();
                        Log.e("ERROR:", statusLine);
                    }

                } catch (Exception e) {
                    Log.e("ERROR: ", Arrays.toString(e.getStackTrace()) + "");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error + "");

                //VERIFICA SI EL ERROR ES UN 401
                if (error instanceof AuthFailureError) {
                    Toast.makeText(OlvidarContrasena.this, "¡Correo incorrecto!", Toast.LENGTH_SHORT).show();
                }

            }
        }) {

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            public byte[] getBody() throws AuthFailureError {
                try {

                    //OBTENGO DATOS
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("correo", txtCorreo.getText().toString());

                    // CONVIERTE EL OBJETO JSON A BYTES
                    return jsonBody.toString().getBytes("utf-8");
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        RequestQueue RU = Volley.newRequestQueue(this);
        RU.add(stringRequest);
    }

    void completeChecks() {
        checkComplete = true;
    }



}