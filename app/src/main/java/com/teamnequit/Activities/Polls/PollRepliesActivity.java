package com.teamnequit.Activities.Polls;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamnequit.Adapters.PollRepliesAdapter;
import com.teamnequit.Models.Poll;
import com.teamnequit.Models.PollReplies;
import com.teamnequit.databinding.ActivityPollRepliesBinding;

import java.util.ArrayList;

public class PollRepliesActivity extends AppCompatActivity {

    ActivityPollRepliesBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    String name;
    ArrayList <PollReplies> replies;
    PollRepliesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPollRepliesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Poll poll = (Poll) getIntent().getSerializableExtra("PollDetails");
        String pollFor = getIntent().getStringExtra("pollFor");

        getSupportActionBar().setTitle("Poll Replies");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        database.getReference().child("Users").child(auth.getUid()).child("userName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = (String)snapshot.getValue();
//                Toast.makeText(PollRepliesActivity.this,name,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.PollQuestion.setText("Poll : "+poll.getPollQue());
        binding.PollBy.setText("Poll By : "+poll.getPreparedBy());

        binding.PollReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.PollReply.setVisibility(View.GONE);
                binding.PollReplies.setVisibility(View.VISIBLE);
            }
        });

        binding.PollSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.ReplyText.getText().toString().isEmpty()){
                    binding.ReplyText.setError("This cant be empty!!");
                    return;
                }
                else{
                    PollReplies replies = new PollReplies();
                    replies.setReply(binding.ReplyText.getText().toString());
                    replies.setReplyBy(name);
                    database.getReference().child("Polls").child(pollFor).child("Replies").child(poll.getPollDate()).child(replies.getReplyBy()).setValue(replies).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(PollRepliesActivity.this,"Response saved",Toast.LENGTH_SHORT).show();
                            binding.PollReplies.setVisibility(View.GONE);
                            binding.PollReply.setVisibility(View.VISIBLE);
                            binding.ReplyText.setText(null);
                        }
                    });

                }
            }
        });

        binding.PollCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            binding.PollReply.setVisibility(View.VISIBLE);
            binding.PollReplies.setVisibility(View.GONE);
            binding.ReplyText.setText(null);
            binding.ReplyText.setError(null);
            }
        });

        //Replies ..
        replies = new ArrayList<>();
        adapter = new PollRepliesAdapter(this,replies);
        binding.recylerView.setAdapter(adapter);
        binding.recylerView.setLayoutManager(new LinearLayoutManager(PollRepliesActivity.this));

        database.getReference().child("Polls").child(pollFor).child("Replies").child(poll.getPollDate()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                replies.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    PollReplies reply = snapshot1.getValue(PollReplies.class);
                    replies.add(reply);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}