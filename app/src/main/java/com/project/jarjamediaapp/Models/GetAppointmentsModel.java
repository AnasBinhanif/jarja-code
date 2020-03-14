package com.project.jarjamediaapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class GetAppointmentsModel extends BaseResponse implements Parcelable {

    @SerializedName("data")
    @Expose
    public ArrayList<Data> data = new ArrayList<>();

    protected GetAppointmentsModel(Parcel in) {
    }

    public static final Creator<GetAppointmentsModel> CREATOR = new Creator<GetAppointmentsModel>() {
        @Override
        public GetAppointmentsModel createFromParcel(Parcel in) {
            return new GetAppointmentsModel(in);
        }

        @Override
        public GetAppointmentsModel[] newArray(int size) {
            return new GetAppointmentsModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static class Data implements Parcelable{

        @SerializedName("vt_CRM_Lead_Custom")
        @Expose
        public VtCRMLeadCustom leadsData;
        @SerializedName("orderBy")
        @Expose
        public Integer orderBy;
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
        public Object eventID;
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
        @SerializedName("createdBy")
        @Expose
        public Integer createdBy;
        @SerializedName("createdDate")
        @Expose
        public String createdDate;
        @SerializedName("updateBy")
        @Expose
        public Object updateBy;
        @SerializedName("updateDate")
        @Expose
        public Object updateDate;
        @SerializedName("countryName")
        @Expose
        public Object countryName;
        @SerializedName("cityName")
        @Expose
        public Object cityName;
        @SerializedName("crmid")
        @Expose
        public Integer crmid;
        @SerializedName("isGmailApptActive")
        @Expose
        public Object isGmailApptActive;
        @SerializedName("gmailCalenderId")
        @Expose
        public Object gmailCalenderId;
        @SerializedName("isDeleted")
        @Expose
        public Boolean isDeleted;
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
        @SerializedName("agentIds")
        @Expose
        public Object agentIds;
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
        @SerializedName("vt_CRM_LeadAppoinmentDetail_Custom")
        @Expose
        public List<VtCRMLeadAppointmentDetailCustom> vtCRMLeadAppoinmentDetailCustom = null;

        protected Data(Parcel in) {
            leadsData = in.readParcelable(VtCRMLeadCustom.class.getClassLoader());
            if (in.readByte() == 0) {
                orderBy = null;
            } else {
                orderBy = in.readInt();
            }
            leadAppoinmentID = in.readString();
            leadID = in.readString();
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
            byte tmpIsDeleted = in.readByte();
            isDeleted = tmpIsDeleted == 0 ? null : tmpIsDeleted == 1;
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
            byte tmpIsCompleted = in.readByte();
            isCompleted = tmpIsCompleted == 0 ? null : tmpIsCompleted == 1;
            vtCRMLeadAppoinmentDetailCustom = in.createTypedArrayList(VtCRMLeadAppointmentDetailCustom.CREATOR);
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
            dest.writeParcelable(leadsData, flags);
            if (orderBy == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(orderBy);
            }
            dest.writeString(leadAppoinmentID);
            dest.writeString(leadID);
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
            dest.writeByte((byte) (isDeleted == null ? 0 : isDeleted ? 1 : 2));
            dest.writeByte((byte) (isAllDay == null ? 0 : isAllDay ? 1 : 2));
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

        public static class VtCRMLeadAppointmentDetailCustom implements Parcelable {

            @SerializedName("leadAppoinmentDetailID")
            @Expose
            public Integer leadAppoinmentDetailID;
            @SerializedName("leadAppoinmentID")
            @Expose
            public Integer leadAppoinmentID;
            @SerializedName("agentID")
            @Expose
            public String agentID;
            @SerializedName("agentName")
            @Expose
            public String agentName;
            @SerializedName("agentDecryptedID")
            @Expose
            public Integer agentDecryptedID;
            @SerializedName("isSeen")
            @Expose
            public Boolean isSeen;
            @SerializedName("seenDate")
            @Expose
            public Object seenDate;
            @SerializedName("crmid")
            @Expose
            public Integer crmid;
            @SerializedName("vt_CRM_Agent_Custom")
            @Expose
            public Object vtCRMAgentCustom;
            @SerializedName("vt_CRM_LeadAppoinment_Custom")
            @Expose
            public Object vtCRMLeadAppointmentCustom;

            protected VtCRMLeadAppointmentDetailCustom(Parcel in) {
                if (in.readByte() == 0) {
                    leadAppoinmentDetailID = null;
                } else {
                    leadAppoinmentDetailID = in.readInt();
                }
                if (in.readByte() == 0) {
                    leadAppoinmentID = null;
                } else {
                    leadAppoinmentID = in.readInt();
                }
                agentID = in.readString();
                agentName = in.readString();
                agentDecryptedID = in.readInt();
                byte tmpIsSeen = in.readByte();
                isSeen = tmpIsSeen == 0 ? null : tmpIsSeen == 1;
                if (in.readByte() == 0) {
                    crmid = null;
                } else {
                    crmid = in.readInt();
                }
            }

            public static final Creator<VtCRMLeadAppointmentDetailCustom> CREATOR = new Creator<VtCRMLeadAppointmentDetailCustom>() {
                @Override
                public VtCRMLeadAppointmentDetailCustom createFromParcel(Parcel in) {
                    return new VtCRMLeadAppointmentDetailCustom(in);
                }

                @Override
                public VtCRMLeadAppointmentDetailCustom[] newArray(int size) {
                    return new VtCRMLeadAppointmentDetailCustom[size];
                }
            };

            public Integer getLeadAppoinmentDetailID() {
                return leadAppoinmentDetailID;
            }

            public Integer getLeadAppoinmentID() {
                return leadAppoinmentID;
            }

            public String getAgentID() {
                return agentID;
            }

            public Integer getDecryptedAgentID() {
                return agentDecryptedID;
            }

            public String getAgentName() {
                return agentName;
            }

            public Boolean getSeen() {
                return isSeen;
            }

            public Object getSeenDate() {
                return seenDate;
            }

            public Integer getCrmid() {
                return crmid;
            }

            public Object getVtCRMAgentCustom() {
                return vtCRMAgentCustom;
            }

            public Object getVtCRMLeadAppointmentCustom() {
                return vtCRMLeadAppointmentCustom;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                if (leadAppoinmentDetailID == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(leadAppoinmentDetailID);
                }
                if (leadAppoinmentID == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(leadAppoinmentID);
                }
                dest.writeString(agentID);
                dest.writeString(agentName);
                dest.writeInt(agentDecryptedID);
                dest.writeByte((byte) (isSeen == null ? 0 : isSeen ? 1 : 2));
                if (crmid == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(crmid);
                }
            }
        }

        public static class VtCRMLeadCustom implements Parcelable {

            @SerializedName("leadID")
            @Expose
            public String leadID;
            @SerializedName("firstName")
            @Expose
            public String firstName;
            @SerializedName("lastName")
            @Expose
            public String lastName;
            @SerializedName("primaryEmail")
            @Expose
            public String primaryEmail;
            @SerializedName("primaryPhone")
            @Expose
            public String primaryPhone;
            @SerializedName("address")
            @Expose
            public String address;
            @SerializedName("street")
            @Expose
            public String street;
            @SerializedName("zipcode")
            @Expose
            public String zipcode;
            @SerializedName("city")
            @Expose
            public String city;
            @SerializedName("state")
            @Expose
            public String state;
            @SerializedName("description")
            @Expose
            public Object description;
            @SerializedName("source")
            @Expose
            public String source;
            @SerializedName("crmid")
            @Expose
            public Integer crmid;
            @SerializedName("sendWelcomeEmail")
            @Expose
            public Boolean sendWelcomeEmail;
            @SerializedName("isDeleted")
            @Expose
            public Boolean isDeleted;
            @SerializedName("createBy")
            @Expose
            public Integer createBy;
            @SerializedName("createDate")
            @Expose
            public String createDate;
            @SerializedName("updateBy")
            @Expose
            public Integer updateBy;
            @SerializedName("updateDate")
            @Expose
            public String updateDate;
            @SerializedName("company")
            @Expose
            public String company;
            @SerializedName("subscribe")
            @Expose
            public Object subscribe;
            @SerializedName("msgSubs")
            @Expose
            public Object msgSubs;
            @SerializedName("county")
            @Expose
            public String county;
            @SerializedName("spousname")
            @Expose
            public String spousname;
            @SerializedName("imageURL")
            @Expose
            public Object imageURL;
            @SerializedName("leadScoreId")
            @Expose
            public Object leadScoreId;
            @SerializedName("timeFrameId")
            @Expose
            public Integer timeFrameId;
            @SerializedName("sourceId")
            @Expose
            public Integer sourceId;
            @SerializedName("isOpenHouseSource")
            @Expose
            public Object isOpenHouseSource;
            @SerializedName("isCreated")
            @Expose
            public Boolean isCreated;
            @SerializedName("state2")
            @Expose
            public String state2;
            @SerializedName("city2")
            @Expose
            public String city2;
            @SerializedName("zipcode2")
            @Expose
            public String zipcode2;
            @SerializedName("countryid")
            @Expose
            public Object countryid;
            @SerializedName("isBulkSource")
            @Expose
            public Boolean isBulkSource;
            @SerializedName("leadScore")
            @Expose
            public Integer leadScore;
            @SerializedName("houseNumber1")
            @Expose
            public Object houseNumber1;
            @SerializedName("houseNumber2")
            @Expose
            public Object houseNumber2;
            @SerializedName("apiLeadID")
            @Expose
            public Object apiLeadID;
            @SerializedName("unbounceNumber")
            @Expose
            public Object unbounceNumber;
            @SerializedName("mid")
            @Expose
            public Object mid;
            @SerializedName("exchangeEmailInboxID")
            @Expose
            public Object exchangeEmailInboxID;
            @SerializedName("isZapier")
            @Expose
            public Object isZapier;
            @SerializedName("isLeadDistributionRoundRobin")
            @Expose
            public Object isLeadDistributionRoundRobin;
            @SerializedName("isLeadDistributionManual")
            @Expose
            public Object isLeadDistributionManual;
            @SerializedName("isLeadDistributionLeadRouting")
            @Expose
            public Object isLeadDistributionLeadRouting;
            @SerializedName("emailSubscribed")
            @Expose
            public Boolean emailSubscribed;
            @SerializedName("dateOfBirth")
            @Expose
            public String dateOfBirth;
            @SerializedName("dateOfMarriage")
            @Expose
            public String dateOfMarriage;
            @SerializedName("batchNo")
            @Expose
            public String batchNo;
            @SerializedName("rowNum")
            @Expose
            public Integer rowNum;
            @SerializedName("isNew")
            @Expose
            public Boolean isNew;
            @SerializedName("isLock")
            @Expose
            public Boolean isLock;
            @SerializedName("sourceDate")
            @Expose
            public Object sourceDate;
            @SerializedName("isTransClose")
            @Expose
            public Object isTransClose;
            @SerializedName("isTransTwoClose")
            @Expose
            public Object isTransTwoClose;
            @SerializedName("isBirthDayNotify")
            @Expose
            public Object isBirthDayNotify;
            @SerializedName("isAnniversaryNotify")
            @Expose
            public Object isAnniversaryNotify;
            @SerializedName("leadTypeID")
            @Expose
            public Integer leadTypeID;

            protected VtCRMLeadCustom(Parcel in) {
                leadID = in.readString();
                firstName = in.readString();
                lastName = in.readString();
                primaryEmail = in.readString();
                primaryPhone = in.readString();
                address = in.readString();
                street = in.readString();
                zipcode = in.readString();
                city = in.readString();
                state = in.readString();
                source = in.readString();
                if (in.readByte() == 0) {
                    crmid = null;
                } else {
                    crmid = in.readInt();
                }
                byte tmpSendWelcomeEmail = in.readByte();
                sendWelcomeEmail = tmpSendWelcomeEmail == 0 ? null : tmpSendWelcomeEmail == 1;
                byte tmpIsDeleted = in.readByte();
                isDeleted = tmpIsDeleted == 0 ? null : tmpIsDeleted == 1;
                if (in.readByte() == 0) {
                    createBy = null;
                } else {
                    createBy = in.readInt();
                }
                createDate = in.readString();
                if (in.readByte() == 0) {
                    updateBy = null;
                } else {
                    updateBy = in.readInt();
                }
                updateDate = in.readString();
                company = in.readString();
                county = in.readString();
                spousname = in.readString();
                if (in.readByte() == 0) {
                    timeFrameId = null;
                } else {
                    timeFrameId = in.readInt();
                }
                if (in.readByte() == 0) {
                    sourceId = null;
                } else {
                    sourceId = in.readInt();
                }
                byte tmpIsCreated = in.readByte();
                isCreated = tmpIsCreated == 0 ? null : tmpIsCreated == 1;
                state2 = in.readString();
                city2 = in.readString();
                zipcode2 = in.readString();
                byte tmpIsBulkSource = in.readByte();
                isBulkSource = tmpIsBulkSource == 0 ? null : tmpIsBulkSource == 1;
                if (in.readByte() == 0) {
                    leadScore = null;
                } else {
                    leadScore = in.readInt();
                }
                byte tmpEmailSubscribed = in.readByte();
                emailSubscribed = tmpEmailSubscribed == 0 ? null : tmpEmailSubscribed == 1;
                dateOfBirth = in.readString();
                dateOfMarriage = in.readString();
                batchNo = in.readString();
                if (in.readByte() == 0) {
                    rowNum = null;
                } else {
                    rowNum = in.readInt();
                }
                byte tmpIsNew = in.readByte();
                isNew = tmpIsNew == 0 ? null : tmpIsNew == 1;
                byte tmpIsLock = in.readByte();
                isLock = tmpIsLock == 0 ? null : tmpIsLock == 1;
                if (in.readByte() == 0) {
                    leadTypeID = null;
                } else {
                    leadTypeID = in.readInt();
                }
            }

            public static final Creator<VtCRMLeadCustom> CREATOR = new Creator<VtCRMLeadCustom>() {
                @Override
                public VtCRMLeadCustom createFromParcel(Parcel in) {
                    return new VtCRMLeadCustom(in);
                }

                @Override
                public VtCRMLeadCustom[] newArray(int size) {
                    return new VtCRMLeadCustom[size];
                }
            };

            public String getLeadID() {
                return leadID;
            }

            public String getFirstName() {
                return firstName;
            }

            public String getLastName() {
                return lastName;
            }

            public String getPrimaryEmail() {
                return primaryEmail;
            }

            public String getPrimaryPhone() {
                return primaryPhone;
            }

            public String getAddress() {
                return address;
            }

            public String getStreet() {
                return street;
            }

            public String getZipcode() {
                return zipcode;
            }

            public String getCity() {
                return city;
            }

            public String getState() {
                return state;
            }

            public Object getDescription() {
                return description;
            }

            public String getSource() {
                return source;
            }

            public Integer getCrmid() {
                return crmid;
            }

            public Boolean getSendWelcomeEmail() {
                return sendWelcomeEmail;
            }

            public Boolean getDeleted() {
                return isDeleted;
            }

            public Integer getCreateBy() {
                return createBy;
            }

            public String getCreateDate() {
                return createDate;
            }

            public Integer getUpdateBy() {
                return updateBy;
            }

            public String getUpdateDate() {
                return updateDate;
            }

            public String getCompany() {
                return company;
            }

            public Object getSubscribe() {
                return subscribe;
            }

            public Object getMsgSubs() {
                return msgSubs;
            }

            public String getCounty() {
                return county;
            }

            public String getSpousname() {
                return spousname;
            }

            public Object getImageURL() {
                return imageURL;
            }

            public Object getLeadScoreId() {
                return leadScoreId;
            }

            public Integer getTimeFrameId() {
                return timeFrameId;
            }

            public Integer getSourceId() {
                return sourceId;
            }

            public Object getIsOpenHouseSource() {
                return isOpenHouseSource;
            }

            public Boolean getCreated() {
                return isCreated;
            }

            public String getState2() {
                return state2;
            }

            public String getCity2() {
                return city2;
            }

            public String getZipcode2() {
                return zipcode2;
            }

            public Object getCountryid() {
                return countryid;
            }

            public Boolean getBulkSource() {
                return isBulkSource;
            }

            public Integer getLeadScore() {
                return leadScore;
            }

            public Object getHouseNumber1() {
                return houseNumber1;
            }

            public Object getHouseNumber2() {
                return houseNumber2;
            }

            public Object getApiLeadID() {
                return apiLeadID;
            }

            public Object getUnbounceNumber() {
                return unbounceNumber;
            }

            public Object getMid() {
                return mid;
            }

            public Object getExchangeEmailInboxID() {
                return exchangeEmailInboxID;
            }

            public Object getIsZapier() {
                return isZapier;
            }

            public Object getIsLeadDistributionRoundRobin() {
                return isLeadDistributionRoundRobin;
            }

            public Object getIsLeadDistributionManual() {
                return isLeadDistributionManual;
            }

            public Object getIsLeadDistributionLeadRouting() {
                return isLeadDistributionLeadRouting;
            }

            public Boolean getEmailSubscribed() {
                return emailSubscribed;
            }

            public String getDateOfBirth() {
                return dateOfBirth;
            }

            public String getDateOfMarriage() {
                return dateOfMarriage;
            }

            public String getBatchNo() {
                return batchNo;
            }

            public Integer getRowNum() {
                return rowNum;
            }

            public Boolean getNew() {
                return isNew;
            }

            public Boolean getLock() {
                return isLock;
            }

            public Object getSourceDate() {
                return sourceDate;
            }

            public Object getIsTransClose() {
                return isTransClose;
            }

            public Object getIsTransTwoClose() {
                return isTransTwoClose;
            }

            public Object getIsBirthDayNotify() {
                return isBirthDayNotify;
            }

            public Object getIsAnniversaryNotify() {
                return isAnniversaryNotify;
            }

            public Integer getLeadTypeID() {
                return leadTypeID;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(leadID);
                dest.writeString(firstName);
                dest.writeString(lastName);
                dest.writeString(primaryEmail);
                dest.writeString(primaryPhone);
                dest.writeString(address);
                dest.writeString(street);
                dest.writeString(zipcode);
                dest.writeString(city);
                dest.writeString(state);
                dest.writeString(source);
                if (crmid == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(crmid);
                }
                dest.writeByte((byte) (sendWelcomeEmail == null ? 0 : sendWelcomeEmail ? 1 : 2));
                dest.writeByte((byte) (isDeleted == null ? 0 : isDeleted ? 1 : 2));
                if (createBy == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(createBy);
                }
                dest.writeString(createDate);
                if (updateBy == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(updateBy);
                }
                dest.writeString(updateDate);
                dest.writeString(company);
                dest.writeString(county);
                dest.writeString(spousname);
                if (timeFrameId == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(timeFrameId);
                }
                if (sourceId == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(sourceId);
                }
                dest.writeByte((byte) (isCreated == null ? 0 : isCreated ? 1 : 2));
                dest.writeString(state2);
                dest.writeString(city2);
                dest.writeString(zipcode2);
                dest.writeByte((byte) (isBulkSource == null ? 0 : isBulkSource ? 1 : 2));
                if (leadScore == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(leadScore);
                }
                dest.writeByte((byte) (emailSubscribed == null ? 0 : emailSubscribed ? 1 : 2));
                dest.writeString(dateOfBirth);
                dest.writeString(dateOfMarriage);
                dest.writeString(batchNo);
                if (rowNum == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(rowNum);
                }
                dest.writeByte((byte) (isNew == null ? 0 : isNew ? 1 : 2));
                dest.writeByte((byte) (isLock == null ? 0 : isLock ? 1 : 2));
                if (leadTypeID == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(leadTypeID);
                }
            }
        }

        public VtCRMLeadCustom getLeadsData() {
            return leadsData;
        }

        public Integer getOrderBy() {
            return orderBy;
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

        public Object getEventID() {
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

        public Integer getCreatedBy() {
            return createdBy;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public Object getUpdateDate() {
            return updateDate;
        }

        public Object getCountryName() {
            return countryName;
        }

        public Object getCityName() {
            return cityName;
        }

        public Integer getCrmid() {
            return crmid;
        }

        public Object getIsGmailApptActive() {
            return isGmailApptActive;
        }

        public Object getGmailCalenderId() {
            return gmailCalenderId;
        }

        public Boolean getDeleted() {
            return isDeleted;
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

        public Object getAgentIds() {
            return agentIds;
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

        public List<VtCRMLeadAppointmentDetailCustom> getVtCRMLeadAppoinmentDetailCustom() {
            return vtCRMLeadAppoinmentDetailCustom;
        }
    }

    public ArrayList<Data> getData() {
        return data;
    }
}
