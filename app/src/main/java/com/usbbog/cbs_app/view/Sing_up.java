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

import java.util.HashMap;
import java.util.Map;

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
            String nombre = edtNombre.getText().toString();
            String edad = edtEdad.getText().toString();
            String mail = edtEmail.getText().toString();
            String genero = edtGenero.getText().toString();
            String dni = edtDni.getText().toString();
            String password = edtPassword.getText().toString();

            sing_Up(nombre,edad, mail, genero, dni, password);
        });

    }

    private void sing_Up(String nombre, String edad, String mail, String genero, String dni, String password) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String res) {
//
                        Toast.makeText(Sing_up.this, "¡Usuario creado!", Toast.LENGTH_SHORT).show();
                        System.out.print("-------------------------------------------------------------------------");
                        System.out.print("RESPUESTA " + res);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Sing_up.this, error.getMessage() + "", Toast.LENGTH_LONG).show();
                        System.out.print(error);
                        Log.i("ERROR", error.getMessage());
                    }
                }) {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", nombre);
                params.put("edad", edad);
                params.put("email", mail);
                params.put("genero", genero);
                params.put("dni", dni);
                params.put("password", password);
                return params;
            }
        };
        requestQueue.add(postRequest);
    }

}


