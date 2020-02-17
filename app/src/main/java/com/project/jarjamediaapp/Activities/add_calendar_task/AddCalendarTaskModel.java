package com.project.jarjamediaapp.Activities.add_calendar_task;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetTasksModel;

import java.util.ArrayList;

public class AddCalendarTaskModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    public ArrayList<GetTasksModel.Data> data = null;

    public class Data {

        @SerializedName("text")
        @Expose
        public String text;

        @SerializedName("value")
        @Expose
        public String value;

        public String getText() {
            return text;
        }

        public String getValue() {
            return value;
        }

    }

    public ArrayList<GetTasksModel.Data> getData() {
        return data;
    }
}
