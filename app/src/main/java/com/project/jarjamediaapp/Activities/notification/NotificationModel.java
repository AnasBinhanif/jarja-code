package com.project.jarjamediaapp.Activities.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.List;

public class NotificationModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {

        @SerializedName("taskList")
        @Expose
        public List<TaskList> taskList = null;
        @SerializedName("message")
        @Expose
        public Object message;
        @SerializedName("status")
        @Expose
        public Boolean status;

        public class TaskList {

            @SerializedName("vt_CRM_Lead_Custom")
            @Expose
            public VtCRMLeadCustom vtCRMLeadCustom;
            @SerializedName("taskID")
            @Expose
            public Integer taskID;
            @SerializedName("taskName")
            @Expose
            public String taskName;
            @SerializedName("leadID")
            @Expose
            public Integer leadID;

            public class VtCRMLeadCustom {

                @SerializedName("leadID")
                @Expose
                public String leadID;
                @SerializedName("firstName")
                @Expose
                public String firstName;
                @SerializedName("lastName")
                @Expose
                public String lastName;
                @SerializedName("primaryEmail")
                @Expose
                public String primaryEmail;
                @SerializedName("primaryPhone")
                @Expose
                public String primaryPhone;

                public String getFirstName() {
                    return firstName;
                }

                public String getLastName() {
                    return lastName;
                }

                public String getPrimaryEmail() {
                    return primaryEmail;
                }

                public String getPrimaryPhone() {
                    return primaryPhone;
                }
            }

            public VtCRMLeadCustom getVtCRMLeadCustom() {
                return vtCRMLeadCustom;
            }

            public Integer getTaskID() {
                return taskID;
            }

            public String getTaskName() {
                return taskName;
            }

            public Integer getLeadID() {
                return leadID;
            }
        }

    }

    public Data getData() {
        return data;
    }
}
