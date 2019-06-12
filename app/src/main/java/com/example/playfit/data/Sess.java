package com.example.playfit.data;

import com.example.playfit.dao.UserDAOimpl;
import com.example.playfit.dto.UserDTO;

public interface Sess {
     void create(String username, UserDAOimpl users);
    void close();
    UserDTO getSession();

}
