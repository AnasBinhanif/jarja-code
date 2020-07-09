package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetTimeZoneList {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public List<Datum> data = null;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("message_QA")
    @Expose
    public Object messageQA;

    public class Datum implements Serializable {

        @SerializedName("standardName")
        @Expose
        public String standardName;
        @SerializedName("displayName")
        @Expose
        public String displayName;
    }
}
