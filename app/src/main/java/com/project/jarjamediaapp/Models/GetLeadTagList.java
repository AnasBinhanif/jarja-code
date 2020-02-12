package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetLeadTagList {

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

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("label")
        @Expose
        public String label;
    }

}
