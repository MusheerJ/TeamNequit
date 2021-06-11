package com.teamnequit.Activities.MemberList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamnequit.Adapters.MembersAdapter;
import com.teamnequit.Models.Users;
import com.teamnequit.R;
import com.teamnequit.databinding.ActivityViewMemberListBinding;

import java.util.ArrayList;

public class ViewMemberListActivity extends AppCompatActivity {

    ActivityViewMemberListBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    ArrayList<Users> users;
    MembersAdapter adapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        binding = ActivityViewMemberListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Member list");
        progressDialog = new ProgressDialog(ViewMemberListActivity.this);
        progressDialog.setMessage("Loading ....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        users = new ArrayList<>();
        adapter = new MembersAdapter(this,users);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(ViewMemberListActivity.this));
        binding.recyclerView.setAdapter(adapter);


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
        ArrayList<Users> filteredUsers = new ArrayList<>();
        if (newText.isEmpty())
        {
            adapter.filter(users);
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
        adapter.filter(filteredUsers);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}