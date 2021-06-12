package com.teamnequit.Activities.Rules;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.teamnequit.databinding.ActivityTeamsRulesBinding;

public class TeamsRulesActivity extends AppCompatActivity {
    PDFView teamRules;
    ActivityTeamsRulesBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeamsRulesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Team Rules");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.teamRulesPDF.fromAsset("FB_rules2021.pdf").load();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}