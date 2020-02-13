package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetLeadTimeFrame {

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

        @SerializedName("timeFrameId")
        @Expose
        public String timeFrameId;
        @SerializedName("timeFrame")
        @Expose
        public String timeFrame;
    }
}
