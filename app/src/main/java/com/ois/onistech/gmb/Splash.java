package com.ois.onistech.gmb;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    ImageView i1;
    Animation top;
    long Delay = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        i1=(ImageView)findViewById(R.id.iv1);

        top= AnimationUtils.loadAnimation(this,R.anim.bottom);
        i1.setAnimation(top);
       // startActivity(new Intent(Splash.this,Login.class));
        Timer RunSplash = new Timer();

        // Task to do when the timer ends
        TimerTask ShowSplash = new TimerTask() {
            @Override
            public void run() {
                // Close SplashScreenActivity.class

                // Start MainActivity.class
                Intent myIntent = new Intent(Splash.this,
                        MainScreen.class);
                startActivity(myIntent);
                finish();
            }
        };

        // Start the timer
        RunSplash.schedule(ShowSplash, Delay);

    }
    }

