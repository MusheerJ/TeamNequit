package com.teamnequit.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.teamnequit.Models.Users;
import com.teamnequit.R;
import com.teamnequit.databinding.ActivitySelfUserProfileBinding;

public class SelfUserProfileActivity extends AppCompatActivity {

    ActivitySelfUserProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelfUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("User profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Users user = (Users) getIntent().getSerializableExtra("Self");
        binding.SelfUsername.setText(user.getUserName());
        binding.SelfUserClgDpt.setText(user.getUserClgDpt());
        binding.SelfUserMail.setText(user.getUserEmail());
        binding.SelfUserClubDpt.setText(user.getUserNeqDpt());
        binding.SelfUserDOB.setText(user.getUserBOb());
        binding.SelfUserPhone.setText(user.getUserPhone());

        String userRoll = user.getUserEmail().substring(0,7);
        String userP = "https://firebasestorage.googleapis.com/v0/b/team-nequit.appspot.com/o/usersprofiles%2F"+userRoll+".jpeg?alt=media&token=";
        Glide.with(this).load(userP).placeholder(R.drawable.ic_avatar).into(binding.imageView3);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}