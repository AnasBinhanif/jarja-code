package com.project.jarjamediaapp.Activities.user_profile;

import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentModel;
import com.project.jarjamediaapp.Activities.add_appointment.GetLocationModel;
import com.project.jarjamediaapp.Base.BaseContract;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetUserProfile;

public interface UserProfileContract {

    interface View extends BaseContract.View {

        void updateUIonFalse(String message);

        void updateUI(GetUserProfile getUserProfile);

        void updateUI(BaseResponse getUserProfile);

        void updateUIonError(String error);

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
        void getUserProfile();
        void getTwilioNumber();
        void updateUserProfile();

    }

}
