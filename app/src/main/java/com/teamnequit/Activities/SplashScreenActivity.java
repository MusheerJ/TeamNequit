package com.teamnequit.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.teamnequit.R;

public class SplashScreenActivity extends AppCompatActivity {

    Animation animation;
    ImageView logo;
    ImageView from;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        animation = AnimationUtils.loadAnimation(this,R.anim.topanimation);

        logo = findViewById(R.id.logo);
        logo.setAnimation(animation);

        from = findViewById(R.id.from);
        from.setAnimation(animation);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this,SignInActivity.class));
                finish();
            }
        },2500);
    }
}