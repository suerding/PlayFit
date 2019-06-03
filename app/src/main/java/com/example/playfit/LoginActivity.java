package com.example.playfit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.playfit.dao.UserDAOimpl;
import com.example.playfit.dto.UserDTO;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private String username;
    private String password;
    private UserDAOimpl users = new UserDAOimpl();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        users.createUser();

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText usernametemp = findViewById(R.id.username);
                EditText passwordtemp = findViewById(R.id.password);
                username = usernametemp.getText().toString();
                Log.d("USERNAME",username);
                password = passwordtemp.getText().toString();
                if(usernameCheck(username, password)){
                    Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(loginIntent);
                }else {
                    Context context = getApplicationContext();
                    CharSequence text = "invalid credientials";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    Intent loginIntent = new Intent(LoginActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                }

            }
        });
    }

    public boolean usernameCheck(String username, String password){
        int userID = users.getUserIdbyName(username);
        Log.d("userID", "userID " + userID);
        if(userID != 9999 && username.equals(users.list().get(userID).getUserName())){
            return true;
        }else
            return false;
    }
}
