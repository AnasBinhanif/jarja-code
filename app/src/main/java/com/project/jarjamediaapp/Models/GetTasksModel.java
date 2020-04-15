package com.project.jarjamediaapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.List;

public class GetTasksModel extends BaseResponse implements Parcelable {

    @SerializedName("data")
    @Expose
    public Data data;

    protected GetTasksModel(Parcel in) {
    }

    public static final Creator<GetTasksModel> CREATOR = new Creator<GetTasksModel>() {
        @Override
        public GetTasksModel createFromParcel(Parcel in) {
            return new GetTasksModel(in);
        }

        @Override
        public GetTasksModel[] newArray(int size) {
            return new GetTasksModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static class Data implements Parcelable {

        @SerializedName("taskList")
        @Expose
        public List<TaskList> taskList = null;
        @SerializedName("taskCount")
        @Expose
        public Integer taskCount;

        protected Data(Parcel in) {
            if (in.readByte() == 0) {
                taskCount = null;
            } else {
                taskCount = in.readInt();
            }
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel in) {
                return new Data(in);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (taskCount == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(taskCount);
            }
        }

        public static class TaskList implements Parcelable {

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
            public String leadEncryptedId;
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
            public Object agents;
            @SerializedName("isEndDate")
            @Expose
            public Boolean isEndDate;

            protected TaskList(Parcel in) {
                name = in.readString();
                createdDate = in.readString();
                agentName = in.readString();
                leadID = in.readString();
                firstName = in.readString();
                lastName = in.readString();
                taskID = in.readString();
                assignTime = in.readString();
                scheduleID = in.readString();
                type = in.readString();
                nextRun = in.readString();
                endDate = in.readString();
                startDate = in.readString();
                leadEncryptedId = in.readString();
                description = in.readString();
                recur = in.readString();
                address = in.readString();
                if (in.readByte() == 0) {
                    interval = null;
                } else {
                    interval = in.readInt();
                }
                if (in.readByte() == 0) {
                    scheduleRecurID = null;
                } else {
                    scheduleRecurID = in.readInt();
                }
                byte tmpIsEndDate = in.readByte();
                isEndDate = tmpIsEndDate == 0 ? null : tmpIsEndDate == 1;
            }

            public static final Creator<TaskList> CREATOR = new Creator<TaskList>() {
                @Override
                public TaskList createFromParcel(Parcel in) {
                    return new TaskList(in);
                }

                @Override
                public TaskList[] newArray(int size) {
                    return new TaskList[size];
                }
            };

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

            public String getLeadEncryptedId() {
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

            public Object getAgents() {
                return agents;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(name);
                dest.writeString(createdDate);
                dest.writeString(agentName);
                dest.writeString(leadID);
                dest.writeString(firstName);
                dest.writeString(lastName);
                dest.writeString(taskID);
                dest.writeString(assignTime);
                dest.writeString(scheduleID);
                dest.writeString(type);
                dest.writeString(nextRun);
                dest.writeString(endDate);
                dest.writeString(startDate);
                dest.writeString(leadEncryptedId);
                dest.writeString(description);
                dest.writeString(recur);
                dest.writeString(address);
                if (interval == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(interval);
                }
                if (scheduleRecurID == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(scheduleRecurID);
                }
                dest.writeByte((byte) (isEndDate == null ? 0 : isEndDate ? 1 : 2));
            }
        }

        public List<TaskList> getTaskList() {
            return taskList;
        }

        public Integer getTaskCount() {
            return taskCount;
        }
    }

    public String getStatus() {
        return status;
    }

    public Data getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
