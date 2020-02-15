package com.project.jarjamediaapp.Activities.social_profiles;

import com.project.jarjamediaapp.Base.BaseContract;
import com.project.jarjamediaapp.Models.GetAllSocialProfiles;
import com.project.jarjamediaapp.Models.GetSocialProfileDropdown;

public interface Social_ProfilesContract {

    interface View extends BaseContract.View {

        void initViews();

        void updateUI(GetAllSocialProfiles response);

        void updateUI(GetSocialProfileDropdown response);

        void updateUIonFalse(String message);

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

        void getSocialProfileDropdown();

        void getAllSocialProfiles(String leadId);

        void addSocialProfile(String leadId, String name, String siteName, String profilelink);

        void initScreen();

    }

}
