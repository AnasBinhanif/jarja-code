package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class GetAgentsModel implements Serializable {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public ArrayList<Data> data = null;
    @SerializedName("message")
    @Expose
    public String message;

    public class Data implements Serializable{

        @SerializedName("agentID")
        @Expose
        public Integer agentID;
        @SerializedName("encryptedAgentID")
        @Expose
        public String encryptedAgentID;
        @SerializedName("agentName")
        @Expose
        public String agentName;
        @SerializedName("agentType")
        @Expose
        public String agentType;
    }
}
