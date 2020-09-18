package com.project.jarjamediaapp.Activities.user_profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.List;

public class GetPermissionModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("userMenuPermission")
        @Expose
        private List<UserMenuPermission> userMenuPermission = null;
        @SerializedName("lead")
        @Expose
        private List<Lead> lead = null;
        @SerializedName("dashboard")
        @Expose
        private List<Dashboard> dashboard = null;
        @SerializedName("calendar")
        @Expose
        private List<Calendar> calendar = null;
        @SerializedName("properties")
        @Expose
        private List<Object> properties = null;
        @SerializedName("user_Settings")
        @Expose
        private List<UserSetting> userSettings = null;

        public List<UserMenuPermission> getUserMenuPermission() {
            return userMenuPermission;
        }

        public void setUserMenuPermission(List<UserMenuPermission> userMenuPermission) {
            this.userMenuPermission = userMenuPermission;
        }

        public List<Lead> getLead() {
            return lead;
        }

        public void setLead(List<Lead> lead) {
            this.lead = lead;
        }

        public List<Dashboard> getDashboard() {
            return dashboard;
        }

        public void setDashboard(List<Dashboard> dashboard) {
            this.dashboard = dashboard;
        }

        public List<Calendar> getCalendar() {
            return calendar;
        }

        public void setCalendar(List<Calendar> calendar) {
            this.calendar = calendar;
        }

        public List<Object> getProperties() {
            return properties;
        }

        public void setProperties(List<Object> properties) {
            this.properties = properties;
        }

        public List<UserSetting> getUserSettings() {
            return userSettings;
        }

        public void setUserSettings(List<UserSetting> userSettings) {
            this.userSettings = userSettings;
        }

    }

    public class Calendar {

        @SerializedName("key")
        @Expose
        private String key;
        @SerializedName("value")
        @Expose
        private Boolean value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Boolean getValue() {
            return value;
        }

        public void setValue(Boolean value) {
            this.value = value;
        }

    }

    public class Dashboard {

        @SerializedName("key")
        @Expose
        private String key;
        @SerializedName("value")
        @Expose
        private Boolean value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Boolean getValue() {
            return value;
        }

        public void setValue(Boolean value) {
            this.value = value;
        }

    }

    public class Lead {

        @SerializedName("key")
        @Expose
        private String key;
        @SerializedName("value")
        @Expose
        private Boolean value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Boolean getValue() {
            return value;
        }

        public void setValue(Boolean value) {
            this.value = value;
        }

    }
    public class UserMenuPermission {

        @SerializedName("moduleID")
        @Expose
        private Integer moduleID;
        @SerializedName("moduleName")
        @Expose
        private String moduleName;
        @SerializedName("isActive")
        @Expose
        private Boolean isActive;
        @SerializedName("parentID")
        @Expose
        private Object parentID;
        @SerializedName("crmid")
        @Expose
        private Integer crmid;

        public Integer getModuleID() {
            return moduleID;
        }

        public void setModuleID(Integer moduleID) {
            this.moduleID = moduleID;
        }

        public String getModuleName() {
            return moduleName;
        }

        public void setModuleName(String moduleName) {
            this.moduleName = moduleName;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public Object getParentID() {
            return parentID;
        }

        public void setParentID(Object parentID) {
            this.parentID = parentID;
        }

        public Integer getCrmid() {
            return crmid;
        }

        public void setCrmid(Integer crmid) {
            this.crmid = crmid;
        }

    }

    public class UserSetting {

        @SerializedName("key")
        @Expose
        private String key;
        @SerializedName("value")
        @Expose
        private Boolean value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Boolean getValue() {
            return value;
        }

        public void setValue(Boolean value) {
            this.value = value;
        }

    }


}
