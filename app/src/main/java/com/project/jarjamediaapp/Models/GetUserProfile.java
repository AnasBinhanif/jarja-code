package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class GetUserProfile implements Serializable {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public Data data;

    public class Data implements Serializable {

        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("userProfile")
        @Expose
        public UserProfile userProfileData;
        @SerializedName("stz")
        @Expose
        public ArrayList<Stz> stz = null;
        @SerializedName("countryList")
        @Expose
        public ArrayList<CountryList> countryList = null;
        @SerializedName("registertedWebsites")
        @Expose
        public Object registertedWebsites;
        @SerializedName("agentData")
        @Expose
        public AgentData agentData;

        public AgentData getAgentData() {
            return agentData;
        }

    }

    public class AgentData implements Serializable {

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

    public class Stz implements Serializable {

        @SerializedName("standardName")
        @Expose
        public String standardName;
        @SerializedName("displayName")
        @Expose
        public String displayName;
    }

    public class CountryList implements Serializable {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("countryName")
        @Expose
        public String countryName;
        @SerializedName("isoCodes")
        @Expose
        public String isoCodes;
        @SerializedName("countryCodes")
        @Expose
        public String countryCodes;
        @SerializedName("isDeleted")
        @Expose
        public Boolean isDeleted;
        @SerializedName("crmid")
        @Expose
        public Integer crmid;

    }

    public class UserProfile implements Serializable {

        @SerializedName("userID")
        @Expose
        public String userID;
        @SerializedName("firstName")
        @Expose
        public String firstName;
        @SerializedName("lastName")
        @Expose
        public String lastName;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("phone")
        @Expose
        public String phone;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("password")
        @Expose
        public String password;
        @SerializedName("agentType")
        @Expose
        public String agentType;
        @SerializedName("roleID")
        @Expose
        public Integer roleID;
        @SerializedName("isActive")
        @Expose
        public Boolean isActive;
        @SerializedName("picName")
        @Expose
        public String picName;
        @SerializedName("picPath")
        @Expose
        public String picPath;
        @SerializedName("picGuid")
        @Expose
        public String picGuid;
        @SerializedName("demoSettingsID")
        @Expose
        public Object demoSettingsID;
        @SerializedName("signature")
        @Expose
        public String signature;
        @SerializedName("subscribeEmails")
        @Expose
        public Object subscribeEmails;
        @SerializedName("subscribeTexts")
        @Expose
        public Object subscribeTexts;
        @SerializedName("leadTextAlerts")
        @Expose
        public Object leadTextAlerts;
        @SerializedName("receiveVoiceBlasts")
        @Expose
        public Object receiveVoiceBlasts;
        @SerializedName("plainTextEmails")
        @Expose
        public Object plainTextEmails;
        @SerializedName("chatNotifications")
        @Expose
        public Object chatNotifications;
        @SerializedName("contactInfoNotifications")
        @Expose
        public Object contactInfoNotifications;
        @SerializedName("openAppPreference")
        @Expose
        public Object openAppPreference;
        @SerializedName("loginLandingPage")
        @Expose
        public Object loginLandingPage;
        @SerializedName("leadAlertCC")
        @Expose
        public Object leadAlertCC;
        @SerializedName("listingAgentLeadAlertCC")
        @Expose
        public Object listingAgentLeadAlertCC;
        @SerializedName("createBy")
        @Expose
        public Integer createBy;
        @SerializedName("updateBy")
        @Expose
        public Integer updateBy;
        @SerializedName("createDate")
        @Expose
        public String createDate;
        @SerializedName("updateDate")
        @Expose
        public String updateDate;
        @SerializedName("lead_ID")
        @Expose
        public Object leadID;
        @SerializedName("isTrail")
        @Expose
        public Object isTrail;
        @SerializedName("trailId")
        @Expose
        public Object trailId;
        @SerializedName("license")
        @Expose
        public String license;
        @SerializedName("company")
        @Expose
        public String company;
        @SerializedName("biography")
        @Expose
        public String biography;
        @SerializedName("isDeleted")
        @Expose
        public Boolean isDeleted;
        @SerializedName("aboutUs")
        @Expose
        public Object aboutUs;
        @SerializedName("locationId")
        @Expose
        public Object locationId;
        @SerializedName("userName")
        @Expose
        public Object userName;
        @SerializedName("streetAddress")
        @Expose
        public String streetAddress;
        @SerializedName("city")
        @Expose
        public String city;
        @SerializedName("state")
        @Expose
        public String state;
        @SerializedName("zipcode")
        @Expose
        public Object zipcode;
        @SerializedName("companyLogo")
        @Expose
        public Object companyLogo;
        @SerializedName("companyAddress")
        @Expose
        public String companyAddress;
        @SerializedName("twilioPhone")
        @Expose
        public String twilioPhone;
        @SerializedName("forwardedNumber")
        @Expose
        public Object forwardedNumber;
        @SerializedName("countryID")
        @Expose
        public Integer countryID;
        @SerializedName("crmid")
        @Expose
        public Integer crmid;
        @SerializedName("webUserName")
        @Expose
        public String webUserName;
        @SerializedName("isBulkSource")
        @Expose
        public Boolean isBulkSource;
        @SerializedName("isJsdUser")
        @Expose
        public Boolean isJsdUser;
        @SerializedName("groupName")
        @Expose
        public String groupName;
        @SerializedName("isSendEmail")
        @Expose
        public Boolean isSendEmail;
        @SerializedName("leadDistributionMessageEnabled")
        @Expose
        public Boolean leadDistributionMessageEnabled;
        @SerializedName("userEmailLimit")
        @Expose
        public Integer userEmailLimit;
        @SerializedName("userEmailLimitCount")
        @Expose
        public Integer userEmailLimitCount;
        @SerializedName("userEmailLimitDate")
        @Expose
        public String userEmailLimitDate;
        @SerializedName("isAddToAllOutgoingEmails")
        @Expose
        public Boolean isAddToAllOutgoingEmails;
        @SerializedName("isCoach")
        @Expose
        public Boolean isCoach;
        @SerializedName("isStudent")
        @Expose
        public Boolean isStudent;
        @SerializedName("crM_URL")
        @Expose
        public Object crMURL;
        @SerializedName("agent")
        @Expose
        public Object agent;
        @SerializedName("web_ID")
        @Expose
        public Integer webID;
        @SerializedName("extraPermissions")
        @Expose
        public Object extraPermissions;
        @SerializedName("permissionGroupID")
        @Expose
        public Integer permissionGroupID;
        @SerializedName("userExtraPermissionjuncID")
        @Expose
        public Integer userExtraPermissionjuncID;
        @SerializedName("name")
        @Expose
        public Object name;
        @SerializedName("allow")
        @Expose
        public Boolean allow;
        @SerializedName("upload")
        @Expose
        public Object upload;
        @SerializedName("logo")
        @Expose
        public Object logo;
        @SerializedName("canAcceptLead")
        @Expose
        public Boolean canAcceptLead;
        @SerializedName("extraPerm")
        @Expose
        public Object extraPerm;
        @SerializedName("tmzone")
        @Expose
        public String tmzone;
        @SerializedName("countryCode")
        @Expose
        public Object countryCode;
        @SerializedName("existingGUID")
        @Expose
        public Object existingGUID;

    }

}
