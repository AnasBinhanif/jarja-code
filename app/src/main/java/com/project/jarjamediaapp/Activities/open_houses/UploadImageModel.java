package com.project.jarjamediaapp.Activities.open_houses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

public class UploadImageModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    private Response data;

    public Response getResponse() {
        return data;
    }

    public void setResponse(Response response) {
        this.data = response;
    }

    public class Response {

        @SerializedName("file_url")
        @Expose
        private String fileUrl;

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }
    }
}

/*

 */
