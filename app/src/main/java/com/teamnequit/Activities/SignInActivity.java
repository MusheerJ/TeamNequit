package com.teamnequit.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.teamnequit.Models.Users;
import com.teamnequit.R;
import com.teamnequit.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity {
    ActivitySignInBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();


        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(SignInActivity.this);
        dialog.setMessage("Login you in please wait ...");
        dialog.setCancelable(false);
        binding.userLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.userEmail.getText().toString().isEmpty())
                {
                    binding.userEmail.setError("This cant be empty !");
                    return;
                }
                if(binding.userPassword.getText().toString().isEmpty())
                {
                    binding.userPassword.setError("This cant be empty !");
                    return;
                }
                dialog.show();
                String UserMail = binding.userEmail.getText().toString();
                String UserPass = binding.userPassword.getText().toString();
                auth.createUserWithEmailAndPassword(UserMail,UserPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Users user = new Users(UserMail,UserPass);
                            String Uid = auth.getUid();
                            database.getReference().child("Users").child(Uid).setValue(user);
                            Toast.makeText(SignInActivity.this,"userCreated !",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            startActivity(new Intent(SignInActivity.this,MainActivity.class));
                            finish();
                        }
                    }
                });

                binding.userEmail.setText(null);
                binding.userPassword.setText(null);

            }
        });


    }
}