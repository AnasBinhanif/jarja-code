package com.project.jarjamediaapp.Activities.notes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class UploadFilesModel implements Parcelable {

    private boolean status;
    private String message = "";
    private String Image = "";
    private String Name = "";
    private String SelectionType = "";


    protected UploadFilesModel(Parcel in) {
        status = in.readByte() != 0;
        message = in.readString();
        Image = in.readString();
        Name = in.readString();
        SelectionType = in.readString();
    }

    public UploadFilesModel(String image, String name) {
        Image = image;
        Name = name;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeString(message);
        dest.writeString(Image);
        dest.writeString(Name);
        dest.writeString(SelectionType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UploadFilesModel> CREATOR = new Creator<UploadFilesModel>() {
        @Override
        public UploadFilesModel createFromParcel(Parcel in) {
            return new UploadFilesModel(in);
        }

        @Override
        public UploadFilesModel[] newArray(int size) {
            return new UploadFilesModel[size];
        }
    };

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSelectionType() {
        return SelectionType;
    }

    public void setSelectionType(String selectionType) {
        SelectionType = selectionType;
    }

}
