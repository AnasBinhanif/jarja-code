package com.project.jarjamediaapp.Activities.appointments;

import com.project.jarjamediaapp.Base.BaseContract;

public interface AppointmentContract {

    interface View extends BaseContract.View{

    }

    /*
	Actions - this defines the methods the pure Presenter class will implement. Also known as user actions,
	this is where the app logic is defined.
 	*/
    interface Actions extends BaseContract.Actions {

        void addData();

        void initScreen();

    }

}
