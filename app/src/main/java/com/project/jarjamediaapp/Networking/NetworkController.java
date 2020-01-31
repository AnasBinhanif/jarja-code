package com.project.jarjamediaapp.Networking;

import android.app.Activity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.jarjamediaapp.Utilities.AppConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkController {


    private static NetworkController singleTonInstance = null;
    private Retrofit mRetrofit;
    private ApiMethods mApiMethods;

    private NetworkController() {
    }

    public static NetworkController getInstance() {
        if (singleTonInstance == null) {
            singleTonInstance = new NetworkController();
        }
        return singleTonInstance;
    }

    public Retrofit getRetrofit() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.HTTP.BASE_URL)
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return mRetrofit;
    }

    //Time out Issue to be Resolved
    private OkHttpClient getHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();
    }


    public ApiMethods getApiMethods() {
        if (mApiMethods == null) {
            mApiMethods = getRetrofit().create(ApiMethods.class);
        }
        return mApiMethods;
    }

    public <T> void NetworkCall(final Call<T> call, final Callback<T> callback) {
        call.clone().enqueue(callback);
    }

    public void ShowRestErrorToast(Activity activity, String message, String Message) {

        if (message == null && Message != null) {

            Toast.makeText(activity, Message, Toast.LENGTH_SHORT).show();

        } else if (Message == null && message != null) {

            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

        } else {
            // Message Need to be changed
            Toast.makeText(activity, "Unable to connect .Please try again", Toast.LENGTH_SHORT).show();
        }

    }

}
