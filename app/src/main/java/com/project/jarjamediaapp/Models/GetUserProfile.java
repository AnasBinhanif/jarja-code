package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Activities.login.LoginModel;

public class GetUserProfile {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {

        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("userProfile")
        @Expose
        public LoginModel.UserProfile userProfile;

        @SerializedName("agentData")
        @Expose
        public AgentData agentData;

        public class AgentData {

            @SerializedName("agentID")
            @Expose
            public Integer agentID;
            @SerializedName("encrypted_AgentID")
            @Expose
            public String encryptedAgentID;
            @SerializedName("agentName")
            @Expose
            public String agentName;

            public String getEncryptedAgentID() {
                return encryptedAgentID;
            }

            public String getAgentName() {
                return agentName;
            }

            public int getAgentID() {
                return agentID;
            }
        }

        public AgentData getAgentData() {
            return agentData;
        }

    }


}
