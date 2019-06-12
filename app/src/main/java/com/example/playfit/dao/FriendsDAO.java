package com.example.playfit.dao;

import com.example.playfit.dto.UserDTO;

import java.util.List;

public interface FriendsDAO {
    List<UserDTO> list();
    UserDTO getFriendbyID(String userID, String sessionUserID, UserDAOimpl users);
    void add(String userID, String username);
}
