package com.project.jarjamediaapp.Networking;

import com.project.jarjamediaapp.Base.BaseResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class FailureException extends Exception {


    public int getmCode() {
        return mCode;
    }

    public void setmCode(int mCode) {
        this.mCode = mCode;
    }

    public Response getmResponseBody() {
        return mResponse;
    }

    public void setmResponseBody(Response mResponseBody) {
        this.mResponse = mResponseBody;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    private int mCode;
//    private Response mResponseBody;
    private Response mResponse;
    private String mMessage;


    FailureException(int code, String message, Response responseBody) {
        super(message);
        mCode = code;
        mMessage = message;
        mResponse = responseBody;
    }

    public <T> T getErrorBodyAs(Class<T> type) throws IOException {
        if (mResponse == null || mResponse.errorBody() == null) {
            return null;
        }
        Converter<ResponseBody, T> converter = NetworkController.getInstance().getRetrofit().responseBodyConverter(type, new Annotation[0]);
        return converter.convert(mResponse.errorBody());
    }

    public BaseResponse getError() {
        Converter<ResponseBody, BaseResponse> converter = NetworkController.getInstance().getRetrofit().responseBodyConverter(BaseResponse.class, new Annotation[0]);
        BaseResponse error = null;
        try {
            error = converter.convert(mResponse.errorBody());
            return error;
        } catch (IOException e) {
            e.printStackTrace();
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMessage(e.getMessage());
            return baseResponse;
        }
    }

}
