/*
created by sknobla
finalized by suerding
 */
package com.example.playfit.dto;

public class GymDTO {
    private int gymID;

    public int getGymID() {
        return gymID;
    }

    public void setGymID(int gymID) {
        this.gymID = gymID;
    }

    public int getGymName() {
        return gymName;
    }

    public void setGymName(int gymName) {
        this.gymName = gymName;
    }

    public int getPointRate() {
        return pointRate;
    }

    public void setPointRate(int pointRate) {
        this.pointRate = pointRate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private int gymName;
    private int pointRate;
    private String location;

}
