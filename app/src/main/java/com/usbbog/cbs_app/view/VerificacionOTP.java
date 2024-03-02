package com.usbbog.cbs_app.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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
import com.chaos.view.PinView;
import com.google.gson.JsonObject;
import com.usbbog.cbs_app.R;
import com.usbbog.cbs_app.networking.Network;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
public class VerificacionOTP extends AppCompatActivity {


    Button btnNext;
    ImageView btnClose;
    PinView PVcode;
    String otpCode;

    private Network url = new Network();
    private String apiUrl = url.getApiOtp(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verificacion_otp);

        //HOOKS START------------------------------------
        btnClose = findViewById(R.id.btnClose);
        btnNext = findViewById(R.id.btnNextTwo);
        PVcode = findViewById(R.id.code);
        //HOOKS END--------------------------------------


        btnClose.setOnClickListener((View view)->{
            Intent a = new Intent(VerificacionOTP.this, Login.class);
            startActivity(a);
        });


        btnNext.setOnClickListener((View view)->{

            otpCode = String.valueOf(PVcode.getText());
            apiUrl = url.getApiOtp(Integer.parseInt(otpCode));

            PVcode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    apiUrl = url.getApiOtp(s.hashCode());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            otp(apiUrl);
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



    private void otp(String baseUrl){
        JsonObject otpJson = new JsonObject();
        otpJson.addProperty("codigo", Integer.parseInt(otpCode));


        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Toast.makeText(VerificacionOTP.this, "Codigo correcto", Toast.LENGTH_SHORT).show();
                    Intent b = new Intent(VerificacionOTP.this, CambiarContrasena.class);
                    startActivity(b);

                }catch (Exception e){
                    Log.e("ERROR EN EL BLOQUE onResponse: ", Arrays.toString(e.getStackTrace()) + "");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error:", error.toString());
                Toast.makeText(VerificacionOTP.this, "Ha ocurrido un error.", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                return otpJson.toString().getBytes(StandardCharsets.UTF_8);
            }
        };

        RequestQueue RU = Volley.newRequestQueue(this);
        RU.add(stringRequest);

    }

}