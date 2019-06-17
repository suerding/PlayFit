/*
created by suerding, sknobla2
 */
package com.example.playfit.data;

import android.util.Log;

import com.example.playfit.dao.UserDAOimpl;
import com.example.playfit.dto.UserDTO;
import com.google.zxing.Result;

// diese Klasse wird auch als Interface zwischen dem Scanner und dem User dienen
public class Points {
    private UserDAOimpl users = new UserDAOimpl();
    private Session session = new Session();
    private UserDTO user = new UserDTO();
    private Result scanResult;


    public void processPoints(String input, UserDTO user){
        String location = identifyLocation(input);
        int points = identifyPoints(input);
        users.grantPoints(points,user);


    }

    // Gym_Sportfabrik_20190611_7
    private String identifyLocation(String input){
        return input.split("_")[0];
    }

    private int identifyPoints(String input){
        return Integer.parseInt(input.split("_")[3]);
    }



}

