package com.abdeveloper.library;

public class MultiSelectModelForWebsite {
    private String id;
    private String encyptedIDs;
    private String name;
    private Boolean isSelected;

    public MultiSelectModelForWebsite(String id, String name, String encyptedIDs) {
        this.id = id;
        this.name = name;
        this.encyptedIDs = encyptedIDs;
    }

    public MultiSelectModelForWebsite(String id, String name) {
        this.id = id;
        this.name = name;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Boolean getSelected() {
        return isSelected;
    }

    void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public String getEncyptedIDs() {
        return encyptedIDs;
    }

    public void setEncyptedIDs(String ids) {
        this.encyptedIDs = ids;
    }
}
