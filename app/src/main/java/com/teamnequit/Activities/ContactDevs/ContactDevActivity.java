package com.teamnequit.Activities.ContactDevs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.teamnequit.databinding.ActivityContactDevBinding;

public class ContactDevActivity extends AppCompatActivity {

    ActivityContactDevBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactDevBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        binding.DevEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"musheerjamadar1024@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT,"");
                intent.setPackage("com.google.android.gm");
                if (intent.resolveActivity(getPackageManager())!=null)
                    startActivity(intent);
                else
                    Toast.makeText(ContactDevActivity.this,"Gmail App is not installed", Toast.LENGTH_SHORT).show();
            }
        });

        binding.DevInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://www.instagram.com/musheerjr/";
                Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse(url) );
                startActivity(browse);
            }
        });

        binding.DevPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:7620196445"));
                startActivity(intent);
            }
        });


    }
}