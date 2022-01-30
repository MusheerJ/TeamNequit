package com.teamnequit.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.teamnequit.Models.Users;
import com.teamnequit.R;
import com.teamnequit.databinding.ActivitySelfUserProfileBinding;

public class SelfUserProfileActivity extends AppCompatActivity {

    ActivitySelfUserProfileBinding binding;
    FirebaseStorage storage;
    FirebaseAuth auth;
    Uri selectedImage;
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelfUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("User profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(SelfUserProfileActivity.this);
        progressDialog.setMessage("Uploading Profile Please Wait ....");

        Users user = (Users) getIntent().getSerializableExtra("Self");
        binding.SelfUsername.setText(user.getUserName());
        binding.SelfUserClgDpt.setText(user.getUserClgDpt());
        binding.SelfUserMail.setText(user.getUserEmail());
        binding.SelfUserClubDpt.setText(user.getUserNeqDpt());
        binding.SelfUserDOB.setText(user.getUserBOb());
        binding.SelfUserPhone.setText(user.getUserPhone());

        String userRoll = user.getUserEmail().substring(0,7);
        String userP = "https://firebasestorage.googleapis.com/v0/b/team-nequit.appspot.com/o/usersprofiles%2F"+userRoll+".jpeg?alt=media&token=";
        Glide.with(this).load(user.getUserProfile()).placeholder(R.drawable.avatar).into(binding.imageView3);

        binding.UploadProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,45);
            }
        });

        binding.SaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                StorageReference reference = storage.getReference().child("usersprofiles")
                        .child(user.getUserEmail().substring(0,7));

                reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    database.getReference().child("Users").child(auth.getUid()).child("userProfile").setValue(uri.toString());
                                    progressDialog.dismiss();
                                    binding.SaveProfile.setVisibility(View.GONE);
                                    binding.UploadProfile.setVisibility(View.VISIBLE);
                                    Toast.makeText(SelfUserProfileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            Toast.makeText(SelfUserProfileActivity.this,task.getException().toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            if(data.getData() != null){
                Uri uri ;
                uri = data.getData();
                Cursor returnCursor = getContentResolver().query(uri, null, null, null, null);
//                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                returnCursor.moveToFirst();
                long imageSize = returnCursor.getLong(sizeIndex)/1000;
                if (imageSize > 200) {
                    Toast.makeText(SelfUserProfileActivity.this, "Please select image whose size is less than 200KB", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    binding.imageView3.setImageURI(data.getData());
                    selectedImage = data.getData();
                    binding.UploadProfile.setVisibility(View.GONE);
                    binding.SaveProfile.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}