package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class GetPropertyLeads {

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

        @SerializedName("message")
        @Expose
        public Object message;
        @SerializedName("status")
        @Expose
        public Boolean status;
        @SerializedName("leadsCount")
        @Expose
        public ArrayList<LeadsCount> leadsCount = null;
        @SerializedName("leadsList")
        @Expose
        public ArrayList<LeadsList> leadsList = null;
        @SerializedName("noOfPages")
        @Expose
        public Integer noOfPages;

    }

    public class LeadsCount implements Serializable {

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
        @SerializedName("lockLeadIDList")
        @Expose
        public String lockLeadIDList;
        @SerializedName("leadIDList")
        @Expose
        public String leadIDList;
    }

    public class LeadsList implements Serializable {

        @SerializedName("leadIDForCheckBoxes")
        @Expose
        public String leadIDForCheckBoxes;
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("resultSetType")
        @Expose
        public String resultSetType;
        @SerializedName("leadType")
        @Expose
        public String leadType;
        @SerializedName("leadTypeColor")
        @Expose
        public String leadTypeColor;
        @SerializedName("fNameLNamePhoneEmail")
        @Expose
        public String fNameLNamePhoneEmail;
        @SerializedName("firstName")
        @Expose
        public String firstName;
        @SerializedName("lastName")
        @Expose
        public String lastName;
        @SerializedName("primaryPhone")
        @Expose
        public String primaryPhone;
        @SerializedName("primaryEmail")
        @Expose
        public String primaryEmail;
        @SerializedName("registered")
        @Expose
        public String registered;
        @SerializedName("lastLoginedIn")
        @Expose
        public String lastLoginedIn;
        @SerializedName("lastTouchedIn")
        @Expose
        public String lastTouchedIn;
        @SerializedName("callCount")
        @Expose
        public String callCount;
        @SerializedName("timeFrame")
        @Expose
        public String timeFrame;
        @SerializedName("searchSavedCount")
        @Expose
        public String searchSavedCount;
        @SerializedName("dripCompaignCount")
        @Expose
        public String dripCompaignCount;
        @SerializedName("agentName")
        @Expose
        public String agentName;
        @SerializedName("city")
        @Expose
        public String city;
        @SerializedName("priceMax")
        @Expose
        public String priceMax;
        @SerializedName("priceMin")
        @Expose
        public String priceMin;
        @SerializedName("isNew")
        @Expose
        public String isNew;
        @SerializedName("isLock")
        @Expose
        public String isLock;
        @SerializedName("recordID")
        @Expose
        public Integer recordID;

    }

}
