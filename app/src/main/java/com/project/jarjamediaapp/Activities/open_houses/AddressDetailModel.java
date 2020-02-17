package com.project.jarjamediaapp.Activities.open_houses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.List;

public class AddressDetailModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {

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
    }

    public Data getData() {
        return data;
    }
}
