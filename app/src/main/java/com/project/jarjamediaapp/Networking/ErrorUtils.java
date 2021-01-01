package com.project.jarjamediaapp.Networking;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.project.jarjamediaapp.Activities.login.LoginActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.ProjectApplication;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.CallingClass;
import com.project.jarjamediaapp.Utilities.EasyPreference;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;

import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {
    public static int count = 0;


    public static ApiError parseError(Response<?> response) {


        Converter<ResponseBody, ApiError> converter = NetworkController.getInstance().getRetrofit().responseBodyConverter(ApiError.class, new Annotation[0]);
        ApiError error;
        try {
            error = converter.convert(response.errorBody());

            Log.d("parseError: ", count + "");

            if (error.message().equalsIgnoreCase("Authorization has been denied for this request.")
                    && count == 0) {
                Log.d("parseErrorIntent: ", "intentexecuted");
                /*Context context = ProjectApplication.getAppContext();
                EasyPreference.Builder easyPreference = EasyPreference.with(context);
                easyPreference.clearAll().save();
                easyPreference.addString(GH.KEYS.ISNOTIFICATIONALLOW.name(), "true").save();
                Intent intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);*/
                aVoid();
                count++;
            }


        } catch (Exception e) {
            ApiError error1 = null;
            if (response.code() == 503)
                error1 = new ApiError(response.code(), "The server is currently unavailable");
            else if (response.code() == 500) {
                error1 = new ApiError(response.code(), "Internal Server Error");
            } else
                error1 = new ApiError(0, "Something unexpected happened");
            return error1;
        }


        return error;
    }

    public static void aVoid() {
        // comment code here for repeating progress dialogue
        Call<BaseResponse> _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).UnAuthanticate_UserDevice(GH.getInstance().getAuthorization(), FirebaseInstanceId.getInstance().getToken(), "FCM");
        _call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {


                if (response.isSuccessful()) {

                    BaseResponse baseResponse = response.body();
                    if (baseResponse.getStatus().equalsIgnoreCase("Success")) {
                        Context context = ProjectApplication.getAppContext();
                        EasyPreference.Builder easyPreference = EasyPreference.with(ProjectApplication.getAppContext());
                        easyPreference.clearAll().save();
                        // for notification permission setting first time dialogue in login activity
                        EasyPreference.Builder pref = new EasyPreference.Builder(ProjectApplication.getAppContext());
                        pref.addString(GH.KEYS.ISNOTIFICATIONALLOW.name(), "true").save();

                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {


            }
        });

    }

}
