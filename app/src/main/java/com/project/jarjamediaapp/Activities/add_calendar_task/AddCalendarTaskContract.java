package com.project.jarjamediaapp.Activities.add_calendar_task;

import com.project.jarjamediaapp.Base.BaseContract;

public interface AddCalendarTaskContract {

    interface View extends BaseContract.View {


    }

    /*
	Actions - this defines the methods the pure Presenter class will implement. Also known as user actions,
	this is where the app logic is defined.
 	*/
    interface Actions extends BaseContract.Actions {

        void addUpdateCalendarAppointmentViaTask(String leadStringID, String agentIDsString, String agentIds, String leadAppointmentID, String isAppointmentFixed,
                                                 String isAppointmentAttend, boolean isSend, boolean isAllDay, boolean isCompleted, String datedFrom, String datedTo,
                                                 String appointmentDate, String startTime, String eventTitle, String desc, String location, String viaReminder,
                                                 Integer interval, Integer orderBy, boolean isGmailApptActive, String calendarType, String gmailCalendarId);


    }

}
