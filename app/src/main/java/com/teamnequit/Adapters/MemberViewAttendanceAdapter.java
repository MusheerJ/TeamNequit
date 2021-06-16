package com.teamnequit.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamnequit.Models.MemberAttendance;
import com.teamnequit.R;
import com.teamnequit.databinding.SampleMemberAttendanceviewBinding;

import java.util.ArrayList;

public class MemberViewAttendanceAdapter extends RecyclerView.Adapter<MemberViewAttendanceAdapter.MemberViewAttendanceViewHolder>
{
    Context context;
    ArrayList <MemberAttendance> attendances;
    public MemberViewAttendanceAdapter(Context context, ArrayList<MemberAttendance> attendances) {
        this.context = context;
        this.attendances = attendances;
    }

    @NonNull
    @Override
    public MemberViewAttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.sample_member_attendanceview,parent,false);
        return new MemberViewAttendanceViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewAttendanceViewHolder holder, int position) {
    MemberAttendance attendance = attendances.get(position);
    holder.binding.MemberName.setText(attendance.getName());
    holder.binding.MemberPrn.setText(attendance.getPrn().substring(0,7));
    if (attendance.getStatus().toLowerCase().equals("p"))
    {
        holder.binding.Status.setText(attendance.getStatus());
        holder.binding.Status.setTextColor(Color.parseColor("#FF06A76F"));;

    }
    else
        {
            holder.binding.Status.setText(attendance.getStatus());
            holder.binding.Status.setTextColor(Color.parseColor("#e4002b"));;
        }

    }

    @Override
    public int getItemCount() {
        return attendances.size();
    }


    public class MemberViewAttendanceViewHolder extends RecyclerView.ViewHolder{
        SampleMemberAttendanceviewBinding binding;
        public MemberViewAttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleMemberAttendanceviewBinding.bind(itemView);
        }
    }
}
