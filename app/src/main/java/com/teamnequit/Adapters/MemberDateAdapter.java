package com.teamnequit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamnequit.Activities.AttendanceSheet.ViewMemberAttendanceActivity;
import com.teamnequit.R;
import com.teamnequit.databinding.SampleAttendanceBinding;

import java.util.ArrayList;

public class MemberDateAdapter extends RecyclerView.Adapter<MemberDateAdapter.MemberDateViewHolder>
{
    Context context;
    ArrayList<String> dates;

    public MemberDateAdapter(Context context, ArrayList<String> dates) {
        this.context = context;
        this.dates = dates;
    }

    @NonNull
    @Override
    public MemberDateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_attendance,parent,false);
        return new MemberDateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberDateViewHolder holder, int position) {
        String date = dates.get(position);
        holder.binding.condate.setText(date);
        holder.binding.dateTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ViewMemberAttendanceActivity.class);
                i.putExtra("attendanceDate",date);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public class MemberDateViewHolder extends RecyclerView.ViewHolder{
        SampleAttendanceBinding binding;
        public MemberDateViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleAttendanceBinding.bind(itemView);
        }
    }
}
