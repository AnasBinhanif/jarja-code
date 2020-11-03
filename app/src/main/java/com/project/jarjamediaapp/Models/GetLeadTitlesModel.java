package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetLeadTitlesModel {

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
        return "GetLeadTitlesModel{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }

    public class Data {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("leadID")
        @Expose
        public String leadID;
        @SerializedName("decryptedLeadID")
        @Expose
        public Integer decryptedLeadID;


        @Override
        public String toString() {
            return "Data{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", leadID='" + leadID + '\'' +
                    ", decryptedLeadID=" + decryptedLeadID +
                    '}';
        }
    }
}
