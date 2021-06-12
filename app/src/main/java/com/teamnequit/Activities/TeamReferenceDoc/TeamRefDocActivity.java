package com.teamnequit.Activities.TeamReferenceDoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.teamnequit.databinding.ActivityTeamRefDocBinding;

public class TeamRefDocActivity extends AppCompatActivity {

    ActivityTeamRefDocBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeamRefDocBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Team Reference Documents");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.teamReferenceDocPDF.fromAsset("TeamNeQuitGoalSheet2021.pdf").load();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}