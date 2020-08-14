package com.project.jarjamediaapp.Activities.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.List;

public class FollowUpNotificationModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    private List<Data> data = null;

    public class Data {

        @SerializedName("leadID")
        @Expose
        private String leadID;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("leadName")
        @Expose
        private String leadName;
        @SerializedName("summary")
        @Expose
        private String summary;
        @SerializedName("dripType")
        @Expose
        private String dripType;
        @SerializedName("dripDetailID")
        @Expose
        private String dripDetailID;
        @SerializedName("reminderId")
        @Expose
        private String reminderId;
        @SerializedName("assignDate")
        @Expose
        private String assignDate;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("followUpsType")
        @Expose
        private String followUpsType;

        @SerializedName("isSeen")
        @Expose
        private Boolean isSeen;

        public Boolean getIsSeen() {
            return isSeen;
        }

        public void setIsSeen(Boolean isSeen) {
            this.isSeen = isSeen;
        }

        public String getLeadID() {
            return leadID;
        }

        public void setLeadID(String leadID) {
            this.leadID = leadID;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLeadName() {
            return leadName;
        }

        public void setLeadName(String leadName) {
            this.leadName = leadName;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getDripType() {
            return dripType;
        }

        public void setDripType(String dripType) {
            this.dripType = dripType;
        }

        public String getDripDetailID() {
            return dripDetailID;
        }

        public void setDripDetailID(String dripDetailID) {
            this.dripDetailID = dripDetailID;
        }

        public String getReminderId() {
            return reminderId;
        }

        public void setReminderId(String reminderId) {
            this.reminderId = reminderId;
        }

        public String getAssignDate() {
            return assignDate;
        }

        public void setAssignDate(String assignDate) {
            this.assignDate = assignDate;
        }

        public String getDescription() {
            return description;
        }

        public String getFollowUpsType() {
            return followUpsType;
        }

        public void setFollowUpsType(String followUpsType) {
            this.followUpsType = followUpsType;
        }

    }

    public List<Data> getData() {
        return data;
    }
}
