package com.project.jarjamediaapp.Activities.lead_detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.List;

public class LeadDetailModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {

        @SerializedName("fromEmailList")
        @Expose
        public List<String> fromEmailList = null;
        @SerializedName("toEmailList")
        @Expose
        public List<String> toEmailList = null;

    }

    public Data getData() {
        return data;
    }
}
