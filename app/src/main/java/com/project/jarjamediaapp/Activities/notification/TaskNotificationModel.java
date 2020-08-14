package com.project.jarjamediaapp.Activities.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.List;

public class TaskNotificationModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {

        @SerializedName("taskList")
        @Expose
        public List<TaskList> taskList = null;

        public class TaskList {

            @SerializedName("vt_CRM_Lead_Custom")
            @Expose
            public VtCRMLeadCustom vtCRMLeadCustom;
            @SerializedName("taskID")
            @Expose
            public Integer taskID;
            @SerializedName("encryptedTaskID")
            @Expose
            public String encryptedTaskID;
            @SerializedName("taskName")
            @Expose
            public String taskName;
            @SerializedName("description")
            @Expose
            public String description;

            @SerializedName("taskType")
            @Expose
            public String taskType;

            @SerializedName("isSeen")
            @Expose
            private Boolean isSeen;

            public Boolean getIsSeen() {
                return isSeen;
            }

            public void setIsSeen(Boolean isSeen) {
                this.isSeen = isSeen;
            }

            public String getEncryptedTaskID() {
                return encryptedTaskID;
            }

            public void setEncryptedTaskID(String encryptedTaskID) {
                this.encryptedTaskID = encryptedTaskID;
            }

            public String getDescription() {
                return description;
            }

            public String getTaskType() {
                return taskType;
            }

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

        public List<TaskList> getTaskList() {
            return taskList;
        }
    }

    public Data getData() {
        return data;
    }

}
