package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewFollowUpModel {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("message")
    @Expose
    public String message;

    @Override
    public String toString() {
        return "ViewFollowUpModel{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }

    public class Data {


        @SerializedName("_ViewDPCStep")
        @Expose
        public ViewDPCStep viewDPCStep;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("status")
        @Expose
        public String status;

        @Override
        public String toString() {
            return "Data{" +
                    "viewDPCStep=" + viewDPCStep +
                    ", message='" + message + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

    public class ViewDPCStep{

        @SerializedName("dripDetailID")
        @Expose
        public String dripDetailID;
        @SerializedName("dripCompaignID")
        @Expose
        public String dripCompaignID;
        @SerializedName("dripTypeID")
        @Expose
        public String dripTypeID;
        @SerializedName("wait")
        @Expose
        public String wait;
        @SerializedName("subject")
        @Expose
        public String subject;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("sendTime")
        @Expose
        public String sendTime;
        @SerializedName("sendDateTime")
        @Expose
        public String sendDateTime;
        @SerializedName("sentType")
        @Expose
        public String sentType;

        @Override
        public String toString() {
            return "ViewDPCStep{" +
                    "dripDetailID='" + dripDetailID + '\'' +
                    ", dripCompaignID='" + dripCompaignID + '\'' +
                    ", dripTypeID='" + dripTypeID + '\'' +
                    ", wait='" + wait + '\'' +
                    ", subject='" + subject + '\'' +
                    ", message='" + message + '\'' +
                    ", sendTime='" + sendTime + '\'' +
                    ", sendDateTime='" + sendDateTime + '\'' +
                    ", sentType='" + sentType + '\'' +
                    '}';
        }
    }

}
