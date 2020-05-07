package com.project.jarjamediaapp.Activities.all_leads;

import com.project.jarjamediaapp.Base.BaseContract;
import com.project.jarjamediaapp.Models.GetAllLeads;
import com.project.jarjamediaapp.Models.GetPropertyLeads;

public interface AllLeadsContract {

    interface View extends BaseContract.View {

        void initViews();

        void updateUI(GetAllLeads response);

        void updateUI(GetPropertyLeads response);

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

        void GetPropertyLeads(String propertyID);

        void SearchLead(int pageNo, String query);

        void getAllLeads(String data);

        void initScreen();

    }

}
