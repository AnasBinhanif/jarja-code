package com.project.jarjamediaapp.Activities.notification;

import com.project.jarjamediaapp.Base.BaseContract;

import java.util.List;

public interface NotificationContract {

    interface View extends BaseContract.View {

        void updateUIListT(List<TaskNotificationModel.TaskList> response,Integer taskNotificationCount);

        void updateUIListA(List<AppointmentNotificationModel.FollowUpsList> response,Integer appointmentNotificationCount);

        void updateUIListF(List<FollowUpNotificationModel.FollowUpsList> response,Integer foolowupsNotificationCount);

    }

    /*
	Actions - this defines the methods the pure Presenter class will implement. Also known as user actions,
	this is where the app logic is defined.
 	*/
    interface Actions extends BaseContract.Actions {

        void getNotificationByTasks(int page);

        void getNotificationByAppointments(int page);

        void getNotificationByFollowUps(int page);

        void initScreen();

    }

}
