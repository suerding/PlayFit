/*
created by sknobla
finalized by suerding
 */
package com.example.playfit.dao;

import android.util.Log;

import com.example.playfit.data.Session;
import com.example.playfit.dto.UserDTO;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOimpl implements UserDAO {

    private List<UserDTO> users = new ArrayList<>();

    public void add(UserDTO user)
    {
        users.add(user);
    }

    @Override
    public List<UserDTO> list() {
        return this.users;
    }

    //created by suerding & sknobla
    //reale userid im xml
    @Override
    public Integer getUserIdbyName(String username) {
        //this.createUser();
        for (int i = 0; i < list().size(); i++) {
            if (username.equals(list().get(i).getUserName())) {
                return i;
            }

        }
        return 9999;
    }

    public UserDAOimpl newUser(String username, String password, UserDAOimpl userlist){
        UserDTO nUser = new UserDTO();
        nUser.setUserName(username);
        nUser.setPassword(password);
        userlist.list().add(nUser);
        Log.d("newUser", users.get(3).getUserName());
        return userlist;
    }
    public void readUserXML(String[] xmlUsers)
    {
        UserDTO user = new UserDTO();
          //Add Basti - 0
          user.setUserID(xmlUsers[0]);
          user.setUserName(xmlUsers[1]);
          user.setName(xmlUsers[2]);
          user.setUserPoints(Integer.parseInt(xmlUsers[5]));
          user.setTotalPoints(Integer.parseInt(xmlUsers[6]));
          user.setUserEmail(xmlUsers[4]);
          user.setPassword(xmlUsers[3]);
          user.setFriendCount(Integer.parseInt(xmlUsers[7]));
          users.add(user);
          Log.d("Knobie", user.getUserEmail());

    }

// manuelle id
    public UserDTO getUserbyID(String userID) {
        UserDTO user = new UserDTO();
        for (int i = 0; i < list().size(); i++) {
            if (userID.equals(list().get(i).getUserID())) {
                user = list().get(i);
                return user;
            }

        }
        return null;
    }

    public void grantPoints(int points, UserDTO user){
        int userPoints = user.getUserPoints();
        userPoints = userPoints + points;
        user.setUserPoints(userPoints);
        user.setTotalPoints(userPoints);
        users.get(getUserIdbyName(user.getUserName())).setUserPoints(userPoints);
        users.get(getUserIdbyName(user.getUserName())).setTotalPoints(userPoints);
    }
    public void revokePoints(int points, UserDTO user){
        int userPoints = user.getUserPoints();
        userPoints = userPoints - points;
        user.setUserPoints(userPoints);
        users.get(getUserIdbyName(user.getUserName())).setUserPoints(userPoints);
    }

    public void ausgabe(){
        for (int i = 0; i < users.size(); i++)
        {
            Log.d("Nutzername", users.get(i).getUserName());
            Log.d("Email", users.get(i).getUserEmail());
            Log.d("Passwort", users.get(i).getPassword());

        }
    }

    public void friendships(String[] xmlusers, int counter)
    {
        Log.d("CALL","CALL");
        String[] tempfriends = xmlusers[8].split(";");
        Log.d("CALLFriends", tempfriends[0] + tempfriends[1] +tempfriends[2]);
        Log.d("CALLFriends", String.valueOf(tempfriends.length));
        Integer[] temp = new Integer[tempfriends.length];

        for(int i = 0; i < tempfriends.length; i++){

            temp[i] = Integer.parseInt(tempfriends[i]);

            //Log.d("ZU_USER:", users.get(counter).getUserName());
            Log.d("USER_CURRENT", this.getUserbyID(String.valueOf(temp[i])).getUserName());

            //ich glaube das Problem liegt in dem Users objekt (es kann sein, dass das nicht richtig referenziert wird, denn in der folgenden Zeile entsteht der Fehler


        }
        users.get(counter).setFriends(temp);

    }

    public int getLevel(UserDTO user){
        Log.d("totalpoints", String.valueOf(user.getTotalPoints()));
        if(user.getTotalPoints() < 50){
            return 0;
        }else if(user.getTotalPoints() >= 50 && user.getTotalPoints() <200){
            return 1;
        }else if(user.getTotalPoints() >= 200){
            return 2;
        }
        return 3;
    }
    public int calcLevel(UserDTO user , String input){
        int newPoints =  Integer.parseInt(input.split("_")[3]);
        user.setUserPoints(user.getUserPoints()+newPoints);
        int totalPoints = user.getTotalPoints() + newPoints;

        return totalPoints;
    }



}