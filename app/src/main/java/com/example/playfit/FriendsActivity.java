/*
created by suerding
expanded by fhadric
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.playfit.dao.FriendsDAOimpl;
import com.example.playfit.dao.UserDAO;
import com.example.playfit.dao.UserDAOimpl;
import com.example.playfit.data.Session;
import com.example.playfit.dto.UserDTO;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.example.playfit.LoginActivity.USERNAME;

public class FriendsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button friendsprofileButton;
    private Session session = new Session();
    private UserDAOimpl users = new UserDAOimpl();
    SharedPreferences sharedPreferences;
    SharedPreferences sharedUsers;
    public static final String FRIENDSNAME = "name";
    private ListView friendsListView;
    private ArrayAdapter<String> friendsadapter;
    private ArrayList<String> friendsarrayList;
    private UserDTO loggedinUser;
    private  NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //sessionhandling - created by suerding
        sessionHandling();

        //friendButtons();
        friendsList();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Nav_view header - created by suerding
        navViewHeader();


        //friends list listener
        friendsListener();

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


    private void navViewHeader(){
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.nav_header_title);
        navUsername.setText("Hi " + session.getSession().getUserName());
        TextView emailTextview = (TextView) headerView.findViewById(R.id.emailText);
        emailTextview.setText(session.getSession().getUserEmail());

        ImageView profileImage = (ImageView) headerView.findViewById(R.id.profileImage);
        String resName = "@drawable/" + session.getSession().getUserName();
        int resID = getResources().getIdentifier(resName, null, this.getPackageName());
        profileImage.setImageResource(resID);
    }

    private void setDetails(String selectedItem) {
        Log.d("selectedItem", selectedItem);
        sharedPreferences = getSharedPreferences(FRIENDSNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FRIENDSNAME, selectedItem);
        editor.commit();
    }

    //created by suerding
    private void friendsList() {
        friendsListView = (ListView) findViewById(R.id.friendsList);
        friendsarrayList = new ArrayList<String>();
        friendsadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, friendsarrayList);
        Log.d("Laenge", String.valueOf(session.getSession().getFriends().length));
        for(int i = 0; i < session.getSession().getFriends().length; i++){
            UserDTO tempUser = users.getUserbyID(String.valueOf(session.getSession().getFriends()[i]));
            friendsarrayList.add(tempUser.getUserName());
            friendsadapter.notifyDataSetChanged();
        }

        friendsListView.setAdapter(friendsadapter);
    }

    private void friendsListener(){
        friendsListView = (ListView) findViewById(R.id.friendsList);
        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                setDetails(selectedItem);
                finish();
                Intent friendsintent = new Intent(FriendsActivity.this, FriendsDetailActivity.class);
                startActivity(friendsintent);
            }
        });
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
        getMenuInflater().inflate(R.menu.friends, menu);
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
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            finish();
            Log.d("navHome","Nav_home");
            Intent homeIntent = new Intent(FriendsActivity.this, MainActivity.class);
            startActivity(homeIntent);
        } else if (id == R.id.nav_profile) {
            finish();
            Log.d("navProfile","Nav_profile");
            Intent profileIntent = new Intent(FriendsActivity.this, ProfileActvity.class);
            startActivity(profileIntent);
            return true;
        } else if (id == R.id.nav_friends) {
            finish();
            Intent friendsIntent = new Intent(FriendsActivity.this, FriendsActivity.class);
            startActivity(friendsIntent);
        } else if (id == R.id.nav_maps) {
            finish();
            Intent mapsIntent = new Intent(FriendsActivity.this, MapsActivity.class);
            startActivity(mapsIntent);
        } else if (id == R.id.nav_scan) {
            finish();
            Intent scanIntent = new Intent(FriendsActivity.this, ScanActivity.class);
            startActivity(scanIntent);
        } else if (id == R.id.nav_logout) {     //hardlogout -- reboot
            Intent mStartActivity = new Intent(FriendsActivity.this, MainActivity.class);
            int mPendingIntentId = 123456;
            PendingIntent mPendingIntent = PendingIntent.getActivity(FriendsActivity.this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager mgr = (AlarmManager)FriendsActivity.this.getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
            System.exit(0);
        } else if (id == R.id.nav_settings) {
            finish();
            Intent settingsIntent = new Intent(FriendsActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        }

        DrawerLayout drawer = findViewById(R.id.activity_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

