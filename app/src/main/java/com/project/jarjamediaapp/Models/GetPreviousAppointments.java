package com.project.jarjamediaapp.Models;

public class GetPreviousAppointments {


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


    public GetPreviousAppointments(String name, String address) {

        this.name = name;
        this.address = address;
    }

}
