package com.project.jarjamediaapp.Activities.notification;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.paris.Paris;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.CustomAdapter.AppointmentNotificationRecyclerAdapter;
import com.project.jarjamediaapp.CustomAdapter.FollowUpsNotificationRecyclerAdapter;
import com.project.jarjamediaapp.CustomAdapter.TaskNotificatonRecyclerAdapter;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.databinding.ActivityNotificationBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import java.util.ArrayList;
import java.util.List;

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
    LinearLayoutManager layoutManager;
    int page = 1;
    int totalPages;
    private static final int PAGE_SIZE = 25;
    boolean isLoading = false;

    int buttonType = 1;


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

    @Override
    protected void onResume() {
        super.onResume();
        if (taskRecyclerviewAdapter != null && buttonType == 1) {
            presenter.getNotificationByTasks(page);
        } else if (appointmentRecyclerviewAdapter != null && buttonType == 2) {
            presenter.getNotificationByAppointments(page);
        } else if (folloupsRecyclerviewAdapter != null && buttonType == 3) {
            presenter.getNotificationByFollowUps(page);
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


        bi.rvNotifications.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    // Load more if we have reach the end to the recyclerView
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        Log.d("scroll", "last item");


                        if (notificationListA.size() > 0) {


                            if (!isLoading) {

                                if (totalPages > notificationListA.size()) {


                                    isLoading = true;
                                    page++;
                                    try {

                                        presenter.getNotificationByAppointments(page);
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("scroll", "More to come");
                                }

                            }


                        } else if (notificationListT.size() > 0) {

                            if (!isLoading) {
                                if (totalPages > notificationListT.size()) {
                                    isLoading = true;
                                    page++;
                                    try {

                                        presenter.getNotificationByTasks(page);
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("scroll", "More to come");
                                }

                            }


                        } else if (notificationListF.size() > 0) {

                            if (!isLoading) {

                                if (totalPages > notificationListF.size()) {
                                    isLoading = true;
                                    page++;
                                    try {

                                        presenter.getNotificationByFollowUps(page);
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("scroll", "More to come");
                                }

                            }


                        }


                    }
                }
            }
        });


    }

    private void populateListDataT() {


        layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        bi.rvNotifications.setLayoutManager(layoutManager);
        // hide for view line
        //     bi.rvNotifications.setItemAnimator(new DefaultItemAnimator());
        //    bi.rvNotifications.addItemDecoration(new DividerItemDecoration(bi.rvNotifications.getContext(), 1));

        taskRecyclerviewAdapter = new TaskNotificatonRecyclerAdapter(context, (ArrayList<TaskNotificationModel.TaskList>) notificationListT);


        bi.rvNotifications.setAdapter(taskRecyclerviewAdapter);
        taskRecyclerviewAdapter.notifyDataSetChanged();
        bi.rvNotifications.setVisibility(View.VISIBLE);


    }

    private void populateListDataA() {

        try {


            layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
            bi.rvNotifications.setLayoutManager(layoutManager);
            // hide for view line
            //       bi.rvNotifications.setItemAnimator(new DefaultItemAnimator());
            //          bi.rvNotifications.addItemDecoration(new DividerItemDecoration(bi.rvNotifications.getContext(), 1));
            appointmentRecyclerviewAdapter = new AppointmentNotificationRecyclerAdapter(context, (ArrayList<AppointmentNotificationModel.FollowUpsList>) notificationListA);

            bi.rvNotifications.setAdapter(appointmentRecyclerviewAdapter);
            appointmentRecyclerviewAdapter.notifyDataSetChanged();
            bi.rvNotifications.setVisibility(View.VISIBLE);


        } catch (Exception e) {

            showLongToastMessage("crashed");
        }

    }

    private void populateListDataF() {


        try {


            layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
            bi.rvNotifications.setLayoutManager(layoutManager);
            // hide for view line
            //   bi.rvNotifications.setItemAnimator(new DefaultItemAnimator());
            //   bi.rvNotifications.addItemDecoration(new DividerItemDecoration(bi.rvNotifications.getContext(), 1));
            folloupsRecyclerviewAdapter = new FollowUpsNotificationRecyclerAdapter(context, (ArrayList<FollowUpNotificationModel.FollowUpsList>) notificationListF);

            bi.rvNotifications.setAdapter(folloupsRecyclerviewAdapter);
            folloupsRecyclerviewAdapter.notifyDataSetChanged();
            bi.rvNotifications.setVisibility(View.VISIBLE);

        } catch (Exception e) {

            showLongToastMessage("crashed");
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnTasks:

                buttonType = 1;

                isLoading = false;

            /*    notificationListA.clear();
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
*/
                Paris.style(bi.btnTasks).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnAppointments).apply(R.style.TabButtonTranparentMiddle);
                Paris.style(bi.btnFollowUps).apply(R.style.TabButtonTranparentRight);
                page = 1;
                presenter.getNotificationByTasks(page);


                break;

            case R.id.btnAppointments:

                buttonType = 2;

                isLoading = false;

           /*     notificationListA.clear();
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
*/


                Paris.style(bi.btnTasks).apply(R.style.TabButtonTranparentLeft);
                Paris.style(bi.btnAppointments).apply(R.style.TabButtonYellowMiddle);
                Paris.style(bi.btnFollowUps).apply(R.style.TabButtonTranparentRight);
                page = 1;
                presenter.getNotificationByAppointments(page);


                break;

            case R.id.btnFollowUps:

                buttonType = 3;

                isLoading = false;
              /*  notificationListA.clear();
                notificationListT.clear();
                notificationListF.clear();


                // for indexout exception
                if (appointmentRecyclerviewAdapter != null){

                    appointmentRecyclerviewAdapter.notifyDataSetChanged();

                }else if (taskRecyclerviewAdapter != null){

                    taskRecyclerviewAdapter.notifyDataSetChanged();

                }else if (folloupsRecyclerviewAdapter != null){

                    folloupsRecyclerviewAdapter.notifyDataSetChanged();
                }*/

                Paris.style(bi.btnTasks).apply(R.style.TabButtonTranparentLeft);
                Paris.style(bi.btnAppointments).apply(R.style.TabButtonTranparentMiddle);
                Paris.style(bi.btnFollowUps).apply(R.style.TabButtonYellowRight);
                page = 1;
                presenter.getNotificationByFollowUps(page);


                break;
        }
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
    public void updateUIListT(List<TaskNotificationModel.TaskList> response, Integer taskNotificationCount) {

       /* notificationListA.clear();
        notificationListF.clear();
        notificationListT.clear();*/

        isLoading = false;
        if (response.size() > 0) {

            if (page > 1) {

                notificationListT.addAll(response);
                taskRecyclerviewAdapter.sortData();
                taskRecyclerviewAdapter.notifyDataSetChanged();
            } else {


                notificationListA.clear();
                notificationListT.clear();
                notificationListF.clear();
                notificationListT.addAll(response);
                totalPages = taskNotificationCount != null ? taskNotificationCount : 0;


                bi.rvNotifications.setVisibility(View.VISIBLE);
                bi.tvMessage.setVisibility(View.GONE);
                populateListDataT();
            }


        } else {
            bi.rvNotifications.setVisibility(View.GONE);
            bi.tvMessage.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void updateUIListA(List<AppointmentNotificationModel.FollowUpsList> response, Integer appointmentNotificationCount) {


      /*  notificationListA.clear();
        notificationListF.clear();
        notificationListT.clear();*/
        isLoading = false;
        if (response.size() > 0) {

            if (page > 1) {
                notificationListA.addAll(response);
                appointmentRecyclerviewAdapter.sortData();
                appointmentRecyclerviewAdapter.notifyDataSetChanged();
            } else {

                notificationListA.clear();
                notificationListT.clear();
                notificationListF.clear();

                notificationListA.addAll(response);


                totalPages = appointmentNotificationCount != null ? appointmentNotificationCount : 0;
                bi.rvNotifications.setVisibility(View.VISIBLE);
                bi.tvMessage.setVisibility(View.GONE);
                populateListDataA();
            }


        } else {
            bi.rvNotifications.setVisibility(View.GONE);
            bi.tvMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void updateUIListF(List<FollowUpNotificationModel.FollowUpsList> response, Integer followupsNotificationCount) {


       /* notificationListA.clear();
        notificationListF.clear();
        notificationListT.clear();*/
        isLoading = false;
        if (response.size() > 0) {
            if (page > 1) {

                notificationListF.addAll(response);
                folloupsRecyclerviewAdapter.sortData();
                folloupsRecyclerviewAdapter.notifyDataSetChanged();

            } else {

                notificationListA.clear();
                notificationListT.clear();
                notificationListF.clear();

                notificationListF.addAll(response);
                totalPages = followupsNotificationCount != null ? followupsNotificationCount : 0;
                bi.rvNotifications.setVisibility(View.VISIBLE);
                bi.tvMessage.setVisibility(View.GONE);
                populateListDataF();
            }


        } else {
            bi.rvNotifications.setVisibility(View.GONE);
            bi.tvMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void updateAdapter(int type) {
        if (type == R.integer.Tasks) {
            taskRecyclerviewAdapter.sortData();
            taskRecyclerviewAdapter.notifyDataSetChanged();

        } else if (type == R.integer.Appointment) {
            // notificationListA.get(pos).setIsSeen(true);
            appointmentRecyclerviewAdapter.sortData();
            appointmentRecyclerviewAdapter.notifyDataSetChanged();

        } else if (type == R.integer.Folloups) {
            folloupsRecyclerviewAdapter.sortData();
            folloupsRecyclerviewAdapter.notifyDataSetChanged();

        }
    }


}