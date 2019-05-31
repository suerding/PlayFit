package com.example.playfit.dto;

public class RewardDTO {
    private int rewardID;

    public int getRewardID() {
        return rewardID;
    }

    public void setRewardID(int rewardID) {
        this.rewardID = rewardID;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public String getRewardDescription() {
        return rewardDescription;
    }

    public void setRewardDescription(String rewardDescription) {
        this.rewardDescription = rewardDescription;
    }

    public int getPointRate() {
        return pointRate;
    }

    public void setPointRate(int pointRate) {
        this.pointRate = pointRate;
    }

    private String rewardName;
    private String rewardDescription;
    private int pointRate;
}
