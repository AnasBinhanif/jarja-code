package com.project.jarjamediaapp.Activities.add_appointment;

import com.project.jarjamediaapp.Base.BaseContract;
import com.project.jarjamediaapp.Models.GetAgentsModel;

public interface AddAppointmentContract {

    interface View extends BaseContract.View {

        void updateUI(GetAgentsModel response);

        void updateUIListForReminders(AddAppointmentModel response);

        void updateUIListForVia(AddAppointmentModel response);

        void updateUIListForLocation(GetLocationModel.Data response);

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

        void getAgentNames();

        void addAppointment(String prefix, String fromid);

        void getDropDownLocation(String prefix);

        void getReminder();

        void getVia();

    }

}
