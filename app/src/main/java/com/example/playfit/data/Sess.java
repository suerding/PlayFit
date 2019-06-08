package com.example.playfit.data;

import com.example.playfit.dto.UserDTO;

public interface Sess {
     void create(String username);
    void close();
    UserDTO getSession();

}
