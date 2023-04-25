package com.example.navigationdrawersample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
  Toolbar toolbar;
  DrawerLayout drawerLayout;
  NavigationView navigationView;
  public ImageView USER_IMAGE;
  public TextView USER_NAME_MAIL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout=findViewById(R.id.drawerlayout);
        navigationView=findViewById(R.id.navigationview);
        toolbar=findViewById(R.id.toolbar);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String name = user.getDisplayName();
        String email = user.getEmail();
        String profileImage = user.getPhotoUrl().toString();

        //step 1
        setSupportActionBar(toolbar);
        View headerView = navigationView.getHeaderView(0);
        USER_IMAGE = headerView.findViewById(R.id.userProfileImage);
        USER_NAME_MAIL = headerView.findViewById(R.id.userName);
        USER_NAME_MAIL.setText(name + "\n" + email);
        USER_IMAGE.setImageURI(Uri.parse(profileImage));
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.opendrawer,R.string.closedrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        //
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.BMI)
                {
                    Toast.makeText(MainActivity.this, "BMI", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,BMI.class);
                    startActivity(intent);
                }
                else if (id==R.id.wc)
                {
                    Toast.makeText(MainActivity.this, "Shubham's Task", Toast.LENGTH_SHORT).show();

                }
                else if(id == R.id.medRm)
                {
                    startActivity(new Intent(MainActivity.this,PillsReminder.class));
                }
                else if(id == R.id.notes)
                {
                    Toast.makeText(MainActivity.this, "Shubham's Task", Toast.LENGTH_SHORT).show();

                }
                else if (id == R.id.pedd)
                {
                    startActivity(new Intent(MainActivity.this,Pedometer.class));
                }
                else
                {
                    Toast.makeText(MainActivity.this, "abhaddf", Toast.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

}