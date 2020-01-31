package com.project.jarjamediaapp.Activities.splash;

import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ResponseModel.AccessCode;

import retrofit2.Response;

public interface MainContract {

    interface View {

        void setupViews();

        void updateUI(Response<AccessCode> response);

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

    interface Actions {

        void initScreen();

        void getToken(String username, String password, String grantType);

    }
}
