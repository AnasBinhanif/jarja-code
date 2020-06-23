package com.project.jarjamediaapp.Activities.user_profile;

import android.util.Log;

import com.google.gson.Gson;
import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentContract;
import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentModel;
import com.project.jarjamediaapp.Activities.add_appointment.GetLocationModel;
import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfilePresenter extends BasePresenter<UserProfileContract.View> implements UserProfileContract.Actions {


    public UserProfilePresenter(UserProfileContract.View view) {
        super(view);
    }

    public void initScreen() {
        _view.initViews();
    }

}
