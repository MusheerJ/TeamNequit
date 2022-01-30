package com.teamnequit.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamnequit.Activities.ContactDevs.ContactDevsActivity;
import com.teamnequit.Activities.Rules.TeamsRulesActivity;
import com.teamnequit.Models.Users;
import com.teamnequit.R;
import com.teamnequit.databinding.ActivityMainBinding;
import com.teamnequit.databinding.NavHeaderMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    ActivityMainBinding binding;
    NavHeaderMainBinding navHeaderMainBinding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    Users user;
    Users profile;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar();
//        toolbar.setForceDarkAllowed(false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.LogOut)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        navHeaderMainBinding = NavHeaderMainBinding.bind(navigationView.getHeaderView(0));
        
        database.getReference().child("Users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(Users.class);
                navHeaderMainBinding.userName.setText(user.getUserName());
                navHeaderMainBinding.userMail.setText(user.getUserEmail());
                String userRoll = user.getUserEmail().substring(0,7);
                try {
                    String userP = "https://firebasestorage.googleapis.com/v0/b/team-nequit.appspot.com/o/usersprofiles%2F"+userRoll+".jpeg?alt=media&token=";
                    Glide.with(MainActivity.this).load(user.getUserProfile()).placeholder(R.drawable.avatar).into(navHeaderMainBinding.imageView);
                }
                catch (Exception e){
//                    Toast.makeText(MainActivity.this,"Please Restart the App!!",Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_gallery:
                        Intent i = new Intent(MainActivity.this,SelfUserProfileActivity.class);
                        i.putExtra("Self",user);
                        startActivity(i);
                        break;
                    case R.id.nav_home:
                        drawer.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.Rules:
                        Intent intent = new Intent(MainActivity.this, TeamsRulesActivity.class);
                        intent.putExtra("news","");
                        startActivity(intent);
                        break;
                    case R.id.newsLetter:
                        Intent intent1 = new Intent(MainActivity.this,TeamsRulesActivity.class);
                        intent1.putExtra("news","News Letter");
                        startActivity(intent1);
                        break;
                    case R.id.AbtDevs:
                        startActivity(new Intent(MainActivity.this, ContactDevsActivity.class));
                        break;
                    case R.id.LogOut:
                        Toast.makeText(MainActivity.this,"Signed Out",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,SignInActivity.class));
                        auth.signOut();
                        break;
                }
                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.userLogout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,SignInActivity.class));
                finishAffinity();
                Toast.makeText(MainActivity.this,"Logout successfully!",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onPause() {
//        finishAffinity();
        super.onPause();
    }
}