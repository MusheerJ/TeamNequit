package com.teamnequit.Activities.Polls;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.teamnequit.databinding.ActivityPollBinding;

public class PollActivity extends AppCompatActivity {

    ActivityPollBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPollBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Team Polls");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        binding.AllPolls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PollActivity.this,AddPollActivity.class);
                intent.putExtra("pollFor","AllPolls");
                startActivity(intent);
            }
        });

        binding.HeadPolls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PollActivity.this,AddPollActivity.class);
                intent.putExtra("pollFor","HeadsPolls");
                startActivity(intent);
            }
        });

        binding.CoreMemberPolls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PollActivity.this,AddPollActivity.class);
                intent.putExtra("pollFor","CoreMemberPolls");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}