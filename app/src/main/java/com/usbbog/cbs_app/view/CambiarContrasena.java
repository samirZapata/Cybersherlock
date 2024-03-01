package com.usbbog.cbs_app.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

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

public class CambiarContrasena extends AppCompatActivity {

    ImageView btnBack;
    Button btnFinish;
    EditText passOne, passConfirm;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cambiar_contrasena);

        //HOOKS START------------------------------------
            btnBack = findViewById(R.id.btnBackThree);
            btnFinish = findViewById(R.id.btnLoginFP);
            passOne = findViewById(R.id.edtNuevaContrasena);
            passConfirm = findViewById(R.id.edtCNuevaContrasena);
        //HOOKS END--------------------------------------



        btnBack.setOnClickListener((View view)->{
            Intent a = new Intent(CambiarContrasena.this, CambiarContrasena.class);
            startActivity(a);
        });

        btnFinish.setOnClickListener((View view)->{
            Intent b = new Intent(CambiarContrasena.this, MensajeFinal.class);
            startActivity(b);
        });
    }


    private void cambiarContrasena(){

    }

}