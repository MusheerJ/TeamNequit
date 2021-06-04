package com.teamnequit.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.teamnequit.R;
import com.teamnequit.databinding.ActivityForgotPassBinding;

public class ForgotPassActivity extends AppCompatActivity {

    ActivityForgotPassBinding binding;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(ForgotPassActivity.this);
        progressDialog.setMessage("Sending reset link ...");
        progressDialog.setCancelable(false);

        binding.GetResetLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.ForgotuserEmail.getText().toString().isEmpty())
                {
                    binding.ForgotuserEmail.setError("This cant be empty");
                    return;
                }
                progressDialog.show();
                FirebaseAuth.getInstance().sendPasswordResetEmail(binding.ForgotuserEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ForgotPassActivity.this,"Reset link sent !",Toast. LENGTH_SHORT).show();
                                    startActivity(new Intent(ForgotPassActivity.this,SignInActivity.class));
                                    finishAffinity();
                                }
                            }
                        });
            }
        });

    }
}