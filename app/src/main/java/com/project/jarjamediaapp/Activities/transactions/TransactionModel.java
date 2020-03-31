package com.project.jarjamediaapp.Activities.transactions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.List;

public class TransactionModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    public List<Data> data = null;

    public class Data {

        @SerializedName("agentID")
        @Expose
        public String agentID;
        @SerializedName("commission")
        @Expose
        public Integer commission;
        @SerializedName("agentName")
        @Expose
        public String agentName;

        public String getAgentID() {
            return agentID;
        }

        public Integer getCommission() {
            return commission;
        }

        public String getAgentName() {
            return agentName;
        }

        public void setCommission(Integer commission) {
            this.commission = commission;
        }
    }

    public List<Data> getData() {
        return data;
    }
}
