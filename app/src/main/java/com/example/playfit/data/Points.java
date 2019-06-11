/*
created by suerding
 */
package com.example.playfit.data;

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
        switch(input){
            case "Gym":
                users.grantPoints(5, user);
        }
    }
    }

