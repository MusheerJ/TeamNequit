package com.teamnequit.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.teamnequit.Activities.AttendanceSheet.TeamAttendanceActivity;
import com.teamnequit.Models.MemberAttendance;
import com.teamnequit.Models.Users;
import com.teamnequit.R;
import com.teamnequit.databinding.AttendanceMarkBinding;
import com.teamnequit.databinding.SampleAddAttendanceBinding;

import java.util.ArrayList;

public class MemberAttendanceAdapter extends RecyclerView.Adapter<MemberAttendanceAdapter.MemberAttendanceViewHolder>{
    Context context;
    ArrayList<Users> users;

    String date;
    FirebaseDatabase database;

    public MemberAttendanceAdapter(Context context, ArrayList<Users> users,String date) {
        this.context = context;
        this.users = users;
        this.date = date;

    }

    @NonNull
    @Override
    public MemberAttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_add_attendance,parent,false);
        return new MemberAttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAttendanceViewHolder holder, int position) {
        Users user = users.get(position);

        //mark Attendance
        View view = LayoutInflater.from(context).inflate(R.layout.attendance_mark,null);
        AttendanceMarkBinding markBinding = AttendanceMarkBinding.bind(view);
        AlertDialog markAttendance = new AlertDialog.Builder(context)
                .setTitle(date)
                .setView(markBinding.getRoot())
                .create();

        holder.binding.AttendeeName.setText(user.getUserName());
        holder.binding.memberEmailAT.setText(user.getUserEmail().substring(0,7));
        holder.binding.AddMemberAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                markAttendance.show();
                markBinding.attendeeNameMark.setText(user.getUserName());

                markBinding.CancelMark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        markBinding.radioGroup.clearCheck();
                        markAttendance.dismiss();
                    }
                });

                markBinding.SaveMark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(markBinding.AbsentButton.isChecked() || markBinding.PresentButton.isChecked())
                        {
                            database = FirebaseDatabase.getInstance();
                            String name = user.getUserName();
                            String prn = user.getUserEmail().substring(0,7);
                            String status;
                            if (markBinding.AbsentButton.isChecked())
                            {
                                status = "A";
                            }
                            else {
                                status = "P";
                            }
                            MemberAttendance memberAttendance = new MemberAttendance(name,prn,status);
                            database.getReference().child("MemberAttendance").child(date).child(memberAttendance.getPrn()).setValue(memberAttendance).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(context,"Attendance Marked !!",Toast.LENGTH_SHORT).show();
                                        markAttendance.dismiss();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(context,"Please select one option !!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        users.remove(position);
                        notifyDataSetChanged();
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public boolean allMarked ()
    {
        return users.isEmpty();
    }

    public class MemberAttendanceViewHolder extends RecyclerView.ViewHolder{
        SampleAddAttendanceBinding binding;
        public MemberAttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleAddAttendanceBinding.bind(itemView);
        }
    }
}
