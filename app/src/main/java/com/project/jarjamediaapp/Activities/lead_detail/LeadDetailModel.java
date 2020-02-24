package com.project.jarjamediaapp.Activities.lead_detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Activities.composeEmail.ComposeEmailModel;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.ArrayList;

public class LeadDetailModel extends BaseResponse {


    @SerializedName("data")
    @Expose
    public ArrayList<ComposeEmailModel.Data> data = null;

    public class Data {

        @SerializedName("text")
        @Expose
        public String text;

        @SerializedName("value")
        @Expose
        public String value;
        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("type")
        @Expose
        public String type;

        public String getText() {
            return text;
        }

        public String getValue() {
            return value;
        }

        public String getId() {
            return id;
        }

        public String getType() {
            return type;
        }
    }

    public ArrayList<ComposeEmailModel.Data> getData() {
        return data;
    }

}
