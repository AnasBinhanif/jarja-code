package com.project.jarjamediaapp.Activities.user_profile;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.project.jarjamediaapp.Activities.HomeActivity;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetUserProfile;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.AppConstants;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityChangePasswordBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends BaseActivity {

    Context context;
    ActivityChangePasswordBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_change_password);
        setToolBarTitle(bi.epToolbar.toolbar, "Change Password", true);
        context = ChangePassword.this;
        initListener();
    }

    private void initListener(){
        bi.btnChangePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()){

            case R.id.btnChangePassword:

                updatePassword();

                break;
        }

    }

    private void updatePassword(){

        String oldPassword = bi.atvOldPassword.getText().toString();
        String newPassword = bi.atvNewPassword.getText().toString();
        String confPassword = bi.atvConfirmPassword.getText().toString();
        String userID = GH.getInstance().getUserID();

        if (oldPassword.equals("") || newPassword.equals("") || oldPassword.equals("")){
            ToastUtils.showToast(context,"Please fill all the fields");
        }else if (newPassword.equals(confPassword)){
            changePasswordAPI(userID,oldPassword,newPassword);
        }else{
            ToastUtils.showToast(context,"Password Doesn't Match");
        }

    }

    private void changePasswordAPI(String userID,String oldPassword,String password) {

        showProgressBar();
        Call<BaseResponse> _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).
                UpdatePassword(GH.getInstance().getAuthorization(),userID,oldPassword,password);
        _call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
            hideProgressBar();
                if (response.isSuccessful()) {

                    BaseResponse getUserProfile = response.body();
                    if (response.body().status.equals("Success")) {

                        ToastUtils.showToast(context, getUserProfile.message);

                    } else {

                        Toast.makeText(context, getUserProfile.message, Toast.LENGTH_SHORT).show();
                    }
                } else {

                    hideProgressBar();
                    ApiError error = ErrorUtils.parseError(response);

                    ToastUtils.showToastLong(context, error.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                hideProgressBar();
                ToastUtils.showToastLong(context, getString(R.string.retrofit_failure));
            }
        });
    }

    public void showProgressBar() {

        GH.getInstance().ShowProgressDialog(ChangePassword.this);
    }

    public void hideProgressBar() {

        GH.getInstance().HideProgressDialog();
    }

}
