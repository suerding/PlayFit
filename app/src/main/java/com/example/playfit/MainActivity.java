/*
created by suerding
finalized by sknobla
 */
package com.example.playfit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.playfit.data.Session;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button profileButton;
    private Button friendsButton;
    private Button mapsButton;
    private Button scanButton;
    public Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dashboardButtons();

        //created by sknobla & suerding
        DrawerLayout drawer = findViewById(R.id.activity_main);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    private void dashboardButtons() {
        //created by suerding - profileButton
        profileButton = findViewById(R.id.buttonProfile);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(MainActivity.this,ProfileActvity.class );
                startActivity(profileIntent);
            }
        });

        //created by suerding - friendsButton
        friendsButton = findViewById(R.id.buttonFriends);
        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent friendsIntent = new Intent(MainActivity.this,FriendsActivity.class );
                startActivity(friendsIntent);
            }
        });

        //created by suerding - mapsButton
        mapsButton = findViewById(R.id.buttonMaps);
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapsIntent = new Intent(MainActivity.this,MapsActivity.class );
                startActivity(mapsIntent);
            }
        });

        //created by suerding - scanButton
        scanButton = findViewById(R.id.buttonScan);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scanIntent = new Intent(MainActivity.this,ScanActivity.class );
                startActivity(scanIntent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.activity_main);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //created by suerding - Navigatorlogik
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            finish();
            Log.d("navHome","Nav_home");
           Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
           startActivity(homeIntent);
        } else if (id == R.id.nav_profile) {
            finish();
            Log.d("navProfile","Nav_profile");
            Intent profileIntent = new Intent(MainActivity.this, ProfileActvity.class);
            startActivity(profileIntent);
            return true;
        } else if (id == R.id.nav_friends) {
            finish();
            Intent friendsIntent = new Intent(MainActivity.this, FriendsActivity.class);
            startActivity(friendsIntent);
        } else if (id == R.id.nav_maps) {
            finish();
            Intent mapsIntent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(mapsIntent);
        } else if (id == R.id.nav_scan) {
            finish();
            Intent scanIntent = new Intent(MainActivity.this, ScanActivity.class);
            startActivity(scanIntent);
        } else if (id == R.id.nav_logout) {
            finish();
            Log.d("navSocial","Nav_Social");
        } else if (id == R.id.nav_settings) {

        }else if (id== R.id.nav_logout){
            session.close();
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.activity_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
