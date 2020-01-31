package com.project.jarjamediaapp.Networking.ResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AccessCode {

    @SerializedName("access_token")

    public String accessToken;
    @SerializedName("token_type")

    public String tokenType;
    @SerializedName("expires_in")
    public String expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }
}
