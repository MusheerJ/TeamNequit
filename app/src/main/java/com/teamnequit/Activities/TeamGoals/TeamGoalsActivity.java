package com.teamnequit.Activities.TeamGoals;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.teamnequit.R;

public class TeamGoalsActivity extends AppCompatActivity {

    PDFView teamGoals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("Team Goals");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_team_goals);
        teamGoals = (PDFView)findViewById(R.id.teamsGoalsPDF);
        teamGoals.fromAsset("NeverQuit7.0NewsLetter.pdf").load();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}