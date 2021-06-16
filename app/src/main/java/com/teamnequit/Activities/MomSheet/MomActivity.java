package com.teamnequit.Activities.MomSheet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.teamnequit.databinding.ActivityMomBinding;

public class MomActivity extends AppCompatActivity {

    ActivityMomBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("MOM Sheet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Context context = MomActivity.this;

        //1 Accumulator Department
        binding.AccumulatorDpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Acc = new Intent(context,MomSheetActivity.class);
                Acc.putExtra("MomName","Accumulator");
                startActivity(Acc);
            }
        });

        //2 Charger Department
        binding.ChargerDpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent charger = new Intent(context,MomSheetActivity.class);
                charger.putExtra("MomName","Charger");
                startActivity(charger);
            }
        });

        //3 Cooling Department
        binding.CoolingDpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cooling = new Intent(context,MomSheetActivity.class);
                cooling.putExtra("MomName","Cooling");
                startActivity(cooling);
            }
        });

        //4 Drive Department
        binding.DriveDpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Drive = new Intent(context,MomSheetActivity.class);
                Drive.putExtra("MomName","Drive");
                startActivity(Drive);
            }
        });

        //5 Sensor Department
        binding.SensorDpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sensor = new Intent(context,MomSheetActivity.class);
                sensor.putExtra("MomName","Sensor");
                startActivity(sensor);
            }
        });

        //6 TractiveSystem Department
        binding.TractiveDpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ts = new Intent(context,MomSheetActivity.class);
                ts.putExtra("MomName","TractiveSystem");
                startActivity(ts);
            }
        });

        //7 Motor Department
        binding.MotorDpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent motor = new Intent(context,MomSheetActivity.class);
                motor.putExtra("MomName","Motor");
                startActivity(motor);
            }
        });

        //8 Other
        binding.OtherDpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sensor = new Intent(context,MomSheetActivity.class);
                sensor.putExtra("MomName","Other");
                startActivity(sensor);
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}