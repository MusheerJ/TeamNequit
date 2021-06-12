package com.teamnequit.Activities.FeedBack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}