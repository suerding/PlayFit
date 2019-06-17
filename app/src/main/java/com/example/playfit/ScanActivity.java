package com.example.playfit;

import android.content.Context;
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

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view

        setContentView(mScannerView);// Set the scanner view as the content view

        //sessionhandling - created by suerding
       sessionHandling();

        points.processPoints("Gym_Sportfabrik_20190611_7",  session.getSession() );


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
        Log.v("tag", rawResult.getText());// Prints scan results
        SharedPreferences.Editor editorSession = sharedSession.edit();

        //Punkte müssen der Session gutgeschriieben werden
        points.processPoints(rawResult.getText(), session.getSession());
        // Log.v("tag", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        //MainActivity.tvresult.setText(rawResult.getText());
        onBackPressed();

        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }
}
