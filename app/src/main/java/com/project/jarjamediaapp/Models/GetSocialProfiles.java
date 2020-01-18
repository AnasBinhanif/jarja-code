package com.project.jarjamediaapp.Models;

import android.graphics.drawable.Drawable;

public class GetSocialProfiles {


    private String name;
    private Drawable image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }


    public GetSocialProfiles(String name, Drawable image) {

        this.name = name;
        this.image = image;
    }

}
