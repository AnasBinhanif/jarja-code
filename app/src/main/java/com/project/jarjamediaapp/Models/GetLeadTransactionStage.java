package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class GetLeadTransactionStage implements Serializable {
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("message")
    @Expose
    public String message;

    public class Data implements Serializable{

        @SerializedName("pipeLines")
        @Expose
        public ArrayList<PipeLine> pipeLines = null;
        @SerializedName("leadTransactionOne")
        @Expose
        public ArrayList<LeadTransactionOne> leadTransactionOne = null;
        @SerializedName("leadTransactionTwo")
        @Expose
        public ArrayList<LeadTransactionTwo> leadTransactionTwo;
    }

    public class PipeLine implements Serializable{

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("pipeline")
        @Expose
        public String pipeline;
        @SerializedName("createdDate")
        @Expose
        public Object createdDate;
        @SerializedName("modifiedDate")
        @Expose
        public Object modifiedDate;
        @SerializedName("crmid")
        @Expose
        public Integer crmid;
    }

    public class LeadTransactionOne implements Serializable{

        @SerializedName("date")
        @Expose
        public String date;
        @SerializedName("currentStage")
        @Expose
        public String currentStage;
        @SerializedName("encrypted_LeadDetailID")
        @Expose
        public String encrypted_LeadDetailID;
    }

    public class LeadTransactionTwo implements Serializable{

        @SerializedName("date")
        @Expose
        public String date;
        @SerializedName("currentStage")
        @Expose
        public String currentStage;
        @SerializedName("encrypted_LeadDetailID")
        @Expose
        public String encrypted_LeadDetailID;

    }
}
