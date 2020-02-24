package com.project.jarjamediaapp.Activities.composeEmail;

import com.project.jarjamediaapp.Activities.add_appointment.GetLocationModel;
import com.project.jarjamediaapp.Base.BaseContract;

public interface ComposeEmailContract {

    interface View extends BaseContract.View {

        void updateUIListForRecipient(GetLocationModel.Data response);

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

        void initScreen();

        void getLeadRecipient(String leadId);

        void sendEmailContent();

    }

}
