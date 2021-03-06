package com.example.playfit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.playfit.dao.UserDAOimpl;
import com.example.playfit.data.Points;
import com.example.playfit.data.Session;
import com.example.playfit.dto.UserDTO;
import com.google.gson.Gson;
import com.google.zxing.Result;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.example.playfit.LoginActivity.USERNAME;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;
    private Points points = new Points();
    private Session session = new Session();
    private UserDAOimpl users = new UserDAOimpl();
    private UserDTO loggedinUser = new UserDTO();
    SharedPreferences sharedUsers;
    SharedPreferences sharedSession;
    private SharedPreferences.Editor sessionEditor;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view

        setContentView(mScannerView);// Set the scanner view as the content view

        //sessionhandling - created by suerding
       sessionHandling();




    }
    @Override
    public void onBackPressed() {
        finish();
        Intent back = new Intent(ScanActivity.this, MainActivity.class);
        startActivity(back);
    }


    private void sessionHandling(){
        sharedUsers = getSharedPreferences(LoginActivity.USERS, Context.MODE_PRIVATE); // users werden aus XML Read übergeben
        Gson gsonUsers = new Gson();
        String jsonUsers = sharedUsers.getString("Users", "");
        users = gsonUsers.fromJson(jsonUsers, UserDAOimpl.class);
        sharedSession = getSharedPreferences(LoginActivity.SESSION, Context.MODE_PRIVATE); // eigentliche Session
        sessionEditor = sharedSession.edit();
        Gson gsonUser = new Gson();
        String jsonUser = sharedSession.getString("SessionUser", "");
        loggedinUser = gsonUser.fromJson(jsonUser, UserDTO.class);
        session.create(loggedinUser);
        Log.d("benutzer", session.getSession().getUserName());
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    /*
    Barcodes werden nach folgendem Muster erstellt: Gym_Sportfabrik_20190611_7
     */
    public void handleResult(Result rawResult) {
        Log.v("tag", rawResult.getText());// Prints scan results;

        //Punkte werden der Session dem User gutgeschrieben - created by suerding
        if(rawResult !=null) {
            sessionEditor = sharedSession.edit();
            sessionEditor.clear();
            session.getSession().setTotalPoints(users.calcLevel(session.getSession(), rawResult.getText()));
            Gson gsonSession = new Gson();
            String jsonSession = gsonSession.toJson(session.getSession());
            sessionEditor.putString("SessionUser", jsonSession);
            sessionEditor.commit();
        }
        //ggf müssen noch elemmente für einen Toas mitgegeben werden
        Context context = getApplicationContext();
        int newPoints = Integer.parseInt(rawResult.getText().split("_")[3]);
        CharSequence text = "+ " + newPoints + " Punkte";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        finish();
        Intent profileIntent = new Intent(ScanActivity.this, ProfileActvity.class);
        startActivity(profileIntent);
        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }
}
