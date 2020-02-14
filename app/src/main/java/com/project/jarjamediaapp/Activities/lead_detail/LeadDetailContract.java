package com.project.jarjamediaapp.Activities.lead_detail;

import com.project.jarjamediaapp.Base.BaseContract;
import com.project.jarjamediaapp.Models.GetLead;

public interface LeadDetailContract {

    interface View extends BaseContract.View{
        void initViews();

        void updateUI(GetLead response);

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

        void getLead(String leadID);

        void initScreen();

    }

}
