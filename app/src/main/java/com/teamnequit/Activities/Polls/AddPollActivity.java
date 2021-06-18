package com.teamnequit.Activities.Polls;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamnequit.Adapters.PollAdapter;
import com.teamnequit.Models.Poll;
import com.teamnequit.R;
import com.teamnequit.databinding.ActivityAddPollBinding;
import com.teamnequit.databinding.MomParametersBinding;
import com.teamnequit.databinding.PollParametersBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class AddPollActivity extends AppCompatActivity {

    ActivityAddPollBinding binding;
    FirebaseDatabase database;
    public static Poll poll;
    ArrayList<Poll> polls;
    PollAdapter adapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPollBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Context context = AddPollActivity.this;
        getSupportActionBar().setTitle("Add Polls");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading ...");

        String pollFor = getIntent().getStringExtra("pollFor");
        progressDialog.show();


        View view = LayoutInflater.from(context).inflate(R.layout.poll_parameters,null);
        PollParametersBinding mBinding = PollParametersBinding.bind(view);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Poll Parameters")
                .setView(mBinding.getRoot())
                .setCancelable(false)
                .create();

        polls = new ArrayList<>();
        adapter = new PollAdapter(context,polls,pollFor);
        binding.recylerView.setAdapter(adapter);
        binding.recylerView.setLayoutManager(new LinearLayoutManager(context));

        database.getReference().child("Polls").child(pollFor).child("PollList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                polls.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    Poll poll = snapshot1.getValue(Poll.class);
                    polls.add(poll);
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.AddPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        mBinding.continueBtnPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAllFilled(mBinding)){
                    database.getReference().child("Polls").child(pollFor)
                            .child("PollList").child(poll.getPollDate()).setValue(poll).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(context,"Added",Toast.LENGTH_SHORT).show();
                                setToNull(mBinding);
                                dialog.dismiss();
                            }
                        }
                    });
                }
                else{
                    return;
                }
            }
        });
        mBinding.cancelPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToNull(mBinding);
                dialog.dismiss();
            }
        });

    }

    void setToNull(PollParametersBinding binding){
        binding.preparedBy.setText(null);
        binding.poll.setText(null);
        binding.pollDate.setText(null);

        binding.preparedBy.setError(null);
        binding.poll.setError(null);
        binding.pollDate.setError(null);
    }

    boolean checkAllFilled( PollParametersBinding mBinding )
    {
        poll = new Poll();


        if (mBinding.pollDate.getText().toString().isEmpty()){
            mBinding.pollDate.setError("This cant be Empty!!");
            return false;
        }
        else {
            poll.setPollDate(mBinding.pollDate.getText().toString());
        }


        if (mBinding.preparedBy.getText().toString().isEmpty()){
            mBinding.preparedBy.setError("This cant be Empty!!");
            return false;
        }

        else {
            poll.setPreparedBy(mBinding.preparedBy.getText().toString());
        }

        if (mBinding.poll.getText().toString().isEmpty())
        {
            mBinding.poll.setError("This cant be empty!!");
            return false;
        }

        else {
            poll.setPollQue(mBinding.poll.getText().toString());
        }

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}