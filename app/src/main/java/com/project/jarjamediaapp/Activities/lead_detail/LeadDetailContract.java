package com.project.jarjamediaapp.Activities.lead_detail;

import com.project.jarjamediaapp.Base.BaseContract;

public interface LeadDetailContract {

    interface View extends BaseContract.View{

    }

    /*
	Actions - this defines the methods the pure Presenter class will implement. Also known as user actions,
	this is where the app logic is defined.
 	*/
    interface Actions extends BaseContract.Actions {

        void addData();

    }

}
