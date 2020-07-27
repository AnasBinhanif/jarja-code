package com.project.jarjamediaapp.Activities.calendar;

import android.util.Log;

import com.project.jarjamediaapp.Base.BasePresenter;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.Utilities.GH;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarPresenter extends BasePresenter<CalendarContract.View> implements CalendarContract.Actions {

    Call<CalendarModel> _call;

    public CalendarPresenter(CalendarContract.View view) {
        super(view);
    }

    @Override
    public void getCalendarEvents(String agentIdFromLogin, String month, String year) {

        _view.showProgressBar();
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getCalendarEvents(GH.getInstance().getAuthorization(), agentIdFromLogin, "NULL", month, year);
        _call.enqueue(new Callback<CalendarModel>() {
            @Override
            public void onResponse(Call<CalendarModel> call, Response<CalendarModel> response) {


                _view.hideProgressBar();
                if (response.isSuccessful()) {

                    CalendarModel calendarModel = response.body();
                    Log.i("response",""+response.raw());
                    Log.i("response",""+response.body().data);
                    Log.i("response",""+response.body().getData());


                    if (calendarModel.getStatus().equalsIgnoreCase("Success")) {

                        _view.updateUIList(calendarModel);

                    } else {

                        _view.updateUIonFalse(calendarModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<CalendarModel> call, Throwable t) {
                _view.hideProgressBar();
                _view.updateUIonFailure();
            }
        });


    }

    public void initScreen() {
        _view.initViews();
    }


    @Override
    public void detachView() {

        if (_call != null) {

            _call.cancel();
        }
        super.detachView();
    }

}
