package com.project.jarjamediaapp.Activities.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.List;

public class FollowUpNotificationModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    private Data data;


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public class Data {

        @SerializedName("followCount")
        @Expose
        private Integer followCount;
        @SerializedName("followUpsList")
        @Expose
        private List<FollowUpsList> followUpsList = null;

        public Integer getFollowCount() {
            return followCount;
        }

        public void setFollowCount(Integer followCount) {
            this.followCount = followCount;
        }

        public List<FollowUpsList> getFollowUpsList() {
            return followUpsList;
        }

        public void setFollowUpsList(List<FollowUpsList> followUpsList) {
            this.followUpsList = followUpsList;
        }

    }

    public class FollowUpsList {

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
        @SerializedName("reminderDycryptedId")
        @Expose
        private Integer reminderDycryptedId;
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

        public Integer getReminderDycryptedId() {
            return reminderDycryptedId;
        }

        public void setReminderDycryptedId(Integer reminderDycryptedId) {
            this.reminderDycryptedId = reminderDycryptedId;
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

        public void setDescription(String description) {
            this.description = description;
        }

        public String getFollowUpsType() {
            return followUpsType;
        }

        public void setFollowUpsType(String followUpsType) {
            this.followUpsType = followUpsType;
        }

        public Boolean getIsSeen() {
            return isSeen;
        }

        public void setIsSeen(Boolean isSeen) {
            this.isSeen = isSeen;
        }

    }

}
