package com.project.jarjamediaapp.Activities.open_houses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.io.Serializable;
import java.util.List;

public class GetAllOpenHousesModel extends BaseResponse implements Serializable {

    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {

        @SerializedName("past")
        @Expose
        public String past;
        @SerializedName("msg")
        @Expose
        public Object msg;
        @SerializedName("status")
        @Expose
        public Boolean status;
        @SerializedName("openHouse")
        @Expose
        public List<OpenHouse> openHouse = null;
        @SerializedName("upComing")
        @Expose
        public String upComing;
        @SerializedName("comActive")
        @Expose
        public String comActive;
        @SerializedName("upActive")
        @Expose
        public Object upActive;

        public class OpenHouse implements Serializable{

            @SerializedName("imgURL")
            @Expose
            public String imgURL;
            @SerializedName("encryptedPropertyId")
            @Expose
            public String encryptedPropertyId;
            @SerializedName("propertyId")
            @Expose
            public Integer propertyId;
            @SerializedName("listPrice")
            @Expose
            public String listPrice;
            @SerializedName("streetName")
            @Expose
            public String streetName;
            @SerializedName("city")
            @Expose
            public String city;
            @SerializedName("state")
            @Expose
            public String state;
            @SerializedName("leadCount")
            @Expose
            public Integer leadCount;
            @SerializedName("openHouseDate")
            @Expose
            public String openHouseDate;
            @SerializedName("openHouseEndDate")
            @Expose
            public String openHouseEndDate;

            @SerializedName("zip")
            @Expose
            public String zip;

            public String getZipCode() {
                return zip;
            }

            public String getImgURL() {
                return imgURL;
            }

            public String getEncryptedPropertyId() {
                return encryptedPropertyId;
            }

            public Integer getPropertyId() {
                return propertyId;
            }

            public String getListPrice() {
                return listPrice;
            }

            public String getStreetName() {
                return streetName;
            }

            public String getCity() {
                return city;
            }

            public String getState() {
                return state;
            }

            public Integer getLeadCount() {
                return leadCount;
            }

            public String getOpenHouseDate() {
                return openHouseDate;
            }

            public String getOpenHouseEndDate() {
                return openHouseEndDate;
            }
        }

        public String getPast() {
            return past;
        }

        public Object getMsg() {
            return msg;
        }

        public Boolean getStatus() {
            return status;
        }

        public List<OpenHouse> getOpenHouse() {
            return openHouse;
        }

        public String getUpComing() {
            return upComing;
        }

        public String getComActive() {
            return comActive;
        }

        public Object getUpActive() {
            return upActive;
        }
    }

    public Data getData() {
        return data;
    }

}
