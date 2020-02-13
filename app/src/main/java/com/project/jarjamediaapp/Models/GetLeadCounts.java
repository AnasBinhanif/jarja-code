package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetLeadCounts {

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
        @SerializedName("status")
        @Expose
        public Boolean status;
        @SerializedName("leadsCount")
        @Expose
        public ArrayList<LeadsCount> leadsCount = null;

    }

    public class LeadsCount {

        @SerializedName("allLeadsCount")
        @Expose
        public String allLeadsCount;
        @SerializedName("newLeadsCount")
        @Expose
        public String newLeadsCount;
        @SerializedName("hotLeadsCount")
        @Expose
        public String hotLeadsCount;
        @SerializedName("activeLeadsCount")
        @Expose
        public String activeLeadsCount;
        @SerializedName("underContractLeadsCount")
        @Expose
        public String underContractLeadsCount;
        @SerializedName("closeLeadsCount")
        @Expose
        public String closeLeadsCount;
    }
}
