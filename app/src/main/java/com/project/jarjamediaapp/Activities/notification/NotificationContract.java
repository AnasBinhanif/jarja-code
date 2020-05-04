package com.project.jarjamediaapp.Activities.notification;

import com.project.jarjamediaapp.Base.BaseContract;

import java.util.List;

public interface NotificationContract {

    interface View extends BaseContract.View {

        void updateUIListT(List<TaskNotificationModel.Data.TaskList> response);

        void updateUIListA(List<AppointmentNotificationModel.Data> response);

        void updateUIListF(List<FollowUpNotificationModel.Data> response);

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
