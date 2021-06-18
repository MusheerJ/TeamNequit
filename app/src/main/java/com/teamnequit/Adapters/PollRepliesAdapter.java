package com.teamnequit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamnequit.Models.PollReplies;
import com.teamnequit.R;
import com.teamnequit.databinding.SamplePollRepliesBinding;

import java.util.ArrayList;

public class PollRepliesAdapter extends RecyclerView.Adapter<PollRepliesAdapter.PollRepliesViewHolder> {

    Context context;
    ArrayList<PollReplies> replies;

    public PollRepliesAdapter(Context context, ArrayList<PollReplies> replies) {
        this.context = context;
        this.replies = replies;
    }

    @NonNull
    @Override
    public PollRepliesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_poll_replies,parent,false);
        return new PollRepliesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PollRepliesViewHolder holder, int position) {
        PollReplies reply = replies.get(position);
        holder.binding.replyBy.setText("Reply By : "+reply.getReplyBy());
        holder.binding.reply.setText("Reply : "+reply.getReply());
    }

    @Override
    public int getItemCount() {
        return replies.size();
    }

    public class PollRepliesViewHolder extends RecyclerView.ViewHolder{

        SamplePollRepliesBinding binding;
        public PollRepliesViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SamplePollRepliesBinding.bind(itemView);
        }
    }
}
