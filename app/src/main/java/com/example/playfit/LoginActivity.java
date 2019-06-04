/*
created by sknobla & suerding
finalized by suerding & sknobla
 */
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
import com.example.playfit.data.Session;

public class LoginActivity extends AppCompatActivity {
    Button camera;
    private Button loginButton;
    private Button signupButton;
    private String username;
    private String password;
    private Session session = new Session();
    private UserDAOimpl users = new UserDAOimpl();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);
        users.createUser();


        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "invalid credientials";
                int duration = Toast.LENGTH_SHORT;
                EditText usernametemp = findViewById(R.id.username);
                EditText passwordtemp = findViewById(R.id.password);
                username = usernametemp.getText().toString();
                Log.d("USERNAME", username);
                password = passwordtemp.getText().toString();
                ((EditText) findViewById(R.id.username)).getText().clear();
                ((EditText) findViewById(R.id.password)).getText().clear();
                if (usernameCheck(username)) {
                    if(passwordCheck(username, password)) {
                        session.create(username);
                        Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(loginIntent);
                    } else {
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    }
                } else {


                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    Intent loginIntent = new Intent(LoginActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                }
            }});
        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            EditText usernametemp = findViewById(R.id.username);
            EditText passwordtemp = findViewById(R.id.password);
            username = usernametemp.getText().toString();
            password = passwordtemp.getText().toString();
            users = users.newUser(username, password, users);
            Log.d("istUser",users.list().get(3).getUserName());
            Intent loginIntent = new Intent(LoginActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            }
        });




    }

    public boolean usernameCheck(String username){
        int userID = users.getUserIdbyName(username);
        Log.d("userID", "userID " + userID);
        if(userID != 9999 && username.equals(users.list().get(userID).getUserName())){
            return true;
        }else
            return false;
    }

    public boolean passwordCheck(String username, String password){
        int userID = users.getUserIdbyName(username);
        Log.d("userID", "userID " + userID);
        if(userID != 9999 && password.equals(users.list().get(userID).getPassword())){
            return true;
        }else
            return false;
    }
}
