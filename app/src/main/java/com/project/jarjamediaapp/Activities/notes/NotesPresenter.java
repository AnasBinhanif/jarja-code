package com.project.jarjamediaapp.Activities.notes;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetLeadNotes;
import com.project.jarjamediaapp.Models.GetNoteDropDown;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotesPresenter extends BasePresenter<NotesContract.View> implements NotesContract.Actions {

    Call<BaseResponse> _call;
    Call<GetAgentsModel> _callGetAgentsModel;
    Call<GetNoteDropDown> _callGetNoteDropDown;
    Call<GetLeadNotes> _callGetLeadNotes;
    Call<DocumentModel> _callDoc;


    public NotesPresenter(NotesContract.View view) {
        super(view);
    }

    @Override
    public void editNote(String leadID, String noteID, String Desc) {
        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).EditNote(GH.getInstance().getAuthorization(),
                noteID, leadID, Desc);
        _call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (getAppointmentsModel.getStatus().equalsIgnoreCase("Success")) {

                        _view.updateUI(response);

                    } else {

                        _view.updateUIonFalse(getAppointmentsModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void getNoteDropDown() {

        _view.showProgressBar();
        _callGetNoteDropDown = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetNoteDropDown(GH.getInstance().getAuthorization()
        );
        _callGetNoteDropDown.enqueue(new Callback<GetNoteDropDown>() {
            @Override
            public void onResponse(Call<GetNoteDropDown> call, Response<GetNoteDropDown> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetNoteDropDown getAppointmentsModel = response.body();
                    if (getAppointmentsModel.status.equals("Success")) {

                        _view.updateUI(getAppointmentsModel);

                    } else {

                        _view.updateUIonFalse(getAppointmentsModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetNoteDropDown> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });


    }

    @Override
    public void getAgentNames() {

        _view.showProgressBar();
        _callGetAgentsModel = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetAgents(GH.getInstance().getAuthorization());
        _callGetAgentsModel.enqueue(new Callback<GetAgentsModel>() {
            @Override
            public void onResponse(Call<GetAgentsModel> call, Response<GetAgentsModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetAgentsModel getAppointmentsModel = response.body();
                    if (getAppointmentsModel.status.equals("Success")) {

                        _view.updateUI(getAppointmentsModel);

                    } else {

                        _view.updateUIonFalse(getAppointmentsModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetAgentsModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });
    }

    @Override
    public void getLeadNotes(String encryptedLeadID) {


        _view.showProgressBar();
        _callGetLeadNotes = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetLeadNotes(GH.getInstance().getAuthorization(),
                encryptedLeadID);
        _callGetLeadNotes.enqueue(new Callback<GetLeadNotes>() {
            @Override
            public void onResponse(Call<GetLeadNotes> call, Response<GetLeadNotes> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    GetLeadNotes getAppointmentsModel = response.body();
                    if (getAppointmentsModel.status.equals("Success")) {

                        _view.updateUI(getAppointmentsModel);

                    } else {

                        _view.updateUIonFalse(getAppointmentsModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetLeadNotes> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void addNote(String noteID, String leadID, String noteType, String desc, String isSticky, String dated, String agentIDs,
                        String leadStringID) {


        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).AddNote(GH.getInstance().getAuthorization(),
                noteID, leadID, noteType, desc, isSticky, dated, agentIDs, leadStringID);
        _call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (getAppointmentsModel.getStatus().equalsIgnoreCase("Success")) {

                        _view.updateUI(response);

                    } else {

                        _view.updateUIonFalse(getAppointmentsModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });


    }

    public void initScreen() {
        _view.initViews();
    }

    @Override
    public void getDocumentByLeadId(String leadId) {

        // hide progress bar for repeating
     //   _view.showProgressBar();
        _callDoc = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getDocumentByLeadId(GH.getInstance().getAuthorization(), leadId);
        _callDoc.enqueue(new Callback<DocumentModel>() {
            @Override
            public void onResponse(Call<DocumentModel> call, Response<DocumentModel> response) {

                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    DocumentModel documentModel = response.body();
                    if (documentModel.getStatus().equalsIgnoreCase("Success")) {

                        _view.updateUIListDocuments(documentModel);
                        // stop progress bar when file get from api
                        _view.hideProgressBar();

                    } else {

                        _view.updateUIonFalse(documentModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<DocumentModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });

    }

    @Override
    public void addDocumentByLeadId(MultipartBody.Part part, String leadId) {

        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).uploadDocumentByLeadId(GH.getInstance().getAuthorization(), part, leadId);
        _call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                if (response.isSuccessful()) {

                    BaseResponse documentModel = response.body();
                    if (documentModel.getStatus().equalsIgnoreCase("Success")) {

                        _view.updateUIListAfterAddDoc(documentModel);
                        getDocumentByLeadId(leadId);

                    } else {

                        _view.updateUIonFalse(documentModel.message);

                    }
                } else {
                    _view.hideProgressBar();
                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });


    }

    @Override
    public void detachView() {

        if (_callGetNoteDropDown != null) {

            _callGetNoteDropDown.cancel();
        }
        super.detachView();
    }

}
