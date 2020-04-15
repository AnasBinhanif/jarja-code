package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.List;

public class GetFollowUpsModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {

        @SerializedName("followCount")
        @Expose
        public Integer followCount;
        @SerializedName("followUpsList")
        @Expose
        public List<FollowUpsList> followUpsList = null;

        public class FollowUpsList {

            @SerializedName("leadID")
            @Expose
            public String leadID;
            @SerializedName("address")
            @Expose
            public Object address;
            @SerializedName("leadName")
            @Expose
            public String leadName;
            @SerializedName("summary")
            @Expose
            public String summary;
            @SerializedName("dripType")
            @Expose
            public String dripType;
            @SerializedName("dripDetailID")
            @Expose
            public String dripDetailID;
            @SerializedName("reminderId")
            @Expose
            public String reminderId;
            @SerializedName("assignDate")
            @Expose
            public String assignDate;
            @SerializedName("description")
            @Expose
            public Object description;
            @SerializedName("followUpsType")
            @Expose
            public String followUpsType;

        }

        public Integer getFollowCount() {
            return followCount;
        }

        public List<FollowUpsList> getFollowUpsList() {
            return followUpsList;
        }
    }

    public Data getData() {
        return data;
    }
}
