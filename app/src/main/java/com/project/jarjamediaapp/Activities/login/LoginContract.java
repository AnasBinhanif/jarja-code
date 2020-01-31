package com.project.jarjamediaapp.Activities.login;

import com.project.jarjamediaapp.Base.BaseContract;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Networking.ApiError;

import retrofit2.Response;

public interface LoginContract {

    interface View {

        void setupViews();

        void updateUI(Response<LoginModel> response);

        void updateUIList(Response<LoginModel> response);

        void updateUIonFalse(String message);

        void updateUIonError(ApiError error);

        void updateUIonFailure();

        void showProgressBar();

        void hideProgressBar();
    }

    /*
	Actions - this defines the methods the pure Presenter class will implement. Also known as user actions,
	this is where the app logic is defined.
 	*/
    interface Actions extends BaseContract.Actions {

        void initScreen();
        //void loginUser();
    }

}
