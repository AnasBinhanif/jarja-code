package com.project.jarjamediaapp.Activities.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.List;

public class AppointmentNotificationModel extends BaseResponse {


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

        @SerializedName("vt_CRM_Lead_Custom")
        @Expose
        private VtCRMLeadCustom vtCRMLeadCustom;
        @SerializedName("leadAppoinmentID")
        @Expose
        private String leadAppoinmentID;
        @SerializedName("leadID")
        @Expose
        private Object leadID;
        @SerializedName("eventID")
        @Expose
        private String eventID;
        @SerializedName("eventTitle")
        @Expose
        private String eventTitle;
        @SerializedName("isSeen")
        @Expose
        private Boolean isSeen;
        @SerializedName("isAppointmentAttend")
        @Expose
        private Boolean isAppointmentAttend;
        @SerializedName("appointmentDate")
        @Expose
        private Object appointmentDate;
        @SerializedName("datedFrom")
        @Expose
        private String datedFrom;
        @SerializedName("datedTo")
        @Expose
        private String datedTo;
        @SerializedName("createdDate")
        @Expose
        private String createdDate;

        public VtCRMLeadCustom getVtCRMLeadCustom() {
            return vtCRMLeadCustom;
        }

        public void setVtCRMLeadCustom(VtCRMLeadCustom vtCRMLeadCustom) {
            this.vtCRMLeadCustom = vtCRMLeadCustom;
        }

        public String getLeadAppoinmentID() {
            return leadAppoinmentID;
        }

        public void setLeadAppoinmentID(String leadAppoinmentID) {
            this.leadAppoinmentID = leadAppoinmentID;
        }

        public Object getLeadID() {
            return leadID;
        }

        public void setLeadID(Object leadID) {
            this.leadID = leadID;
        }

        public String getEventID() {
            return eventID;
        }

        public void setEventID(String eventID) {
            this.eventID = eventID;
        }

        public String getEventTitle() {
            return eventTitle;
        }

        public void setEventTitle(String eventTitle) {
            this.eventTitle = eventTitle;
        }

        public Boolean getIsSeen() {
            return isSeen;
        }

        public void setIsSeen(Boolean isSeen) {
            this.isSeen = isSeen;
        }

        public Boolean getIsAppointmentAttend() {
            return isAppointmentAttend;
        }

        public void setIsAppointmentAttend(Boolean isAppointmentAttend) {
            this.isAppointmentAttend = isAppointmentAttend;
        }

        public Object getAppointmentDate() {
            return appointmentDate;
        }

        public void setAppointmentDate(Object appointmentDate) {
            this.appointmentDate = appointmentDate;
        }

        public String getDatedFrom() {
            return datedFrom;
        }

        public void setDatedFrom(String datedFrom) {
            this.datedFrom = datedFrom;
        }

        public String getDatedTo() {
            return datedTo;
        }

        public void setDatedTo(String datedTo) {
            this.datedTo = datedTo;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

    }
    public class VtCRMLeadCustom {

        @SerializedName("leadID")
        @Expose
        private String leadID;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("primaryEmail")
        @Expose
        private String primaryEmail;
        @SerializedName("primaryPhone")
        @Expose
        private String primaryPhone;

        public String getLeadID() {
            return leadID;
        }

        public void setLeadID(String leadID) {
            this.leadID = leadID;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPrimaryEmail() {
            return primaryEmail;
        }

        public void setPrimaryEmail(String primaryEmail) {
            this.primaryEmail = primaryEmail;
        }

        public String getPrimaryPhone() {
            return primaryPhone;
        }

        public void setPrimaryPhone(String primaryPhone) {
            this.primaryPhone = primaryPhone;
        }

    }


}
