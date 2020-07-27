
package com.project.jarjamediaapp.Activities.add_appointment;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAppointmentByIDModel implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("message_QA")
    @Expose
    private Object messageQA;

    protected GetAppointmentByIDModel(Parcel in) {
        status = in.readString();
        data = in.readParcelable(Data.class.getClassLoader());
        message = in.readString();
    }

    public static final Creator<GetAppointmentByIDModel> CREATOR = new Creator<GetAppointmentByIDModel>() {
        @Override
        public GetAppointmentByIDModel createFromParcel(Parcel in) {
            return new GetAppointmentByIDModel(in);
        }

        @Override
        public GetAppointmentByIDModel[] newArray(int size) {
            return new GetAppointmentByIDModel[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getMessageQA() {
        return messageQA;
    }

    public void setMessageQA(Object messageQA) {
        this.messageQA = messageQA;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeParcelable(data, i);
        parcel.writeString(message);
    }
}
