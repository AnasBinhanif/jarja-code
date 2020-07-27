package com.project.jarjamediaapp.Activities.calendar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class CalendarModel extends BaseResponse implements Serializable {

    @SerializedName("data")
    @Expose
    public ArrayList<Data> data = null;

    public class Data implements Serializable {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("desc")
        @Expose
        public String desc;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("start")
        @Expose
        public String start;
        @SerializedName("end")
        @Expose
        public String end;
        @SerializedName("url")
        @Expose
        public String url;
        @SerializedName("color")
        @Expose
        public String color;
        @SerializedName("className")
        @Expose
        public Object className;
        @SerializedName("recurrence")
        @Expose
        public Object recurrence;
        @SerializedName("byday")
        @Expose
        public Object byday;
        @SerializedName("freq")
        @Expose
        public Object freq;
        @SerializedName("interval")
        @Expose
        public Object interval;
        @SerializedName("until")
        @Expose
        public Object until;
        @SerializedName("count")
        @Expose
        public Object count;
        @SerializedName("start1")
        @Expose
        public Object start1;
        @SerializedName("end1")
        @Expose
        public Object end1;
        @SerializedName("week")
        @Expose
        public Object week;
        @SerializedName("excludedate")
        @Expose
        public Object excludedate;
        @SerializedName("parent")
        @Expose
        public Boolean parent;
        @SerializedName("allDay")
        @Expose
        public Boolean allDay;
        @SerializedName("type")
        @Expose
        public String type;

        public String getDesc() {
            return desc;
        }



        public String getDescription() {
            return description;
        }



        public String getCalendarId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getStart() {
            return start;
        }

        public String getEnd() {
            return end;
        }

        public String getUrl() {
            return url;
        }

        public String getColor() {
            return color;
        }

        public Object getClassName() {
            return className;
        }

        public Object getRecurrence() {
            return recurrence;
        }

        public Object getByday() {
            return byday;
        }

        public Object getFreq() {
            return freq;
        }

        public Object getInterval() {
            return interval;
        }

        public Object getUntil() {
            return until;
        }

        public Object getCount() {
            return count;
        }

        public Object getStart1() {
            return start1;
        }

        public Object getEnd1() {
            return end1;
        }

        public Object getWeek() {
            return week;
        }

        public Object getExcludedate() {
            return excludedate;
        }

        public Boolean getParent() {
            return parent;
        }

        public Boolean getAllDay() {
            return allDay;
        }

        public String getCalendarType() {
            return type;
        }
    }

    public ArrayList<Data> getData() {
        return data;
    }


}
