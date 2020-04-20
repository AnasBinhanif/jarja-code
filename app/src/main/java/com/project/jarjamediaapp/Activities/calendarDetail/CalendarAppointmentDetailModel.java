package com.project.jarjamediaapp.Activities.calendarDetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.List;

public class CalendarAppointmentDetailModel extends BaseResponse implements Parcelable {

    @SerializedName("data")
    @Expose
    public Data data;

    public static class Data implements Parcelable {

        @SerializedName("calendarData")
        @Expose
        public CalendarData calendarData;

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
            public String gmailCalenderId;
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
            @SerializedName("note")
            @Expose
            public String note;
            @SerializedName("encrypted_LeadAppoinmentID")
            @Expose
            public String encryptedLeadAppoinmentID;

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
                gmailCalenderId = in.readString();
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
                note = in.readString();
                encryptedLeadAppoinmentID = in.readString();
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

            @Override
            public int describeContents() {
                return 0;
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
                dest.writeString(gmailCalenderId);
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
                dest.writeString(note);
                dest.writeString(encryptedLeadAppoinmentID);
            }


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

                public Integer getAgentID() {
                    return agentID;
                }

                public String getEncryptedAgentID() {
                    return encryptedAgentID;
                }

                public String getAgentName() {
                    return agentName;
                }

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

            public String getGmailCalenderId() {
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

            public String getNote() {
                return note;
            }

            public String getEncryptedLeadAppoinmentID() {
                return encryptedLeadAppoinmentID;
            }
        }

        public CalendarData getCalendarData() {
            return calendarData;
        }
    }

    protected CalendarAppointmentDetailModel(Parcel in) {
        data = in.readParcelable(Data.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(data, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CalendarAppointmentDetailModel> CREATOR = new Creator<CalendarAppointmentDetailModel>() {
        @Override
        public CalendarAppointmentDetailModel createFromParcel(Parcel in) {
            return new CalendarAppointmentDetailModel(in);
        }

        @Override
        public CalendarAppointmentDetailModel[] newArray(int size) {
            return new CalendarAppointmentDetailModel[size];
        }
    };

    public Data getData() {
        return data;
    }
}
