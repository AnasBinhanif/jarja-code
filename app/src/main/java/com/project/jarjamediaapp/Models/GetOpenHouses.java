package com.project.jarjamediaapp.Models;

import android.graphics.drawable.Drawable;

public class GetOpenHouses {


    private String address;
    private String startDateTime;
    private String endDateTime;
    private String city;
    private String postalCode;
    private String province;
    private String price;
    private Drawable image;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getProvince() {
        return province;
    }

    public String getPrice() {
        return price;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public GetOpenHouses(String address, String startDateTime, String endDateTime, String city, String postalCode, String province, String price, Drawable image) {
        this.address = address;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.city = city;
        this.postalCode = postalCode;
        this.province = province;
        this.price = price;
        this.image = image;
    }
}
