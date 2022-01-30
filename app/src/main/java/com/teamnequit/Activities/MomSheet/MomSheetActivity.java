package com.teamnequit.Activities.MomSheet;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamnequit.Adapters.MomSheetAdapter;
import com.teamnequit.Models.MOMSheet;
import com.teamnequit.R;
import com.teamnequit.databinding.ActivityMomSheetBinding;
import com.teamnequit.databinding.MomParametersBinding;

import java.util.ArrayList;
import java.util.Collections;

public class MomSheetActivity extends AppCompatActivity {

    ActivityMomSheetBinding binding;
    static FirebaseDatabase database;
    ArrayList <MOMSheet> sheets;
    MomSheetAdapter adapter;
    ProgressDialog pdialog;
    static MOMSheet momSheet = new MOMSheet();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityMomSheetBinding.inflate(getLayoutInflater());
        database = FirebaseDatabase.getInstance();
        setContentView(binding.getRoot());

        String DepartmentName = getIntent().getStringExtra("MomName");
        getSupportActionBar().setTitle(DepartmentName+" MOM");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        momSheet.setDptName(DepartmentName);

        //MOM Parameters
        Context context = MomSheetActivity.this;

        pdialog = new ProgressDialog(context);
        pdialog.setMessage("Loading");
        pdialog.show();

        View view = LayoutInflater.from(context).inflate(R.layout.mom_parameters,null);
        MomParametersBinding mBinding = MomParametersBinding.bind(view);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Mom Parameters")
                .setView(mBinding.getRoot())
                .setCancelable(false)
                .create();

        sheets = new ArrayList<>();
        adapter = new MomSheetAdapter(MomSheetActivity.this,sheets,momSheet.getDptName());


        //Check if the current can add the mom or not
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail().substring(0,7);
        //isHead
        FirebaseDatabase.getInstance().getReference().child("Heads").child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    binding.addMom.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        showMomSheet(binding);
        binding.addMom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                mBinding.preparedBy.requestFocus();
                addMomSheet(mBinding,dialog,DepartmentName);
            }
        });

    }

    //Add MOM
    void addMomSheet(MomParametersBinding binding,AlertDialog dialog,String departmentName)
    {
        String path = "MOM/"+departmentName;
        binding.continueBtnMom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setMomParaMeters(binding))
                {
                    database.getReference().child(path).child(momSheet.getDate()).setValue(momSheet).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                setMomParaMetersToNull(binding);
                            }
                        }
                    });
                }
                else {
                    return;
                }

                dialog.dismiss();
                Toast.makeText(MomSheetActivity.this,"Added",Toast.LENGTH_SHORT).show();
//                dialog.dismiss();

            }
        });

        binding.cancelMom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMomParaMetersToNull(binding);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                dialog.dismiss();
            }
        });
    }

    void showMomSheet(ActivityMomSheetBinding binding)
    {
        String path = "MOM/"+momSheet.getDptName();;
        binding.momRecylerView.setAdapter(adapter);
        binding.momRecylerView.setLayoutManager(new LinearLayoutManager(MomSheetActivity.this));

        database.getReference().child(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sheets.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    MOMSheet sheet = snapshot1.getValue(MOMSheet.class);
                    sheets.add(sheet);
                }
                Collections.reverse(sheets);
                adapter.notifyDataSetChanged();
                pdialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    void setMomParaMetersToNull(MomParametersBinding binding)
    {
        //Text To null
        binding.Mom.setText(null);
        binding.momSubPoints.setText(null);
        binding.preparedBy.setText(null);
        binding.momDate.setText(null);
        binding.momTime.setText(null);
        binding.momAgenda.setText(null);

        //Error to null
        binding.Mom.setError(null);
        binding.momSubPoints.setError(null);
        binding.preparedBy.setError(null);
        binding.momDate.setError(null);
        binding.momTime.setError(null);
        binding.momAgenda.setError(null);
    }

    boolean setMomParaMeters(MomParametersBinding binding)
    {
        //Prepared by
        if (binding.preparedBy.getText().toString().isEmpty())
        {
            binding.preparedBy.setError("This cant be empty!!");
            return false;
        }
        else {
            momSheet.setPreparedBy(binding.preparedBy.getText().toString());
        }

        //Date
        if (binding.momDate.getText().toString().isEmpty()){
            binding.momDate.setError("This cant be empty!!");
            return false;
        }
        else{
            String date = binding.momDate.getText().toString();
            if (date.contains("/") || date.length() != 10 || !date.contains("-"))
            {
                binding.momDate.setError("Invalid Date! Please match the given pattern (dd-mm-yyyy)");
                return false;
            }
            momSheet.setDate(binding.momDate.getText().toString());
        }

        //Time
        if (binding.momTime.getText().toString().isEmpty()){
            binding.momTime.setError("This cant be empty!!");
            return false;
        }
        else{
            momSheet.setTime(binding.momTime.getText().toString());
        }

        //MOM
        if (binding.Mom.getText().toString().isEmpty()){
            binding.Mom.setError("This cant be empty!!");
            return false;
        }
        else{
            momSheet.setMom(binding.Mom.getText().toString());
        }

        //Agenda
        if (binding.momAgenda.getText().toString().isEmpty()){
            binding.momAgenda.setError("This cant be empty!!");
            return false;
        }
        else{
            momSheet.setAgenda(binding.momAgenda.getText().toString());
        }

        //SubPoints
        if (binding.momSubPoints.getText().toString().isEmpty()){
            binding.momSubPoints.setError("This cant be empty!!");
            return false;
        }
        else{
            momSheet.setSubPoints(binding.momSubPoints.getText().toString());
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_members,menu);
        MenuItem menuItem = menu.findItem(R.id.memberSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search ...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void  filter(String newText)
    {
        ArrayList<MOMSheet> filteredSheets = new ArrayList<>();
        if (newText.isEmpty())
        {
            adapter.filter(sheets);
            return;
        }
        else
        {
            for (MOMSheet sheet : sheets)
            {
                if (sheet.getDate().contains(newText))
                {
                    filteredSheets.add(sheet);
                }
            }

        }
        adapter.filter(filteredSheets);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}