package com.usbbog.cbs_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.usbbog.cbs_app.R;
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



        btnBack.setOnClickListener((View view)->{
            Intent a = new Intent(OlvidarContrasena.this, Login.class);
            startActivity(a);
        });

        btnNext.setOnClickListener((View view)->{
            Intent b = new Intent(OlvidarContrasena.this, VerificacionOTP.class);
            startActivity(b);
        });

        btnNext.setOnClickListener((View view)->{
            Intent c = new Intent(OlvidarContrasena.this, VerificacionOTP.class);
            startActivity(c);
        });

    }
}