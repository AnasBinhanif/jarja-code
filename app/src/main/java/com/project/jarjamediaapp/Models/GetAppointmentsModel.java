package com.project.jarjamediaapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.List;

public class GetAppointmentsModel extends BaseResponse  {

    @SerializedName("data")
    @Expose
    public Data data;


    public static class Data implements Parcelable {

        @SerializedName("data")
        @Expose
        public List<Datum> data = null;
        @SerializedName("totalRecordCount")
        @Expose
        public Integer totalRecordCount;

        protected Data(Parcel in) {
            if (in.readByte() == 0) {
                totalRecordCount = null;
            } else {
                totalRecordCount = in.readInt();
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
            if (totalRecordCount == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(totalRecordCount);
            }
        }

        public static class Datum implements Parcelable{

            @SerializedName("vt_CRM_Lead_Custom")
            @Expose
            public VtCRMLeadCustom vtCRMLeadCustom;
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
            public String gmailCalenderId;
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
            public List<VtCRMLeadAppoinmentDetailCustom> vtCRMLeadAppoinmentDetailCustom = null;

            protected Datum(Parcel in) {
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
                vtCRMLeadAppoinmentDetailCustom = in.createTypedArrayList(VtCRMLeadAppoinmentDetailCustom.CREATOR);
            }

            public static final Creator<Datum> CREATOR = new Creator<Datum>() {
                @Override
                public Datum createFromParcel(Parcel in) {
                    return new Datum(in);
                }

                @Override
                public Datum[] newArray(int size) {
                    return new Datum[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
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

            public static class VtCRMLeadAppoinmentDetailCustom implements Parcelable {

                @SerializedName("leadAppoinmentDetailID")
                @Expose
                public Integer leadAppoinmentDetailID;
                @SerializedName("leadAppoinmentID")
                @Expose
                public Integer leadAppoinmentID;
                @SerializedName("agentID")
                @Expose
                public String agentID;
                @SerializedName("agentDecryptedID")
                @Expose
                public Integer agentDecryptedID;
                @SerializedName("agentName")
                @Expose
                public String agentName;
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
                public Object vtCRMLeadAppoinmentCustom;

                protected VtCRMLeadAppoinmentDetailCustom(Parcel in) {
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
                    if (in.readByte() == 0) {
                        agentDecryptedID = null;
                    } else {
                        agentDecryptedID = in.readInt();
                    }
                    agentName = in.readString();
                    byte tmpIsSeen = in.readByte();
                    isSeen = tmpIsSeen == 0 ? null : tmpIsSeen == 1;
                    if (in.readByte() == 0) {
                        crmid = null;
                    } else {
                        crmid = in.readInt();
                    }
                }

                public static final Creator<VtCRMLeadAppoinmentDetailCustom> CREATOR = new Creator<VtCRMLeadAppoinmentDetailCustom>() {
                    @Override
                    public VtCRMLeadAppoinmentDetailCustom createFromParcel(Parcel in) {
                        return new VtCRMLeadAppoinmentDetailCustom(in);
                    }

                    @Override
                    public VtCRMLeadAppoinmentDetailCustom[] newArray(int size) {
                        return new VtCRMLeadAppoinmentDetailCustom[size];
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

                public Integer getAgentDecryptedID() {
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

                public Object getVtCRMLeadAppoinmentCustom() {
                    return vtCRMLeadAppoinmentCustom;
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
                    if (agentDecryptedID == null) {
                        dest.writeByte((byte) 0);
                    } else {
                        dest.writeByte((byte) 1);
                        dest.writeInt(agentDecryptedID);
                    }
                    dest.writeString(agentName);
                    dest.writeByte((byte) (isSeen == null ? 0 : isSeen ? 1 : 2));
                    if (crmid == null) {
                        dest.writeByte((byte) 0);
                    } else {
                        dest.writeByte((byte) 1);
                        dest.writeInt(crmid);
                    }
                }
            }

            public static class VtCRMLeadCustom implements Parcelable  {

                @SerializedName("leadID")
                @Expose
                public String leadID;
                @SerializedName("decryptedLeadID")
                @Expose
                public Integer decryptedLeadID;
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
                public String description;
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
                public Object updateBy;
                @SerializedName("updateDate")
                @Expose
                public Object updateDate;
                @SerializedName("company")
                @Expose
                public Object company;
                @SerializedName("subscribe")
                @Expose
                public Object subscribe;
                @SerializedName("msgSubs")
                @Expose
                public Object msgSubs;
                @SerializedName("county")
                @Expose
                public Object county;
                @SerializedName("spousname")
                @Expose
                public Object spousname;
                @SerializedName("imageURL")
                @Expose
                public Object imageURL;
                @SerializedName("leadScoreId")
                @Expose
                public Object leadScoreId;
                @SerializedName("timeFrameId")
                @Expose
                public Object timeFrameId;
                @SerializedName("sourceId")
                @Expose
                public Object sourceId;
                @SerializedName("isOpenHouseSource")
                @Expose
                public Object isOpenHouseSource;
                @SerializedName("isCreated")
                @Expose
                public Boolean isCreated;
                @SerializedName("state2")
                @Expose
                public Object state2;
                @SerializedName("city2")
                @Expose
                public Object city2;
                @SerializedName("zipcode2")
                @Expose
                public Object zipcode2;
                @SerializedName("countryid")
                @Expose
                public Integer countryid;
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
                public Object emailSubscribed;
                @SerializedName("dateOfBirth")
                @Expose
                public Object dateOfBirth;
                @SerializedName("dateOfMarriage")
                @Expose
                public Object dateOfMarriage;
                @SerializedName("batchNo")
                @Expose
                public Object batchNo;
                @SerializedName("rowNum")
                @Expose
                public Object rowNum;
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
                public Boolean isBirthDayNotify;
                @SerializedName("isAnniversaryNotify")
                @Expose
                public Boolean isAnniversaryNotify;
                @SerializedName("leadTypeID")
                @Expose
                public Integer leadTypeID;

                protected VtCRMLeadCustom(Parcel in) {
                    leadID = in.readString();
                    if (in.readByte() == 0) {
                        decryptedLeadID = null;
                    } else {
                        decryptedLeadID = in.readInt();
                    }
                    firstName = in.readString();
                    lastName = in.readString();
                    primaryEmail = in.readString();
                    primaryPhone = in.readString();
                    address = in.readString();
                    street = in.readString();
                    zipcode = in.readString();
                    city = in.readString();
                    state = in.readString();
                    description = in.readString();
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
                    byte tmpIsCreated = in.readByte();
                    isCreated = tmpIsCreated == 0 ? null : tmpIsCreated == 1;
                    if (in.readByte() == 0) {
                        countryid = null;
                    } else {
                        countryid = in.readInt();
                    }
                    byte tmpIsBulkSource = in.readByte();
                    isBulkSource = tmpIsBulkSource == 0 ? null : tmpIsBulkSource == 1;
                    if (in.readByte() == 0) {
                        leadScore = null;
                    } else {
                        leadScore = in.readInt();
                    }
                    byte tmpIsNew = in.readByte();
                    isNew = tmpIsNew == 0 ? null : tmpIsNew == 1;
                    byte tmpIsLock = in.readByte();
                    isLock = tmpIsLock == 0 ? null : tmpIsLock == 1;
                    byte tmpIsBirthDayNotify = in.readByte();
                    isBirthDayNotify = tmpIsBirthDayNotify == 0 ? null : tmpIsBirthDayNotify == 1;
                    byte tmpIsAnniversaryNotify = in.readByte();
                    isAnniversaryNotify = tmpIsAnniversaryNotify == 0 ? null : tmpIsAnniversaryNotify == 1;
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

                public Integer getDecryptedLeadID() {
                    return decryptedLeadID;
                }

                public Object getFirstName() {
                    return firstName;
                }

                public Object getLastName() {
                    return lastName;
                }

                public String getPrimaryEmail() {
                    return primaryEmail;
                }

                public Object getPrimaryPhone() {
                    return primaryPhone;
                }

                public Object getAddress() {
                    return address;
                }

                public Object getStreet() {
                    return street;
                }

                public Object getZipcode() {
                    return zipcode;
                }

                public Object getCity() {
                    return city;
                }

                public Object getState() {
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

                public Object getUpdateBy() {
                    return updateBy;
                }

                public Object getUpdateDate() {
                    return updateDate;
                }

                public Object getCompany() {
                    return company;
                }

                public Object getSubscribe() {
                    return subscribe;
                }

                public Object getMsgSubs() {
                    return msgSubs;
                }

                public Object getCounty() {
                    return county;
                }

                public Object getSpousname() {
                    return spousname;
                }

                public Object getImageURL() {
                    return imageURL;
                }

                public Object getLeadScoreId() {
                    return leadScoreId;
                }

                public Object getTimeFrameId() {
                    return timeFrameId;
                }

                public Object getSourceId() {
                    return sourceId;
                }

                public Object getIsOpenHouseSource() {
                    return isOpenHouseSource;
                }

                public Boolean getCreated() {
                    return isCreated;
                }

                public Object getState2() {
                    return state2;
                }

                public Object getCity2() {
                    return city2;
                }

                public Object getZipcode2() {
                    return zipcode2;
                }

                public Integer getCountryid() {
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

                public Object getEmailSubscribed() {
                    return emailSubscribed;
                }

                public Object getDateOfBirth() {
                    return dateOfBirth;
                }

                public Object getDateOfMarriage() {
                    return dateOfMarriage;
                }

                public Object getBatchNo() {
                    return batchNo;
                }

                public Object getRowNum() {
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

                public Boolean getBirthDayNotify() {
                    return isBirthDayNotify;
                }

                public Boolean getAnniversaryNotify() {
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
                    if (decryptedLeadID == null) {
                        dest.writeByte((byte) 0);
                    } else {
                        dest.writeByte((byte) 1);
                        dest.writeInt(decryptedLeadID);
                    }
                    dest.writeString(firstName);
                    dest.writeString(lastName);
                    dest.writeString(primaryEmail);
                    dest.writeString(primaryPhone);
                    dest.writeString(address);
                    dest.writeString(street);
                    dest.writeString(zipcode);
                    dest.writeString(city);
                    dest.writeString(state);
                    dest.writeString(description);
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
                    dest.writeByte((byte) (isCreated == null ? 0 : isCreated ? 1 : 2));
                    if (countryid == null) {
                        dest.writeByte((byte) 0);
                    } else {
                        dest.writeByte((byte) 1);
                        dest.writeInt(countryid);
                    }
                    dest.writeByte((byte) (isBulkSource == null ? 0 : isBulkSource ? 1 : 2));
                    if (leadScore == null) {
                        dest.writeByte((byte) 0);
                    } else {
                        dest.writeByte((byte) 1);
                        dest.writeInt(leadScore);
                    }
                    dest.writeByte((byte) (isNew == null ? 0 : isNew ? 1 : 2));
                    dest.writeByte((byte) (isLock == null ? 0 : isLock ? 1 : 2));
                    dest.writeByte((byte) (isBirthDayNotify == null ? 0 : isBirthDayNotify ? 1 : 2));
                    dest.writeByte((byte) (isAnniversaryNotify == null ? 0 : isAnniversaryNotify ? 1 : 2));
                    if (leadTypeID == null) {
                        dest.writeByte((byte) 0);
                    } else {
                        dest.writeByte((byte) 1);
                        dest.writeInt(leadTypeID);
                    }
                }
            }

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

            public Boolean getAppointmentFixed() {
                return isAppointmentFixed;
            }

            public void setAppointmentFixed(Boolean appointmentFixed) {
                isAppointmentFixed = appointmentFixed;
            }

            public Boolean getAppointmentAttend() {
                return isAppointmentAttend;
            }

            public void setAppointmentAttend(Boolean appointmentAttend) {
                isAppointmentAttend = appointmentAttend;
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

            public Boolean getDeleted() {
                return isDeleted;
            }

            public void setDeleted(Boolean deleted) {
                isDeleted = deleted;
            }

            public Boolean getAllDay() {
                return isAllDay;
            }

            public void setAllDay(Boolean allDay) {
                isAllDay = allDay;
            }

            public Object getReminderDate() {
                return reminderDate;
            }

            public void setReminderDate(Object reminderDate) {
                this.reminderDate = reminderDate;
            }

            public Integer getInterval() {
                return interval;
            }

            public void setInterval(Integer interval) {
                this.interval = interval;
            }

            public Boolean getSend() {
                return isSend;
            }

            public void setSend(Boolean send) {
                isSend = send;
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

            public Boolean getCompleted() {
                return isCompleted;
            }

            public void setCompleted(Boolean completed) {
                isCompleted = completed;
            }

            public List<VtCRMLeadAppoinmentDetailCustom> getVtCRMLeadAppoinmentDetailCustom() {
                return vtCRMLeadAppoinmentDetailCustom;
            }

            public void setVtCRMLeadAppoinmentDetailCustom(List<VtCRMLeadAppoinmentDetailCustom> vtCRMLeadAppoinmentDetailCustom) {
                this.vtCRMLeadAppoinmentDetailCustom = vtCRMLeadAppoinmentDetailCustom;
            }
        }

        public List<Datum> getData() {
            return data;
        }

        public Integer getTotalRecordCount() {
            return totalRecordCount;
        }
    }

    public Data getData() {
        return data;
    }
}
