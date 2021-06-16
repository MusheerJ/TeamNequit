package com.teamnequit.Activities.FeedBack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.teamnequit.Activities.MomSheet.MomActivity;
import com.teamnequit.R;
import com.teamnequit.databinding.ActivityFeedBackBinding;

public class FeedBackActivity extends AppCompatActivity {

    ActivityFeedBackBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding  = ActivityFeedBackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("FeedBack Sheet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Accumulator Department
        binding.AccumulatorDpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://classroom.google.com/c/MzYwMzg5NjA5NDU4?cjc=cspd5sj";
                Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse(url) );
                startActivity(browse);
            }
        });

        //Charger Department
        binding.ChargerDpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://classroom.google.com/c/MzYxMzQ2ODkwOTE1?cjc=obaeyln";
                Intent browse = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                startActivity(browse);
            }
        });

        // Cooling Department
        binding.CoolingDpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://classroom.google.com/c/MzYxMzM5NzQ0NDk1?cjc=ezpzt5k";
                Intent browse = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                startActivity(browse);
            }
        });

        //Drive Department
        binding.DriveDpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                https://classroom.google.com/c/MzYwNDgyNDc2OTc4?cjc=vxjf6h3
                String url = "https://classroom.google.com/c/MzYwNDgyNDc2OTc4?cjc=vxjf6h3";
                Intent browse = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                startActivity(browse);
            }
        });

        //Tractive Department
        binding.TractiveDpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                https://classroom.google.com/c/MzYwNDAwMjE2MDA5?cjc=ytdxq7y
                String url = "https://classroom.google.com/c/MzYwNDAwMjE2MDA5?cjc=ytdxq7y";
                Intent browse = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                startActivity(browse);
            }
        });

        //Sensor Department
        binding.SensorDpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                https://classroom.google.com/c/MzYxMjgxMTY5NTY2?cjc=gpoz7pd
                String url = "https://classroom.google.com/c/MzYxMjgxMTY5NTY2?cjc=gpoz7pd";
                Intent browse = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                startActivity(browse);
            }
        });

        //Motor Department
        binding.MotorDpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://classroom.google.com/c/MzYxMzU3NzM1NDUz?cjc=fd2xvpf";
                Intent browse = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                startActivity(browse);

            }
        });



    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}