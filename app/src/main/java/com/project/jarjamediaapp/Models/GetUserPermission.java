package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class GetUserPermission implements Serializable {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("message_QA")
    @Expose
    public Object messageQA;

    public class Data implements Serializable {

        @SerializedName("userMenuPermission")
        @Expose
        public ArrayList<UserMenuPermission> userMenuPermission = null;
        @SerializedName("lead")
        @Expose
        public ArrayList<Lead> lead = null;
        @SerializedName("dashboard")
        @Expose
        public ArrayList<Dashboard> dashboard = null;
        @SerializedName("calendar")
        @Expose
        public ArrayList<Calendar> calendar = null;
        @SerializedName("properties")
        @Expose
        public ArrayList<Object> properties = null;
    }

    public class Lead implements Serializable {

        @SerializedName("key")
        @Expose
        public String key;
        @SerializedName("value")
        @Expose
        public Boolean value;
    }

    public class UserMenuPermission implements Serializable {

        @SerializedName("moduleID")
        @Expose
        public Integer moduleID;
        @SerializedName("moduleName")
        @Expose
        public String moduleName;
        @SerializedName("isActive")
        @Expose
        public Boolean isActive;
        @SerializedName("parentID")
        @Expose
        public Object parentID;
        @SerializedName("crmid")
        @Expose
        public Integer crmid;
    }

    public class Dashboard implements Serializable {

        @SerializedName("key")
        @Expose
        public String key;
        @SerializedName("value")
        @Expose
        public Boolean value;
    }

    public class Calendar implements Serializable {

        @SerializedName("key")
        @Expose
        public String key;
        @SerializedName("value")
        @Expose
        public Boolean value;
    }
}