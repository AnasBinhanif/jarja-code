package com.project.jarjamediaapp.Activities.notes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.jarjamediaapp.Base.BaseResponse;

import java.util.ArrayList;

public class DocumentModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    public ArrayList<Data> data = null;

    public class Data {

        @SerializedName("leadAttachmentID")
        @Expose
        public Integer leadAttachmentID;
        @SerializedName("leadID")
        @Expose
        public Integer leadID;
        @SerializedName("attachmentGuidName")
        @Expose
        public String attachmentGuidName;
        @SerializedName("attachmentOrignalName")
        @Expose
        public String attachmentOrignalName;

        public Integer getLeadID() {
            return leadID;
        }

        @SerializedName("documentUrl")
        @Expose
        public String documentUrl;

        public Integer getLeadAttachmentID() {
            return leadAttachmentID;
        }

        public String getAttachmentGuidName() {
            return attachmentGuidName;
        }

        public String getAttachmentOrignalName() {
            return attachmentOrignalName;
        }

        public String getDocumentUrl() {
            return documentUrl;
        }
    }

    public ArrayList<Data> getData() {
        return data;
    }

}
