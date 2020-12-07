package com.project.jarjamediaapp.Activities.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.List;

public class TaskNotificationModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    private Data data;


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("taskList")
        @Expose
        private List<TaskList> taskList = null;
        @SerializedName("taskCount")
        @Expose
        private Integer taskCount;

        public List<TaskList> getTaskList() {
            return taskList;
        }

        public void setTaskList(List<TaskList> taskList) {
            this.taskList = taskList;
        }

        public Integer getTaskCount() {
            return taskCount;
        }

        public void setTaskCount(Integer taskCount) {
            this.taskCount = taskCount;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "taskList=" + taskList +
                    ", taskCount=" + taskCount +
                    '}';
        }
    }
    public class TaskList {

        @SerializedName("taskID")
        @Expose
        private Integer taskID;
        @SerializedName("encryptedTaskID")
        @Expose
        private String encryptedTaskID;
        @SerializedName("taskName")
        @Expose
        private String taskName;
        @SerializedName("scheduleID")
        @Expose
        private Integer scheduleID;
        @SerializedName("taskType")
        @Expose
        private String taskType;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("assignTime")
        @Expose
        private String assignTime;
        @SerializedName("createdDate")
        @Expose
        private String createdDate;
        @SerializedName("isDone")
        @Expose
        private Object isDone;
        @SerializedName("doneDate")
        @Expose
        private Object doneDate;
        @SerializedName("isSeen")
        @Expose
        private Boolean isSeen;
        @SerializedName("isPostPonned")
        @Expose
        private Object isPostPonned;
        @SerializedName("postPonnedMinutes")
        @Expose
        private Object postPonnedMinutes;
        @SerializedName("dumiDate")
        @Expose
        private Object dumiDate;
        @SerializedName("crmid")
        @Expose
        private Integer crmid;
        @SerializedName("vt_CRM_Schedule")
        @Expose
        private Object vtCRMSchedule;

        public Integer getTaskID() {
            return taskID;
        }

        public void setTaskID(Integer taskID) {
            this.taskID = taskID;
        }

        public String getEncryptedTaskID() {
            return encryptedTaskID;
        }

        public void setEncryptedTaskID(String encryptedTaskID) {
            this.encryptedTaskID = encryptedTaskID;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public Integer getScheduleID() {
            return scheduleID;
        }

        public void setScheduleID(Integer scheduleID) {
            this.scheduleID = scheduleID;
        }

        public String getTaskType() {
            return taskType;
        }

        public void setTaskType(String taskType) {
            this.taskType = taskType;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAssignTime() {
            return assignTime;
        }

        public void setAssignTime(String assignTime) {
            this.assignTime = assignTime;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public Object getIsDone() {
            return isDone;
        }

        public void setIsDone(Object isDone) {
            this.isDone = isDone;
        }

        public Object getDoneDate() {
            return doneDate;
        }

        public void setDoneDate(Object doneDate) {
            this.doneDate = doneDate;
        }

        public Boolean getIsSeen() {
            return isSeen;
        }

        public void setIsSeen(Boolean isSeen) {
            this.isSeen = isSeen;
        }

        public Object getIsPostPonned() {
            return isPostPonned;
        }

        public void setIsPostPonned(Object isPostPonned) {
            this.isPostPonned = isPostPonned;
        }

        public Object getPostPonnedMinutes() {
            return postPonnedMinutes;
        }

        public void setPostPonnedMinutes(Object postPonnedMinutes) {
            this.postPonnedMinutes = postPonnedMinutes;
        }

        public Object getDumiDate() {
            return dumiDate;
        }

        public void setDumiDate(Object dumiDate) {
            this.dumiDate = dumiDate;
        }

        public Integer getCrmid() {
            return crmid;
        }

        public void setCrmid(Integer crmid) {
            this.crmid = crmid;
        }

        public Object getVtCRMSchedule() {
            return vtCRMSchedule;
        }

        public void setVtCRMSchedule(Object vtCRMSchedule) {
            this.vtCRMSchedule = vtCRMSchedule;
        }

        @Override
        public String toString() {
            return "TaskList{" +
                    "taskID=" + taskID +
                    ", encryptedTaskID='" + encryptedTaskID + '\'' +
                    ", taskName='" + taskName + '\'' +
                    ", scheduleID=" + scheduleID +
                    ", taskType='" + taskType + '\'' +
                    ", description='" + description + '\'' +
                    ", assignTime='" + assignTime + '\'' +
                    ", createdDate='" + createdDate + '\'' +
                    ", isDone=" + isDone +
                    ", doneDate=" + doneDate +
                    ", isSeen=" + isSeen +
                    ", isPostPonned=" + isPostPonned +
                    ", postPonnedMinutes=" + postPonnedMinutes +
                    ", dumiDate=" + dumiDate +
                    ", crmid=" + crmid +
                    ", vtCRMSchedule=" + vtCRMSchedule +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TaskNotificationModel{" +
                "data=" + data +
                '}';
    }
}
