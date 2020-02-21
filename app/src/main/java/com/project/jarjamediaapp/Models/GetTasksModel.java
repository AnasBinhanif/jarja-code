package com.project.jarjamediaapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetTasksModel implements Parcelable {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public ArrayList<Data> data = null;
    @SerializedName("message")
    @Expose
    public String message;

    protected GetTasksModel(Parcel in) {
        status = in.readString();
        data = in.createTypedArrayList(Data.CREATOR);
        message = in.readString();
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
        dest.writeString(status);
        dest.writeTypedList(data);
        dest.writeString(message);
    }

    public static class Data implements Parcelable {

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

        protected Data(Parcel in) {
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
        }
    }

    public ArrayList<Data> getData() {
        return data;
    }
}
