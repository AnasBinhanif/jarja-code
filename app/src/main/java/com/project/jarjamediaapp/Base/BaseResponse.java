package com.project.jarjamediaapp.Base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

