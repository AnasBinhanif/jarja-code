package com.project.jarjamediaapp.Activities.transactions;

import com.project.jarjamediaapp.Base.BaseContract;
import com.project.jarjamediaapp.Base.BaseResponse;

import retrofit2.Response;

public interface TransactionContract {

    interface View extends BaseContract.View{

        void initViews();

        void updateUI(Response<BaseResponse> response);

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

        void addPipelineMark(String pipelineID, String encrypted_LeadDetailID,
                             String presentationID);

        void initScreen();

    }

}
