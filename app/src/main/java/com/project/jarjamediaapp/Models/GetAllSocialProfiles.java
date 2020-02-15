package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetAllSocialProfiles {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public ArrayList<Data> data = null;
    @SerializedName("message")
    @Expose
    public String message;


    public class Data {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("leadId")
        @Expose
        public Integer leadId;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("gender")
        @Expose
        public Object gender;
        @SerializedName("userName")
        @Expose
        public Object userName;
        @SerializedName("siteName")
        @Expose
        public String siteName;
        @SerializedName("profilelink")
        @Expose
        public String profilelink;
        @SerializedName("profileImage")
        @Expose
        public Object profileImage;
        @SerializedName("siteImage")
        @Expose
        public Object siteImage;
        @SerializedName("createdDate")
        @Expose
        public String createdDate;
        @SerializedName("isDeleted")
        @Expose
        public Object isDeleted;
        @SerializedName("isProfileExist")
        @Expose
        public Object isProfileExist;
        @SerializedName("crmid")
        @Expose
        public Integer crmid;
    }

}
