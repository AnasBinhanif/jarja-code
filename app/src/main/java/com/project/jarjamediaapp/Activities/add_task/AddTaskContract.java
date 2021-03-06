package com.project.jarjamediaapp.Activities.add_task;

import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentModel;
import com.project.jarjamediaapp.Base.BaseContract;
import com.project.jarjamediaapp.Models.GetAgentsModel;

public interface AddTaskContract {

    interface View extends BaseContract.View {

        void initViews();

        void updateUI(GetAgentsModel response);

        void updateUIListForReminders(AddAppointmentModel response);

        void updateUIListForVia(AddAppointmentModel response);

        void updateUIListForType(AddAppointmentModel response);

        void updateUIListForReoccur(AddAppointmentModel response);

        void updateTaskDetail(GetTaskDetail response);

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

        void initScreen();

        void getAgentNames();

        void getType();

        void getRecur();

        void getReminder();

        void getVia();

        void updateTask(String jsonObject);

        void getTaskDetail(String taskId);

        void getFutureTaskDetail(String scheduleID);

        void addTask(String body);

    }

}
