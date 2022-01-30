package com.teamnequit.Activities.Polls;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamnequit.Adapters.PollAdapter;
import com.teamnequit.Models.Poll;
import com.teamnequit.R;
import com.teamnequit.databinding.ActivityAddPollBinding;
import com.teamnequit.databinding.PollParametersBinding;

import java.util.ArrayList;
import java.util.Collections;

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
        getSupportActionBar().setTitle("Team Polls");
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

        //isCore
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail().substring(0,7);

        FirebaseDatabase.getInstance().getReference().child("CoreMembers").child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    binding.AddPoll.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //isHead
        FirebaseDatabase.getInstance().getReference().child("Heads").child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    binding.AddPoll.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        database.getReference().child("Polls").child(pollFor).child("PollList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                polls.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    Poll poll = snapshot1.getValue(Poll.class);
                    polls.add(poll);
                }
                Collections.reverse(polls);
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
                mBinding.pollDate.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
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
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
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
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                dialog.dismiss();
            }
        });

    }

    void setToNull(PollParametersBinding binding){
        binding.preparedBy.setText(null);
        binding.poll.setText(null);
        binding.pollDate.setText(null);
        binding.pollOp1.setText(null);
        binding.pollOp2.setText(null);

        binding.pollOp1.setError(null);
        binding.pollOp2.setError(null);
        binding.preparedBy.setError(null);
        binding.poll.setError(null);
        binding.pollDate.setError(null);
    }

    boolean checkAllFilled( PollParametersBinding mBinding )
    {
        poll = new Poll();


        //Poll Creation
        if (mBinding.pollDate.getText().toString().isEmpty()){
            mBinding.pollDate.setError("This cant be Empty!!");
            return false;
        }
        else {
            String date = mBinding.pollDate.getText().toString();
            if (date.contains("/") || date.length() != 10 || !date.contains("-"))
            {
                mBinding.pollDate.setError("Invalid Date! Please match the given pattern (dd-mm-yyyy)");
                return false;
            }
            poll.setPollDate(mBinding.pollDate.getText().toString());
        }


        //Prepared by
        if (mBinding.preparedBy.getText().toString().isEmpty()){
            mBinding.preparedBy.setError("This cant be Empty!!");
            return false;
        }

        else {
            poll.setPreparedBy(mBinding.preparedBy.getText().toString());
        }

        //POll Question
        if (mBinding.poll.getText().toString().isEmpty())
        {
            mBinding.poll.setError("This cant be empty!!");
            return false;
        }

        else {
            poll.setPollQue(mBinding.poll.getText().toString());
        }

        if (mBinding.pollOp1.getText().toString().isEmpty()){
            mBinding.pollOp1.setError("This cant be empty!!");
            return false;
        }
        else {
            poll.setPollOp1(mBinding.pollOp1.getText().toString());
        }

        if (mBinding.pollOp2.getText().toString().isEmpty()){
            mBinding.pollOp2.setError("This cant be empty!!");
            return false;
        }
        else {
            poll.setPollOp2(mBinding.pollOp2.getText().toString());
        }


        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_members,menu);
        MenuItem menuItem = menu.findItem(R.id.memberSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search ...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private  void filter(String newText)
    {
        ArrayList<Poll> filteredPolls = new ArrayList<>();
        if (newText.isEmpty())
        {
            adapter.filter(polls);
            return;
        }
        else{
            for (Poll poll : polls)
            {
                if (poll.getPollDate().contains(newText))
                {
                    filteredPolls.add(poll);
                }

            }
        }
        adapter.filter(filteredPolls);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}