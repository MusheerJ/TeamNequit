package com.teamnequit.Activities.ContactDevs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.teamnequit.databinding.ActivityContactDevsBinding;

public class ContactDevsActivity extends AppCompatActivity {

    ActivityContactDevsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityContactDevsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().setTitle("Contact Devs");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.contactManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactDevsActivity.this,ContactProjectManager.class));
            }
        });

        binding.contactDesignerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ContactDevsActivity.this,ContactDesignerActivity.class));
            }
        });

        binding.contactDeveloperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactDevsActivity.this,ContactDevActivity.class));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}