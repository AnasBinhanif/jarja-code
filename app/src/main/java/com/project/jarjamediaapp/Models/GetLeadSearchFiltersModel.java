package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.List;

/**
 * Created by Akshay Kumar on 12/26/2020.
 */
public class GetLeadSearchFiltersModel extends BaseResponse {

    @SerializedName("data")
    private List<KeyValueModel> objectsList;

    public GetLeadSearchFiltersModel() {
    }

    public GetLeadSearchFiltersModel(List<KeyValueModel> objectsList) {
        this.objectsList = objectsList;
    }

    public List<KeyValueModel> getObjectsList() {
        return objectsList;
    }

    public void setObjectsList(List<KeyValueModel> objectsList) {
        this.objectsList = objectsList;
    }

    @Override
    public String toString() {
        return "GetLeadSearchFiltersModel{" +
                "objectsList=" + objectsList +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
