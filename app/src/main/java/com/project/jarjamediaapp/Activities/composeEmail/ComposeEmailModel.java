package com.project.jarjamediaapp.Activities.composeEmail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.ArrayList;

public class ComposeEmailModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    public ArrayList<Data> data = null;

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

    public ArrayList<Data> getData() {
        return data;
    }
}
