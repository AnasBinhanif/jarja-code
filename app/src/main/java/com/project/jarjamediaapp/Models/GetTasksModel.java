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

        @SerializedName("propertyAddress")
        @Expose
        public String propertyAddress;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("viaReminder")
        @Expose
        public String viaReminder;
        @SerializedName("scheduleRecurID")
        @Expose
        public String scheduleRecurID;
        @SerializedName("interval")
        @Expose
        public String interval;
        @SerializedName("firstName")
        @Expose
        public String firstName;
        @SerializedName("lastName")
        @Expose
        public String lastName;
        @SerializedName("scheduleID")
        @Expose
        public String scheduleID;
        @SerializedName("agentName")
        @Expose
        public String agentName;
        @SerializedName("taskID")
        @Expose
        public String taskID;
        @SerializedName("taskName")
        @Expose
        public String taskName;
        @SerializedName("taskType")
        @Expose
        public String taskType;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("agentID")
        @Expose
        public String agentID;
        @SerializedName("crmid")
        @Expose
        public String crmid;
        @SerializedName("assignTime")
        @Expose
        public String assignTime;
        @SerializedName("nextRun")
        @Expose
        public String nextRun;
        @SerializedName("assignedLead")
        @Expose
        public String assignedLead;
        @SerializedName("leadID")
        @Expose
        public String leadID;

    }

}
