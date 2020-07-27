
package com.project.jarjamediaapp.Activities.add_appointment;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VtCRMLeadAppoinmentDetailCustom implements Parcelable {

    @SerializedName("leadAppoinmentDetailID")
    @Expose
    private Integer leadAppoinmentDetailID;
    @SerializedName("leadAppoinmentID")
    @Expose
    private Integer leadAppoinmentID;
    @SerializedName("agentID")
    @Expose
    private String agentID;
    @SerializedName("agentDecryptedID")
    @Expose
    private Integer agentDecryptedID;
    @SerializedName("agentName")
    @Expose
    private String agentName;
    @SerializedName("isSeen")
    @Expose
    private Boolean isSeen;
    @SerializedName("seenDate")
    @Expose
    private Object seenDate;
    @SerializedName("crmid")
    @Expose
    private Integer crmid;
    @SerializedName("vt_CRM_Agent_Custom")
    @Expose
    private Object vtCRMAgentCustom;
    @SerializedName("vt_CRM_LeadAppoinment_Custom")
    @Expose
    private Object vtCRMLeadAppoinmentCustom;

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

    public void setLeadAppoinmentDetailID(Integer leadAppoinmentDetailID) {
        this.leadAppoinmentDetailID = leadAppoinmentDetailID;
    }

    public Integer getLeadAppoinmentID() {
        return leadAppoinmentID;
    }

    public void setLeadAppoinmentID(Integer leadAppoinmentID) {
        this.leadAppoinmentID = leadAppoinmentID;
    }

    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }

    public Integer getAgentDecryptedID() {
        return agentDecryptedID;
    }

    public void setAgentDecryptedID(Integer agentDecryptedID) {
        this.agentDecryptedID = agentDecryptedID;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Boolean getIsSeen() {
        return isSeen;
    }

    public void setIsSeen(Boolean isSeen) {
        this.isSeen = isSeen;
    }

    public Object getSeenDate() {
        return seenDate;
    }

    public void setSeenDate(Object seenDate) {
        this.seenDate = seenDate;
    }

    public Integer getCrmid() {
        return crmid;
    }

    public void setCrmid(Integer crmid) {
        this.crmid = crmid;
    }

    public Object getVtCRMAgentCustom() {
        return vtCRMAgentCustom;
    }

    public void setVtCRMAgentCustom(Object vtCRMAgentCustom) {
        this.vtCRMAgentCustom = vtCRMAgentCustom;
    }

    public Object getVtCRMLeadAppoinmentCustom() {
        return vtCRMLeadAppoinmentCustom;
    }

    public void setVtCRMLeadAppoinmentCustom(Object vtCRMLeadAppoinmentCustom) {
        this.vtCRMLeadAppoinmentCustom = vtCRMLeadAppoinmentCustom;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (leadAppoinmentDetailID == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(leadAppoinmentDetailID);
        }
        if (leadAppoinmentID == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(leadAppoinmentID);
        }
        parcel.writeString(agentID);
        if (agentDecryptedID == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(agentDecryptedID);
        }
        parcel.writeString(agentName);
        parcel.writeByte((byte) (isSeen == null ? 0 : isSeen ? 1 : 2));
        if (crmid == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(crmid);
        }
    }
}
