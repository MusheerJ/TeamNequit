package com.teamnequit.Activities.AttendanceSheet;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamnequit.Adapters.MemberDateAdapter;
import com.teamnequit.R;
import com.teamnequit.databinding.ActivityTeamAttandanceBinding;
import com.teamnequit.databinding.AttendenceDatedayBinding;

import java.util.ArrayList;
import java.util.Collections;

public class TeamAttendanceActivity extends AppCompatActivity {

    ActivityTeamAttandanceBinding binding;
    FirebaseDatabase database;
    MemberDateAdapter adapter;
    ArrayList<String> dates;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeamAttandanceBinding.inflate(getLayoutInflater());
        database = FirebaseDatabase.getInstance();
        setContentView(binding.getRoot());

        //AlertDialog
        View view = LayoutInflater.from(TeamAttendanceActivity.this).inflate(R.layout.attendence_dateday,null);
        AttendenceDatedayBinding attendenceDatedayBinding = AttendenceDatedayBinding.bind(view);
        AlertDialog attendance = new AlertDialog.Builder(TeamAttendanceActivity.this)
                .setTitle("Attendance Parameters")
                .setView(attendenceDatedayBinding.getRoot())
                .create();


        getSupportActionBar().setTitle("Team Attendance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail().substring(0,7);

        //is CoreMember
        FirebaseDatabase.getInstance().getReference().child("CoreMembers").child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    binding.addAllTeamAttendance.setVisibility(View.VISIBLE);
                }
                else{
                    binding.addAllTeamAttendance.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        if (isCoreMember){
//            binding.addAllTeamAttendance.setVisibility(View.VISIBLE);
//        }else{
//            binding.addAllTeamAttendance.setVisibility(View.GONE);
//        }

        progressDialog = new ProgressDialog(TeamAttendanceActivity.this);
        progressDialog.setMessage("Loading ....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        dates = new ArrayList<>();
        adapter = new MemberDateAdapter(this,dates,"");
        binding.recyclerView2.setLayoutManager(new LinearLayoutManager(TeamAttendanceActivity.this));
        binding.recyclerView2.setAdapter(adapter);

        database.getReference().child("MemberAttendanceDates").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dates.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    String date = snapshot1.getValue(String.class);
                    dates.add(date);
                }
                Collections.reverse(dates);
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        binding.addAllTeamAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                attendance.show();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                attendenceDatedayBinding.AttendanceDate.requestFocus();
                attendenceDatedayBinding.cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        attendenceDatedayBinding.AttendanceDate.setText(null);
                        attendenceDatedayBinding.AttendanceDate.setError(null);
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                        attendance.dismiss();
                    }
                });

                attendenceDatedayBinding.continueBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String date;
                        if (attendenceDatedayBinding.AttendanceDate.getText().toString().isEmpty())
                        {
                            attendenceDatedayBinding.AttendanceDate.setError("This cant be empty");
                            return;
                        }

                        else {


                            date = attendenceDatedayBinding.AttendanceDate.getText().toString();
                            if(date.contains("/") || date.length() != 10 || !date.contains("-"))
                            {
                                attendenceDatedayBinding.AttendanceDate.setError("Invalid Date! Please match the given pattern");
                                return;
                            }

                            database.getReference().child("MemberAttendanceDates").child(date).setValue(date);

                            Intent i = new Intent(TeamAttendanceActivity.this,AddTeamAttendanceActivity.class);
                            i.putExtra("Date",date);
                            startActivity(i);
//                            startActivity(new Intent(TeamAttendanceActivity.this,AddTeamAttendanceActivity.class));
                            attendance.dismiss();

                            attendenceDatedayBinding.AttendanceDate.setText(null);
                            attendenceDatedayBinding.AttendanceDate.setError(null);
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);


                        }

                    }
                });
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_members,menu);
        MenuItem menuItem = menu.findItem(R.id.memberSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search date ...");

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


    private void  filter(String newText)
    {
        ArrayList<String> filteredDates = new ArrayList<>();
        if (newText.isEmpty())
        {
            adapter.filter(dates);
            return;
        }
        else
            {
                for (String date : dates)
                {
                    if (date.contains(newText))
                    {
                        filteredDates.add(date);
                    }
                }

            }
        adapter.filter(filteredDates);
    }




    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}