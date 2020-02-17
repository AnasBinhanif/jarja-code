package com.project.jarjamediaapp.Activities.add_appointment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.List;

public class GetLocationModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {

        @SerializedName("groupName")
        @Expose
        public List<GroupName> groupName = null;

        public class GroupName {

            @SerializedName("label")
            @Expose
            public String label;
            @SerializedName("val")
            @Expose
            public String val;

            public String getLabel() {
                return label;
            }

            public String getVal() {
                return val;
            }
        }

        public List<GroupName> getGroupName() {
            return groupName;
        }
    }

    public Data getData() {
        return data;
    }
}
