package com.usbbog.cbs_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.usbbog.cbs_app.R;

public class MainActivity extends AppCompatActivity {


    //VARIABLES
    Animation topAnimation, bottomAnimation;
    private static int SPLASH_SCREEN = 5000;
    ImageView logo;
    TextView txtLogo, slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);



        //ANIMATION
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //HOOKS
        logo = findViewById(R.id.imgLogo);
        txtLogo = findViewById(R.id.txtLogo);
        slogan = findViewById(R.id.txtDescription);

        //ANIMATIONS
        logo.setAnimation(topAnimation);
        txtLogo.setAnimation(bottomAnimation);
        slogan.setAnimation(bottomAnimation);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this,Login.class);
                startActivity(i);
                finish();
            }
        },SPLASH_SCREEN);

    }
}