package com.teamnequit.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.teamnequit.Models.Users;
import com.teamnequit.databinding.ActivityAddNeUserBinding;

public class AddNeUserActivity extends AppCompatActivity {
    ActivityAddNeUserBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNeUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dialog  = new ProgressDialog(AddNeUserActivity.this);
        dialog.setMessage("Adding User please wait ...");
        dialog.setCancelable(false);

        binding.AddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users user = new Users();
                user.setUserName(binding.AddUserName.getText().toString());
                user.setUserBOb(binding.AddUserDob.getText().toString());
                user.setUserEmail(binding.AddUserEmail.getText().toString());
                user.setUserPhone(binding.AddUserPhone.getText().toString());
                user.setUserClgDpt(binding.AddUserClgDpt.getText().toString());
                user.setUserNeqDpt(binding.AddUserClubDpt.getText().toString());
                user.setUserPass(binding.AdduserPassword.getText().toString());

                dialog.show();


                auth.createUserWithEmailAndPassword(user.getUserEmail(),user.getUserPass()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            database.getReference().child("Users").child(auth.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(AddNeUserActivity.this,"User Added",Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        binding.AddUserName.setText(null);
                                        binding.AddUserDob.setText(null);
                                        binding.AddUserEmail.setText(null);
                                        binding.AddUserPhone.setText(null);
                                        binding.AddUserClgDpt.setText(null);
                                        binding.AddUserClubDpt.setText(null);
                                        binding.AdduserPassword.setText(null);
                                    }
                                }
                            });

                        }
                    }
                });

            }
        });
    }
}