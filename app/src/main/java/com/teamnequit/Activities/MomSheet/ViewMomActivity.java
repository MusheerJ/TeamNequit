package com.teamnequit.Activities.MomSheet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamnequit.Models.MOMSheet;
import com.teamnequit.databinding.ActivityViewMomBinding;

public class ViewMomActivity extends AppCompatActivity {

    ActivityViewMomBinding binding;
    FirebaseDatabase database;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewMomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("MOM Sheet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MOMSheet momSheet = (MOMSheet) getIntent().getSerializableExtra("MOM");
        binding.ViewMomPreparedBy.setText(momSheet.getPreparedBy());
        binding.ViewMomDate.setText(momSheet.getDate());
        binding.ViewMomTime.setText(momSheet.getTime());
        binding.ViewMom.setText(momSheet.getMom());
        binding.ViewMomAgenda.setText(momSheet.getAgenda());
        binding.ViewMomSubPoint.setText(momSheet.getSubPoints());





    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}