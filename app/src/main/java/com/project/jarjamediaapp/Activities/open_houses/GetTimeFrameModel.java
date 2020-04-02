package com.project.jarjamediaapp.Activities.open_houses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.ArrayList;

public class GetTimeFrameModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    public ArrayList<Data> data = null;

    public class Data {

        @SerializedName("timeFrameId")
        @Expose
        public String timeFrameId;
        @SerializedName("timeFrame")
        @Expose
        public String timeFrame;
        @SerializedName("text")
        @Expose
        public String text;
        @SerializedName("value")
        @Expose
        public String value;

        public String getTimeFrameId() {
            return timeFrameId;
        }

        public String getTimeFrame() {
            return timeFrame;
        }

        public String getText() {
            return text;
        }

        public String getValue() {
            return value;
        }
    }

    public ArrayList<Data> getData() {
        return data;
    }

}
