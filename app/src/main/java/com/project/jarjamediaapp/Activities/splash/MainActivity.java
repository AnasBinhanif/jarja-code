package com.project.jarjamediaapp.Activities.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.project.jarjamediaapp.Activities.HomeActivity;
import com.project.jarjamediaapp.Activities.login.LoginActivity;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ResponseModel.AccessCode;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivitySplashBinding;

import retrofit2.Response;

public class MainActivity extends BaseActivity implements View.OnClickListener, MainContract.View {

    ActivitySplashBinding bi;
    Context context = MainActivity.this;
    MainPresenter presenter;
    long BASE_TIME_OUT = 3;
    String typeOfNotification;
    String notificationID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        presenter = new MainPresenter(this);
        presenter.initScreen();

     /*   if (getIntent().getExtras() != null) {

             typeOfNotification = getIntent().getStringExtra("notificationType");
             notificationID = getIntent().getStringExtra("notificationID");

             ToastUtils.showToastLong(context,""+typeOfNotification);

         *//*   switch (typeOfNotification) {
                case "apointment":

                    notificationType = "apointment";

                    getAppointmentById(notificationID);
                    //  easyPreference.addString(GH.KEYS.NOTIFICATIONTYPE.name(), notificationType).save();

                    break;

                case "followup":


                    getFolloUpDetailByID(notificationID);

                    break;

                case "task":


                    notificationType = "task";

                    getTaskDetail(notificationID);

                    break;
                case "futureTask":


                    notificationType = "futureTask";

                    getFutureTaskDetail(notificationID);

                    break;


            }*//*
        }*/

    }

    public void splash(long baseTimeout) {

        BASE_TIME_OUT = baseTimeout * 1000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if (GH.getInstance().getAuthorization() != null && !GH.getInstance().getAuthorization().equals(""))
                {

                    if(getIntent().getExtras() != null){

                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("notificationType",typeOfNotification);
                        intent.putExtra("notificationID",notificationID);
                    }else {

                          switchActivity(HomeActivity.class);
                    }

                }else{
                    switchActivity(LoginActivity.class);
                }

                finish();
            }
        };
        handler.postDelayed(runnable, BASE_TIME_OUT);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void setupViews() {

        splash(3);


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

}
