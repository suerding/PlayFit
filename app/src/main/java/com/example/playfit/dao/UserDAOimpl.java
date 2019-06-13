/*
created by sknobla
finalized by suerding
 */
package com.example.playfit.dao;

import android.util.Log;

import com.example.playfit.dto.UserDTO;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOimpl implements UserDAO {

    private List<UserDTO> users = new ArrayList<>();

    public void add(UserDTO user)
    {
        users.add(user);
    }
    public void createUser() {

        UserDTO user = new UserDTO();


        //Add Basti - 0
        user.setUserID("1");
        user.setUserName("Basti");
        user.setName("Basti Knoblauch");
        user.setUserPoints(5);
        user.setUserEmail("sknobla2@smail.uni-koeln.de");
        user.setPassword("user123");
        users.add(user);
        user = new UserDTO();


        //Add SimonUE - 1
        user.setUserID("2");
        user.setUserName("SU");
        user.setName("Simon Uerdingen");
        user.setUserPoints(15);
        user.setUserEmail("suerding@smail.uni-koeln.de");
        user.setPassword("Simon123");
        users.add(user);
        user = new UserDTO();


        //Add Fritz - 2
        user.setUserID("3");
        user.setUserName("f");
        user.setName("Fritz Haedric");
        user.setUserPoints(15);
        user.setUserEmail("fhaedric@smail.uni-koeln.de");
        user.setPassword("f1");
        users.add(user);
        user = new UserDTO();

        //Add SimonFM - 3
        user.setUserID("4");
        user.setUserName("s");
        user.setName("Simon FM");
        user.setUserPoints(25);
        user.setUserEmail("simonfm@smail.uni-koeln.de");
        user.setPassword("s1");
        users.add(user);
        user = new UserDTO();

        //Add Imke - 4
        user.setUserID("5");
        user.setUserName("Imke");
        user.setName("Imke");
        user.setUserPoints(20);
        user.setUserEmail("imke@smail.uni-koeln.de");
        user.setPassword("i1");
        users.add(user);
        user = new UserDTO();

        //Add FriendsTest_1 - 5
        user.setUserID("8");
        user.setUserName("Tester_1");
        user.setName("Tester_1");
        user.setUserPoints(44);
        user.setUserEmail("tester1@smail.uni-koeln.de");
        user.setPassword("f1");
        users.add(user);
        user = new UserDTO();

        //Add FriendsTest_2 - 6
        user.setUserID("9");
        user.setUserName("Tester_2");
        user.setName("Tester_2");
        user.setUserPoints(33);
        user.setUserEmail("tester2@smail.uni-koeln.de");
        user.setPassword("f1");
        users.add(user);
        user = new UserDTO();


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
          user.setTotalPoints(Integer.parseInt(xmlUsers[5]));
          user.setUserEmail(xmlUsers[4]);
          user.setPassword(xmlUsers[3]);
          user.setFriendCount(Integer.parseInt(xmlUsers[7]));
          users.add(user);
          Log.d("Knobie", user.getUserEmail());

    }

// manuelle id
    public UserDTO getUserbyID(String userID) {
        UserDTO user = new UserDTO();
        this.createUser();
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

        UserDAOimpl tempusers = new UserDAOimpl();


        UserDTO user = new UserDTO();
        String[] tempfriends = xmlusers[8].toString().split(";");
        for(int i = 0; i < tempfriends.length; i++){
            Integer tempint = Integer.parseInt(tempfriends[i]);
            tempusers.add(getUserbyID(tempfriends[i]));
            Log.d("USER HINZU:", tempusers.list().get(i).getUserName());
            Log.d("ZU USER:", users.get(counter).getUserName());


        }


        // Verstehe nicht warum es in der folgene Zeile einen fehler gibt
        //users.get(counter).setFriends(tempusers.list());



    }


}