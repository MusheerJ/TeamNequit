package com.teamnequit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamnequit.Activities.MemberList.ViewMemberDetailsActivity;
import com.teamnequit.Models.Users;
import com.teamnequit.R;
import com.teamnequit.databinding.SampleMemberlistBinding;

import java.util.ArrayList;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MembersViewHolder>
{
    Context context;
    ArrayList<Users> users;
    ArrayList<Users> backUsers;

    public MembersAdapter(Context context, ArrayList<Users> users) {
        this.context = context;
        this.users = users;
        this.backUsers = users;
    }

    @NonNull
    @Override
    public MembersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_memberlist,parent,false);
        return new  MembersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MembersViewHolder holder, int position) {
        Users user = users.get(position);
        holder.binding.memberEmail.setText(user.getUserEmail());
        holder.binding.memberName.setText(user.getUserName());
        holder.binding.Member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,ViewMemberDetailsActivity.class);
                i.putExtra("Values",user);
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public void filter(ArrayList<Users> filterUsers) {
        users = filterUsers;
        notifyDataSetChanged();
    }


    public class MembersViewHolder extends RecyclerView.ViewHolder{
        SampleMemberlistBinding binding;
        public MembersViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleMemberlistBinding.bind(itemView);
        }
    }
}
