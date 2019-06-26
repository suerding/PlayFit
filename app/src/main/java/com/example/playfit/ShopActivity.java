/* created by fhaedric
*
* */
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.playfit.dao.UserDAOimpl;
import com.example.playfit.data.Session;
import com.example.playfit.dto.UserDTO;
import com.google.gson.Gson;

public class ShopActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Session session = new Session();
    private UserDAOimpl users = new UserDAOimpl();
    private UserDTO loggedinUser = new UserDTO();
    private  NavigationView navigationView;
    SharedPreferences sharedUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        Toolbar toolbar = findViewById(R.id.toolbar);

        //sessionhandling - created by suerding
        sessionHandling();

        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.activity_shop);
        navigationView = findViewById(R.id.nav_profile);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Nav_view header - created by suerding
        navViewHeader();

        TextView points = findViewById(R.id.pointsText);
        points.setText(String.valueOf(session.getSession().getUserPoints()));
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
        Log.d("benutzer", session.getSession().getUserName());
    }


    /*
    private void profiledetails(){
        //picture
        ImageView imageView = findViewById(R.id.detailImageProfile);
        String imageName ="@drawable/" + session.getSession().getUserName();
        int imageID = getResources().getIdentifier(imageName, null, this.getPackageName());
        Log.d("drawableID", imageName);
        imageView.setImageResource(imageID);

        //username
        TextView username = findViewById(R.id.usernameOfUser);
        username.setText(session.getSession().getUserName());

        //Email
        TextView eMail = findViewById(R.id.eMail);
        eMail.setText(session.getSession().getUserEmail());

        //Name
        TextView fullname = findViewById(R.id.fullName);
        fullname.setText(session.getSession().getName());

        //Points
        TextView pointsText = findViewById(R.id.pointsText);
        // pointsText.setText(session.getSession().getUserPoints());

        //level - berechnet aus Total Points
        TextView level = findViewById(R.id.levelOfUser);
        // Log.d("totalpoints", users.getLevel(session.getSession()));
        level.setText(String.valueOf(users.getLevel(session.getSession())));

    }
    */

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

    @Override
    public void onBackPressed() {
        finish();
        Intent back = new Intent(ShopActivity.this, MainActivity.class);
        startActivity(back);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_actvity, menu);
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
            Intent settingsIntent = new Intent(ShopActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //created by suerding - Navigatorlogik
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            finish();
            Log.d("navHome","Nav_home");
            Intent homeIntent = new Intent(ShopActivity.this, MainActivity.class);
            startActivity(homeIntent);
        } else if (id == R.id.nav_profile) {
            finish();
            Log.d("navProfile","Nav_profile");
            Intent profileIntent = new Intent(ShopActivity.this, ProfileActvity.class);
            startActivity(profileIntent);
            return true;
        } else if (id == R.id.nav_friends) {
            finish();
            Intent friendsIntent = new Intent(ShopActivity.this, FriendsActivity.class);
            startActivity(friendsIntent);
        } else if (id == R.id.nav_maps) {
            finish();
            Intent mapsIntent = new Intent(ShopActivity.this, MapsActivity.class);
            startActivity(mapsIntent);
        } else if (id == R.id.nav_scan) {
            finish();
            Intent scanIntent = new Intent(ShopActivity.this, ScanActivity.class);
            startActivity(scanIntent);
        } else if (id == R.id.nav_logout) {
            //hardlogout -- reboot
            Intent mStartActivity = new Intent(ShopActivity.this, MainActivity.class);
            int mPendingIntentId = 123456;
            PendingIntent mPendingIntent = PendingIntent.getActivity(ShopActivity.this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager mgr = (AlarmManager)ShopActivity.this.getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
            System.exit(0);
        } else if (id == R.id.nav_settings) {
            finish();
            Intent settingsIntent = new Intent(ShopActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);

        }
        /*
        DrawerLayout drawer = findViewById(R.id.activity_main);
        drawer.closeDrawer(GravityCompat.START);
        */
        return true;

    }
}

