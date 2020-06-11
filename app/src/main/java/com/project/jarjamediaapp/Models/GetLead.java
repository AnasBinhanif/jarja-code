package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class GetLead implements Serializable {

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

        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("leadList")
        @Expose
        public LeadList leadList;
    }

    public class AgentsList implements Serializable {

        @SerializedName("assignAgentsID")
        @Expose
        public Integer assignAgentsID;
        @SerializedName("agentID")
        @Expose
        public String agentID;
        @SerializedName("agentDryptedID")
        @Expose
        public Integer agentDryptedID;
        @SerializedName("agentName")
        @Expose
        public String agentName;
        @SerializedName("picGuid")
        @Expose
        public String picGuid;
        @SerializedName("isPrimaryAgent")
        @Expose
        public Boolean isPrimaryAgent;
        @SerializedName("agentPicture")
        @Expose
        public String agentPicture;
    }

    public class LeadDripCompaignList implements Serializable {

        @SerializedName("dripCompaignID")
        @Expose
        public Integer dripCompaignID;
        @SerializedName("encryptedID")
        @Expose
        public String encryptedID;
        @SerializedName("name")
        @Expose
        public String name;

    }

    public class LeadTaskList implements Serializable {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("encrypted_TagID")
        @Expose
        public String encryptedTagID;
        @SerializedName("label")
        @Expose
        public String label;
        private final static long serialVersionUID = 804799143587321048L;

    }

    public class LeadList implements Serializable {

        @SerializedName("leadID")
        @Expose
        public String leadID;
        @SerializedName("firstName")
        @Expose
        public String firstName;
        @SerializedName("lastName")
        @Expose
        public String lastName;
        @SerializedName("primaryEmail")
        @Expose
        public String primaryEmail;
        @SerializedName("primaryPhone")
        @Expose
        public String primaryPhone;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("street")
        @Expose
        public String street;
        @SerializedName("zipcode")
        @Expose
        public String zipcode;
        @SerializedName("city")
        @Expose
        public String city;
        @SerializedName("state")
        @Expose
        public String state;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("source")
        @Expose
        public String source;
        @SerializedName("crmid")
        @Expose
        public String crmid;
        @SerializedName("sendWelcomeEmail")
        @Expose
        public Boolean sendWelcomeEmail;
        @SerializedName("scoreColor")
        @Expose
        public String scoreColor;
        @SerializedName("isDeleted")
        @Expose
        public Boolean isDeleted;
        @SerializedName("createBy")
        @Expose
        public String createBy;
        @SerializedName("createDate")
        @Expose
        public String createDate;
        @SerializedName("updateBy")
        @Expose
        public String updateBy;
        @SerializedName("updateDate")
        @Expose
        public String updateDate;
        @SerializedName("company")
        @Expose
        public String company;
        @SerializedName("subscribe")
        @Expose
        public String subscribe;
        @SerializedName("msgSubs")
        @Expose
        public String msgSubs;
        @SerializedName("county")
        @Expose
        public String county;
        @SerializedName("spousname")
        @Expose
        public String spousname;
        @SerializedName("imageURL")
        @Expose
        public String imageURL;
        @SerializedName("leadScoreId")
        @Expose
        public String leadScoreId;
        @SerializedName("timeFrameId")
        @Expose
        public Integer timeFrameId;
        @SerializedName("sourceId")
        @Expose
        public String sourceId;
        @SerializedName("isOpenHouseSource")
        @Expose
        public String isOpenHouseSource;
        @SerializedName("isCreated")
        @Expose
        public String isCreated;
        @SerializedName("state2")
        @Expose
        public String state2;
        @SerializedName("city2")
        @Expose
        public String city2;
        @SerializedName("zipcode2")
        @Expose
        public String zipcode2;
        @SerializedName("countryid")
        @Expose
        public String countryid;
        @SerializedName("isBulkSource")
        @Expose
        public String isBulkSource;
        @SerializedName("leadScore")
        @Expose
        public String leadScore;
        @SerializedName("houseNumber1")
        @Expose
        public String houseNumber1;
        @SerializedName("houseNumber2")
        @Expose
        public String houseNumber2;
        @SerializedName("apiLeadID")
        @Expose
        public String apiLeadID;
        @SerializedName("unbounceNumber")
        @Expose
        public String unbounceNumber;
        @SerializedName("mid")
        @Expose
        public String mid;
        @SerializedName("exchangeEmailInboxID")
        @Expose
        public String exchangeEmailInboxID;
        @SerializedName("isZapier")
        @Expose
        public String isZapier;
        @SerializedName("isLeadDistributionRoundRobin")
        @Expose
        public String isLeadDistributionRoundRobin;
        @SerializedName("isLeadDistributionManual")
        @Expose
        public String isLeadDistributionManual;
        @SerializedName("isLeadDistributionLeadRouting")
        @Expose
        public String isLeadDistributionLeadRouting;
        @SerializedName("emailSubscribed")
        @Expose
        public String emailSubscribed;
        @SerializedName("dateOfBirth")
        @Expose
        public String dateOfBirth;
        @SerializedName("dateOfMarriage")
        @Expose
        public String dateOfMarriage;
        @SerializedName("spouseBirthday")
        @Expose
        public String spouseBirthday;
        @SerializedName("batchNo")
        @Expose
        public String batchNo;
        @SerializedName("rowNum")
        @Expose
        public String rowNum;
        @SerializedName("isNew")
        @Expose
        public String isNew;
        @SerializedName("isLock")
        @Expose
        public String isLock;
        @SerializedName("sourceDate")
        @Expose
        public String sourceDate;
        @SerializedName("isTransClose")
        @Expose
        public String isTransClose;
        @SerializedName("isTransTwoClose")
        @Expose
        public String isTransTwoClose;
        @SerializedName("isBirthDayNotify")
        @Expose
        public Boolean isBirthDayNotify;
        @SerializedName("isAnniversaryNotify")
        @Expose
        public Boolean isAnniversaryNotify;
        @SerializedName("leadTypeID")
        @Expose
        public Integer leadTypeID;
        @SerializedName("leadAgentIDs")
        @Expose
        public String leadAgentIDs;
        @SerializedName("allagentids")
        @Expose
        public String allagentids;
        @SerializedName("leadStringID")
        @Expose
        public String leadStringID;
        @SerializedName("agentsList")
        @Expose
        public ArrayList<AgentsList> agentsList = null;
        @SerializedName("leadDripCompaignList")
        @Expose
        public ArrayList<LeadDripCompaignList> leadDripCompaignList = null;
        @SerializedName("leadTaskList")
        @Expose
        public ArrayList<LeadTaskList> leadTaskList = null;
        @SerializedName("phoneNumber")
        @Expose
        public String phoneNumber;
        @SerializedName("emailAddress")
        @Expose
        public String emailAddress;
        @SerializedName("msg")
        @Expose
        public String msg;
        @SerializedName("preApproved")
        @Expose
        public boolean preApproved;
        @SerializedName("timeFrameName")
        @Expose
        public String timeFrameName;

    }
}