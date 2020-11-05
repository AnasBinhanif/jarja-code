package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetTagListByLeadID {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public ArrayList<Data> data = null;
    @SerializedName("message")
    @Expose
    public String message;

    @Override
    public String toString() {
        return "GetTagListByLeadID{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }

    public class Data {

        @SerializedName("tagID")
        @Expose
        public Integer tagID;
        @SerializedName("encrypted_TagID")
        @Expose
        public String encryptedTagID;
        @SerializedName("label")
        @Expose
        public String label;
        @SerializedName("added")
        @Expose
        public Object added;

        @Override
        public String toString() {
            return "Data{" +
                    "tagID=" + tagID +
                    ", encryptedTagID='" + encryptedTagID + '\'' +
                    ", label='" + label + '\'' +
                    ", added=" + added +
                    '}';
        }
    }
    
}
