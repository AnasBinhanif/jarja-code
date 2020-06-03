package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class UpgradeSocialProfile implements Serializable {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("message_QA")
    @Expose
    public Object messageQA;


    public class Data implements Serializable {

        @SerializedName("updated")
        @Expose
        public Boolean updated;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("leadSocialProfile_list")
        @Expose
        public ArrayList<LeadSocialProfileList> leadSocialProfileList = null;
    }

    public class LeadSocialProfileList implements Serializable {

        @SerializedName("imagelink")
        @Expose
        public String imagelink;
        @SerializedName("profilelink")
        @Expose
        public String profilelink;
        @SerializedName("siteName")
        @Expose
        public String siteName;
        @SerializedName("name")
        @Expose
        public String name;
    }
}