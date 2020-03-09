package com.project.jarjamediaapp.Activities.add_task;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.List;

public class GetTaskDetail extends BaseResponse {

    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {

        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("createdDate")
        @Expose
        public String createdDate;
        @SerializedName("agentName")
        @Expose
        public String agentName;
        @SerializedName("leadID")
        @Expose
        public String leadID;
        @SerializedName("firstName")
        @Expose
        public String firstName;
        @SerializedName("lastName")
        @Expose
        public String lastName;
        @SerializedName("taskID")
        @Expose
        public String taskID;
        @SerializedName("assignTime")
        @Expose
        public String assignTime;
        @SerializedName("scheduleID")
        @Expose
        public String scheduleID;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("nextRun")
        @Expose
        public String nextRun;
        @SerializedName("endDate")
        @Expose
        public String endDate;
        @SerializedName("startDate")
        @Expose
        public String startDate;
        @SerializedName("lead_EncryptedId")
        @Expose
        public Object leadEncryptedId;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("recur")
        @Expose
        public String recur;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("interval")
        @Expose
        public Integer interval;
        @SerializedName("viaReminder")
        @Expose
        public Object viaReminder;
        @SerializedName("scheduleRecurID")
        @Expose
        public Integer scheduleRecurID;
        @SerializedName("agents")
        @Expose
        public List<Agent> agents = null;

        public class Agent {

            @SerializedName("agentName")
            @Expose
            public String agentName;
            @SerializedName("agentID_Encrypted")
            @Expose
            public String agentIDEncrypted;

            public String getAgentName() {
                return agentName;
            }

            public String getAgentIDEncrypted() {
                return agentIDEncrypted;
            }
        }

        public String getName() {
            return name;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public String getAgentName() {
            return agentName;
        }

        public String getLeadID() {
            return leadID;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getTaskID() {
            return taskID;
        }

        public String getAssignTime() {
            return assignTime;
        }

        public String getScheduleID() {
            return scheduleID;
        }

        public String getType() {
            return type;
        }

        public String getNextRun() {
            return nextRun;
        }

        public String getEndDate() {
            return endDate;
        }

        public String getStartDate() {
            return startDate;
        }

        public Object getLeadEncryptedId() {
            return leadEncryptedId;
        }

        public String getDescription() {
            return description;
        }

        public String getRecur() {
            return recur;
        }

        public String getAddress() {
            return address;
        }

        public Integer getInterval() {
            return interval;
        }

        public Object getViaReminder() {
            return viaReminder;
        }

        public Integer getScheduleRecurID() {
            return scheduleRecurID;
        }

        public List<Agent> getAgents() {
            return agents;
        }
    }

    public Data getData() {
        return data;
    }
}
