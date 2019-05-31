package com.example.playfit.dto;

public class ShopDTO {
    private int shopID;

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopDecription() {
        return shopDecription;
    }

    public void setShopDecription(String shopDecription) {
        this.shopDecription = shopDecription;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String shopName;
    private String shopDecription;
    private String location;
}
