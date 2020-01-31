package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Activities.login.LoginModel;

public class GetUserProfile {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public LoginModel.Data data;

    public class Data {

        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("userProfile")
        @Expose
        public LoginModel.UserProfile userProfile;

    }

    public class UserProfile {
        @SerializedName("userID")
        @Expose
        public String userID;
        @SerializedName("firstName")
        @Expose
        public String firstName;
        @SerializedName("lastName")
        @Expose
        public String lastName;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("picPath")
        @Expose
        public String picPath;

    }

}
