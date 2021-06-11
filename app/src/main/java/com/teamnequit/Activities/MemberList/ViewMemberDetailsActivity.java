package com.teamnequit.Activities.MemberList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.teamnequit.Models.Users;
import com.teamnequit.R;
import com.teamnequit.databinding.ActivityViewMemberDetailsBinding;

public class ViewMemberDetailsActivity extends AppCompatActivity {

    ActivityViewMemberDetailsBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewMemberDetailsBinding.inflate(getLayoutInflater());
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Member Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Users user = (Users)getIntent().getSerializableExtra("Values");
        binding.memberUsername.setText(user.getUserName());
        binding.memberUserMail.setText(user.getUserEmail());
        binding.memberUserPhone.setText(user.getUserPhone());
        binding.memberUserDOB.setText(user.getUserBOb());
        binding.memberUserClgDpt.setText(user.getUserClgDpt());
        binding.memberUserClubDpt.setText(user.getUserNeqDpt());

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}