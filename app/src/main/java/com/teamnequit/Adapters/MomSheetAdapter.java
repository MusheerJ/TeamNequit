package com.teamnequit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.teamnequit.Activities.MomSheet.ViewMomActivity;
import com.teamnequit.Models.MOMSheet;
import com.teamnequit.R;
import com.teamnequit.databinding.SampleAttendanceBinding;

import java.util.ArrayList;

public class MomSheetAdapter extends RecyclerView.Adapter<MomSheetAdapter.MomSheetViewHolder>{
    Context context;
    ArrayList<MOMSheet> momSheets;
    ArrayList<MOMSheet> backUp;
    String dept;

    public MomSheetAdapter(Context context, ArrayList<MOMSheet> momSheets,String dept) {
        this.context = context;
        this.momSheets = momSheets;
        this.dept = dept;
    }

    @NonNull
    @Override
    public MomSheetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_attendance,parent,false);
        return new MomSheetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MomSheetViewHolder holder, int position) {
        MOMSheet sheet = momSheets.get(position);
        holder.binding.condate.setText(sheet.getDate());

        holder.binding.dateTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewMomActivity.class);
                intent.putExtra("MOM",sheet);
                context.startActivity(intent);
            }
        });

        holder.binding.dateTable.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String path = "MOM/"+dept+"/"+sheet.getDate();
                FirebaseDatabase.getInstance().getReference().child(path).setValue(null);
                Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return momSheets.size();
    }

    public class MomSheetViewHolder extends RecyclerView.ViewHolder{
        SampleAttendanceBinding binding;
        public MomSheetViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleAttendanceBinding.bind(itemView);
        }
    }
}
