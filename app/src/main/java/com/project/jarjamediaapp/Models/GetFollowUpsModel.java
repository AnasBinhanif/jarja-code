package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetFollowUpsModel {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public ArrayList<Data> data = null;
    @SerializedName("message")
    @Expose
    public String message;

    public class Data {

        @SerializedName("leadID")
        @Expose
        public String leadID;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("leadName")
        @Expose
        public String leadName;
        @SerializedName("summary")
        @Expose
        public String summary;
        @SerializedName("dripType")
        @Expose
        public String dripType;
        @SerializedName("dripDetailID")
        @Expose
        public String dripDetailID;
        @SerializedName("reminderId")
        @Expose
        public String reminderId;
        @SerializedName("assignDate")
        @Expose
        public String assignDate;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("followUpsType")
        @Expose
        public String followUpsType;
    }
}
