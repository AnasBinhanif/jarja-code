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

        void addUpdateCalendarAppointmentViaTask(Integer leadAppointmentID, String isAppointmentAttend, String datedFrom, String datedTo, boolean isAllDay,
                                                 boolean isCompleted, String eventTitle, String desc, boolean isGmailAppActive, String calendarType, String gmailCalendarId);


    }

}
