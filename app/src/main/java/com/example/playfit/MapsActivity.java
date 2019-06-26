/*
created by suerding, sknobla
finalized by
 */
package com.example.playfit;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
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
import android.widget.Toast;

import com.example.playfit.dao.UserDAOimpl;
import com.example.playfit.data.Session;
import com.example.playfit.dto.UserDTO;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import static com.example.playfit.LoginActivity.USERNAME;

public class MapsActivity extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private Session session = new Session();
    private UserDAOimpl users;
    private UserDTO loggedinUser;
    private NavigationView navigationView;
    SharedPreferences sharedUsers;

    GoogleMap map;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //map by simonFM
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();

        //sessionhandling by suerding
        sessionhandling();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Nav_view header - created by suerding
        navigationHeader();


    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude()
                    +""+currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment)
                            getSupportFragmentManager().findFragmentById(R.id.map);
                    supportMapFragment.getMapAsync(MapsActivity.this);
                }
            }
        });
    }


    private void setSupportActionBar(Toolbar toolbar) {
    }

    private void navigationHeader() {
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

    private void sessionhandling() {
        //sessionhandling - created by suerding
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

    @Override
    public void onBackPressed() {
        finish();
        Intent back = new Intent(MapsActivity.this, MainActivity.class);
        startActivity(back);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.maps, menu);
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
            Intent settingsIntent = new Intent(MapsActivity.this, SettingsActivity.class);
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
            Log.d("navHome","Nav_home");
            Intent homeIntent = new Intent(MapsActivity.this, MainActivity.class);
            startActivity(homeIntent);
        } else if (id == R.id.nav_profile) {
            finish();
            Log.d("navProfile","Nav_profile");
            Intent profileIntent = new Intent(MapsActivity.this, ProfileActvity.class);
            startActivity(profileIntent);
            return true;
        } else if (id == R.id.nav_friends) {
            finish();
            Intent friendsIntent = new Intent(MapsActivity.this, FriendsActivity.class);
            startActivity(friendsIntent);
        } else if (id == R.id.nav_maps) {
            finish();
            Intent mapsIntent = new Intent(MapsActivity.this, MapsActivity.class);
            startActivity(mapsIntent);
        } else if (id == R.id.nav_scan) {
            finish();
            Intent scanIntent = new Intent(MapsActivity.this, ScanActivity.class);
            startActivity(scanIntent);
        } else if (id == R.id.nav_logout) {
            //hardlogout -- reboot
            Intent mStartActivity = new Intent(MapsActivity.this, MainActivity.class);
            int mPendingIntentId = 123456;
            PendingIntent mPendingIntent = PendingIntent.getActivity(MapsActivity.this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager mgr = (AlarmManager)MapsActivity.this.getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
            System.exit(0);
        } else if (id == R.id.nav_settings) {
            finish();
            Intent settingsIntent = new Intent(MapsActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);

        }
        /*
        DrawerLayout drawer = findViewById(R.id.activity_main);
        drawer.closeDrawer(GravityCompat.START);
        */
        return true;

    }

    //created by SimonFM
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng Zollstock = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(Zollstock)
                .title("I am here");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(Zollstock));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Zollstock, 15));
        map.addMarker(markerOptions);
    }

    //created by SimonFM
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                break;
        }
    }
}
