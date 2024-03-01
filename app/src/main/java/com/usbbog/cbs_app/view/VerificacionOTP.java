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
import java.util.HashMap;
import java.util.Map;

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


    private void otp(String baseUrl){
        JsonObject otpJson = new JsonObject();
        otpJson.addProperty("correo", "<el correo electrónico del usuario>");
        otpJson.addProperty("codigo", Integer.parseInt(otpCode));


        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("Codigo correcto")) {
                    // Redirecciona a la próxima pantalla si el código es correcto
                    Intent b = new Intent(VerificacionOTP.this, CambiarContrasena.class);
                    startActivity(b);
                } else {
                    Toast.makeText(VerificacionOTP.this, "Código incorrecto.", Toast.LENGTH_SHORT).show();
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