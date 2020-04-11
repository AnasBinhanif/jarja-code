package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class GetLeadSocialProfile {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public ArrayList<Data> data = null;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("message_QA")
    @Expose
    public Object messageQA;

    public class Data implements Serializable
    {

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
