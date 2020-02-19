package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetLeadListingInfo {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("message")
    @Expose
    public String message;

    public class Data {

        @SerializedName("listingInfo_TransactionOne")
        @Expose
        public ListingInfoTransactionOne listingInfoTransactionOne;
        @SerializedName("listingInfo_TransactionTwo")
        @Expose
        public ListingInfoTransactionTwo listingInfoTransactionTwo;
    }

    public class ListingInfoTransactionOne {

        @SerializedName("listingPrice")
        @Expose
        public String listingPrice;
        @SerializedName("propertyType")
        @Expose
        public String propertyType;
        @SerializedName("location")
        @Expose
        public String location;
        @SerializedName("bedrooms")
        @Expose
        public String bedrooms;
        @SerializedName("baths")
        @Expose
        public String baths;
        @SerializedName("yearBuilt")
        @Expose
        public String yearBuilt;
        @SerializedName("squareFeet")
        @Expose
        public String squareFeet;
        @SerializedName("timeFrame")
        @Expose
        public String timeFrame;
        @SerializedName("fthb")
        @Expose
        public String fthb;
        @SerializedName("houseToSell")
        @Expose
        public String houseToSell;
        @SerializedName("withAgent")
        @Expose
        public String withAgent;
        @SerializedName("acres")
        @Expose
        public String acres;
        @SerializedName("garage")
        @Expose
        public String garage;
        @SerializedName("referredBy")
        @Expose
        public String referredBy;
        @SerializedName("referredTo")
        @Expose
        public String referredTo;
        @SerializedName("referralAmt")
        @Expose
        public String referralAmt;
        @SerializedName("preApproved")
        @Expose
        public Object preApproved;
        @SerializedName("mls")
        @Expose
        public String mls;
        @SerializedName("escrow")
        @Expose
        public String escrow;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("viewedProperties")
        @Expose
        public Integer viewedProperties;
        @SerializedName("inquiries")
        @Expose
        public Integer inquiries;
        @SerializedName("favoritedProperties")
        @Expose
        public Integer favoritedProperties;
        @SerializedName("uploadedAttachments")
        @Expose
        public String uploadedAttachments;
    }

    public class ListingInfoTransactionTwo {

        @SerializedName("listingPrice")
        @Expose
        public String listingPrice;
        @SerializedName("propertyType")
        @Expose
        public String propertyType;
        @SerializedName("location")
        @Expose
        public String location;
        @SerializedName("bedrooms")
        @Expose
        public String bedrooms;
        @SerializedName("baths")
        @Expose
        public String baths;
        @SerializedName("yearBuilt")
        @Expose
        public String yearBuilt;
        @SerializedName("squareFeet")
        @Expose
        public String squareFeet;
        @SerializedName("timeFrame")
        @Expose
        public String timeFrame;
        @SerializedName("fthb")
        @Expose
        public String fthb;
        @SerializedName("houseToSell")
        @Expose
        public String houseToSell;
        @SerializedName("withAgent")
        @Expose
        public String withAgent;
        @SerializedName("acres")
        @Expose
        public String acres;
        @SerializedName("garage")
        @Expose
        public String garage;
        @SerializedName("referredBy")
        @Expose
        public String referredBy;
        @SerializedName("referredTo")
        @Expose
        public String referredTo;
        @SerializedName("referralAmt")
        @Expose
        public String referralAmt;
        @SerializedName("preApproved")
        @Expose
        public Object preApproved;
        @SerializedName("mls")
        @Expose
        public String mls;
        @SerializedName("escrow")
        @Expose
        public String escrow;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("viewedProperties")
        @Expose
        public Integer viewedProperties;
        @SerializedName("inquiries")
        @Expose
        public Integer inquiries;
        @SerializedName("favoritedProperties")
        @Expose
        public Integer favoritedProperties;
        @SerializedName("uploadedAttachments")
        @Expose
        public String uploadedAttachments;
    }

}
