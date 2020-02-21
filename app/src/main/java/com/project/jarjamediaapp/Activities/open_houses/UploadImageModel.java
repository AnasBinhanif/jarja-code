package com.project.jarjamediaapp.Activities.open_houses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

public class UploadImageModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    private String data;

    public String getData() {
        return data;
    }
}

