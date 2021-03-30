package com.project.jarjamediaapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommunicationModel {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("data")
    @Expose
    public List<Data> data;
    @SerializedName("message")
    @Expose
    public String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public class Data {

        private String date, type, msg, callDuration, subject, sentAt, noteType, html, agents, id,emailSendFrom,emailSendTo,emailSubject,emailBody;

        private int  leadID, notedId;

        private boolean isSticky, isDetected;


        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getCallDuration() {
            return callDuration;
        }

        public void setCallDuration(String callDuration) {
            this.callDuration = callDuration;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getSentAt() {
            return sentAt;
        }

        public void setSentAt(String sentAt) {
            this.sentAt = sentAt;
        }

        public String getNoteType() {
            return noteType;
        }

        public void setNoteType(String noteType) {
            this.noteType = noteType;
        }

        public String getHtml() {
            return html;
        }

        public void setHtml(String html) {
            this.html = html;
        }

        public String getAgents() {
            return agents;
        }

        public void setAgents(String agents) {
            this.agents = agents;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getLeadID() {
            return leadID;
        }

        public void setLeadID(int leadID) {
            this.leadID = leadID;
        }

        public boolean isSticky() {
            return isSticky;
        }

        public void setSticky(boolean sticky) {
            isSticky = sticky;
        }

        public boolean isDetected() {
            return isDetected;
        }

        public void setDetected(boolean detected) {
            isDetected = detected;

        }

        public int getNotedId() {
            return notedId;
        }

        public void setNotedId(int notedId) {
            this.notedId = notedId;
        }

        public String getEmailSendFrom() {
            return emailSendFrom;
        }

        public void setEmailSendFrom(String emailSendFrom) {
            this.emailSendFrom = emailSendFrom;
        }

        public String getEmailSendTo() {
            return emailSendTo;
        }

        public void setEmailSendTo(String emailSendTo) {
            this.emailSendTo = emailSendTo;
        }

        public String getEmailSubject() {
            return emailSubject;
        }

        public void setEmailSubject(String emailSubject) {
            this.emailSubject = emailSubject;
        }

        public String getEmailBody() {
            return emailBody;
        }

        public void setEmailBody(String emailBody) {
            this.emailBody = emailBody;
        }
    }

}
