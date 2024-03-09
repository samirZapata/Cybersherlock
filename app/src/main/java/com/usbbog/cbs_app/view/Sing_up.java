package com.usbbog.cbs_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.usbbog.cbs_app.R;
import com.usbbog.cbs_app.networking.Network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
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
public class Sing_up extends AppCompatActivity {


    Button btnSingUp, btnLogin;
    EditText edtNombre, edtEdad, edtEmail, edtGenero, edtDni, edtPassword;

    private Network url = new Network();
    private String apiUrl = url.getApiSingUp();
    private RequestQueue requestQueue;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sing_up);


        //HOOKS
        btnSingUp = findViewById(R.id.btnSingUp);
        btnLogin = findViewById(R.id.btnLoginSG);
        edtNombre = findViewById(R.id.edtName);
        edtEdad = findViewById(R.id.edtEdad);
        edtEmail = findViewById(R.id.edtMail);
        edtGenero = findViewById(R.id.edtGenero);
        edtDni = findViewById(R.id.edtDni);
        edtPassword = findViewById(R.id.edtPassSG);

        requestQueue = Volley.newRequestQueue(this);

        btnLogin.setOnClickListener((View view)->{
            Intent i = new Intent(Sing_up.this, Login.class);
            startActivity(i);
        });


        btnSingUp.setOnClickListener((View view)-> {
            //if(validacion()) {
                sing_Up(apiUrl);
            //}

        });

    }
    //Método validación de campos
    /*private boolean validacion(){
        String name = edtNombre.getText().toString();
        String age = edtEdad.getText().toString();
        String email = edtEmail.getText().toString();

    }*/

    private void sing_Up(String baseUrl) {
        Log.i("URL ROUTE: ", baseUrl);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Sing_up.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                Intent a = new Intent(Sing_up.this, Login.class);
                startActivity(a);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error + "");
            }
        }){

            public String getBodyContentType(){
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    //CONSUMIR OBJETO JSON
                    JSONObject jsonBody = new JSONObject();

                    jsonBody.put("nombre", edtNombre.getText().toString());
                    jsonBody.put("edad", edtEdad.getText().toString());
                    jsonBody.put("correo", edtEmail.getText().toString());
                    jsonBody.put("genero", edtGenero.getText().toString());
                    jsonBody.put("dni", edtDni.getText().toString());
                    jsonBody.put("password", edtPassword.getText().toString());

                    //CONVERTIR OBJETO A BYTES
                    return jsonBody.toString().getBytes("utf-8");

                }catch (JSONException e){
                    Log.e("ERROR: ", e + "");
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

}


