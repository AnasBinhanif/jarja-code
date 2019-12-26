package com.project.jarjamediaapp.Networking;


public class ApiError {

    private int statusCode;
    private String message;
    private String Message;

    public ApiError() {
    }

    public ApiError(int statusCode,String message){
        this.message = message;
        this.statusCode = statusCode;
    }

    public int status() {
        return statusCode;
    }

    public String message() {
        return message;
    }

    public String Message() {
        return Message;
    }

}
