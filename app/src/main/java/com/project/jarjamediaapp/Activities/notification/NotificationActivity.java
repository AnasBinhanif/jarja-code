package com.project.jarjamediaapp.Activities.notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airbnb.paris.Paris;
import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentActivity;
import com.project.jarjamediaapp.Activities.add_appointment.Data;
import com.project.jarjamediaapp.Activities.add_appointment.GetAppointmentByIDModel;
import com.project.jarjamediaapp.Activities.lead_detail.LeadDetailActivity;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAllLeads;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityNotificationBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends BaseActivity implements NotificationContract.View {

    ActivityNotificationBinding bi;
    Context context = NotificationActivity.this;
    NotificationPresenter presenter;
    List<TaskNotificationModel.Data.TaskList> notificationListT;
    List<AppointmentNotificationModel.Data> notificationListA;
    List<FollowUpNotificationModel.Data> notificationListF;
    RecyclerAdapterUtil recyclerAdapterUtilT, recyclerAdapterUtilA, recyclerAdapterUtilF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_notification);
        presenter = new NotificationPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, "Notifications", true);

      /*  if (getIntent().getExtras() != null) {

           String typeOfNotification = getIntent().getStringExtra("notificationType");

           switch (typeOfNotification){
               case "apointment":

                   Paris.style(bi.btnTasks).apply(R.style.TabButtonTranparentLeft);
                   Paris.style(bi.btnAppointments).apply(R.style.TabButtonYellowMiddle);
                   Paris.style(bi.btnFollowUps).apply(R.style.TabButtonTranparentRight);
                   presenter.getNotificationByAppointments();

                   break;

           }
        }*/


    }

    private void populateListDataT() {

        bi.rvNotifications.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bi.rvNotifications.setItemAnimator(new DefaultItemAnimator());
        bi.rvNotifications.addItemDecoration(new DividerItemDecoration(bi.rvNotifications.getContext(), 1));
        recyclerAdapterUtilT = new RecyclerAdapterUtil(context, notificationListT, R.layout.custom_notifications_layout);
        recyclerAdapterUtilT.addViewsList(R.id.tvName, R.id.tvLeadName, R.id.tvContact, R.id.tvEmail);

        recyclerAdapterUtilT.addOnDataBindListener((Function4<View, TaskNotificationModel.Data.TaskList, Integer, Map<Integer, ? extends View>, Unit>) (view, data, integer, integerMap) -> {

            try {

                TextView tvName = (TextView) integerMap.get(R.id.tvName);
                tvName.setText(data.getTaskName() != null ? data.getTaskName() : "N/A");

                TextView tvLeadName = (TextView) integerMap.get(R.id.tvLeadName);

                tvLeadName.setText(data.getDescription());

               /* String firstName = data.getVtCRMLeadCustom().getFirstName();
                String lastName = data.getVtCRMLeadCustom().getFirstName();
                if (firstName != null && lastName != null) {
                    tvLeadName.setText(firstName + " " + lastName);
                } else if (firstName == null && lastName != null) {
                    tvLeadName.setText(lastName);
                } else if (firstName != null && lastName == null) {
                    tvLeadName.setText(firstName);
                } else if (firstName == null && lastName == null) {
                    tvLeadName.setText("N/A");
                }

                TextView tvContact = (TextView) integerMap.get(R.id.tvContact);
                tvContact.setText(data.getVtCRMLeadCustom().getPrimaryPhone() != null ? data.getVtCRMLeadCustom().getPrimaryPhone() : "N/A");
*/
                TextView tvContact = (TextView) integerMap.get(R.id.tvContact);
                TextView tvEmail = (TextView) integerMap.get(R.id.tvEmail);

                tvContact.setText(data.getTaskType());

             /*  if (data.getVtCRMLeadCustom() != null){


                   tvContact.setText(data.getVtCRMLeadCustom().getPrimaryEmail() != null ? data.getVtCRMLeadCustom().getPrimaryEmail() : "N/A");

               }else {
                   tvContact.setText("N/A");

               }*/

              /*  TextView tvEmail = (TextView) integerMap.get(R.id.tvEmail);
                tvEmail.setText(data.getVtCRMLeadCustom().getPrimaryEmail() != null ? data.getVtCRMLeadCustom().getPrimaryEmail() : "N/A");
*/
            } catch (Exception e) {
                e.printStackTrace();

                ToastUtils.showToastLong(context,"Exception");
            }

            return Unit.INSTANCE;
        });

        bi.rvNotifications.setAdapter(recyclerAdapterUtilT);
        bi.rvNotifications.setVisibility(View.VISIBLE);

    }

    private void populateListDataA() {

        bi.rvNotifications.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bi.rvNotifications.setItemAnimator(new DefaultItemAnimator());
        bi.rvNotifications.addItemDecoration(new DividerItemDecoration(bi.rvNotifications.getContext(), 1));
        recyclerAdapterUtilA = new RecyclerAdapterUtil(context, notificationListA, R.layout.custom_notifications_layout);
        recyclerAdapterUtilA.addViewsList(R.id.tvName, R.id.tvLeadName, R.id.tvContact, R.id.tvEmail);

        recyclerAdapterUtilA.addOnDataBindListener((Function4<View, AppointmentNotificationModel.Data, Integer, Map<Integer, ? extends View>, Unit>) (view, data, integer, integerMap) -> {

            try {

                TextView tvName = (TextView) integerMap.get(R.id.tvName);
                tvName.setText(data.getEventTitle() != null ? data.getEventTitle() : "N/A");

                TextView tvLeadName = (TextView) integerMap.get(R.id.tvLeadName);

                String firstName = data.getVtCRMLeadCustom().getFirstName();
                String lastName = data.getVtCRMLeadCustom().getLastName();
                if (firstName != null && lastName != null) {
                    tvLeadName.setText(firstName + " " + lastName);
                } else if (firstName == null && lastName != null) {
                    tvLeadName.setText(lastName);
                } else if (firstName != null && lastName == null) {
                    tvLeadName.setText(firstName);
                } else if (firstName == null && lastName == null) {
                    tvLeadName.setText("N/A");
                }

                TextView tvContact = (TextView) integerMap.get(R.id.tvContact);
                tvContact.setText(data.getVtCRMLeadCustom().getPrimaryPhone() != null ? data.getVtCRMLeadCustom().getPrimaryPhone() : "N/A");

                TextView tvEmail = (TextView) integerMap.get(R.id.tvEmail);
                tvEmail.setText(data.getVtCRMLeadCustom().getPrimaryEmail() != null ? data.getVtCRMLeadCustom().getPrimaryEmail() : "N/A");


            } catch (Exception e) {
                e.printStackTrace();
            }

            return Unit.INSTANCE;
        });


    /*  recyclerAdapterUtilA.addOnClickListener((Function2<AppointmentNotificationModel.Data, Integer, Unit>) (viewComplainList, integer)-> {


          getAppointmentById(viewComplainList.getVtCRMLeadCustom().getLeadID());



          return Unit.INSTANCE;
      });
     */

        bi.rvNotifications.setAdapter(recyclerAdapterUtilA);
        bi.rvNotifications.setVisibility(View.VISIBLE);

    }

    private void populateListDataF() {

        bi.rvNotifications.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bi.rvNotifications.setItemAnimator(new DefaultItemAnimator());
        bi.rvNotifications.addItemDecoration(new DividerItemDecoration(bi.rvNotifications.getContext(), 1));
        recyclerAdapterUtilF = new RecyclerAdapterUtil(context, notificationListF, R.layout.custom_notifications_layout);
        recyclerAdapterUtilF.addViewsList(R.id.tvName, R.id.tvLeadName, R.id.tvContact, R.id.tvEmail);

        recyclerAdapterUtilF.addOnDataBindListener((Function4<View, FollowUpNotificationModel.Data, Integer, Map<Integer, ? extends View>, Unit>) (view, data, integer, integerMap) -> {

            try {

                TextView tvName = (TextView) integerMap.get(R.id.tvName);
                tvName.setText(data.getFollowUpsType() != null ? data.getFollowUpsType() : "N/A");

                TextView tvLeadName = (TextView) integerMap.get(R.id.tvLeadName);
                tvLeadName.setText(data.getLeadName() != null ? data.getLeadName() : "N/A");

                TextView tvContact = (TextView) integerMap.get(R.id.tvContact);
                tvContact.setText(data.getAddress() != null ? data.getAddress() : "N/A");

                TextView tvEmail = (TextView) integerMap.get(R.id.tvEmail);
                tvEmail.setText(data.getDripType() != null ? data.getDripType() : "N/A");

            } catch (Exception e) {
                e.printStackTrace();
            }

            return Unit.INSTANCE;
        });

        bi.rvNotifications.setAdapter(recyclerAdapterUtilF);
        bi.rvNotifications.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnTasks:

                Paris.style(bi.btnTasks).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnAppointments).apply(R.style.TabButtonTranparentMiddle);
                Paris.style(bi.btnFollowUps).apply(R.style.TabButtonTranparentRight);
                presenter.getNotificationByTasks();

                break;

            case R.id.btnAppointments:

                Paris.style(bi.btnTasks).apply(R.style.TabButtonTranparentLeft);
                Paris.style(bi.btnAppointments).apply(R.style.TabButtonYellowMiddle);
                Paris.style(bi.btnFollowUps).apply(R.style.TabButtonTranparentRight);
                presenter.getNotificationByAppointments();

                break;

            case R.id.btnFollowUps:

                Paris.style(bi.btnTasks).apply(R.style.TabButtonTranparentLeft);
                Paris.style(bi.btnAppointments).apply(R.style.TabButtonTranparentMiddle);
                Paris.style(bi.btnFollowUps).apply(R.style.TabButtonYellowRight);
                presenter.getNotificationByFollowUps();

                break;
        }
    }

    @Override
    public void initViews() {

        notificationListT = new ArrayList<>();
        notificationListA = new ArrayList<>();
        notificationListF = new ArrayList<>();
        bi.btnTasks.setOnClickListener(this);
        bi.btnAppointments.setOnClickListener(this);
        bi.btnFollowUps.setOnClickListener(this);
        presenter.getNotificationByTasks();

    }

    @Override
    public void updateUI(Response<BaseResponse> response) {


    }

    @Override
    public void updateUIonFalse(String message) {

        bi.rvNotifications.setVisibility(View.GONE);
        bi.tvMessage.setVisibility(View.VISIBLE);

    }

    @Override
    public void updateUIonError(String error) {

        /*if (error.contains("Authorization has been denied for this request")) {
            ToastUtils.showErrorToast(context, "Session Expired", "Please Login Again");
            logout();
        } else {*/
            bi.rvNotifications.setVisibility(View.GONE);
            bi.tvMessage.setVisibility(View.VISIBLE);

    }

    @Override
    public void updateUIonFailure() {

        bi.rvNotifications.setVisibility(View.GONE);
        bi.tvMessage.setVisibility(View.VISIBLE);

    }

    @Override
    public void showProgressBar() {
        GH.getInstance().ShowProgressDialog(NotificationActivity.this);
    }

    @Override
    public void hideProgressBar() {
        GH.getInstance().HideProgressDialog();
    }

    @Override
    public void updateUIListT(List<TaskNotificationModel.Data.TaskList> response) {


        populateListDataT();
        notificationListA.clear();
        notificationListF.clear();
        notificationListT.clear();
        if (response.size() > 0) {
            notificationListT.addAll(response);
            recyclerAdapterUtilT.notifyDataSetChanged();
            bi.rvNotifications.setVisibility(View.VISIBLE);
            bi.tvMessage.setVisibility(View.GONE);
        } else {
            bi.rvNotifications.setVisibility(View.GONE);
            bi.tvMessage.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void updateUIListA(List<AppointmentNotificationModel.Data> response) {

        populateListDataA();
        notificationListA.clear();
        notificationListF.clear();
        notificationListT.clear();
        if (response.size() > 0) {
            notificationListA.addAll(response);
            recyclerAdapterUtilA.notifyDataSetChanged();
            bi.rvNotifications.setVisibility(View.VISIBLE);
            bi.tvMessage.setVisibility(View.GONE);
        } else {
            bi.rvNotifications.setVisibility(View.GONE);
            bi.tvMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void updateUIListF(List<FollowUpNotificationModel.Data> response) {

        populateListDataF();
        notificationListA.clear();
        notificationListF.clear();
        notificationListT.clear();
        if (response.size() > 0) {
            notificationListF.addAll(response);
            recyclerAdapterUtilF.notifyDataSetChanged();
            bi.rvNotifications.setVisibility(View.VISIBLE);
            bi.tvMessage.setVisibility(View.GONE);
        } else {
            bi.rvNotifications.setVisibility(View.GONE);
            bi.tvMessage.setVisibility(View.VISIBLE);
        }
    }

    public void getAppointmentById(String appointmentID) {

        Call<GetAppointmentByIDModel> _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetAppointmentByID(GH.getInstance().getAuthorization(),appointmentID);
        _call.enqueue(new Callback<GetAppointmentByIDModel>() {
            @Override
            public void onResponse(Call<GetAppointmentByIDModel> call, Response<GetAppointmentByIDModel> response) {

                if (response.isSuccessful()) {

                    GetAppointmentByIDModel getAppointmentsModel = response.body();

                    if (getAppointmentsModel.getStatus().equals("Success")) {

                        // from notifcation screen when tap on notification item
                        Data models = getAppointmentsModel.getData();
                        context.startActivity(new Intent(context, AddAppointmentActivity.class)
                                .putExtra("leadID", models.getLeadID())
                                .putExtra("from", "7")
                                .putExtra("leadName",models.getEventTitle())
                                .putExtra("models", models));
                        //_view.updateUI(getAppointmentsModel);

                    } else {

                       /* _view.hideProgressBar();
                        _view.updateUIonFalse(getAppointmentsModel.message);*/

                    }
                } else {

//                    _view.hideProgressBar();
//                    ApiError error = ErrorUtils.parseError(response);
//                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetAppointmentByIDModel> call, Throwable t) {
               /* _view.hideProgressBar();
                _view.updateUIonFailure();*/
            }
        });
    }

}