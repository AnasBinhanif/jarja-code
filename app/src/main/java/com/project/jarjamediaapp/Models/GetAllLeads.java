package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetAllLeads {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("message")
    @Expose
    public String message;


    public class Data {
        @SerializedName("message")
        @Expose
        public Object message;
        @SerializedName("noOfPages")
        @Expose
        public int noOfPages;
        @SerializedName("status")
        @Expose
        public Boolean status;
        @SerializedName("leadsList")
        @Expose
        public ArrayList<LeadsList> leadsList = null;
    }

    public class LeadsList {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("resultSetType")
        @Expose
        public String resultSetType;
        @SerializedName("firstName")
        @Expose
        public String firstName;
        @SerializedName("lastName")
        @Expose
        public String lastName;
        @SerializedName("primaryPhone")
        @Expose
        public String primaryPhone;
        @SerializedName("primaryEmail")
        @Expose
        public String primaryEmail;
    }
}
