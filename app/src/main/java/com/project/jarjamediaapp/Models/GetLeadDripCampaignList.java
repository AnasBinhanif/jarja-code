package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetLeadDripCampaignList {

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

        @SerializedName("dripCompaignID")
        @Expose
        public Integer dripCompaignID;
        @SerializedName("name")
        @Expose
        public String name;
        /*@SerializedName("startMovingLeadToPipeline")
        @Expose
        public String startMovingLeadToPipeline;
        @SerializedName("startAddingLabel")
        @Expose
        public String startAddingLabel;
        @SerializedName("filterByLeadSource")
        @Expose
        public String filterByLeadSource;
        @SerializedName("stopMovingLeadToPipeline")
        @Expose
        public String stopMovingLeadToPipeline;
        @SerializedName("stopAddingLabel")
        @Expose
        public String stopAddingLabel;
        @SerializedName("category")
        @Expose
        public String category;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("sendWelcomeMsg")
        @Expose
        public Boolean sendWelcomeMsg;
        @SerializedName("applyAutomaticOnLeadsCome")
        @Expose
        public Boolean applyAutomaticOnLeadsCome;
        @SerializedName("isDeleted")
        @Expose
        public Boolean isDeleted;
        @SerializedName("isActive")
        @Expose
        public Boolean isActive;
        @SerializedName("autoPauseReply")
        @Expose
        public String autoPauseReply;
        @SerializedName("createBy")
        @Expose
        public String createBy;
        @SerializedName("createdDate")
        @Expose
        public String createdDate;
        @SerializedName("updateBy")
        @Expose
        public String updateBy;
        @SerializedName("updateDate")
        @Expose
        public String updateDate;
        @SerializedName("clientID")
        @Expose
        public String clientID;
        @SerializedName("startOnInquiryTrans")
        @Expose
        public Boolean startOnInquiryTrans;
        @SerializedName("startOnPropertyTrans")
        @Expose
        public Boolean startOnPropertyTrans;
        @SerializedName("stopOnPropertyTrans")
        @Expose
        public Boolean stopOnPropertyTrans;
        @SerializedName("stopOnInquiryTrans")
        @Expose
        public Boolean stopOnInquiryTrans;
        @SerializedName("isTransactionOne")
        @Expose
        public Boolean isTransactionOne;
        @SerializedName("isTransactionTwo")
        @Expose
        public Boolean isTransactionTwo;
        @SerializedName("sourceId")
        @Expose
        public String sourceId;
        @SerializedName("crmid")
        @Expose
        public String crmid;
        @SerializedName("parentDripCompaignID")
        @Expose
        public String parentDripCompaignID;
        @SerializedName("forUserID")
        @Expose
        public String forUserID;
        @SerializedName("isShared")
        @Expose
        public String isShared;
        @SerializedName("isSharedToOnlyManagers")
        @Expose
        public String isSharedToOnlyManagers;
        @SerializedName("isUserRoleManager")
        @Expose
        public String isUserRoleManager;*/

    }
}
