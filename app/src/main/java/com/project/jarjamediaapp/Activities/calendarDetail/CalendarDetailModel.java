package com.project.jarjamediaapp.Activities.calendarDetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.List;

public class CalendarDetailModel extends BaseResponse implements Parcelable {

    @SerializedName("data")
    @Expose
    public Data data;

    protected CalendarDetailModel(Parcel in) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static class Data implements Parcelable {

        @SerializedName("calendarData")
        @Expose
        public CalendarData calendarData;
        @SerializedName("list")
        @Expose
        public CalendarList list;

        protected Data(Parcel in) {
            calendarData = in.readParcelable(CalendarData.class.getClassLoader());
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
            dest.writeParcelable(calendarData, flags);
        }

        public static class CalendarData implements Parcelable {

            @SerializedName("leadName")
            @Expose
            public String leadName;
            @SerializedName("agentIds")
            @Expose
            public Object agentIds;
            @SerializedName("orderBy")
            @Expose
            public Integer orderBy;
            @SerializedName("startTime")
            @Expose
            public Object startTime;
            @SerializedName("endTime")
            @Expose
            public Object endTime;
            @SerializedName("calendarType")
            @Expose
            public Object calendarType;
            @SerializedName("isCompleted")
            @Expose
            public Boolean isCompleted;
            @SerializedName("leadAppoinmentID")
            @Expose
            public Integer leadAppoinmentID;
            @SerializedName("leadID")
            @Expose
            public Integer leadID;
            @SerializedName("propertyID")
            @Expose
            public Object propertyID;
            @SerializedName("eventID")
            @Expose
            public String eventID;
            @SerializedName("eventTitle")
            @Expose
            public String eventTitle;
            @SerializedName("location")
            @Expose
            public String location;
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
            @SerializedName("isAllDay")
            @Expose
            public Boolean isAllDay;
            @SerializedName("reminderDate")
            @Expose
            public Object reminderDate;
            @SerializedName("interval")
            @Expose
            public Integer interval;
            @SerializedName("isSend")
            @Expose
            public Boolean isSend;
            @SerializedName("viaReminder")
            @Expose
            public String viaReminder;
            @SerializedName("agentList")
            @Expose
            public List<AgentList> agentList = null;
            @SerializedName("encrypted_LeadID")
            @Expose
            public String encryptedLeadID;
            @SerializedName("encrypted_LeadAppoinmentID")
            @Expose
            public String encrypted_LeadAppoinmentID;
            @SerializedName("note")
            @Expose
            public String note;


            public static class AgentList implements Parcelable {

                @SerializedName("agentID")
                @Expose
                public Integer agentID;
                @SerializedName("encrypted_AgentID")
                @Expose
                public String encryptedAgentID;
                @SerializedName("agentName")
                @Expose
                public String agentName;

                public Integer getAgentID() {
                    return agentID;
                }

                public String getEncryptedAgentID() {
                    return encryptedAgentID;
                }

                public String getAgentName() {
                    return agentName;
                }

                protected AgentList(Parcel in) {
                    if (in.readByte() == 0) {
                        agentID = null;
                    } else {
                        agentID = in.readInt();
                    }
                    encryptedAgentID = in.readString();
                    agentName = in.readString();
                }

                public static final Creator<AgentList> CREATOR = new Creator<AgentList>() {
                    @Override
                    public AgentList createFromParcel(Parcel in) {
                        return new AgentList(in);
                    }

                    @Override
                    public AgentList[] newArray(int size) {
                        return new AgentList[size];
                    }
                };

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    if (agentID == null) {
                        dest.writeByte((byte) 0);
                    } else {
                        dest.writeByte((byte) 1);
                        dest.writeInt(agentID);
                    }
                    dest.writeString(encryptedAgentID);
                    dest.writeString(agentName);
                }
            }


            protected CalendarData(Parcel in) {
                leadName = in.readString();
                if (in.readByte() == 0) {
                    orderBy = null;
                } else {
                    orderBy = in.readInt();
                }
                byte tmpIsCompleted = in.readByte();
                isCompleted = tmpIsCompleted == 0 ? null : tmpIsCompleted == 1;
                if (in.readByte() == 0) {
                    leadAppoinmentID = null;
                } else {
                    leadAppoinmentID = in.readInt();
                }
                if (in.readByte() == 0) {
                    leadID = null;
                } else {
                    leadID = in.readInt();
                }
                eventID = in.readString();
                eventTitle = in.readString();
                location = in.readString();
                desc = in.readString();
                byte tmpIsAppointmentFixed = in.readByte();
                isAppointmentFixed = tmpIsAppointmentFixed == 0 ? null : tmpIsAppointmentFixed == 1;
                byte tmpIsAppointmentAttend = in.readByte();
                isAppointmentAttend = tmpIsAppointmentAttend == 0 ? null : tmpIsAppointmentAttend == 1;
                datedFrom = in.readString();
                datedTo = in.readString();
                createdDate = in.readString();
                byte tmpIsAllDay = in.readByte();
                isAllDay = tmpIsAllDay == 0 ? null : tmpIsAllDay == 1;
                if (in.readByte() == 0) {
                    interval = null;
                } else {
                    interval = in.readInt();
                }
                byte tmpIsSend = in.readByte();
                isSend = tmpIsSend == 0 ? null : tmpIsSend == 1;
                viaReminder = in.readString();
                agentList = in.createTypedArrayList(AgentList.CREATOR);
                encryptedLeadID = in.readString();
                encrypted_LeadAppoinmentID = in.readString();
                note = in.readString();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(leadName);
                if (orderBy == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(orderBy);
                }
                dest.writeByte((byte) (isCompleted == null ? 0 : isCompleted ? 1 : 2));
                if (leadAppoinmentID == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(leadAppoinmentID);
                }
                if (leadID == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(leadID);
                }
                dest.writeString(eventID);
                dest.writeString(eventTitle);
                dest.writeString(location);
                dest.writeString(desc);
                dest.writeByte((byte) (isAppointmentFixed == null ? 0 : isAppointmentFixed ? 1 : 2));
                dest.writeByte((byte) (isAppointmentAttend == null ? 0 : isAppointmentAttend ? 1 : 2));
                dest.writeString(datedFrom);
                dest.writeString(datedTo);
                dest.writeString(createdDate);
                dest.writeByte((byte) (isAllDay == null ? 0 : isAllDay ? 1 : 2));
                if (interval == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(interval);
                }
                dest.writeByte((byte) (isSend == null ? 0 : isSend ? 1 : 2));
                dest.writeString(viaReminder);
                dest.writeTypedList(agentList);
                dest.writeString(encryptedLeadID);
                dest.writeString(encrypted_LeadAppoinmentID);
                dest.writeString(note);
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

            public String getLeadName() {
                return leadName;
            }

            public Object getAgentIds() {
                return agentIds;
            }

            public Integer getOrderBy() {
                return orderBy;
            }

            public Object getStartTime() {
                return startTime;
            }

            public Object getEndTime() {
                return endTime;
            }

            public Object getCalendarType() {
                return calendarType;
            }

            public Boolean getCompleted() {
                return isCompleted;
            }

            public Integer getLeadAppoinmentID() {
                return leadAppoinmentID;
            }

            public Integer getLeadID() {
                return leadID;
            }

            public Object getPropertyID() {
                return propertyID;
            }

            public String getEventID() {
                return eventID;
            }

            public String getEventTitle() {
                return eventTitle;
            }

            public String getLocation() {
                return location;
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

            public Boolean getAllDay() {
                return isAllDay;
            }

            public Object getReminderDate() {
                return reminderDate;
            }

            public Integer getInterval() {
                return interval;
            }

            public Boolean getSend() {
                return isSend;
            }

            public String getViaReminder() {
                return viaReminder;
            }

            public List<AgentList> getAgentList() {
                return agentList;
            }

            public String getEncryptedLeadID() {
                return encryptedLeadID;
            }

            public String getEncrypted_LeadAppointmentID() {
                return encrypted_LeadAppoinmentID;
            }

            public String getNote() {
                return note;
            }
        }

        public CalendarData getCalendarData() {
            return calendarData;
        }

        public static class CalendarList implements Parcelable{

            @SerializedName("id")
            @Expose
            public Object id;
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
            public Object location;
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

            protected CalendarList(Parcel in) {
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
                if (in.readByte() == 0) {
                    crmid = null;
                } else {
                    crmid = in.readInt();
                }
                byte tmpIsAllDay = in.readByte();
                isAllDay = tmpIsAllDay == 0 ? null : tmpIsAllDay == 1;
            }

            public static final Creator<CalendarList> CREATOR = new Creator<CalendarList>() {
                @Override
                public CalendarList createFromParcel(Parcel in) {
                    return new CalendarList(in);
                }

                @Override
                public CalendarList[] newArray(int size) {
                    return new CalendarList[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
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
                if (crmid == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(crmid);
                }
                dest.writeByte((byte) (isAllDay == null ? 0 : isAllDay ? 1 : 2));
            }

            public Object getId() {
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

            public Object getLocation() {
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
        }

        public CalendarList getList() {
            return list;
        }
    }

    public Data getData() {
        return data;
    }
}
