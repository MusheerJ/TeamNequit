package com.teamnequit.Activities.MemberList;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.teamnequit.Models.Users;
import com.teamnequit.R;
import com.teamnequit.databinding.ActivityViewMemberDetailsBinding;

public class ViewMemberDetailsActivity extends AppCompatActivity {

    ActivityViewMemberDetailsBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewMemberDetailsBinding.inflate(getLayoutInflater());
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
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

        String userRoll = user.getUserEmail().substring(0,7);
        String userP = "https://firebasestorage.googleapis.com/v0/b/team-nequit.appspot.com/o/usersprofiles%2F"+userRoll+"?alt=media&token=";

//        https://firebasestorage.googleapis.com/v0/b/team-nequit.appspot.com/o/usersprofiles%2F1910054?alt=media&token=213d0f2d-a4f8-4119-b9a1-ffa3cce7be3c
        //https://firebasestorage.googleapis.com/v0/b/team-nequit.appspot.com/o/usersprofiles%2F1909021?alt=media&token=3137be54-c2bc-45d0-b732-8819d6ecb220
        Glide.with(this).load(userP).placeholder(R.drawable.avatar).into(binding.imageView3);



    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}