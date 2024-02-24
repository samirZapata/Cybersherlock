package com.usbbog.cbs_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.chaos.view.PinView;
import com.usbbog.cbs_app.R;

import java.util.Objects;

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

            Objects.requireNonNull(PVcode.getAutofillValue()).getTextValue();
            System.out.print(PVcode);
            if(PVcode.getTextSizeUnit() != 0){

                try {
                    Intent b = new Intent(VerificacionOTP.this, CambiarContrasena.class);
                    startActivity(b);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        });


    }
}