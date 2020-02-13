package com.project.jarjamediaapp.Activities.notification;

import com.project.jarjamediaapp.Base.BaseContract;

import retrofit2.Response;

public interface NotificationContract {

    interface View extends BaseContract.View {

        void updateUIList(Response<NotificationModel> response);

    }

    /*
	Actions - this defines the methods the pure Presenter class will implement. Also known as user actions,
	this is where the app logic is defined.
 	*/
    interface Actions extends BaseContract.Actions {

        void getNotificationByTasks();

        void getNotificationByAppointments();

        void getNotificationByFollowUps();

        void initScreen();

    }

}
