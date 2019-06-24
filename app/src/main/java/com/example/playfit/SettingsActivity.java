package com.example.playfit;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.playfit.dao.UserDAOimpl;
import com.example.playfit.data.Session;
import com.example.playfit.dto.UserDTO;
import com.google.gson.Gson;

public class SettingsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences sharedUsers;
    private SharedPreferences sharedPreferences;
    private UserDAOimpl users;
    private UserDTO loggedinUser;
    private Session session = new Session();
    private  NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Sessionhandling & NavViewer
        sessionHandling();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_settings);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navViewHeader();
        profiledetails();


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
// hier wollte ich die infos vom nutzer ziehen um sie in den Einstellungen anzeigen und ändern zu können
        private void profiledetails(){

        /*
        //username
        TextView username = findViewById(R.id.usernameOfUser);
        username.setText(session.getSession().getUserName());
        */

        //Email
        TextView eMail = findViewById(R.id.eMail);
        eMail.setText(session.getSession().getUserEmail());

        //Name
        TextView fullname = findViewById(R.id.fullName);
        fullname.setText(session.getSession().getName());


        /*
        //Points
        TextView pointsText = findViewById(R.id.pointsText);
        // pointsText.setText(session.getSession().getUserPoints());

        //level - berechnet aus Total Points
        TextView level = findViewById(R.id.levelOfUser);
        // Log.d("totalpoints", users.getLevel(session.getSession()));
        level.setText(String.valueOf(users.getLevel(session.getSession())));
        */

    }


    private void navViewHeader(){
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.nav_header_title);
        navUsername.setText("Hi " + session.getSession().getUserName());
        TextView emailTextview = (TextView) headerView.findViewById(R.id.emailText);
        emailTextview.setText(session.getSession().getUserEmail());

        ImageView profileImage = (ImageView) headerView.findViewById(R.id.profileImage);
        String resName = "@drawable/"+session.getSession().getUserName();
        int resID = getResources().getIdentifier(resName,null, this.getPackageName());
        profileImage.setImageResource(resID);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            finish();
            Log.d("navHome","Nav_home");
            Intent homeIntent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(homeIntent);
        } else if (id == R.id.nav_profile) {
            finish();
            Log.d("navProfile","Nav_profile");
            Intent profileIntent = new Intent(SettingsActivity.this, ProfileActvity.class);
            startActivity(profileIntent);
            return true;
        } else if (id == R.id.nav_friends) {
            finish();
            Intent friendsIntent = new Intent(SettingsActivity.this, FriendsActivity.class);
            startActivity(friendsIntent);
        } else if (id == R.id.nav_maps) {
            finish();
            Intent mapsIntent = new Intent(SettingsActivity.this, MapsActivity.class);
            startActivity(mapsIntent);
        } else if (id == R.id.nav_scan) {
            finish();
            Intent scanIntent = new Intent(SettingsActivity.this, ScanActivity.class);
            startActivity(scanIntent);
        } else if (id == R.id.nav_logout) {
            finish();
            Log.d("navSocial","Nav_Social");
        } else if (id == R.id.nav_settings) {
            finish();
            Intent settingsIntent = new Intent(SettingsActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);

        }else if (id== R.id.nav_logout){
            //hardlogout -- reboot
            Intent mStartActivity = new Intent(SettingsActivity.this, MainActivity.class);
            int mPendingIntentId = 123456;
            PendingIntent mPendingIntent = PendingIntent.getActivity(SettingsActivity.this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager mgr = (AlarmManager)SettingsActivity.this.getSystemService(Context.ALARM_SERVICE);
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
