package com.project.jarjamediaapp.Activities.listing_info;

import com.project.jarjamediaapp.Activities.open_houses.UploadImageModel;
import com.project.jarjamediaapp.Base.BaseContract;

import retrofit2.Response;

public interface ListingInfoContract {

    interface View extends BaseContract.View {

        void updateUIList(ListingInfoModel.Data response);

    }

    /*
	Actions - this defines the methods the pure Presenter class will implement. Also known as user actions,
	this is where the app logic is defined.
 	*/
    interface Actions extends BaseContract.Actions {

        void getLeadBuyingInfoDetails(String leadId);

        void getLeadListingInfoDetails(String leadId);

    }

}
