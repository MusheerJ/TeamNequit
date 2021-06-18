package com.teamnequit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamnequit.Activities.Polls.PollRepliesActivity;
import com.teamnequit.Models.Poll;
import com.teamnequit.R;
import com.teamnequit.databinding.SamplePollBinding;

import java.util.ArrayList;

public class PollAdapter extends RecyclerView.Adapter<PollAdapter.PollViewHolder>{
    Context context;
    ArrayList<Poll> polls;
    String pollFor;

    public PollAdapter(Context context, ArrayList<Poll> polls,String pollFor) {
        this.context = context;
        this.polls = polls;
        this.pollFor = pollFor;
    }



    @NonNull
    @Override
    public PollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_poll,parent,false);
        return new PollViewHolder(view);
//        return null;
//        View view = LayoutInflater.from(context).inflate(R.layout.sample_add_attendance,parent,false);
//        return new MemberAttendanceAdapter.MemberAttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PollViewHolder holder, int position) {
        Poll poll = polls.get(position);
        holder.binding.condate1.setText(poll.getPollDate());
        holder.binding.dateTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,poll.getPreparedBy(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, PollRepliesActivity.class);
                intent.putExtra("PollDetails",poll);
                intent.putExtra("pollFor",pollFor);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return polls.size();
    }

    public class PollViewHolder extends RecyclerView.ViewHolder{

        SamplePollBinding binding;
        public PollViewHolder(@NonNull View itemView) {
            super(itemView);
            binding =  SamplePollBinding.bind(itemView);
        }
    }
}
