
package com.project.jarjamediaapp.Activities.add_appointment;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data implements Parcelable {

    @SerializedName("vt_CRM_Lead_Custom")
    @Expose
    private VtCRMLeadCustom vtCRMLeadCustom;
    @SerializedName("orderBy")
    @Expose
    private Integer orderBy;
    @SerializedName("leadAppoinmentID")
    @Expose
    private String leadAppoinmentID;
    @SerializedName("leadID")
    @Expose
    private String leadID;
    @SerializedName("propertyID")
    @Expose
    private Object propertyID;
    @SerializedName("eventID")
    @Expose
    private String eventID;
    @SerializedName("eventTitle")
    @Expose
    private String eventTitle;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("isAppointmentFixed")
    @Expose
    private Boolean isAppointmentFixed;
    @SerializedName("isAppointmentAttend")
    @Expose
    private Boolean isAppointmentAttend;
    @SerializedName("appointmentDate")
    @Expose
    private Object appointmentDate;
    @SerializedName("datedFrom")
    @Expose
    private String datedFrom;
    @SerializedName("datedTo")
    @Expose
    private String datedTo;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("updateBy")
    @Expose
    private Object updateBy;
    @SerializedName("updateDate")
    @Expose
    private Object updateDate;
    @SerializedName("countryName")
    @Expose
    private Object countryName;
    @SerializedName("cityName")
    @Expose
    private Object cityName;
    @SerializedName("crmid")
    @Expose
    private Integer crmid;
    @SerializedName("isGmailApptActive")
    @Expose
    private Object isGmailApptActive;
    @SerializedName("gmailCalenderId")
    @Expose
    private String gmailCalenderId;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("isAllDay")
    @Expose
    private Boolean isAllDay;
    @SerializedName("reminderDate")
    @Expose
    private String reminderDate;
    @SerializedName("interval")
    @Expose
    private Integer interval;
    @SerializedName("isSend")
    @Expose
    private Boolean isSend;
    @SerializedName("viaReminder")
    @Expose
    private String viaReminder;
    @SerializedName("agentIds")
    @Expose
    private Object agentIds;
    @SerializedName("startTime")
    @Expose
    private Object startTime;
    @SerializedName("endTime")
    @Expose
    private Object endTime;
    @SerializedName("calendarType")
    @Expose
    private Object calendarType;
    @SerializedName("isCompleted")
    @Expose
    private Boolean isCompleted;
    @SerializedName("vt_CRM_LeadAppoinmentDetail_Custom")
    @Expose
    private List<VtCRMLeadAppoinmentDetailCustom> vtCRMLeadAppoinmentDetailCustom = null;

    protected Data(Parcel in) {
        vtCRMLeadCustom = in.readParcelable(VtCRMLeadCustom.class.getClassLoader());
        if (in.readByte() == 0) {
            orderBy = null;
        } else {
            orderBy = in.readInt();
        }
        leadAppoinmentID = in.readString();
        leadID = in.readString();
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
        if (in.readByte() == 0) {
            createdBy = null;
        } else {
            createdBy = in.readInt();
        }
        createdDate = in.readString();
        if (in.readByte() == 0) {
            crmid = null;
        } else {
            crmid = in.readInt();
        }
        gmailCalenderId = in.readString();
        byte tmpIsDeleted = in.readByte();
        isDeleted = tmpIsDeleted == 0 ? null : tmpIsDeleted == 1;
        byte tmpIsAllDay = in.readByte();
        isAllDay = tmpIsAllDay == 0 ? null : tmpIsAllDay == 1;
        reminderDate = in.readString();
        if (in.readByte() == 0) {
            interval = null;
        } else {
            interval = in.readInt();
        }
        byte tmpIsSend = in.readByte();
        isSend = tmpIsSend == 0 ? null : tmpIsSend == 1;
        viaReminder = in.readString();
        byte tmpIsCompleted = in.readByte();
        isCompleted = tmpIsCompleted == 0 ? null : tmpIsCompleted == 1;
        vtCRMLeadAppoinmentDetailCustom = in.createTypedArrayList(VtCRMLeadAppoinmentDetailCustom.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(vtCRMLeadCustom, flags);
        if (orderBy == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(orderBy);
        }
        dest.writeString(leadAppoinmentID);
        dest.writeString(leadID);
        dest.writeString(eventID);
        dest.writeString(eventTitle);
        dest.writeString(location);
        dest.writeString(desc);
        dest.writeByte((byte) (isAppointmentFixed == null ? 0 : isAppointmentFixed ? 1 : 2));
        dest.writeByte((byte) (isAppointmentAttend == null ? 0 : isAppointmentAttend ? 1 : 2));
        dest.writeString(datedFrom);
        dest.writeString(datedTo);
        if (createdBy == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(createdBy);
        }
        dest.writeString(createdDate);
        if (crmid == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(crmid);
        }
        dest.writeString(gmailCalenderId);
        dest.writeByte((byte) (isDeleted == null ? 0 : isDeleted ? 1 : 2));
        dest.writeByte((byte) (isAllDay == null ? 0 : isAllDay ? 1 : 2));
        dest.writeString(reminderDate);
        if (interval == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(interval);
        }
        dest.writeByte((byte) (isSend == null ? 0 : isSend ? 1 : 2));
        dest.writeString(viaReminder);
        dest.writeByte((byte) (isCompleted == null ? 0 : isCompleted ? 1 : 2));
        dest.writeTypedList(vtCRMLeadAppoinmentDetailCustom);
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

    public VtCRMLeadCustom getVtCRMLeadCustom() {
        return vtCRMLeadCustom;
    }

    public void setVtCRMLeadCustom(VtCRMLeadCustom vtCRMLeadCustom) {
        this.vtCRMLeadCustom = vtCRMLeadCustom;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public String getLeadAppoinmentID() {
        return leadAppoinmentID;
    }

    public void setLeadAppoinmentID(String leadAppoinmentID) {
        this.leadAppoinmentID = leadAppoinmentID;
    }

    public String getLeadID() {
        return leadID;
    }

    public void setLeadID(String leadID) {
        this.leadID = leadID;
    }

    public Object getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(Object propertyID) {
        this.propertyID = propertyID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean getIsAppointmentFixed() {
        return isAppointmentFixed;
    }

    public void setIsAppointmentFixed(Boolean isAppointmentFixed) {
        this.isAppointmentFixed = isAppointmentFixed;
    }

    public Boolean getIsAppointmentAttend() {
        return isAppointmentAttend;
    }

    public void setIsAppointmentAttend(Boolean isAppointmentAttend) {
        this.isAppointmentAttend = isAppointmentAttend;
    }

    public Object getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Object appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getDatedFrom() {
        return datedFrom;
    }

    public void setDatedFrom(String datedFrom) {
        this.datedFrom = datedFrom;
    }

    public String getDatedTo() {
        return datedTo;
    }

    public void setDatedTo(String datedTo) {
        this.datedTo = datedTo;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Object getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Object updateBy) {
        this.updateBy = updateBy;
    }

    public Object getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Object updateDate) {
        this.updateDate = updateDate;
    }

    public Object getCountryName() {
        return countryName;
    }

    public void setCountryName(Object countryName) {
        this.countryName = countryName;
    }

    public Object getCityName() {
        return cityName;
    }

    public void setCityName(Object cityName) {
        this.cityName = cityName;
    }

    public Integer getCrmid() {
        return crmid;
    }

    public void setCrmid(Integer crmid) {
        this.crmid = crmid;
    }

    public Object getIsGmailApptActive() {
        return isGmailApptActive;
    }

    public void setIsGmailApptActive(Object isGmailApptActive) {
        this.isGmailApptActive = isGmailApptActive;
    }

    public String getGmailCalenderId() {
        return gmailCalenderId;
    }

    public void setGmailCalenderId(String gmailCalenderId) {
        this.gmailCalenderId = gmailCalenderId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsAllDay() {
        return isAllDay;
    }

    public void setIsAllDay(Boolean isAllDay) {
        this.isAllDay = isAllDay;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Boolean getIsSend() {
        return isSend;
    }

    public void setIsSend(Boolean isSend) {
        this.isSend = isSend;
    }

    public String getViaReminder() {
        return viaReminder;
    }

    public void setViaReminder(String viaReminder) {
        this.viaReminder = viaReminder;
    }

    public Object getAgentIds() {
        return agentIds;
    }

    public void setAgentIds(Object agentIds) {
        this.agentIds = agentIds;
    }

    public Object getStartTime() {
        return startTime;
    }

    public void setStartTime(Object startTime) {
        this.startTime = startTime;
    }

    public Object getEndTime() {
        return endTime;
    }

    public void setEndTime(Object endTime) {
        this.endTime = endTime;
    }

    public Object getCalendarType() {
        return calendarType;
    }

    public void setCalendarType(Object calendarType) {
        this.calendarType = calendarType;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public List<VtCRMLeadAppoinmentDetailCustom> getVtCRMLeadAppoinmentDetailCustom() {
        return vtCRMLeadAppoinmentDetailCustom;
    }

    public void setVtCRMLeadAppoinmentDetailCustom(List<VtCRMLeadAppoinmentDetailCustom> vtCRMLeadAppoinmentDetailCustom) {
        this.vtCRMLeadAppoinmentDetailCustom = vtCRMLeadAppoinmentDetailCustom;
    }

}
