/*
created by suerding
finalized by sknobla
 */
package com.example.playfit;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.playfit.dao.UserDAOimpl;
import com.example.playfit.data.Points;
import com.example.playfit.data.Session;
import com.example.playfit.dto.UserDTO;
import com.google.gson.Gson;


import static com.example.playfit.LoginActivity.USERNAME;
import static com.example.playfit.LoginActivity.USERS;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button shopButton;
    private Button profileButton;
    private Button friendsButton;
    private Button mapsButton;
    private Button scanButton;
    private Session session = new Session();
    private UserDAOimpl users = new UserDAOimpl();
    private UserDTO loggedinUser = new UserDTO();
    private NavigationView navigationView;
    SharedPreferences sharedUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //sessionhandling - created by suerding
        sessionHandling();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dashboardButtons();

        //created by sknobla & suerding
        DrawerLayout drawer = findViewById(R.id.activity_main);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        //Nav_view header - created by suerding
        navViewHeader();

        TextView points = findViewById(R.id.points);
        points.setText(String.valueOf(session.getSession().getUserPoints()));


        int toastcounter = 0;
        if(session.getSession().getUserName()!= "simonu" && toastcounter == 0){

        toastcounter = 1;

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Context context = getApplicationContext();
                CharSequence text = "Simon ist nun ein Fitnessblogger!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }, 5000);
        }




    }

    private void sessionHandling(){
        sharedUsers = getSharedPreferences(LoginActivity.USERS, Context.MODE_PRIVATE); // users werden aus XML Read übergeben
        Gson gsonUsers = new Gson();
        String jsonUsers = sharedUsers.getString("Users", "");
        users = gsonUsers.fromJson(jsonUsers, UserDAOimpl.class);
        SharedPreferences sharedSession = getSharedPreferences(LoginActivity.SESSION, Context.MODE_PRIVATE); // eigentliche Session
        SharedPreferences.Editor editor = sharedSession.edit();
        Gson gsonUser = new Gson();
        String jsonUser = sharedSession.getString("SessionUser", "");
        loggedinUser = gsonUser.fromJson(jsonUser, UserDTO.class);
        session.create(loggedinUser);
        Log.d("benutzer", loggedinUser.getUserName());
    }

    private void navViewHeader(){
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nav_header_title);
        navUsername.setText("Hi " + session.getSession().getUserName());
        TextView emailTextview = headerView.findViewById(R.id.emailText);
        emailTextview.setText(session.getSession().getUserEmail());

        ImageView profileImage = headerView.findViewById(R.id.profileImage);
        String resName = "@drawable/"+session.getSession().getUserName();
        int resID = getResources().getIdentifier(resName,null, this.getPackageName());
        profileImage.setImageResource(resID);
    }

    private void dashboardButtons() {
        //created by suerding - profileButton
        profileButton = findViewById(R.id.buttonProfile);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();                                      // Achtung: ProfileActvity ist falsch geschrieben
                Intent profileIntent = new Intent(MainActivity.this,ProfileActvity.class );
                startActivity(profileIntent);
            }
        });

        //created by suerding - friendsButton
        friendsButton = findViewById(R.id.buttonFriends);
        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent friendsIntent = new Intent(MainActivity.this,FriendsActivity.class );
                startActivity(friendsIntent);
            }
        });

        //created by suerding - mapsButton
        mapsButton = findViewById(R.id.buttonMaps);
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent mapsIntent = new Intent(MainActivity.this,MapsActivity.class );
                startActivity(mapsIntent);
            }
        });

        //created by sfriedr8 & fhaedric
        scanButton = findViewById(R.id.buttonScan);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent scanIntent = new Intent(MainActivity.this,ScanActivity.class );
                startActivity(scanIntent);
            }
        });

       //created by fhaedric buttonShop
        shopButton = findViewById(R.id.buttonShop);
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent shopIntent = new Intent(MainActivity.this,ShopActivity.class );
                startActivity(shopIntent);
           }
        });

    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onBackPressed() {
      finish();
      Intent back = new Intent(MainActivity.this, LoginActivity.class);
      session.close();
      startActivity(back);


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

            finish();
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
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
        //für shop
       // else if (id == R.id.nav_scan) {
         //   finish();
          //  Intent shopIntent = new Intent(MainActivity.this, ShopActivity.class);
          //  startActivity(shopIntent);

        }  else if (id == R.id.nav_settings) {
            finish();
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);

        }else if (id== R.id.nav_logout){
            //hardlogout -- reboot
            Intent mStartActivity = new Intent(MainActivity.this, MainActivity.class);
            int mPendingIntentId = 123456;
            PendingIntent mPendingIntent = PendingIntent.getActivity(MainActivity.this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager mgr = (AlarmManager)MainActivity.this.getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
            System.exit(0);

        }
        /*
        DrawerLayout drawer = findViewById(R.id.activity_main);
        drawer.closeDrawer(GravityCompat.START);
        */
        return true;
    }
}
