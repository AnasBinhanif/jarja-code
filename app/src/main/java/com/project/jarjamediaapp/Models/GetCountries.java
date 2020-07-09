package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetCountries {

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

    public class Datum implements Serializable
    {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("countryName")
        @Expose
        public String countryName;

    }

}
