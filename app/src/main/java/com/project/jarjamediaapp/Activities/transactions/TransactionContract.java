package com.project.jarjamediaapp.Activities.transactions;

import com.google.gson.JsonObject;
import com.project.jarjamediaapp.Base.BaseContract;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetLeadTransactionStage;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Response;

public interface TransactionContract {

    interface View extends BaseContract.View {

        void initViews();

        void updateUI(Response<BaseResponse> response);

        void updateUIToCallTransactionApiAgain(Response<BaseResponse> responseResponse);

        void addAgentCommission(Response<BaseResponse> response);

        void getAgentCommission(Response<TransactionModel> response);

        void updateUI(GetLeadTransactionStage response);

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

        void addPipelineMark(String body);

        void getTransaction(String leadID);

        void addAgentCommission(String data);

        void getAgentCommission(String leadId,String leadDetailId);

        void initScreen();

        void assignAgents(String agentsIDs, String leadID, boolean typeIndex);

    }

}
