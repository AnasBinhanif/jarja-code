package com.project.jarjamediaapp.Activities.notification;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airbnb.paris.Paris;
import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentActivity;
import com.project.jarjamediaapp.Activities.add_appointment.Data;
import com.project.jarjamediaapp.Activities.add_appointment.GetAppointmentByIDModel;
import com.project.jarjamediaapp.Activities.add_task.AddTaskActivity;
import com.project.jarjamediaapp.Activities.add_task.GetTaskDetail;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.ViewFollowUpModel;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityNotificationBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import java.util.ArrayList;
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
    CardView cardviewNotification;
    int position;

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
        recyclerAdapterUtilT.addViewsList(R.id.tvName, R.id.tvLeadName, R.id.tvContact, R.id.tvEmail,R.id.cardview_notification);

        recyclerAdapterUtilT.addOnDataBindListener((Function4<View, TaskNotificationModel.Data.TaskList, Integer, Map<Integer, ? extends View>, Unit>) (view, data, integer, integerMap) -> {

            try {

                if(!data.getIsSeen()){

                    position = integer;
                    cardviewNotification = (CardView) integerMap.get(R.id.cardview_notification);
                    cardviewNotification.setCardBackgroundColor(getResources().getColor(R.color.colorYellow));

                }


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

           recyclerAdapterUtilT.addOnClickListener((Function2<TaskNotificationModel.Data.TaskList,Integer,Unit>) (viewComplainList, integer)-> {


               if (position == integer){

                   int backgroundColor = cardviewNotification.getCardBackgroundColor().getDefaultColor();
                   if (backgroundColor == getResources().getColor(R.color.colorYellow)){

                       cardviewNotification.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));

                   }
               }

               getTaskDetail(viewComplainList.encryptedTaskID);



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
        recyclerAdapterUtilA.addViewsList(R.id.tvName, R.id.tvLeadName, R.id.tvContact, R.id.tvEmail,R.id.cardview_notification);


        recyclerAdapterUtilA.addOnDataBindListener((Function4<View, AppointmentNotificationModel.Data, Integer, Map<Integer, ? extends View>, Unit>) (view, data, integer, integerMap) -> {

            try {



                if(!data.getIsSeen()){

                    position = integer;
                    cardviewNotification = (CardView) integerMap.get(R.id.cardview_notification);
                    cardviewNotification.setCardBackgroundColor(getResources().getColor(R.color.colorYellow));

                }

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


      recyclerAdapterUtilA.addOnClickListener((Function2<AppointmentNotificationModel.Data, Integer, Unit>) (viewComplainList, integer)-> {


          if (position == integer){

              int backgroundColor = cardviewNotification.getCardBackgroundColor().getDefaultColor();
              if (backgroundColor == getResources().getColor(R.color.colorYellow)){

                  cardviewNotification.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));

              }
          }


          getAppointmentById(viewComplainList.getLeadAppoinmentID());



          return Unit.INSTANCE;
      });


        bi.rvNotifications.setAdapter(recyclerAdapterUtilA);
        bi.rvNotifications.setVisibility(View.VISIBLE);

    }

    private void populateListDataF() {

        bi.rvNotifications.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bi.rvNotifications.setItemAnimator(new DefaultItemAnimator());
        bi.rvNotifications.addItemDecoration(new DividerItemDecoration(bi.rvNotifications.getContext(), 1));
        recyclerAdapterUtilF = new RecyclerAdapterUtil(context, notificationListF, R.layout.custom_notifications_layout);
        recyclerAdapterUtilF.addViewsList(R.id.tvName, R.id.tvLeadName, R.id.tvContact, R.id.tvEmail,R.id.cardview_notification);

        recyclerAdapterUtilF.addOnDataBindListener((Function4<View, FollowUpNotificationModel.Data, Integer, Map<Integer, ? extends View>, Unit>) (view, data, integer, integerMap) -> {

            try {

                if(!data.getIsSeen()){

                    position = integer;
                    cardviewNotification = (CardView) integerMap.get(R.id.cardview_notification);
                    cardviewNotification.setCardBackgroundColor(getResources().getColor(R.color.colorYellow));

                }

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

        recyclerAdapterUtilF.addOnClickListener((Function2<FollowUpNotificationModel.Data, Integer, Unit>) (viewComplainList, integer)-> {


            if (position == integer){

                int backgroundColor = cardviewNotification.getCardBackgroundColor().getDefaultColor();
                if (backgroundColor == getResources().getColor(R.color.colorYellow)){

                    cardviewNotification.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));

                }
            }

            getFolloUpDetailByID(viewComplainList.getDripDetailID());



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

    public void getTaskDetail(String taskId) {


        Call<GetTaskDetail> apiCall = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getTaskDetail(GH.getInstance().getAuthorization(), taskId);
        apiCall.enqueue(new Callback<GetTaskDetail>() {
            @Override
            public void onResponse(Call<GetTaskDetail> call, Response<GetTaskDetail> response) {

                if (response.isSuccessful()) {

                    GetTaskDetail getTaskDetail = response.body();

                    if (getTaskDetail.getStatus().equalsIgnoreCase("Success")) {

                        context.startActivity(new Intent(context, AddTaskActivity.class)
                                .putExtra("from", "3")
                                .putExtra("whichTasks", 1)
                                .putExtra("leadID", getTaskDetail.getData().leadID)
                                .putExtra("taskId", taskId));
                        // _view.updateTaskDetail(getTaskDetail);

                    } else {

                        ToastUtils.showToastLong(getApplicationContext(),getTaskDetail.message);
                       /* _view.hideProgressBar();

                        _view.updateUIonFalse(getTaskDetail.message);*/

                    }
                } else {


                    ApiError error = ErrorUtils.parseError(response);
                    ToastUtils.showToastLong(getApplicationContext(),error.Message());

                }
            }

            @Override
            public void onFailure(Call<GetTaskDetail> call, Throwable t) {
                ToastUtils.showToastLong(getApplicationContext(),t.getMessage());
            }
        });

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
    private void getFolloUpDetailByID(String dripDetailId) {

        Call<ViewFollowUpModel> _callToday;
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetFollowUpDetails(GH.getInstance().getAuthorization(), dripDetailId);
        _callToday.enqueue(new Callback<ViewFollowUpModel>() {
            @Override
            public void onResponse(Call<ViewFollowUpModel> call, Response<ViewFollowUpModel> response) {
                GH.getInstance().HideProgressDialog();
                if (response.isSuccessful()) {

                    ViewFollowUpModel getDetails = response.body();
                    if (getDetails.status.equals("Success")) {

                        GH.getInstance().HideProgressDialog();
                        String wait = getDetails.data.viewDPCStep.wait;
                        String time = getDetails.data.viewDPCStep.sendTime;
                        String note = getDetails.data.viewDPCStep.message;
                        String title = getDetails.data.viewDPCStep.subject;
                        String senType = getDetails.data.viewDPCStep.sentType;
                        String dateTime = getDetails.data.viewDPCStep.sendDateTime;

                        showViewFollowUpDialog(context, wait, title, dateTime, time, note, senType);


                    } else {

                        ToastUtils.showToast(context, getDetails.message);
                        GH.getInstance().HideProgressDialog();

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    ToastUtils.showToast(context, error.message());
                    GH.getInstance().HideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<ViewFollowUpModel> call, Throwable t) {
                GH.getInstance().HideProgressDialog();

                ToastUtils.showToastLong(context, context.getString(R.string.retrofit_failure));
            }
        });
    }

    public void showViewFollowUpDialog(Context context, String wait, String title, String dateTime, String time, String note, String sentType) {

        TextView edtWait, edtTitle, edtTime, edtNote;
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCancelable(true);


        if (sentType != null && sentType.equalsIgnoreCase("Wait")) {
            dialog.setContentView(R.layout.custom_view_followup_wait_dialog);

            edtNote = (TextView) dialog.findViewById(R.id.edtNote);
            edtTitle = (TextView) dialog.findViewById(R.id.edtTitle);
            edtTime = (TextView) dialog.findViewById(R.id.edtTime);
            edtWait = (TextView) dialog.findViewById(R.id.edtWait);
            edtWait.setText(wait);
            edtTime.setText(time);

        } else {

            dialog.setContentView(R.layout.custom_view_followup_dialog);
            edtNote = (TextView) dialog.findViewById(R.id.edtNote);
            edtTitle = (TextView) dialog.findViewById(R.id.edtTitle);
            edtTime = (TextView) dialog.findViewById(R.id.edtTime);
            edtTime.setText(GH.getInstance().formatter(dateTime,"MM-dd-yyyy hh:mm a","yyyy-MM-dd'T'HH:mm:ss"));

        }

        edtNote.setText(note);
        edtTitle.setText(title);

        dialog.show();
    }


}