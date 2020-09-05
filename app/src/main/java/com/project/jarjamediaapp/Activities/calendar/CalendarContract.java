package com.project.jarjamediaapp.Activities.calendar;

import com.project.jarjamediaapp.Base.BaseContract;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GmailCalender;

public interface CalendarContract {

    interface View extends BaseContract.View {

        void updateUIList(CalendarModel calendarModel);
        void updateUIGmailCalender(GmailCalender gmailCalender);


    }

    /*
	Actions - this defines the methods the pure Presenter class will implement. Also known as user actions,
	this is where the app logic is defined.
 	*/
    interface Actions extends BaseContract.Actions {

        void getCalendarEvents(String agentIdFromLogin, String month, String year);
        void getGmailCalender();

        void initScreen();

    }

}
