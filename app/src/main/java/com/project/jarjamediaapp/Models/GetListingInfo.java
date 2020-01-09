package com.project.jarjamediaapp.Models;

public class GetListingInfo {


    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public GetListingInfo(String name, String description) {

        this.name = name;
        this.description = description;
    }

}
