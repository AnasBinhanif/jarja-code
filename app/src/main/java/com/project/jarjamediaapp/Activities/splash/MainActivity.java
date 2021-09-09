package com.project.jarjamediaapp.Activities.splash;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.google.firebase.iid.FirebaseInstanceId;
import com.project.jarjamediaapp.Activities.HomeActivity;
import com.project.jarjamediaapp.Activities.login.LoginActivity;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.CallbackInterface;
import com.project.jarjamediaapp.Networking.FailureException;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Networking.ResponseModel.AccessCode;
import com.project.jarjamediaapp.Networking.RetrofitCallback;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.AppConstants;
import com.project.jarjamediaapp.Utilities.EasyPreference;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivitySplashBinding;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements View.OnClickListener, MainContract.View {

    ActivitySplashBinding bi;
    Context context = MainActivity.this;
    MainPresenter presenter;
    long BASE_TIME_OUT = 1;
    Object notificationType;
    Object NotificationID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        presenter = new MainPresenter(this);
        presenter.initScreen();

        if (getIntent().getExtras() != null) {

            notificationType = getIntent().getExtras().get("notification_type");
            NotificationID = getIntent().getExtras().get("NotificationID");


        }


        // for testing
        // save o start acivity other wise application crash due to null values
        EasyPreference.Builder pref = new EasyPreference.Builder(context);
        pref.addString(GH.KEYS.FRAGMENTSTATUS.name(), "").save();
        // for notification dialogue


        //pref.addString(GH.KEYS.ISNOTIFICATIONALLOW.name(),"false").save();

    }

    @Override
    public void setupViews() {

//        splash(3);
        splash(1);

    }

    public void splash(long baseTimeout) {

        BASE_TIME_OUT = baseTimeout * 1000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {

                //check if user is logged in or not
                if (GH.getInstance().getAuthorization() != null && !GH.getInstance().getAuthorization().equals("")) {
                    String email = GH.getInstance().getEmail();
                    String password = GH.getInstance().getPassword();
//                    String email = "";
//                    String password = "";
                    NetworkController.getInstance().NetworkCall(NetworkController.getInstance().getApiMethods().getToken(email, password, AppConstants.HTTP.GRANT_TYPE),
                            new RetrofitCallback<>(new CallbackInterface<AccessCode>() {
                                @Override
                                public void onSuccess(AccessCode response) {
                                    //MyLog.d("Response", "onSuccess: " + response.toString());
                                    Log.d("token", response.accessToken + "");

                                    easyPreference.addString(GH.KEYS.AUTHORIZATION.name(), "bearer" + " " + response.accessToken).save();

                                    easyPreference.addBoolean(GH.KEYS.NAVIGATIONSTATUS.name(), true).save();
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    intent.putExtra("notificationType", String.valueOf(notificationType));
                                    intent.putExtra("notificationID", String.valueOf(NotificationID));
                                    startActivity(intent);
                                    // switchActivity(HomeActivity.class);
                                    finish();


                                }

                                @Override
                                public void onError(Throwable throwable) {

                                    Log.d("Response", "onError: " + throwable.toString());

//                                    ToastUtils.showToastLong(context, "Invalid Username or Password");
                                    switchActivity(LoginActivity.class);
                                    finish();

                                }

                            }));


                   /* easyPreference.addBoolean(GH.KEYS.NAVIGATIONSTATUS.name(), true).save();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("notificationType", String.valueOf(notificationType));
                    intent.putExtra("notificationID", String.valueOf(NotificationID));
                    startActivity(intent);
                    // switchActivity(HomeActivity.class);
                    finish();*/

                } else {
                    switchActivity(LoginActivity.class);
                    finish();
                }

//                finish();
            }
        };
        handler.postDelayed(runnable, BASE_TIME_OUT);
    }

    @Override
    public void onClick(View v) {

    }



    @Override
    public void updateUI(Response<AccessCode> response) {

    }

    @Override
    public void updateUIonFalse(String message) {

        ToastUtils.showToastLong(context, message);

    }

    @Override
    public void updateUIonError(ApiError error) {

    }

    @Override
    public void updateUIonFailure() {

        ToastUtils.showToastLong(context, getString(R.string.retrofit_failure));
    }

    @Override
    public void showProgressBar() {

        GH.getInstance().ShowProgressDialog(MainActivity.this);
    }

    @Override
    public void hideProgressBar() {

        GH.getInstance().HideProgressDialog();
    }

   /* public void updateData(String title,String message) {

        ToastUtils.showToastLong(context, title);

    }*/


   /* @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);


        super.onNewIntent(intent);

        Log.d("Tag", "onNewIntent - starting");
        Bundle extras = intent.getExtras();
        if (extras != null) {
            for (String key : extras.keySet()) {
                Object value = extras.get(key);
                Log.d("Tag", "Extras received at onNewIntent:  Key: " + key + " Value: " + value);
            }
            String title = extras.getString("title");
            String message = extras.getString("body");
            if (message!=null && message.length()>0) {
                getIntent().removeExtra("body");
                updateData(title, message);
            }
        }
    }*/
}
