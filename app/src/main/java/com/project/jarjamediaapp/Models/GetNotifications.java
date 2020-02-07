package com.project.jarjamediaapp.Models;

public class GetNotifications {


    private String name;
    private String leadName;
    private String contact;
    private String email;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeadName() {
        return leadName;
    }

    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public GetNotifications(String name, String leadName, String contact, String email) {

        this.name = name;
        this.leadName = leadName;
        this.contact = contact;
        this.email = email;
    }
}
