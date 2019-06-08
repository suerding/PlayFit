/*
created by suerding
expanded by fhadric
finalized by sknobla
 */
package com.example.playfit;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.playfit.dao.FriendsDAOimpl;
import com.example.playfit.dao.UserDAOimpl;
import com.example.playfit.data.Session;
import com.example.playfit.dto.UserDTO;

import java.util.ArrayList;

import static com.example.playfit.LoginActivity.USERNAME;

public class FriendsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button friendsprofileButton;
    private Session session = new Session();
    private UserDAOimpl users = new UserDAOimpl();
    SharedPreferences sharedPreferences;
    public static final String FRIENDSNAME = "name";
    private ListView friendsListView;
    private ArrayAdapter<String> friendsadapter;
    private ArrayList<String> friendsarrayList;
    private FriendsDAOimpl friends = new FriendsDAOimpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //sessionhandling - created by suerding
        users.createUser();
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        session.create(sharedPreferences.getString(USERNAME,"Default"));

        //friendButtons();
        friendsList();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Nav_view header - created by suerding
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.nav_header_title);
        navUsername.setText("Hi " + session.getSession().getUserName());
        TextView emailTextview = (TextView) headerView.findViewById(R.id.emailText);
        emailTextview.setText(session.getSession().getUserEmail());

        //friends list listener
        friendsListView = (ListView) findViewById(R.id.friendsList);
        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                setDetails(selectedItem);
                Intent friendsintent = new Intent(FriendsActivity.this, FriendsDetailActivity.class);
                startActivity(friendsintent);
            }
        });

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
        friends.createforuser(session.getSession().getUserID());
        friendsListView = (ListView) findViewById(R.id.friendsList);
        friendsarrayList = new ArrayList<String>();
        friendsadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, friendsarrayList);

        for(int i = 0; i < friends.list().size(); i++){
            friendsarrayList.add(friends.list().get(i).getUserName());
            friendsadapter.notifyDataSetChanged();
        }

        friendsListView.setAdapter(friendsadapter);
    }

    /*
    private void friendButtons() {
        //created by fhaedric - friend1button
        friendsprofileButton = findViewById(R.id.freund1);
        //zu testzwecken erstmal ID = 0 - created by suerding
        UserDTO friend = users.list().get(0);
        String friendsID = friend.getUserID();
        sharedPreferences = getSharedPreferences(FRIENDSID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FRIENDSID, friendsID);
        editor.commit();
        friendsprofileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(FriendsActivity.this,FriendsDetailActivity.class );
                startActivity(detailIntent);
            }
        });

        //created by fhaedric - friend2button
        friendsprofileButton = findViewById(R.id.freund2);
        friendsprofileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(FriendsActivity.this,ProfileActvity.class );
                startActivity(profileIntent);
            }
        });

        //created by fhaedric - friend3button
        friendsprofileButton = findViewById(R.id.freund3);
        friendsprofileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(FriendsActivity.this,ProfileActvity.class );
                startActivity(profileIntent);
            }
        });

        //created by fhaedric - friend4button
        friendsprofileButton = findViewById(R.id.freund4);
        friendsprofileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(FriendsActivity.this,ProfileActvity.class );
                startActivity(profileIntent);
            }
        });

    }
    */


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
        } else if (id == R.id.nav_logout) {
            finish();
            Log.d("navSocial","Nav_Social");
        } else if (id == R.id.nav_settings) {
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.activity_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

