package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTwilioNumber {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public String data;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("message_QA")
    @Expose
    public Object messageQA;
}
