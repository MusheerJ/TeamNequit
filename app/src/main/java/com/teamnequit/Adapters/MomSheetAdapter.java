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
import com.teamnequit.Activities.MomSheet.ViewMomActivity;
import com.teamnequit.Models.MOMSheet;
import com.teamnequit.R;
import com.teamnequit.databinding.AttendanceDeleteBinding;
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

        View view = LayoutInflater.from(context).inflate(R.layout.attendance_delete,null);
        AttendanceDeleteBinding deleteBinding= AttendanceDeleteBinding.bind(view);
        AlertDialog Mom = new AlertDialog.Builder(context)
                .setTitle("Delete MoM?")
                .setView(deleteBinding.getRoot())
                .create();

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
                String currentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail().substring(0,7);
                //isHead
                FirebaseDatabase.getInstance().getReference().child("Heads").child(currentUser).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Mom.show();
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
                String path = "MOM/"+dept+"/"+sheet.getDate();
                FirebaseDatabase.getInstance().getReference().child(path).setValue(null);
                Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                Mom.dismiss();
            }
        });

        deleteBinding.CancelAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mom.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return momSheets.size();
    }

    public void filter(ArrayList<MOMSheet> sheets) {
        this.momSheets = sheets;
        notifyDataSetChanged();
    }

    public class MomSheetViewHolder extends RecyclerView.ViewHolder{
        SampleAttendanceBinding binding;
        public MomSheetViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleAttendanceBinding.bind(itemView);
        }
    }
}
