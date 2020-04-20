package com.project.jarjamediaapp.Activities.calendarDetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

public class CalendarTaskDetailModel extends BaseResponse implements Parcelable {

    @SerializedName("data")
    @Expose
    public Data data;

    protected CalendarTaskDetailModel(Parcel in) {
        data = in.readParcelable(Data.class.getClassLoader());
    }

    public static final Creator<CalendarTaskDetailModel> CREATOR = new Creator<CalendarTaskDetailModel>() {
        @Override
        public CalendarTaskDetailModel createFromParcel(Parcel in) {
            return new CalendarTaskDetailModel(in);
        }

        @Override
        public CalendarTaskDetailModel[] newArray(int size) {
            return new CalendarTaskDetailModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(data, flags);
    }

    public static class Data implements Parcelable {

        @SerializedName("calID")
        @Expose
        public Integer calID;
        @SerializedName("calendarEventID")
        @Expose
        public String calendarEventID;
        @SerializedName("userCalenderID")
        @Expose
        public String userCalenderID;
        @SerializedName("userID")
        @Expose
        public String userID;
        @SerializedName("eventTitle")
        @Expose
        public String eventTitle;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("startTime")
        @Expose
        public String startTime;
        @SerializedName("endTime")
        @Expose
        public String endTime;
        @SerializedName("link")
        @Expose
        public String link;
        @SerializedName("location")
        @Expose
        public String location;
        @SerializedName("createDate")
        @Expose
        public Object createDate;
        @SerializedName("updateDate")
        @Expose
        public Object updateDate;
        @SerializedName("crmid")
        @Expose
        public Integer crmid;
        @SerializedName("agentID")
        @Expose
        public Object agentID;
        @SerializedName("isDeleted")
        @Expose
        public Object isDeleted;
        @SerializedName("attendees")
        @Expose
        public Object attendees;
        @SerializedName("integLogId")
        @Expose
        public Object integLogId;
        @SerializedName("recurrence")
        @Expose
        public Object recurrence;
        @SerializedName("freq")
        @Expose
        public Object freq;
        @SerializedName("count")
        @Expose
        public Object count;
        @SerializedName("interval")
        @Expose
        public Object interval;
        @SerializedName("until")
        @Expose
        public Object until;
        @SerializedName("byDay")
        @Expose
        public Object byDay;
        @SerializedName("isDateWithTime")
        @Expose
        public Object isDateWithTime;
        @SerializedName("isAllDay")
        @Expose
        public Boolean isAllDay;
        @SerializedName("updatedBy")
        @Expose
        public Object updatedBy;
        @SerializedName("calendarType")
        @Expose
        public String calendarType;
        @SerializedName("isComplete")
        @Expose
        public Boolean isComplete;

        protected Data(Parcel in) {
            if (in.readByte() == 0) {
                calID = null;
            } else {
                calID = in.readInt();
            }
            calendarEventID = in.readString();
            userCalenderID = in.readString();
            userID = in.readString();
            eventTitle = in.readString();
            description = in.readString();
            startTime = in.readString();
            endTime = in.readString();
            link = in.readString();
            location = in.readString();
            if (in.readByte() == 0) {
                crmid = null;
            } else {
                crmid = in.readInt();
            }
            byte tmpIsAllDay = in.readByte();
            isAllDay = tmpIsAllDay == 0 ? null : tmpIsAllDay == 1;
            calendarType = in.readString();
            byte tmpIsComplete = in.readByte();
            isComplete = tmpIsComplete == 0 ? null : tmpIsComplete == 1;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (calID == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(calID);
            }
            dest.writeString(calendarEventID);
            dest.writeString(userCalenderID);
            dest.writeString(userID);
            dest.writeString(eventTitle);
            dest.writeString(description);
            dest.writeString(startTime);
            dest.writeString(endTime);
            dest.writeString(link);
            dest.writeString(location);
            if (crmid == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(crmid);
            }
            dest.writeByte((byte) (isAllDay == null ? 0 : isAllDay ? 1 : 2));
            dest.writeString(calendarType);
            dest.writeByte((byte) (isComplete == null ? 0 : isComplete ? 1 : 2));
        }

        @Override
        public int describeContents() {
            return 0;
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

        public Integer getCalID() {
            return calID;
        }

        public String getCalendarEventID() {
            return calendarEventID;
        }

        public String getUserCalenderID() {
            return userCalenderID;
        }

        public String getUserID() {
            return userID;
        }

        public String getEventTitle() {
            return eventTitle;
        }

        public String getDescription() {
            return description;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public String getLink() {
            return link;
        }

        public String getLocation() {
            return location;
        }

        public Object getCreateDate() {
            return createDate;
        }

        public Object getUpdateDate() {
            return updateDate;
        }

        public Integer getCrmid() {
            return crmid;
        }

        public Object getAgentID() {
            return agentID;
        }

        public Object getIsDeleted() {
            return isDeleted;
        }

        public Object getAttendees() {
            return attendees;
        }

        public Object getIntegLogId() {
            return integLogId;
        }

        public Object getRecurrence() {
            return recurrence;
        }

        public Object getFreq() {
            return freq;
        }

        public Object getCount() {
            return count;
        }

        public Object getInterval() {
            return interval;
        }

        public Object getUntil() {
            return until;
        }

        public Object getByDay() {
            return byDay;
        }

        public Object getIsDateWithTime() {
            return isDateWithTime;
        }

        public Boolean getAllDay() {
            return isAllDay;
        }

        public Object getUpdatedBy() {
            return updatedBy;
        }

        public String getCalendarType() {
            return calendarType;
        }

        public Boolean getComplete() {
            return isComplete;
        }
    }

    public Data getData() {
        return data;
    }

}
