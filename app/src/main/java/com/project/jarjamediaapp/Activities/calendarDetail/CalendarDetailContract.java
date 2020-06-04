package com.project.jarjamediaapp.Activities.calendarDetail;

import com.project.jarjamediaapp.Activities.calendar.CalendarModel;
import com.project.jarjamediaapp.Base.BaseContract;

public interface CalendarDetailContract {

    interface View extends BaseContract.View {

        void updateUIList(CalendarModel calendarModel);
    }

    /*
	Actions - this defines the methods the pure Presenter class will implement. Also known as user actions,
	this is where the app logic is defined.
 	*/
    interface Actions extends BaseContract.Actions {

        void getCalendarEvents(String agentIdFromLogin, String month, String year);
    }

}
