package com.project.jarjamediaapp.Activities.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.load.HttpException;
import com.google.android.gms.common.api.Api;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.project.jarjamediaapp.Activities.HomeActivity;
import com.project.jarjamediaapp.Activities.forgot_password.ForgotPasswordActivity;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.CallbackInterface;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.FailureException;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Networking.ResponseModel.AccessCode;
import com.project.jarjamediaapp.Networking.RetrofitCallback;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.AppConstants;
import com.project.jarjamediaapp.Utilities.EasyPreference;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.Methods;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.Utilities.Validator;
import com.project.jarjamediaapp.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginContract.View {

    ActivityLoginBinding bi;
    Context context = LoginActivity.this;
    LoginPresenter presenter;
    String email = "", password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_login);
        presenter = new LoginPresenter(this);
        presenter.initScreen();

        if (GH.getInstance().getNotificationAllowStatus().equals("false")) {

            notificationPermission();


        }

        // for testing
        // save o start acivity other wise application crash due to null values
        EasyPreference.Builder pref = new EasyPreference.Builder(context);
        pref.addString(GH.KEYS.FRAGMENTSTATUS.name(), "").save();

        //to reset the count
        ErrorUtils.count = 0;

    }

    @Override
    public void setupViews() {

       /* bi.atvEmail.setText("bunny@outlook.com");
        bi.atvPassword.setText("admin");*/
        bi.tvForgotPassword.setOnClickListener(this);
        bi.btnLogin.setOnClickListener(this);

    }

    private void createNetWorkCallRequest(String userName, String password) {

        GH.getInstance().ShowProgressDialog(LoginActivity.this);
        NetworkController.getInstance().NetworkCall(NetworkController.getInstance().getApiMethods().getToken(userName, password, AppConstants.HTTP.GRANT_TYPE),
                new RetrofitCallback<>(new CallbackInterface<AccessCode>() {
                    @Override
                    public void onSuccess(AccessCode response) {
                        //MyLog.d("Response", "onSuccess: " + response.toString());
                        Log.d("token", response.accessToken + "");

                        easyPreference.addString(GH.KEYS.AUTHORIZATION.name(), "bearer" + " " + response.accessToken).save();

                        //saving email and password to call gettoken api on splash screen
                        easyPreference.addString(GH.KEYS.EMAIL.name(), userName).save();
                        easyPreference.addString(GH.KEYS.PASSWORD.name(), password).save();

                        userAuthenticate(FirebaseInstanceId.getInstance().getToken(), "FCM", "bearer" + " " + response.accessToken);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                        Log.d("Response", "onError: " + throwable.toString());
                        GH.getInstance().HideProgressDialog();
//                        ToastUtils.showToastLong(context, "Invalid Username or Password");
                        try {
                            FailureException failureException = ((FailureException) throwable);
                            ResponseBody message = failureException.getmResponseBody().errorBody();
                            String data = message.string();
                            JSONObject jObjError = new JSONObject(data);
                            ToastUtils.showToastLong(context, jObjError.getString("error_description"));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }));
    }

    private void userAuthenticate(String deviceToken, String network, String token) {

        Call<BaseResponse> _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).Authanticate_UserDevice(token, deviceToken, network);
        _call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                hideProgressBar();
                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (getAppointmentsModel.getStatus().equalsIgnoreCase("Success")) {

                        // when successfully authenticate then store access token
                        easyPreference.addString(GH.KEYS.AUTHORIZATION.name(), token).save();
                        easyPreference.addBoolean(GH.KEYS.NAVIGATIONSTATUS.name(), true).save();
                        switchActivity(HomeActivity.class);
                        finish();

                    } else {

                        updateUIonFalse(getAppointmentsModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    updateUIonError(error);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                hideProgressBar();
                updateUIonFailure();
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tvForgotPassword:

                switchActivity(ForgotPasswordActivity.class);

                break;
            case R.id.btnLogin:

                email = bi.atvEmail.getText().toString();
                password = bi.atvPassword.getText().toString();

                if (validate())
                    createNetWorkCallRequest(email, password);


                break;
        }
    }

    private boolean validate() {

        if (Methods.isEmpty(bi.atvEmail)) {
            showToastMessage("Please enter email");
            bi.atvEmail.requestFocus();
            return false;
        }
        if (Methods.isEmpty(bi.atvPassword)) {
            showToastMessage("Please enter password");
            bi.atvPassword.requestFocus();
            return false;
        }
        if (!Validator.isEmailValid(email)) {
            showToastMessage("Invalid Email ");
            bi.atvEmail.requestFocus();
            return false;
        }
        return true;

    }

    @Override
    public void updateUI(Response<LoginModel> response) {
    }

    @Override
    public void updateUIList(Response<LoginModel> response) {

    }

    @Override
    public void updateUIonFalse(String message) {

        ToastUtils.showToastLong(context, message);

    }

    @Override
    public void updateUIonError(ApiError error) {
        ToastUtils.showToastLong(context, error.message());
    }

    @Override
    public void updateUIonFailure() {

        ToastUtils.showToastLong(context, getString(R.string.retrofit_failure));
    }


    @Override
    public void showProgressBar() {

        GH.getInstance().ShowProgressDialog(LoginActivity.this);
    }

    @Override
    public void hideProgressBar() {

        GH.getInstance().HideProgressDialog();
    }

    public void notificationPermission() {

        AlertDialog alertDialog1;
        alertDialog1 = new AlertDialog.Builder(
                LoginActivity.this).create();

        // Setting Dialog Title
        //alertDialog1.setTitle("Alert");

        // Setting Dialog Message
        alertDialog1.setMessage(context.getResources().getString(R.string.notification_dialog));
        alertDialog1.setCanceledOnTouchOutside(false);

        // Setting Icon to Dialog
        // alertDialog1.setIcon(R.drawable.tick);

        // Setting OK Button
        alertDialog1.setButton("ALLOW", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                EasyPreference.Builder pref = new EasyPreference.Builder(context);
                pref.addString(GH.KEYS.ISNOTIFICATIONALLOW.name(), "true").save();

                alertDialog1.dismiss();
                openSettingForNotification();


            }
        });

        alertDialog1.setButton2("DENY", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                EasyPreference.Builder pref = new EasyPreference.Builder(context);
                pref.addString(GH.KEYS.ISNOTIFICATIONALLOW.name(), "true").save();
                alertDialog1.dismiss();
                openSettingForNotification();

            }
        });

        // Showing Alert Message
        alertDialog1.show();
    }

    public void openSettingForNotification() {

        Intent intent = new Intent();
        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");

//for Android 5-7
        intent.putExtra("app_package", getApplication().getPackageName());
        intent.putExtra("app_uid", getApplication().getApplicationInfo().uid);

// for Android 8 and above
        intent.putExtra("android.provider.extra.APP_PACKAGE", getApplication().getPackageName());

        startActivity(intent);
    }

}
