package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetTasksModel {

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

        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("createdDate")
        @Expose
        public String createdDate;
        @SerializedName("agentName")
        @Expose
        public String agentName;
        @SerializedName("leadID")
        @Expose
        public String leadID;
        @SerializedName("firstName")
        @Expose
        public String firstName;
        @SerializedName("lastName")
        @Expose
        public String lastName;
        @SerializedName("taskID")
        @Expose
        public String taskID;
        @SerializedName("assignTime")
        @Expose
        public String assignTime;
        @SerializedName("scheduleID")
        @Expose
        public String scheduleID;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("nextRun")
        @Expose
        public String nextRun;
        @SerializedName("endDate")
        @Expose
        public String endDate;

    }

}
