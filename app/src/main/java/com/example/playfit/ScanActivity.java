package com.example.playfit;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.playfit.dao.UserDAOimpl;
import com.example.playfit.data.Points;
import com.example.playfit.data.Session;
import com.example.playfit.dto.UserDTO;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.example.playfit.LoginActivity.USERNAME;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;
    private Points points = new Points();
    private Session session = new Session();
    private UserDAOimpl users = new UserDAOimpl();

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view

        setContentView(mScannerView);// Set the scanner view as the content view
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SESSION, Context.MODE_PRIVATE);
        session.create(sharedPreferences.getString(USERNAME,"Default"), users);
        Log.d("benutzer", session.getSession().getUserName());
        points.processPoints("Gym_Sportfabrik_20190611_7",  session.getSession() );
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
        // Do something with the result here
        Log.v("tag", rawResult.getText());// Prints scan results
        points.processPoints(rawResult.getText(), session.getSession());
        // Log.v("tag", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        //MainActivity.tvresult.setText(rawResult.getText());
        onBackPressed();

        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }
}
