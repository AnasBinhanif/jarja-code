package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Activities.login.LoginModel;

import java.util.ArrayList;

public class GetAppointmentsModel {


    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public ArrayList<Data> data= new ArrayList<>();

    public class Data {

        @SerializedName("vt_CRM_Lead_Custom")
        @Expose
        public LeadsData leadsData;

    }

    public class LeadsData {

        @SerializedName("leadID")
        @Expose
        public String leadID;
        @SerializedName("firstName")
        @Expose
        public String firstName;
        @SerializedName("lastName")
        @Expose
        public String lastName;
        @SerializedName("address")
        @Expose
        public String address;


    }
}
