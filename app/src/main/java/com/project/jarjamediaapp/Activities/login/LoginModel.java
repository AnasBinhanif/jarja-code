package com.project.jarjamediaapp.Activities.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {

        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("userProfile")
        @Expose
        public UserProfile userProfile;

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
