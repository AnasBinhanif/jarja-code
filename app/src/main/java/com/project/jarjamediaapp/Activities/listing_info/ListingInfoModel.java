package com.project.jarjamediaapp.Activities.listing_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.List;

public class ListingInfoModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {

        @SerializedName("listingInfo_TransactionOne")
        @Expose
        public List<ListingInfo> listingInfo_TransactionOne = null;

        @SerializedName("listingInfo_TransactionTwo")
        @Expose
        public List<ListingInfo> listingInfo_TransactionTwo = null;

        @SerializedName("buyingInfo_TransactionOne")
        @Expose
        public List<ListingInfo> buyingInfo_TransactionOne = null;

        @SerializedName("buyingInfo_TransactionTwo")
        @Expose
        public List<ListingInfo> buyingInfo_TransactionTwo = null;

        public class ListingInfo {

            @SerializedName("key")
            @Expose
            public String key;
            @SerializedName("value")
            @Expose
            public String value;

            public String getKey() {
                return key;
            }

            public String getValue() {
                return value;
            }
        }


        public List<ListingInfo> getListingInfoTransactionOne() {
            return listingInfo_TransactionOne;
        }

        public List<ListingInfo> getListingInfoTransactionTwo() {
            return listingInfo_TransactionTwo;
        }

        public List<ListingInfo> getBuyingInfoTransactionOne() {
            return buyingInfo_TransactionOne;
        }

        public List<ListingInfo> getBuyingInfoTransactionTwo() {
            return buyingInfo_TransactionTwo;
        }
    }

    public Data getData() {
        return data;
    }
}
