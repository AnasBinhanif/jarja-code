package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Upload_ProfileImage {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("message_QA")
    @Expose
    public Object messageQA;

    public class Data implements Serializable
    {

        @SerializedName("picName")
        @Expose
        public String picName;
        @SerializedName("picGuid")
        @Expose
        public String picGuid;
        @SerializedName("picLink")
        @Expose
        public String picLink;

    }

}
