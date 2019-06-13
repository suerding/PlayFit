package com.example.playfit.data;

import com.example.playfit.dao.UserDAOimpl;
import com.example.playfit.dto.UserDTO;

public interface Sess {
     void create(UserDTO loggedInUser);
    void close();
    UserDTO getSession();

}
