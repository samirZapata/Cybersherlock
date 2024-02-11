package com.usbbog.cbs_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class Login extends AppCompatActivity {

    Button btnLogin, btnSingUp;
    EditText txtCorreo, txtPass;

    private Network url = new Network();
    private String apiUrl = url.getApiSinIn();
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        requestQueue = Volley.newRequestQueue(this);

        //HOOKS
        btnLogin = findViewById(R.id.btnLogin);
        btnSingUp = findViewById(R.id.btnNUser);
        txtCorreo = findViewById(R.id.edtUserlg);
        txtPass = findViewById(R.id.edtPasslg);

        btnSingUp.setOnClickListener((View view) -> {
            Intent i = new Intent(Login.this, Sing_up.class);
            startActivity(i);
        });

        btnLogin.setOnClickListener((View view) -> {
            sing_In(apiUrl);
        });

    }


    private void sing_In(String baseUrl) {
        Log.i("URL ROUTE: ", baseUrl);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //JSONObject object = new JSONObject();
                    //VERIFICACION DEL TOKEN
                    //String correo = object.getString("correo");
                    //String pass = object.getString("password");
                    //if (correo.equals(txtCorreo.getText().toString()) && pass.equals(txtPass.getText().toString())) {
                        Toast.makeText(Login.this, "Bienveni@", Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
                        boolean primerIngreso = sharedPreferences.getBoolean("primer_ingreso", false);

                        if (primerIngreso){
                            Intent a = new Intent(Login.this, Dashboard.class);
                            startActivity(a);
                        }else {
                            Intent b = new Intent(Login.this, WMiddle.class);
                            startActivity(b);
                        }

                        SharedPreferences.Editor editor  = sharedPreferences.edit();
                        editor.putBoolean("primer_ingreso", true);
                        editor.apply();

                    //} else {
                   // }
                } catch (Exception e) {
                    Toast.makeText(Login.this, "¡Credenciales incorrectas!", Toast.LENGTH_SHORT).show();
                    Log.e("ERROR: ", e + "");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error + "");

                //VERIFICA SI EL ERROR ES UN 401
                if (error instanceof AuthFailureError) {
                    Toast.makeText(Login.this, "¡Credenciales incorrectas!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this, "¡Credenciales incorrectas!", Toast.LENGTH_SHORT).show();
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
                    jsonBody.put("password", txtPass.getText().toString());

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

    private void saveTokenSharedPreferences(String token) {
        // Guarda el token en las preferencias compartidas para su uso posterior
        //SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        //SharedPreferences.Editor editor = preferences.edit();
        //editor.putString("token", token);
        //editor.apply();
    }

}