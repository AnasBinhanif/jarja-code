package com.project.jarjamediaapp.Activities.calendarDetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

public class CalendarDetailModel extends BaseResponse implements Parcelable {

    @SerializedName("calendarData")
    @Expose
    public CalendarData calendarData;

    public static class CalendarData implements Parcelable {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("excludeDates")
        @Expose
        public Object excludeDates;
        @SerializedName("eventSTime")
        @Expose
        public Object eventSTime;
        @SerializedName("eventETime")
        @Expose
        public Object eventETime;
        @SerializedName("calendarType")
        @Expose
        public String calendarType;
        @SerializedName("calID")
        @Expose
        public Integer calID;
        @SerializedName("calendarEventID")
        @Expose
        public String calendarEventID;
        @SerializedName("userCalenderID")
        @Expose
        public Object userCalenderID;
        @SerializedName("userID")
        @Expose
        public Object userID;
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
        @SerializedName("vt_CRM_IntegrationLog")
        @Expose
        public Object vtCRMIntegrationLog;

        @SerializedName("agentIds")
        @Expose
        public Object agentIds;
        @SerializedName("orderBy")
        @Expose
        public Integer orderBy;
        @SerializedName("isCompleted")
        @Expose
        public Boolean isCompleted;
        @SerializedName("leadAppoinmentID")
        @Expose
        public String leadAppoinmentID;
        @SerializedName("leadID")
        @Expose
        public String leadID;
        @SerializedName("propertyID")
        @Expose
        public Object propertyID;
        @SerializedName("eventID")
        @Expose
        public String eventID;
        @SerializedName("desc")
        @Expose
        public String desc;
        @SerializedName("isAppointmentFixed")
        @Expose
        public Boolean isAppointmentFixed;
        @SerializedName("isAppointmentAttend")
        @Expose
        public Boolean isAppointmentAttend;
        @SerializedName("appointmentDate")
        @Expose
        public Object appointmentDate;
        @SerializedName("datedFrom")
        @Expose
        public String datedFrom;
        @SerializedName("datedTo")
        @Expose
        public String datedTo;
        @SerializedName("createdDate")
        @Expose
        public String createdDate;
        @SerializedName("countryName")
        @Expose
        public Object countryName;
        @SerializedName("cityName")
        @Expose
        public Object cityName;
        @SerializedName("isGmailApptActive")
        @Expose
        public Object isGmailApptActive;
        @SerializedName("gmailCalenderId")
        @Expose
        public Object gmailCalenderId;
        @SerializedName("reminderDate")
        @Expose
        public Object reminderDate;
        @SerializedName("isSend")
        @Expose
        public Boolean isSend;
        @SerializedName("viaReminder")
        @Expose
        public String viaReminder;


        protected CalendarData(Parcel in) {
            calendarType = in.readString();
            if (in.readByte() == 0) {
                calID = null;
            } else {
                calID = in.readInt();
            }
            calendarEventID = in.readString();
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
            if (in.readByte() == 0) {
                orderBy = null;
            } else {
                orderBy = in.readInt();
            }
            byte tmpIsCompleted = in.readByte();
            isCompleted = tmpIsCompleted == 0 ? null : tmpIsCompleted == 1;
            leadAppoinmentID = in.readString();
            leadID = in.readString();
            eventID = in.readString();
            desc = in.readString();
            byte tmpIsAppointmentFixed = in.readByte();
            isAppointmentFixed = tmpIsAppointmentFixed == 0 ? null : tmpIsAppointmentFixed == 1;
            byte tmpIsAppointmentAttend = in.readByte();
            isAppointmentAttend = tmpIsAppointmentAttend == 0 ? null : tmpIsAppointmentAttend == 1;
            datedFrom = in.readString();
            datedTo = in.readString();
            createdDate = in.readString();
            byte tmpIsSend = in.readByte();
            isSend = tmpIsSend == 0 ? null : tmpIsSend == 1;
            viaReminder = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(calendarType);
            if (calID == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(calID);
            }
            dest.writeString(calendarEventID);
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
            if (orderBy == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(orderBy);
            }
            dest.writeByte((byte) (isCompleted == null ? 0 : isCompleted ? 1 : 2));
            dest.writeString(leadAppoinmentID);
            dest.writeString(leadID);
            dest.writeString(eventID);
            dest.writeString(desc);
            dest.writeByte((byte) (isAppointmentFixed == null ? 0 : isAppointmentFixed ? 1 : 2));
            dest.writeByte((byte) (isAppointmentAttend == null ? 0 : isAppointmentAttend ? 1 : 2));
            dest.writeString(datedFrom);
            dest.writeString(datedTo);
            dest.writeString(createdDate);
            dest.writeByte((byte) (isSend == null ? 0 : isSend ? 1 : 2));
            dest.writeString(viaReminder);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<CalendarData> CREATOR = new Creator<CalendarData>() {
            @Override
            public CalendarData createFromParcel(Parcel in) {
                return new CalendarData(in);
            }

            @Override
            public CalendarData[] newArray(int size) {
                return new CalendarData[size];
            }
        };

        public String getCalendarId() {
            return id;
        }

        public Object getExcludeDates() {
            return excludeDates;
        }

        public Object getEventSTime() {
            return eventSTime;
        }

        public Object getEventETime() {
            return eventETime;
        }

        public String getCalendarType() {
            return calendarType;
        }

        public Integer getCalID() {
            return calID;
        }

        public String getCalendarEventID() {
            return calendarEventID;
        }

        public Object getUserCalenderID() {
            return userCalenderID;
        }

        public Object getUserID() {
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

        public Object getVtCRMIntegrationLog() {
            return vtCRMIntegrationLog;
        }

        public Object getAgentIds() {
            return agentIds;
        }

        public Integer getOrderBy() {
            return orderBy;
        }

        public Boolean getCompleted() {
            return isCompleted;
        }

        public String getLeadAppoinmentID() {
            return leadAppoinmentID;
        }

        public String getLeadID() {
            return leadID;
        }

        public Object getPropertyID() {
            return propertyID;
        }

        public String getEventID() {
            return eventID;
        }

        public String getDesc() {
            return desc;
        }

        public Boolean getAppointmentFixed() {
            return isAppointmentFixed;
        }

        public Boolean getAppointmentAttend() {
            return isAppointmentAttend;
        }

        public Object getAppointmentDate() {
            return appointmentDate;
        }

        public String getDatedFrom() {
            return datedFrom;
        }

        public String getDatedTo() {
            return datedTo;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public Object getCountryName() {
            return countryName;
        }

        public Object getCityName() {
            return cityName;
        }

        public Object getIsGmailApptActive() {
            return isGmailApptActive;
        }

        public Object getGmailCalenderId() {
            return gmailCalenderId;
        }

        public Object getReminderDate() {
            return reminderDate;
        }

        public Boolean getSend() {
            return isSend;
        }

        public String getViaReminder() {
            return viaReminder;
        }
    }

    protected CalendarDetailModel(Parcel in) {
        calendarData = in.readParcelable(CalendarData.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(calendarData, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CalendarDetailModel> CREATOR = new Creator<CalendarDetailModel>() {
        @Override
        public CalendarDetailModel createFromParcel(Parcel in) {
            return new CalendarDetailModel(in);
        }

        @Override
        public CalendarDetailModel[] newArray(int size) {
            return new CalendarDetailModel[size];
        }
    };

    public CalendarData getCalendarData() {
        return calendarData;
    }
}
