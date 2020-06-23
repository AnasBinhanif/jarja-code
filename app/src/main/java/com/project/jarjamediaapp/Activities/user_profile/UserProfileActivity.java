package com.project.jarjamediaapp.Activities.user_profile;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.databinding.ActivityUserProfileBinding;

import retrofit2.Response;

public class UserProfileActivity extends BaseActivity implements UserProfileContract.View, View.OnClickListener {

    ActivityUserProfileBinding bi;
    Context context = UserProfileActivity.this;
    UserProfilePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_user_profile);
        presenter = new UserProfilePresenter(this);
        presenter.initScreen();

    }

    @Override
    public void initViews() {
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.my_profile), true);
    }

    @Override
    public void updateUI(Response<BaseResponse> response) {

    }

    @Override
    public void updateUIonFalse(String message) {

    }

    @Override
    public void updateUIonError(String error) {

    }

    @Override
    public void updateUIonFailure() {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }
}