package com.teamnequit.Activities;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.teamnequit.Models.Users;
import com.teamnequit.databinding.ActivityAddNeUserBinding;

public class AddNeUserActivity extends AppCompatActivity {
    ActivityAddNeUserBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog dialog;
    Uri selectedImage;
    FirebaseStorage storage;
    Users user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNeUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        dialog  = new ProgressDialog(AddNeUserActivity.this);
        dialog.setMessage("Adding User please wait ...");
//        dialog.setCancelable();



//        binding.UserProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(intent,45);
//
//            }
//        });

        binding.AddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user  = new Users();
                user.setUserName(binding.AddUserName.getText().toString());
//                user.setUserBOb(binding.AddUserDob.getText().toString());
                user.setUserEmail(binding.AddUserEmail.getText().toString());
//                user.setUserPhone(binding.AddUserPhone.getText().toString());
//                user.setUserClgDpt(binding.AddUserClgDpt.getText().toString());
//                user.setUserNeqDpt(binding.AddUserClubDpt.getText().toString());
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
//                                        binding.AddUserDob.setText(null);
                                        binding.AddUserEmail.setText(null);
//                                        binding.AddUserPhone.setText(null);
//                                        binding.AddUserClgDpt.setText(null);
//                                        binding.AddUserClubDpt.setText(null);
                                        binding.AdduserPassword.setText(null);
//                                        binding.UserProfile.setImageURI(null);
                                        auth.signOut();
                                    }
                                    else
                                    {
                                        Toast.makeText(AddNeUserActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }
                            });

                        }
                    }
                });

//                StorageReference reference = storage.getReference().child("usersprofiles")
//                        .child(user.getUserEmail().substring(0,7));
//                reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                        if (task.isSuccessful()){
//                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    user.setUserProfile(uri.toString());
//                                    Toast.makeText(AddNeUserActivity.this,uri.toString(),Toast.LENGTH_SHORT).show();
//
//
//                                }
//                            });
//                        }
//                        else{
//                            Toast.makeText(AddNeUserActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });



            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(data != null){
//            if(data.getData() != null){
//                Uri uri ;
//                uri = data.getData();
//                Cursor returnCursor = getContentResolver().query(uri, null, null, null, null);
//                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
//                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
//                returnCursor.moveToFirst();
//                long imageSize = returnCursor.getLong(sizeIndex)/1000;
//                if (imageSize > 200) {
//                    Toast.makeText(AddNeUserActivity.this, "Please select image whose size is less than 200KB", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                else{
//                    binding.UserProfile.setImageURI(data.getData());
//                    selectedImage = data.getData();
//                }
//            }
//        }
//    }
}