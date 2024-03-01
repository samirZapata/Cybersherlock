package com.usbbog.cbs_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

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
public class MensajeFinal extends AppCompatActivity {


    Button btnGoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mensaje_final);

        //HOOKS START------------------------------------
            btnGoLogin = findViewById(R.id.btnLoginF);
        //HOOKS END--------------------------------------


        btnGoLogin.setOnClickListener((View view)->{
            Intent a = new Intent(MensajeFinal.this, Login.class);
            startActivity(a);
        });
    }
}