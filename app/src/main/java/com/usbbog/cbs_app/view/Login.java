package com.usbbog.cbs_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.usbbog.cbs_app.R;

public class Login extends AppCompatActivity {

    Button btnLogin, btnSingUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //HOOKS
        btnLogin = findViewById(R.id.btnLogin);
        btnSingUp = findViewById(R.id.btnNUser);

       btnSingUp.setOnClickListener((View view)->{
           Intent i = new Intent(Login.this, Sing_up.class);
           startActivity(i);
       });



    }
}