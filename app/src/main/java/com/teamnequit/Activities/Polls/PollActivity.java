package com.teamnequit.Activities.Polls;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamnequit.databinding.ActivityPollBinding;

public class PollActivity extends AppCompatActivity {

    ActivityPollBinding binding;
    boolean isCoreMember = false;
    boolean isHead =  false;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPollBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Team Polls");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth = FirebaseAuth.getInstance();



        String currentUser = auth.getCurrentUser().getEmail().substring(0,7);
//        Toast.makeText(PollActivity.this,currentUser,Toast.LENGTH_SHORT).show();

        //is CoreMember
        FirebaseDatabase.getInstance().getReference().child("CoreMembers").child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    isCoreMember = true;
                }
                else{
                    isCoreMember = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //is Head
        FirebaseDatabase.getInstance().getReference().child("Heads").child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    isHead = true;
                }
                else{
                    isHead = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                if (isHead){
                    Intent intent = new Intent(PollActivity.this,AddPollActivity.class);
                    intent.putExtra("pollFor","HeadsPolls");
                    startActivity(intent);
                }
                else{
                    Toast.makeText(PollActivity.this,"You Dont have Access",Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.CoreMemberPolls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCoreMember){
                    Intent intent = new Intent(PollActivity.this,AddPollActivity.class);
                    intent.putExtra("pollFor","CoreMemberPolls");
                    startActivity(intent);
                }
                else{
                    Toast.makeText(PollActivity.this,"You Dont have Access",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}