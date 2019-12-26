package com.project.jarjamediaapp.Networking;


import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

    public static ApiError parseError(Response<?> response) {

        Converter<ResponseBody, ApiError> converter = NetworkController.getInstance().getRetrofit().responseBodyConverter(ApiError.class, new Annotation[0]);
        ApiError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (Exception e) {
            ApiError error1 = null;
            if (response.code() == 503)
                error1 = new ApiError(response.code(), "The server is currently unavailable");
            else if (response.code() == 500){
                error1 = new ApiError(response.code(), "Internal Server Error");
            }
            else
                error1 = new ApiError(0,"Something unexpected happened");
            return error1;
        }

        return error;
    }

}
