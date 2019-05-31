package com.example.playfit.dto;

public class ProductDTO {

    private int productID;
    private int productName;

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getProductName() {
        return productName;
    }

    public void setProductName(int productName) {
        this.productName = productName;
    }

    public int getPointRate() {
        return PointRate;
    }

    public void setPointRate(int pointRate) {
        PointRate = pointRate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private int PointRate;
    private String location;
}
