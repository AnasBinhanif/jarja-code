package com.project.jarjamediaapp.Activities.listing_info;

import com.project.jarjamediaapp.Base.BaseContract;

public interface ListingInfoContract {

    interface View extends BaseContract.View {

        void updateUIList(ListingInfoModel response);

    }

    /*
	Actions - this defines the methods the pure Presenter class will implement. Also known as user actions,
	this is where the app logic is defined.
 	*/
    interface Actions extends BaseContract.Actions {

        void getBuyingInfoDetails();

        void getListingInfoDetails();

    }

}
