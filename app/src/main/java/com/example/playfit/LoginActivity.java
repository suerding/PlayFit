/*
created by sknobla & suerding
finalized by suerding & sknobla
 */
package com.example.playfit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.playfit.dao.UserDAOimpl;
import com.example.playfit.data.Session;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private Button signupButton;
    private EditText usernameField;
    private EditText passwordField;
    private String username;
    private String password;
    public Session session = new Session();
    private UserDAOimpl users = new UserDAOimpl();
    SharedPreferences sharedSession;
    SharedPreferences sharedUsers;
    public static final String SESSION = "Session" ;
    public static final String USERNAME = "username" ;
    public static final String USERS = "users";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //XML Import
        Resources res = getResources();
        String[] test = res.getStringArray(R.array.MetadataUsers);
        int startint = R.array.MetadataUsers;
        int counter = startint + Integer.parseInt(test[0]);
        for (int i = startint+1; i <= counter; i++){
            String[] user = res.getStringArray(i);
            users.readUserXML(user);

        }
        sharedUsers = getSharedPreferences(USERS, Context.MODE_PRIVATE);
        SharedPreferences.Editor usersEditor = sharedUsers.edit();
        Gson gson = new Gson();
        String json = gson.toJson(users);
        usersEditor.putString("Users", json);
        usersEditor.commit();


        usernameField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);
        //users.createUser();
        sharedSession = getSharedPreferences(SESSION, Context.MODE_PRIVATE);

        //has to be edited - sollte das manuelle Löschen erübrigen
        usernameField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("entered", "entered");
                usernameField.getText().clear();
                passwordField.getText().clear();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "invalid credientials";
                int duration = Toast.LENGTH_SHORT;

                username = usernameField.getText().toString();
                Log.d("USERNAME", username);
                password = passwordField.getText().toString();
                ((EditText) findViewById(R.id.username)).getText().clear();
                ((EditText) findViewById(R.id.password)).getText().clear();
                if (usernameCheck(username)) {
                    if(passwordCheck(username, password)) {
                        SharedPreferences.Editor editorSession = sharedSession.edit();
                        editorSession.putString(USERNAME, username);
                        editorSession.commit();
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
        Log.d("username", "entered");
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
