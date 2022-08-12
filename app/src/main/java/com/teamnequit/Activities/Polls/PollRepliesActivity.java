package com.teamnequit.Activities.Polls;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
    ProgressDialog progressDialog;

    //Count
    int TotalReplies;
    int PollOption1 =0;
    int PollOption2 =0;
    int PollOption3=0;
    final int totalPortion = 800;

    //OptionPercentage
    int option1Percentage = 0;
    int option2Percentage = 0;
    int option3Percentage = 0;

    //OptionPortion
    int option1Portion = 0;
    int option2Portion = 0;
    int option3Portion = 0;
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



        progressDialog = new ProgressDialog(PollRepliesActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

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
        binding.pollReplyOp1.setText(poll.getPollOp1());
        binding.pollReplyOp2.setText(poll.getPollOp2());
        binding.pollReplyOther.setText("Other");

        binding.PollReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.PollReply.setVisibility(View.GONE);
                binding.PollReplies.setVisibility(View.VISIBLE);
                binding.ReplyText.setVisibility(View.GONE);
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
//                binding.ReplyText.requestFocus();

            }
        });

        binding.PollSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PollReplies replies = new PollReplies();
                if (binding.pollReplyOp1.isChecked()){
                    replies.setReply(poll.getPollOp1());
                    binding.PollReplies.setVisibility(View.GONE);
                    binding.PollReply.setVisibility(View.VISIBLE);
                }
                else if (binding.pollReplyOp2.isChecked()){
                    replies.setReply(poll.getPollOp2());
                    binding.PollReplies.setVisibility(View.GONE);
                    binding.PollReply.setVisibility(View.VISIBLE);
                }
                else{
                    if (binding.ReplyText.getText().toString().isEmpty() ){
                        binding.ReplyText.setError("This cant be empty!!");

                        return;
                    }
                    else{
                        binding.PollReplies.setVisibility(View.GONE);
                        binding.PollReply.setVisibility(View.VISIBLE);
                        replies.setReply(binding.ReplyText.getText().toString());
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                    }

                }
                replies.setReplyBy(name);
                database.getReference().child("Polls").child(pollFor).child("Replies").child(poll.getPollDate()).child(replies.getReplyBy()).setValue(replies);
                Toast.makeText(PollRepliesActivity.this,"Response saved",Toast.LENGTH_SHORT).show();
                binding.ReplyText.setText(null);

                binding.RadioGrp.clearCheck();




            }
        });

        binding.pollReplyOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                    binding.ReplyText.requestFocus();
                binding.ReplyText.setVisibility(View.VISIBLE);
            }
        });

        binding.pollReplyOp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.ReplyText.setVisibility(View.GONE);
            }
        });

        binding.pollReplyOp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.ReplyText.setVisibility(View.GONE);
            }
        });

        binding.pollReplyOp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.ReplyText.setVisibility(View.GONE);
            }
        });

        binding.PollCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            binding.PollReply.setVisibility(View.VISIBLE);
            binding.PollReplies.setVisibility(View.GONE);
            binding.ReplyText.setText(null);
            binding.ReplyText.setError(null);

            binding.RadioGrp.clearCheck();


            }
        });

        //Show Replies
        binding.ShowReplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.PollRepliesLayout.setVisibility(View.VISIBLE);
                binding.PollResultsLayout.setVisibility(View.GONE);
            }
        });

        //Show Result
        binding.showResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.PollRepliesLayout.setVisibility(View.GONE);
                binding.PollResultsLayout.setVisibility(View.VISIBLE);
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

                if (snapshot.exists()){
                    replies.clear();
                    TotalReplies = 0;
                    PollOption1 = 0;
                    PollOption2 = 0;
                    PollOption3 = 0;
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        PollReplies reply = snapshot1.getValue(PollReplies.class);
                        replies.add(reply);
                        if (reply.getReply().contains(poll.getPollOp1()))
                        {
                            PollOption1++;
                        }else if (reply.getReply().contains(poll.getPollOp2()))
                        {
                            PollOption2++;
                        }
                        else {
                            PollOption3++;
                        }
                        TotalReplies++;
                    }
                    adapter.notifyDataSetChanged();




                    //Calculating the Portion;
                    option1Percentage = (100*PollOption1)/TotalReplies;
                    option2Percentage = (100*PollOption2)/TotalReplies;
                    option3Percentage = (100*PollOption3)/TotalReplies;

                    //Adding the portion
                    option1Portion = (totalPortion*option1Percentage)/100;
                    option2Portion = (totalPortion*option2Percentage)/100;
                    option3Portion = (totalPortion*option3Percentage)/100;
                }




                ViewGroup.LayoutParams Option1Por = binding.Option1Portion.getLayoutParams();
                Option1Por.width = option1Portion;
                ViewGroup.LayoutParams Option2Por = binding.Option2Portion.getLayoutParams();
                Option2Por.width = option2Portion;
                ViewGroup.LayoutParams Option3Por = binding.Option3Portion.getLayoutParams();
                Option3Por.width = option3Portion;


                binding.Option1Perc.setText(poll.getPollOp1()+"("+String.valueOf(PollOption1)+")");
                binding.Option2Perc.setText(poll.getPollOp2()+"("+String.valueOf(PollOption2)+")");
                binding.Option3Perc.setText("Other"+"("+String.valueOf(PollOption3)+")");


                progressDialog.dismiss();



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