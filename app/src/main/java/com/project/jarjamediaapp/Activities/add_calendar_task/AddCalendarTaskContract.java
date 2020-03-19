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

        void addCalendarTask(String title, String description, String startDateTime, boolean allDay, boolean markComplete);

        void updateCalendarTask(String title, String description, String startDateTime, boolean allDay, boolean markComplete,String calendarId,String encryptedLeadId);

    }

}
