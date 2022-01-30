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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamnequit.Activities.Polls.PollRepliesActivity;
import com.teamnequit.Models.Poll;
import com.teamnequit.R;
import com.teamnequit.databinding.AttendanceDeleteBinding;
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
        View view = LayoutInflater.from(context).inflate(R.layout.attendance_delete,null);
        AttendanceDeleteBinding deleteBinding= AttendanceDeleteBinding.bind(view);
        AlertDialog Poll = new AlertDialog.Builder(context)
                .setTitle("Delete Poll?")
                .setView(deleteBinding.getRoot())
                .create();

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

        holder.binding.dateTable.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                String currentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail().substring(0,7);
                //isHead
                FirebaseDatabase.getInstance().getReference().child("Heads").child(currentUser).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Poll.show();
                        }
                        else{
                            Toast.makeText(context,"You dont have access to delete !!!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                return false;
            }
        });

        deleteBinding.deleteAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                database.getReference().child("Polls").child(pollFor).child("PollList").child(poll.getPollDate()).setValue(null);
                database.getReference().child("Polls").child(pollFor).child("Replies").child(poll.getPollDate()).setValue(null);
                Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                Poll.dismiss();
            }
        });

        deleteBinding.CancelAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Poll.dismiss();
            }
        });

        holder.binding.dateTable.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                String currentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail().substring(0,7);
                //isHead
                FirebaseDatabase.getInstance().getReference().child("Heads").child(currentUser).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Poll.show();
                        }
                        else{
                            Toast.makeText(context,"You dont have access to delete !!!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                return false;
            }
        });

        deleteBinding.deleteAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                database.getReference().child("Polls").child(pollFor).child("PollList").child(poll.getPollDate()).setValue(null);
                database.getReference().child("Polls").child(pollFor).child("Replies").child(poll.getPollDate()).setValue(null);
                Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                Poll.dismiss();
            }
        });

        deleteBinding.CancelAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Poll.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return polls.size();
    }

    public void filter(ArrayList<Poll> polls) {
        this.polls = polls;
        notifyDataSetChanged();
    }

    public class PollViewHolder extends RecyclerView.ViewHolder{

        SamplePollBinding binding;
        public PollViewHolder(@NonNull View itemView) {
            super(itemView);
            binding =  SamplePollBinding.bind(itemView);
        }
    }
}
