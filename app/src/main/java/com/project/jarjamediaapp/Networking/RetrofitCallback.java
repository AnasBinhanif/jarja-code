package com.project.jarjamediaapp.Networking;

import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitCallback<T> implements Callback<T> {

    private CallbackInterface<T> callbackInterface;

    public RetrofitCallback(CallbackInterface<T> callbackInterface) {
        this.callbackInterface = callbackInterface;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.code() >= 400 && response.code() < 599) {
            callbackInterface.onError(new FailureException(response.code(), response.message(), response));
        } else {
            callbackInterface.onSuccess(response.body());
        }
    }

    @Override
    public void onFailure(final Call<T> call, Throwable t) {
        if (t instanceof UnknownHostException) {
            callbackInterface.onError(t);
        } else {
            callbackInterface.onError(t);
        }
    }
}
