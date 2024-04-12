package com.usbbog.cbs_app.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.usbbog.cbs_app.R;
import com.usbbog.cbs_app.modelHelper.AppData;
import com.usbbog.cbs_app.networking.ApiServices;
import com.usbbog.cbs_app.networking.RetrofitClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfil extends AppCompatActivity {

    ApiServices apiServices;

    TextView txtLetter, txtNombre, txtEdad, txtCorreo, txtDni, txtGenero, txtInicial, logOut;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_perfil);

        logOut = findViewById(R.id.btnLogOut);
        txtInicial = findViewById(R.id.txtInicial);
        txtLetter = findViewById(R.id.txtNombreUsuario);
        txtNombre = findViewById(R.id.txtNombre);
        txtEdad = findViewById(R.id.txtEdad);
        txtCorreo = findViewById(R.id.txtMail);
        txtDni = findViewById(R.id.txtId);
        txtGenero = findViewById(R.id.txtGenero);


        getUserByMail();

        logOut.setOnClickListener((View view)->{
            Intent logOut = new Intent(Perfil.this, Login.class);
            startActivity(logOut);
            finishAffinity();
        });

    }


    private void getUserByMail(){
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        String correo = AppData.getCorreo();

        if (correo != null){
            Call<ResponseBody> call = apiServices.getUserByCorreo(correo);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        try {

                            String user = response.body().string();
                            Gson gson = new Gson();
                            JsonArray jsonArray = gson.fromJson(user, JsonArray.class);

                            if (jsonArray != null && jsonArray.size() > 0){

                                for (int i = 0; i < jsonArray.size(); i++){
                                    JsonObject usuario = jsonArray.get(i).getAsJsonObject();

                                    if (usuario != null){
                                        String nombre = usuario.get("nombre").getAsString();
                                        String edad = usuario.get("edad").getAsString();
                                        String correo = usuario.get("correo").getAsString();
                                        String dni = usuario.get("dni").getAsString();
                                        String genero = usuario.get("genero").getAsString();

                                        txtLetter.setText(nombre); //NOMBRE EN GRANDE
                                        txtNombre.setText(nombre);
                                        txtEdad.setText(edad);
                                        txtCorreo.setText(correo);
                                        txtDni.setText(dni);
                                        txtGenero.setText(genero);


                                        char inicial = nombre.charAt(0);
                                        char primeraLetra = inicial;
                                        txtInicial.setText(primeraLetra);
                                        AppData.setNombre(String.valueOf(primeraLetra));
                                    }
                                }

                            }


                        }catch (Exception e){
                            e.printStackTrace();
                            Log.e("CASOS", "Error al obtener datos del usuario", e.getCause());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("CASOS", "Error de red: " + t.getMessage(), t);
                }
            });
        }else {
            Log.e("CASOS", "El nombre de usuario es NULO");
        }

    }
}