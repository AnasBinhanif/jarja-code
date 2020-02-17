package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class GetLeadNotes  implements Serializable {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("message")
    @Expose
    public String message;

    public class Data implements Serializable {

        @SerializedName("msg")
        @Expose
        public Object msg;
        @SerializedName("status")
        @Expose
        public Boolean status;
        @SerializedName("notesList")
        @Expose
        public ArrayList<NotesList> notesList = null;
    }

    public class NotesList implements Serializable {

        @SerializedName("agentList")
        @Expose
        public ArrayList<AgentList> agentList = null;
        @SerializedName("noteID")
        @Expose
        public String noteID;
        @SerializedName("encryptedNoteID")
        @Expose
        public String encryptedNoteID;
        @SerializedName("leadID")
        @Expose
        public String leadID;
        @SerializedName("encrypted_LeadID")
        @Expose
        public String encrypted_LeadID;
        @SerializedName("noteType")
        @Expose
        public String noteType;
        @SerializedName("desc")
        @Expose
        public String desc;
        @SerializedName("isSticky")
        @Expose
        public Boolean isSticky;
        @SerializedName("createdBy")
        @Expose
        public String createdBy;
        @SerializedName("createdDate")
        @Expose
        public String createdDate;
        @SerializedName("crmid")
        @Expose
        public String crmid;
    }

    public class AgentList implements Serializable  {

        @SerializedName("agentID")
        @Expose
        public Integer agentID;
        @SerializedName("agentName")
        @Expose
        public String agentName;
        @SerializedName("encryptedAgentID")
        @Expose
        public String encryptedAgentID;
    }
}
