/*
created by sknobla
finalized by suerding
 */
package com.example.playfit.dto;

import java.util.List;

public class UserDTO {
    private String userID;
    private int userPoints;
    private int totalPoints;
    private String userName;
    private int friendCount;
    private String userEmail;
    private String name;
    private List<GymDTO> gyms;
    private List<ProductDTO> produkte;
    private List<ShopDTO> shops;
    private List<RewardDTO> rewards;

    public List<UserDTO> getFriends() {
        return friends;
    }

    public void setFriends(List<UserDTO> friends) {
        this.friends = friends;
    }

    private List<UserDTO> friends;

    public String getPassword() {
        return password;
    }
    public String getName(){return name; }
    public void setName(String name){this.name = name;}

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;


    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(int userPoints) {
        this.userPoints = userPoints;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(int friendCount) {
        this.friendCount = friendCount;
    }



    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }



}
