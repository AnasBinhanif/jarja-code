package com.project.jarjamediaapp.Models;

public class GetDueFollowUps {


    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public GetDueFollowUps(String name, String address) {

        this.name = name;
        this.address = address;
    }

}
