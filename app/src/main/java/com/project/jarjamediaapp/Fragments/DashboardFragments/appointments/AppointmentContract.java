//Contract
package com.project.jarjamediaapp.Fragments.DashboardFragments.appointments;


import com.project.jarjamediaapp.Models.GetAppointmentsModel;

public interface AppointmentContract {

    /*
    View - this defines the methods that the pure views like Activity or Fragments etc will implement.
    */
    interface View {
        void setupViews();

        void updateUI(GetAppointmentsModel response,String whichAppointment);

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
    interface Actions {
        void initScreen();
        void getTodayAppointments();
        void getUpcomingAppointments();
        void getPreviousAppointments();
        void getLeadTodayAppointments(String leadID);
        void getLeadUpcomingAppointments(String leadID);
        void getLeadPreviousAppointments(String leadID);
    }

    /*
    this defines the methods that pure model or persistence class like database or server data will implement.
    */
    interface Repository {

    }

}
