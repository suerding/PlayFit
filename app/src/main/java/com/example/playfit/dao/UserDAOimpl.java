package com.example.playfit.dao;

import com.example.playfit.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class UserDAOimpl implements UserDAO {

    private static List<UserDTO> users = new ArrayList<>();


    static {

        UserDTO user = new UserDTO();
        //Add User
        user.setUserID(1);
        user.setUserName("Basti");
        user.setUserPoints(0);

        users.add(user);

    }

    @Override
    public List<UserDTO> list() {
        return null;
    }
}
