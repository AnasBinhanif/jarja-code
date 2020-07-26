
package com.project.jarjamediaapp.Activities.add_appointment;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VtCRMLeadCustom implements Parcelable {

    @SerializedName("leadID")
    @Expose
    private String leadID;
    @SerializedName("decryptedLeadID")
    @Expose
    private Integer decryptedLeadID;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("primaryEmail")
    @Expose
    private Object primaryEmail;
    @SerializedName("primaryPhone")
    @Expose
    private String primaryPhone;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("street")
    @Expose
    private Object street;
    @SerializedName("zipcode")
    @Expose
    private Object zipcode;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("state")
    @Expose
    private Object state;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("crmid")
    @Expose
    private Integer crmid;
    @SerializedName("sendWelcomeEmail")
    @Expose
    private Boolean sendWelcomeEmail;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("createBy")
    @Expose
    private Integer createBy;
    @SerializedName("createDate")
    @Expose
    private String createDate;
    @SerializedName("updateBy")
    @Expose
    private Object updateBy;
    @SerializedName("updateDate")
    @Expose
    private Object updateDate;
    @SerializedName("company")
    @Expose
    private Object company;
    @SerializedName("subscribe")
    @Expose
    private Object subscribe;
    @SerializedName("msgSubs")
    @Expose
    private Object msgSubs;
    @SerializedName("county")
    @Expose
    private String county;
    @SerializedName("spousname")
    @Expose
    private String spousname;
    @SerializedName("imageURL")
    @Expose
    private Object imageURL;
    @SerializedName("leadScoreId")
    @Expose
    private Object leadScoreId;
    @SerializedName("timeFrameId")
    @Expose
    private Integer timeFrameId;
    @SerializedName("sourceId")
    @Expose
    private Integer sourceId;
    @SerializedName("isOpenHouseSource")
    @Expose
    private Object isOpenHouseSource;
    @SerializedName("isCreated")
    @Expose
    private Boolean isCreated;
    @SerializedName("state2")
    @Expose
    private String state2;
    @SerializedName("city2")
    @Expose
    private String city2;
    @SerializedName("zipcode2")
    @Expose
    private Object zipcode2;
    @SerializedName("countryid")
    @Expose
    private Object countryid;
    @SerializedName("isBulkSource")
    @Expose
    private Boolean isBulkSource;
    @SerializedName("leadScore")
    @Expose
    private Integer leadScore;
    @SerializedName("houseNumber1")
    @Expose
    private Object houseNumber1;
    @SerializedName("houseNumber2")
    @Expose
    private Object houseNumber2;
    @SerializedName("apiLeadID")
    @Expose
    private Object apiLeadID;
    @SerializedName("unbounceNumber")
    @Expose
    private Object unbounceNumber;
    @SerializedName("mid")
    @Expose
    private Object mid;
    @SerializedName("exchangeEmailInboxID")
    @Expose
    private Object exchangeEmailInboxID;
    @SerializedName("isZapier")
    @Expose
    private Object isZapier;
    @SerializedName("isLeadDistributionRoundRobin")
    @Expose
    private Object isLeadDistributionRoundRobin;
    @SerializedName("isLeadDistributionManual")
    @Expose
    private Object isLeadDistributionManual;
    @SerializedName("isLeadDistributionLeadRouting")
    @Expose
    private Object isLeadDistributionLeadRouting;
    @SerializedName("emailSubscribed")
    @Expose
    private Boolean emailSubscribed;
    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("dateOfMarriage")
    @Expose
    private String dateOfMarriage;
    @SerializedName("batchNo")
    @Expose
    private String batchNo;
    @SerializedName("rowNum")
    @Expose
    private Integer rowNum;
    @SerializedName("isNew")
    @Expose
    private Boolean isNew;
    @SerializedName("isLock")
    @Expose
    private Boolean isLock;
    @SerializedName("sourceDate")
    @Expose
    private Object sourceDate;
    @SerializedName("isTransClose")
    @Expose
    private Boolean isTransClose;
    @SerializedName("isTransTwoClose")
    @Expose
    private Object isTransTwoClose;
    @SerializedName("isBirthDayNotify")
    @Expose
    private Object isBirthDayNotify;
    @SerializedName("isAnniversaryNotify")
    @Expose
    private Object isAnniversaryNotify;
    @SerializedName("leadTypeID")
    @Expose
    private Integer leadTypeID;

    protected VtCRMLeadCustom(Parcel in) {
        leadID = in.readString();
        if (in.readByte() == 0) {
            decryptedLeadID = null;
        } else {
            decryptedLeadID = in.readInt();
        }
        firstName = in.readString();
        lastName = in.readString();
        primaryPhone = in.readString();
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
        byte tmpIsTransClose = in.readByte();
        isTransClose = tmpIsTransClose == 0 ? null : tmpIsTransClose == 1;
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

    public void setLeadID(String leadID) {
        this.leadID = leadID;
    }

    public Integer getDecryptedLeadID() {
        return decryptedLeadID;
    }

    public void setDecryptedLeadID(Integer decryptedLeadID) {
        this.decryptedLeadID = decryptedLeadID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Object getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(Object primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getStreet() {
        return street;
    }

    public void setStreet(Object street) {
        this.street = street;
    }

    public Object getZipcode() {
        return zipcode;
    }

    public void setZipcode(Object zipcode) {
        this.zipcode = zipcode;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getCrmid() {
        return crmid;
    }

    public void setCrmid(Integer crmid) {
        this.crmid = crmid;
    }

    public Boolean getSendWelcomeEmail() {
        return sendWelcomeEmail;
    }

    public void setSendWelcomeEmail(Boolean sendWelcomeEmail) {
        this.sendWelcomeEmail = sendWelcomeEmail;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    public Object getCompany() {
        return company;
    }

    public void setCompany(Object company) {
        this.company = company;
    }

    public Object getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Object subscribe) {
        this.subscribe = subscribe;
    }

    public Object getMsgSubs() {
        return msgSubs;
    }

    public void setMsgSubs(Object msgSubs) {
        this.msgSubs = msgSubs;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getSpousname() {
        return spousname;
    }

    public void setSpousname(String spousname) {
        this.spousname = spousname;
    }

    public Object getImageURL() {
        return imageURL;
    }

    public void setImageURL(Object imageURL) {
        this.imageURL = imageURL;
    }

    public Object getLeadScoreId() {
        return leadScoreId;
    }

    public void setLeadScoreId(Object leadScoreId) {
        this.leadScoreId = leadScoreId;
    }

    public Integer getTimeFrameId() {
        return timeFrameId;
    }

    public void setTimeFrameId(Integer timeFrameId) {
        this.timeFrameId = timeFrameId;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Object getIsOpenHouseSource() {
        return isOpenHouseSource;
    }

    public void setIsOpenHouseSource(Object isOpenHouseSource) {
        this.isOpenHouseSource = isOpenHouseSource;
    }

    public Boolean getIsCreated() {
        return isCreated;
    }

    public void setIsCreated(Boolean isCreated) {
        this.isCreated = isCreated;
    }

    public String getState2() {
        return state2;
    }

    public void setState2(String state2) {
        this.state2 = state2;
    }

    public String getCity2() {
        return city2;
    }

    public void setCity2(String city2) {
        this.city2 = city2;
    }

    public Object getZipcode2() {
        return zipcode2;
    }

    public void setZipcode2(Object zipcode2) {
        this.zipcode2 = zipcode2;
    }

    public Object getCountryid() {
        return countryid;
    }

    public void setCountryid(Object countryid) {
        this.countryid = countryid;
    }

    public Boolean getIsBulkSource() {
        return isBulkSource;
    }

    public void setIsBulkSource(Boolean isBulkSource) {
        this.isBulkSource = isBulkSource;
    }

    public Integer getLeadScore() {
        return leadScore;
    }

    public void setLeadScore(Integer leadScore) {
        this.leadScore = leadScore;
    }

    public Object getHouseNumber1() {
        return houseNumber1;
    }

    public void setHouseNumber1(Object houseNumber1) {
        this.houseNumber1 = houseNumber1;
    }

    public Object getHouseNumber2() {
        return houseNumber2;
    }

    public void setHouseNumber2(Object houseNumber2) {
        this.houseNumber2 = houseNumber2;
    }

    public Object getApiLeadID() {
        return apiLeadID;
    }

    public void setApiLeadID(Object apiLeadID) {
        this.apiLeadID = apiLeadID;
    }

    public Object getUnbounceNumber() {
        return unbounceNumber;
    }

    public void setUnbounceNumber(Object unbounceNumber) {
        this.unbounceNumber = unbounceNumber;
    }

    public Object getMid() {
        return mid;
    }

    public void setMid(Object mid) {
        this.mid = mid;
    }

    public Object getExchangeEmailInboxID() {
        return exchangeEmailInboxID;
    }

    public void setExchangeEmailInboxID(Object exchangeEmailInboxID) {
        this.exchangeEmailInboxID = exchangeEmailInboxID;
    }

    public Object getIsZapier() {
        return isZapier;
    }

    public void setIsZapier(Object isZapier) {
        this.isZapier = isZapier;
    }

    public Object getIsLeadDistributionRoundRobin() {
        return isLeadDistributionRoundRobin;
    }

    public void setIsLeadDistributionRoundRobin(Object isLeadDistributionRoundRobin) {
        this.isLeadDistributionRoundRobin = isLeadDistributionRoundRobin;
    }

    public Object getIsLeadDistributionManual() {
        return isLeadDistributionManual;
    }

    public void setIsLeadDistributionManual(Object isLeadDistributionManual) {
        this.isLeadDistributionManual = isLeadDistributionManual;
    }

    public Object getIsLeadDistributionLeadRouting() {
        return isLeadDistributionLeadRouting;
    }

    public void setIsLeadDistributionLeadRouting(Object isLeadDistributionLeadRouting) {
        this.isLeadDistributionLeadRouting = isLeadDistributionLeadRouting;
    }

    public Boolean getEmailSubscribed() {
        return emailSubscribed;
    }

    public void setEmailSubscribed(Boolean emailSubscribed) {
        this.emailSubscribed = emailSubscribed;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfMarriage() {
        return dateOfMarriage;
    }

    public void setDateOfMarriage(String dateOfMarriage) {
        this.dateOfMarriage = dateOfMarriage;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Boolean getIsLock() {
        return isLock;
    }

    public void setIsLock(Boolean isLock) {
        this.isLock = isLock;
    }

    public Object getSourceDate() {
        return sourceDate;
    }

    public void setSourceDate(Object sourceDate) {
        this.sourceDate = sourceDate;
    }

    public Boolean getIsTransClose() {
        return isTransClose;
    }

    public void setIsTransClose(Boolean isTransClose) {
        this.isTransClose = isTransClose;
    }

    public Object getIsTransTwoClose() {
        return isTransTwoClose;
    }

    public void setIsTransTwoClose(Object isTransTwoClose) {
        this.isTransTwoClose = isTransTwoClose;
    }

    public Object getIsBirthDayNotify() {
        return isBirthDayNotify;
    }

    public void setIsBirthDayNotify(Object isBirthDayNotify) {
        this.isBirthDayNotify = isBirthDayNotify;
    }

    public Object getIsAnniversaryNotify() {
        return isAnniversaryNotify;
    }

    public void setIsAnniversaryNotify(Object isAnniversaryNotify) {
        this.isAnniversaryNotify = isAnniversaryNotify;
    }

    public Integer getLeadTypeID() {
        return leadTypeID;
    }

    public void setLeadTypeID(Integer leadTypeID) {
        this.leadTypeID = leadTypeID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(leadID);
        if (decryptedLeadID == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(decryptedLeadID);
        }
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(primaryPhone);
        parcel.writeString(source);
        if (crmid == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(crmid);
        }
        parcel.writeByte((byte) (sendWelcomeEmail == null ? 0 : sendWelcomeEmail ? 1 : 2));
        parcel.writeByte((byte) (isDeleted == null ? 0 : isDeleted ? 1 : 2));
        if (createBy == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(createBy);
        }
        parcel.writeString(createDate);
        parcel.writeString(county);
        parcel.writeString(spousname);
        if (timeFrameId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(timeFrameId);
        }
        if (sourceId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(sourceId);
        }
        parcel.writeByte((byte) (isCreated == null ? 0 : isCreated ? 1 : 2));
        parcel.writeString(state2);
        parcel.writeString(city2);
        parcel.writeByte((byte) (isBulkSource == null ? 0 : isBulkSource ? 1 : 2));
        if (leadScore == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(leadScore);
        }
        parcel.writeByte((byte) (emailSubscribed == null ? 0 : emailSubscribed ? 1 : 2));
        parcel.writeString(dateOfBirth);
        parcel.writeString(dateOfMarriage);
        parcel.writeString(batchNo);
        if (rowNum == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(rowNum);
        }
        parcel.writeByte((byte) (isNew == null ? 0 : isNew ? 1 : 2));
        parcel.writeByte((byte) (isLock == null ? 0 : isLock ? 1 : 2));
        parcel.writeByte((byte) (isTransClose == null ? 0 : isTransClose ? 1 : 2));
        if (leadTypeID == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(leadTypeID);
        }
    }
}
