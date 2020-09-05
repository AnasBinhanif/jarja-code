package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GmailCalender {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Boolean data;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("message_QA")
    @Expose
    private Object messageQA;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getData() {
        return data;
    }

    public void setData(Boolean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getMessageQA() {
        return messageQA;
    }

    public void setMessageQA(Object messageQA) {
        this.messageQA = messageQA;
    }

}