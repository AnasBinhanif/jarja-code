package com.project.jarjamediaapp.Activities.notification;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.paris.Paris;
import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentActivity;
import com.project.jarjamediaapp.Activities.add_appointment.Data;
import com.project.jarjamediaapp.Activities.add_appointment.GetAppointmentByIDModel;
import com.project.jarjamediaapp.Activities.add_task.AddTaskActivity;
import com.project.jarjamediaapp.Activities.add_task.GetTaskDetail;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.CustomAdapter.AppointmentNotificationRecyclerAdapter;
import com.project.jarjamediaapp.CustomAdapter.FollowUpsNotificationRecyclerAdapter;
import com.project.jarjamediaapp.CustomAdapter.TaskNotificatonRecyclerAdapter;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends BaseActivity implements NotificationContract.View {

    ActivityNotificationBinding bi;
    Context context = NotificationActivity.this;
    NotificationPresenter presenter;
    List<TaskNotificationModel.TaskList> notificationListT;
    List<AppointmentNotificationModel.FollowUpsList> notificationListA;
    List<FollowUpNotificationModel.FollowUpsList> notificationListF;
    RecyclerAdapterUtil recyclerAdapterUtilT, recyclerAdapterUtilA, recyclerAdapterUtilF;
    AppointmentNotificationRecyclerAdapter appointmentRecyclerviewAdapter;
    TaskNotificatonRecyclerAdapter taskRecyclerviewAdapter;
    FollowUpsNotificationRecyclerAdapter folloupsRecyclerviewAdapter;
    LinearLayoutManager layoutManagerAppointment,layoutManagerFollowups,layoutManagerTask;
    int page = 0;
    int totalPages;


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



            layoutManagerTask = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
            bi.rvNotificationsTask.setLayoutManager(layoutManagerTask);
            bi.rvNotificationsTask.setItemAnimator(new DefaultItemAnimator());
            bi.rvNotificationsTask.addItemDecoration(new DividerItemDecoration(bi.rvNotificationsTask.getContext(), 1));

            taskRecyclerviewAdapter = new TaskNotificatonRecyclerAdapter(context, (ArrayList<TaskNotificationModel.TaskList>) notificationListT);


            bi.rvNotificationsTask.setAdapter(taskRecyclerviewAdapter);
            taskRecyclerviewAdapter.notifyDataSetChanged();
            bi.rvNotificationsTask.setVisibility(View.VISIBLE);



    }

    private void populateListDataA() {

        try {


            layoutManagerAppointment = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
            bi.rvNotificationsAppointment.setLayoutManager(layoutManagerAppointment);
            bi.rvNotificationsAppointment.setItemAnimator(new DefaultItemAnimator());
            bi.rvNotificationsAppointment.addItemDecoration(new DividerItemDecoration(bi.rvNotificationsAppointment.getContext(), 1));
            appointmentRecyclerviewAdapter = new AppointmentNotificationRecyclerAdapter(context, (ArrayList<AppointmentNotificationModel.FollowUpsList>) notificationListA);

            bi.rvNotificationsAppointment.setAdapter(appointmentRecyclerviewAdapter);
            appointmentRecyclerviewAdapter.notifyDataSetChanged();
            bi.rvNotificationsAppointment.setVisibility(View.VISIBLE);

        }catch (Exception e){

            showLongToastMessage("crashed");
        }

    }

    private void populateListDataF() {


        try {


            layoutManagerFollowups = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
            bi.rvNotificationsFollowups.setLayoutManager(layoutManagerFollowups);
            bi.rvNotificationsFollowups.setItemAnimator(new DefaultItemAnimator());
            bi.rvNotificationsFollowups.addItemDecoration(new DividerItemDecoration(bi.rvNotificationsFollowups.getContext(), 1));
            folloupsRecyclerviewAdapter = new FollowUpsNotificationRecyclerAdapter(context, (ArrayList<FollowUpNotificationModel.FollowUpsList>) notificationListF);

            bi.rvNotificationsFollowups.setAdapter(folloupsRecyclerviewAdapter);
            folloupsRecyclerviewAdapter.notifyDataSetChanged();
            bi.rvNotificationsFollowups.setVisibility(View.VISIBLE);

        }catch (Exception e){

            showLongToastMessage("crashed");
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnTasks:


                notificationListA.clear();
                notificationListT.clear();
                notificationListF.clear();


                // for indexout exception
                if (appointmentRecyclerviewAdapter != null){

                    appointmentRecyclerviewAdapter.notifyDataSetChanged();

                }else if (taskRecyclerviewAdapter != null){

                    taskRecyclerviewAdapter.notifyDataSetChanged();

                }else if (folloupsRecyclerviewAdapter != null){

                    folloupsRecyclerviewAdapter.notifyDataSetChanged();
                }

                Paris.style(bi.btnTasks).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnAppointments).apply(R.style.TabButtonTranparentMiddle);
                Paris.style(bi.btnFollowUps).apply(R.style.TabButtonTranparentRight);
                page = 0;
                presenter.getNotificationByTasks(page);


                break;

            case R.id.btnAppointments:

                notificationListA.clear();
                notificationListT.clear();
                notificationListF.clear();


                // for indexout exception
                if (appointmentRecyclerviewAdapter != null){

                    appointmentRecyclerviewAdapter.notifyDataSetChanged();

                }else if (taskRecyclerviewAdapter != null){

                    taskRecyclerviewAdapter.notifyDataSetChanged();

                }else if (folloupsRecyclerviewAdapter != null){

                    folloupsRecyclerviewAdapter.notifyDataSetChanged();
                }




                Paris.style(bi.btnTasks).apply(R.style.TabButtonTranparentLeft);
                Paris.style(bi.btnAppointments).apply(R.style.TabButtonYellowMiddle);
                Paris.style(bi.btnFollowUps).apply(R.style.TabButtonTranparentRight);
                page = 0;
                presenter.getNotificationByAppointments(page);


                break;

            case R.id.btnFollowUps:


                notificationListA.clear();
                notificationListT.clear();
                notificationListF.clear();


                // for indexout exception
                if (appointmentRecyclerviewAdapter != null){

                    appointmentRecyclerviewAdapter.notifyDataSetChanged();

                }else if (taskRecyclerviewAdapter != null){

                    taskRecyclerviewAdapter.notifyDataSetChanged();

                }else if (folloupsRecyclerviewAdapter != null){

                    folloupsRecyclerviewAdapter.notifyDataSetChanged();
                }

                Paris.style(bi.btnTasks).apply(R.style.TabButtonTranparentLeft);
                Paris.style(bi.btnAppointments).apply(R.style.TabButtonTranparentMiddle);
                Paris.style(bi.btnFollowUps).apply(R.style.TabButtonYellowRight);
                page = 0;
                presenter.getNotificationByFollowUps(page);


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
        presenter.getNotificationByTasks(page);

       bi.rvNotificationsAppointment.addOnScrollListener(new RecyclerView.OnScrollListener() {
           @Override
           public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
               super.onScrollStateChanged(recyclerView, newState);





           }

           @Override
           public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
               super.onScrolled(recyclerView, dx, dy);

               if (dy > 0) //check for scroll down
               {
                   Log.d("scroll", "scroll down");
                   int visibleItemCount = layoutManagerAppointment.getChildCount();
                   int totalItemCount = layoutManagerAppointment.getItemCount();
                   int firstVisibleItemPosition = layoutManagerAppointment.findFirstVisibleItemPosition();

                   // Load more if we have reach the end to the recyclerView
                   if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                       Log.d("scroll", "last item");

                    //   if (notificationListA.size() > 0){

                           if (totalPages > notificationListA.size()) {
                               page++;
                               try {

                             presenter.getNotificationByAppointments(page);
                               } catch (NullPointerException e) {
                                   e.printStackTrace();
                               }
                               Log.d("scroll", "More to come");
                           }

                      /* }else if(notificationListT.size() > 0){

                           if (totalPages > notificationListT.size()) {
                               page++;
                               try {

                                   presenter.getNotificationByTasks(page);
                               } catch (NullPointerException e) {
                                   e.printStackTrace();
                               }
                               Log.d("scroll", "More to come");
                           }


                       }else if(notificationListF.size() > 0){


                           if (totalPages > notificationListF.size()) {
                               page++;
                               try {

                                   presenter.getNotificationByFollowUps(page);
                               } catch (NullPointerException e) {
                                   e.printStackTrace();
                               }
                               Log.d("scroll", "More to come");
                           }


                       }*/


                   }
               }
           }
       });
        bi.rvNotificationsFollowups.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);





            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) //check for scroll down
                {
                    Log.d("scroll", "scroll down");
                    int visibleItemCount = layoutManagerFollowups.getChildCount();
                    int totalItemCount = layoutManagerFollowups.getItemCount();
                    int firstVisibleItemPosition = layoutManagerFollowups.findFirstVisibleItemPosition();

                    // Load more if we have reach the end to the recyclerView
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        Log.d("scroll", "last item");

                        //   if (notificationListA.size() > 0){

                        if (totalPages > notificationListF.size()) {
                            page++;
                            try {

                                presenter.getNotificationByFollowUps(page);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                            Log.d("scroll", "More to come");
                        }

                      /* }else if(notificationListT.size() > 0){

                           if (totalPages > notificationListT.size()) {
                               page++;
                               try {

                                   presenter.getNotificationByTasks(page);
                               } catch (NullPointerException e) {
                                   e.printStackTrace();
                               }
                               Log.d("scroll", "More to come");
                           }


                       }else if(notificationListF.size() > 0){


                           if (totalPages > notificationListF.size()) {
                               page++;
                               try {

                                   presenter.getNotificationByFollowUps(page);
                               } catch (NullPointerException e) {
                                   e.printStackTrace();
                               }
                               Log.d("scroll", "More to come");
                           }


                       }*/


                    }
                }
            }
        });
        bi.rvNotificationsTask.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);





            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) //check for scroll down
                {
                    Log.d("scroll", "scroll down");
                    int visibleItemCount = layoutManagerTask.getChildCount();
                    int totalItemCount = layoutManagerTask.getItemCount();
                    int firstVisibleItemPosition = layoutManagerTask.findFirstVisibleItemPosition();

                    // Load more if we have reach the end to the recyclerView
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        Log.d("scroll", "last item");

                        //   if (notificationListA.size() > 0){

                        if (totalPages > notificationListT.size()) {
                            page++;
                            try {

                                presenter.getNotificationByTasks(page);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                            Log.d("scroll", "More to come");
                        }


                      /* }else if(notificationListT.size() > 0){

                           if (totalPages > notificationListT.size()) {
                               page++;
                               try {

                                   presenter.getNotificationByTasks(page);
                               } catch (NullPointerException e) {
                                   e.printStackTrace();
                               }
                               Log.d("scroll", "More to come");
                           }


                       }else if(notificationListF.size() > 0){


                           if (totalPages > notificationListF.size()) {
                               page++;
                               try {

                                   presenter.getNotificationByFollowUps(page);
                               } catch (NullPointerException e) {
                                   e.printStackTrace();
                               }
                               Log.d("scroll", "More to come");
                           }


                       }*/


                    }
                }
            }
        });


    }

    @Override
    public void updateUI(Response<BaseResponse> response) {


    }

    @Override
    public void updateUIonFalse(String message) {

        bi.rvNotificationsAppointment.setVisibility(View.GONE);
        bi.rvNotificationsFollowups.setVisibility(View.GONE);
        bi.rvNotificationsTask.setVisibility(View.GONE);
        bi.tvMessage.setVisibility(View.VISIBLE);

    }

    @Override
    public void updateUIonError(String error) {

        /*if (error.contains("Authorization has been denied for this request")) {
            ToastUtils.showErrorToast(context, "Session Expired", "Please Login Again");
            logout();
        } else {*/
        bi.rvNotificationsAppointment.setVisibility(View.GONE);
        bi.rvNotificationsFollowups.setVisibility(View.GONE);
        bi.rvNotificationsTask.setVisibility(View.GONE);
            bi.tvMessage.setVisibility(View.VISIBLE);

    }

    @Override
    public void updateUIonFailure() {

        bi.rvNotificationsAppointment.setVisibility(View.GONE);
        bi.rvNotificationsFollowups.setVisibility(View.GONE);
        bi.rvNotificationsTask.setVisibility(View.GONE);
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
    public void updateUIListT(List<TaskNotificationModel.TaskList> response,Integer taskNotificationCount) {

       /* notificationListA.clear();
        notificationListF.clear();
        notificationListT.clear();*/
        if (response.size() > 0) {

            if (page > 0){


                notificationListT.addAll(response);
                taskRecyclerviewAdapter.notifyDataSetChanged();
            }else {

                notificationListT.addAll(response);
                totalPages = taskNotificationCount != null ? taskNotificationCount : 0;


                bi.rvNotificationsTask.setVisibility(View.VISIBLE);
                bi.tvMessage.setVisibility(View.GONE);
                populateListDataT();
            }



        } else {
            bi.rvNotificationsTask.setVisibility(View.GONE);
            bi.tvMessage.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void updateUIListA(List<AppointmentNotificationModel.FollowUpsList> response,Integer appointmentNotificationCount) {


      /*  notificationListA.clear();
        notificationListF.clear();
        notificationListT.clear();*/
        if (response.size() > 0) {

            if (page > 0){

                notificationListA.addAll(response);
                appointmentRecyclerviewAdapter.notifyDataSetChanged();
            }else {

                notificationListA.addAll(response);

                totalPages = appointmentNotificationCount != null ? appointmentNotificationCount : 0;
                bi.rvNotificationsAppointment.setVisibility(View.VISIBLE);
                bi.tvMessage.setVisibility(View.GONE);
                populateListDataA();
            }



        } else {
            bi.rvNotificationsAppointment.setVisibility(View.GONE);
            bi.tvMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void updateUIListF(List<FollowUpNotificationModel.FollowUpsList> response,Integer followupsNotificationCount) {


       /* notificationListA.clear();
        notificationListF.clear();
        notificationListT.clear();*/
        if (response.size() > 0) {
            if (page > 0){

                notificationListF.addAll(response);
                folloupsRecyclerviewAdapter.notifyDataSetChanged();

            }else {
                notificationListF.addAll(response);
                totalPages = followupsNotificationCount != null ? followupsNotificationCount : 0;
                bi.rvNotificationsFollowups.setVisibility(View.VISIBLE);
                bi.tvMessage.setVisibility(View.GONE);
                populateListDataF();
            }



        } else {
            bi.rvNotificationsFollowups.setVisibility(View.GONE);
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