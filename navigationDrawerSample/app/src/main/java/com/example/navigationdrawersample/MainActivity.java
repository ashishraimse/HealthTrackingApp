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
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
  Toolbar toolbar;
  DrawerLayout drawerLayout;
  NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout=findViewById(R.id.drawerlayout);
        navigationView=findViewById(R.id.navigationview);
        toolbar=findViewById(R.id.toolbar);
        //step 1
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.opendrawer,R.string.closedrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


         // Navigation items
         // All these items are added in the navigation_item.xml we are identifying them by their Id's
         // This listener is used to identifying them, which activity need to be open
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                // Id of selected Item is within id
                int id=item.getItemId();

                // For BMI Activity
                if(id==R.id.BMI)
                {
                    Toast.makeText(MainActivity.this, "BMI", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,BMI.class));
                }
                else if (id==R.id.wc)
                {
                    Toast.makeText(MainActivity.this, "Weather Forecast", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,WeatherForecast.class));

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
                else{
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