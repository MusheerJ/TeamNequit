package com.teamnequit.Activities.AttendanceSheet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamnequit.Adapters.MemberViewAttendanceAdapter;
import com.teamnequit.Models.MemberAttendance;
import com.teamnequit.databinding.ActivityViewMemberAttendanceBinding;

import java.util.ArrayList;


public class ViewMemberAttendanceActivity extends AppCompatActivity {
    ActivityViewMemberAttendanceBinding binding;
    FirebaseDatabase database;
    ArrayList<MemberAttendance> attendances;
    MemberViewAttendanceAdapter adapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewMemberAttendanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        getSupportActionBar().setTitle("Member Attendance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(ViewMemberAttendanceActivity.this);
        progressDialog.setMessage("Loading ....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        database = FirebaseDatabase.getInstance();
        attendances = new ArrayList<>();
        adapter = new MemberViewAttendanceAdapter(this,attendances);
        binding.recyclerView4.setLayoutManager(new LinearLayoutManager(ViewMemberAttendanceActivity.this));
        binding.recyclerView4.setAdapter(adapter);
        String date = getIntent().getStringExtra("attendanceDate");

        database.getReference().child("MemberAttendance").child(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                attendances.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    MemberAttendance attendance = snapshot1.getValue(MemberAttendance.class);
                    attendances.add(attendance);
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
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}