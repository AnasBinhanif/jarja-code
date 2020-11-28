package com.project.jarjamediaapp.Activities.transactions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.io.Serializable;
import java.util.List;

public class TransactionModel extends BaseResponse implements Serializable {

    @SerializedName("data")
    @Expose
    public List<Data> data = null;

    public class Data {

        @SerializedName("agentID")
        @Expose
        public String agentID;
        @SerializedName("commission")
        @Expose
        public Double commission;
        @SerializedName("agentName")
        @Expose
        public String agentName;

        private String commissionDate;

        private String closeDate;

        public Data(String agentID, Double commission, String agentName, String commissionDate, String closeDate) {
            this.agentID = agentID;
            this.commission = commission;
            this.agentName = agentName;
            this.commissionDate = commissionDate;
            this.closeDate = closeDate;
        }

        public String getAgentID() {
            return agentID;
        }

        public Double getCommission() {
            return commission;
        }

        public String getAgentName() {
            return agentName;
        }

        public void setCommission(Double commission) {
            this.commission = commission;
        }

        public String getCommisionDate() {
            return commissionDate;
        }

        public void setCommisionDate(String commisionDate) {
            this.commissionDate = commisionDate;
        }

        public String getCloseDate() {
            return closeDate;
        }

        public void setCloseDate(String closeDate) {
            this.closeDate = closeDate;
        }
    }

    public List<Data> getData() {
        return data;
    }
}
