package com.project.jarjamediaapp.Networking;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.jarjamediaapp.ProjectApplication;
import com.project.jarjamediaapp.Utilities.AppConstants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NetworkController {

    private static NetworkController singleTonInstance = null;
    private Retrofit mRetrofit;
    private ApiMethods mApiMethods;

    private long cacheSize = (5 * 1024 * 1024);
    private Cache mCache = new Cache(ProjectApplication.getAppContext().getCacheDir(),cacheSize);

    private static final String TAG = "NetworkController";
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_PRAGMA = "Pragma";

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
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return mRetrofit;
    }

    //Time out Issue to be Resolved
    private OkHttpClient getHttpClient() {

        return new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                //.addNetworkInterceptor(networkInterceptor()) // only used when network is on
               // .cache(mCache)
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

    /**
     * This interceptor will be called ONLY if the network is available
     * @return
     */
    private static Interceptor networkInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Log.d(TAG, "network interceptor: called.");

                Response response = chain.proceed(chain.request());

                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(2, TimeUnit.MINUTES)
                        .build();

                return response.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }

}