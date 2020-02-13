package com.project.jarjamediaapp.Activities.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.ArrayList;

public class NotificationModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    private ArrayList<Data> data = null;

    public class Data {

        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("lead_name")
        @Expose
        private String lead_name;
        @SerializedName("contact")
        @Expose
        private String contact;
        @SerializedName("email")
        @Expose
        private String email;

        public String getMessage() {
            return message;
        }

        public String getLeadName() {
            return lead_name;
        }

        public String getContact() {
            return contact;
        }

        public String getEmail() {
            return email;
        }
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

}
