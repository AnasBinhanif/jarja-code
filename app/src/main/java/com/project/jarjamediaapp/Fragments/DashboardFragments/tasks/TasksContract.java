//Contract
package com.project.jarjamediaapp.Fragments.DashboardFragments.tasks;


import com.project.jarjamediaapp.Models.GetTasksModel;

public interface TasksContract {

    /*
    View - this defines the methods that the pure views like Activity or Fragments etc will implement.
    */
    interface View {
        void setupViews();

        void updateUI(GetTasksModel response);

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

        void getDueTasks(int page);

        void getOverDueTasks(int page);

        void getFutureTasks(int page);

        void getLeadDueTasks(String leadID, int page);

        void getLeadOverDueTasks(String leadID, int page);

        void getLeadFutureTasks(String leadID, int page);
    }

    /*
    this defines the methods that pure model or persistence class like database or server data will implement.
    */
    interface Repository {

    }

}
