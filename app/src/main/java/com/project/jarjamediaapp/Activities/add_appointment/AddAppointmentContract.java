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

        void addAppointment(String leadStringID, String agentsStringIDs, String leadAppointmentID, String eventTitle, String location,
                            String desc, String isAppointmentFixed, String isAppointmentAttend, String appointmentDate, String datedFrom,
                            String datedTo, String isAllDay, String interval, String isSend, String viaReminder, String agentIds,
                            String orderBy,String startTime, String endTime, String isCompleted, String fromId,String calendarId);

        void initScreen();

        void getAgentNames();

        void getDropDownLocation(String prefix);

        void getReminder();

        void getVia();

    }

}
