//Contract
package com.project.jarjamediaapp.Fragments.DashboardFragments.followups;


import com.project.jarjamediaapp.Models.GetFollowUpsModel;

public interface FollowUpContract {

    /*
    View - this defines the methods that the pure views like Activity or Fragments etc will implement.
    */
     interface View {
         void setupViews();
        void updateUI(GetFollowUpsModel response);

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
        void getDueFollowUps(int page);
        void getOverDueFollowUps(int page);
        void getLeadFollowupsDue(String leadID,int page);
        void getLeadFollowupsOverDue(String leadID,int page);
    }

    /*
    this defines the methods that pure model or persistence class like database or server data will implement.
    */
     interface Repository {

    }

}
