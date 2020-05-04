package com.project.jarjamediaapp.Activities.lead_detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

public class GetCallerId extends BaseResponse {

    @SerializedName("data")
    @Expose
    private Data data;

    public class Data {

        @SerializedName("number")
        @Expose
        private String number;
        @SerializedName("callerID")
        @Expose
        private String callerID;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getCallerID() {
            return callerID;
        }

        public void setCallerID(String callerID) {
            this.callerID = callerID;
        }

    }

    public Data getData() {
        return data;
    }
}
