package com.teamnequit.Activities.ContactDevs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.teamnequit.databinding.ActivityContactProjectManagerBinding;

public class ContactProjectManager extends AppCompatActivity {

    ActivityContactProjectManagerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactProjectManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        binding.ManagerEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mangeshdharma713@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT,"");
                intent.setPackage("com.google.android.gm");
                if (intent.resolveActivity(getPackageManager())!=null)
                    startActivity(intent);
                else
                    Toast.makeText(ContactProjectManager.this,"Gmail App is not installed", Toast.LENGTH_SHORT).show();
            }
        });

        binding.ManagerInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.instagram.com/mangesh_dharma/";
                Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse(url) );
                startActivity(browse);
            }
        });

        binding.ManagerPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:7775063846"));
                startActivity(intent);
            }
        });

        


    }
}