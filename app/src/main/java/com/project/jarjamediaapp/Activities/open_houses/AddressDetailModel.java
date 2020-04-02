package com.project.jarjamediaapp.Activities.open_houses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.List;

public class
AddressDetailModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {

        @SerializedName("street")
        @Expose
        public List<StreetFilter> street = null;

        public class StreetFilter {

            @SerializedName("n")
            @Expose
            public String n;

            public String getN() {
                return n;
            }
        }

        public List<StreetFilter> getStreetFilter() {
            return street;
        }

        @SerializedName("cityFilter")
        @Expose
        public List<CityFilter> cityFilter = null;

        public class CityFilter {

            @SerializedName("n")
            @Expose
            public String n;

            public String getN() {
                return n;
            }
        }

        public List<CityFilter> getCityFilter() {
            return cityFilter;
        }

        @SerializedName("zipCode")
        @Expose
        public List<ZipCode> zipCode = null;

        public class ZipCode {

            @SerializedName("n")
            @Expose
            public String n;

            public String getN() {
                return n;
            }
        }

        public List<ZipCode> getZipCode() {
            return zipCode;
        }

        @SerializedName("stateFilter")
        @Expose
        public List<StateFilter> stateFilter = null;

        public class StateFilter {

            @SerializedName("n")
            @Expose
            public String n;

            public String getN() {
                return n;
            }
        }

        public List<StateFilter> getStateFilter() {
            return stateFilter;
        }


    }

    public Data getData() {
        return data;
    }
}
