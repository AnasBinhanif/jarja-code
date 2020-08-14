package com.project.jarjamediaapp.Activities.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.List;

public class AppointmentNotificationModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    private List<Data> data = null;

    public class Data {

        @SerializedName("vt_CRM_Lead_Custom")
        @Expose
        private VtCRMLeadCustom vtCRMLeadCustom;
        @SerializedName("eventID")
        @Expose
        private String eventID;
        @SerializedName("leadAppoinmentID")
        @Expose
        private String leadAppoinmentID;
        @SerializedName("eventTitle")
        @Expose
        private String eventTitle;
        @SerializedName("isSeen")
        @Expose
        private Boolean isSeen;

        public Boolean getIsSeen() {
            return isSeen;
        }

        public void setIsSeen(Boolean isSeen) {
            this.isSeen = isSeen;
        }

        public String getLeadAppoinmentID() {
            return leadAppoinmentID;
        }

        public void setLeadAppoinmentID(String leadAppoinmentID) {
            this.leadAppoinmentID = leadAppoinmentID;
        }

        public VtCRMLeadCustom getVtCRMLeadCustom() {
            return vtCRMLeadCustom;
        }

        public void setVtCRMLeadCustom(VtCRMLeadCustom vtCRMLeadCustom) {
            this.vtCRMLeadCustom = vtCRMLeadCustom;
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


        }

        public String getEventID() {
            return eventID;
        }

        public String getEventTitle() {
            return eventTitle;
        }

    }

    public List<Data> getData() {
        return data;
    }
}
