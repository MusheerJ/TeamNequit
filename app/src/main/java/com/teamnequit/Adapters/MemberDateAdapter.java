package com.teamnequit.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.teamnequit.Activities.AttendanceSheet.ViewMemberAttendanceActivity;
import com.teamnequit.Activities.MomSheet.ViewMomActivity;
import com.teamnequit.R;
import com.teamnequit.databinding.AttendanceDeleteBinding;
import com.teamnequit.databinding.SampleAttendanceBinding;

import java.util.ArrayList;

public class MemberDateAdapter extends RecyclerView.Adapter<MemberDateAdapter.MemberDateViewHolder>
{
    Context context;
    ArrayList<String> dates;
    String UsedBy;

    public MemberDateAdapter(Context context, ArrayList<String> dates,String usedBy) {
        this.context = context;
        this.dates = dates;
        this.UsedBy = usedBy;
    }

    @NonNull
    @Override
    public MemberDateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_attendance,parent,false);
        return new MemberDateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberDateViewHolder holder, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.attendance_delete,null);
        AttendanceDeleteBinding  deleteBinding= AttendanceDeleteBinding.bind(view);
        AlertDialog attendance = new AlertDialog.Builder(context)
                .setTitle("Delete Attendance?")
                .setView(deleteBinding.getRoot())
                .create();

        String date = dates.get(position);
        holder.binding.condate.setText(date);
        holder.binding.dateTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UsedBy.contains("MOM"))
                {
//                    Toast.makeText(context,UsedBy,Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, ViewMomActivity.class);
                    String loc = UsedBy+"/"+date;
                    i.putExtra("Loc",loc);
                    context.startActivity(i);
                }
                else
                    {
                        Intent i = new Intent(context, ViewMemberAttendanceActivity.class);
                        i.putExtra("attendanceDate",date);
                        context.startActivity(i);
                    }

            }
        });

        //Show Dialog
        holder.binding.dateTable.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                attendance.show();
                return false;
            }
        });
        //Delete Attendance
        deleteBinding.deleteAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                database.getReference().child("MemberAttendance").child(date).setValue(null);
                database.getReference().child("MemberAttendanceDates").child(date).setValue(null);
                Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                attendance.dismiss();

            }
        });
        deleteBinding.CancelAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendance.dismiss();
            }
        });

        //Show Dialog
        holder.binding.dateTable.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                attendance.show();
                return false;
            }
        });
        //Delete Attendance
        deleteBinding.deleteAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                database.getReference().child("MemberAttendance").child(date).setValue(null);
                database.getReference().child("MemberAttendanceDates").child(date).setValue(null);
                Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                attendance.dismiss();

            }
        });
        deleteBinding.CancelAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendance.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public void filter(ArrayList<String> dates) {
        this.dates = dates;
        notifyDataSetChanged();
    }


    public class MemberDateViewHolder extends RecyclerView.ViewHolder{
        SampleAttendanceBinding binding;
        public MemberDateViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleAttendanceBinding.bind(itemView);
        }
    }
}
