package com.teamnequit.Activities.AttendanceSheet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamnequit.Activities.MemberList.ViewMemberListActivity;
import com.teamnequit.Adapters.MemberAttendanceAdapter;
import com.teamnequit.Adapters.MembersAdapter;
import com.teamnequit.Models.Users;
import com.teamnequit.R;
import com.teamnequit.databinding.ActivityAddTeamAttendanceBinding;

import java.util.ArrayList;

public class AddTeamAttendanceActivity extends AppCompatActivity {

    ActivityAddTeamAttendanceBinding binding;
    ArrayList<Users> users;
    MemberAttendanceAdapter adapter;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    String DATE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTeamAttendanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Add Attendance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(AddTeamAttendanceActivity.this);
        progressDialog.setMessage("Loading ....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        DATE = getIntent().getStringExtra("Date");


        database = FirebaseDatabase.getInstance();
        users = new ArrayList<>();
        adapter = new MemberAttendanceAdapter(this,users,DATE);
        binding.recyclerView3.setAdapter(adapter);
        binding.recyclerView3.setLayoutManager(new LinearLayoutManager(AddTeamAttendanceActivity.this));

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    Users user = snapshot1.getValue(Users.class);
                    users.add(user);
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {

                super.onChanged();
                if (adapter.allMarked())
                {
                    binding.MarkedAll.setVisibility(View.VISIBLE);
                }
            }
        });







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
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    void filter(String newText)
    {
        ArrayList<Users> users = adapter.getUsers();
        ArrayList<Users> filteredUsers = new ArrayList<>();
        if (newText.isEmpty())
        {
            adapter.filter(adapter.getBackUp());
            return;
        }

        else{
            for (Users user : users)
            {
                if (user.getUserName().toLowerCase().contains(newText.toLowerCase()))
                {
                    filteredUsers.add(user);
                }
            }
        }
        if (filteredUsers.isEmpty())
        {
            adapter.filter(adapter.getBackUp());
            return;
        }
        adapter.filter(filteredUsers);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}