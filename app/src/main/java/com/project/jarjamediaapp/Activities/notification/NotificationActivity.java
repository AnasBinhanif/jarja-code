package com.project.jarjamediaapp.Activities.notification;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airbnb.paris.Paris;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityNotificationBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function4;
import retrofit2.Response;

public class NotificationActivity extends BaseActivity implements NotificationContract.View {

    ActivityNotificationBinding bi;
    Context context = NotificationActivity.this;
    NotificationPresenter presenter;
    List<NotificationModel.Data.TaskList> notificationList;
    RecyclerAdapterUtil recyclerAdapterUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_notification);
        presenter = new NotificationPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, "Notifications", true);
    }

    private void populateListData() {

        bi.rvNotifications.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bi.rvNotifications.setItemAnimator(new DefaultItemAnimator());
        bi.rvNotifications.addItemDecoration(new DividerItemDecoration(bi.rvNotifications.getContext(), 1));
        recyclerAdapterUtil = new RecyclerAdapterUtil(context, notificationList, R.layout.custom_notifications_layout);
        recyclerAdapterUtil.addViewsList(R.id.tvName, R.id.tvLeadName, R.id.tvContact, R.id.tvEmail);

        recyclerAdapterUtil.addOnDataBindListener((Function4<View, NotificationModel.Data.TaskList, Integer, Map<Integer, ? extends View>, Unit>) (view, data, integer, integerMap) -> {

            try {

                TextView tvName = (TextView) integerMap.get(R.id.tvName);
                tvName.setText(data.getTaskName() != null ? data.getTaskName() : "");


                TextView tvDesc = (TextView) integerMap.get(R.id.tvLeadName);
                tvDesc.setText(data.getVtCRMLeadCustom().getFirstName() != null && data.getVtCRMLeadCustom().getLastName() != null ? data.getVtCRMLeadCustom().getFirstName() + " " + data.getVtCRMLeadCustom().getLastName() : "");


                TextView tvContact = (TextView) integerMap.get(R.id.tvContact);
                tvContact.setText(data.getVtCRMLeadCustom().getPrimaryPhone() != null ? data.getVtCRMLeadCustom().getPrimaryPhone() : "");


                TextView tvEmail = (TextView) integerMap.get(R.id.tvEmail);
                tvEmail.setText(data.getVtCRMLeadCustom().getPrimaryEmail() != null ? data.getVtCRMLeadCustom().getPrimaryEmail() : "");

            } catch (Exception e) {
                e.printStackTrace();
            }

            return Unit.INSTANCE;
        });

        bi.rvNotifications.setAdapter(recyclerAdapterUtil);

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

        notificationList = new ArrayList<>();
        bi.btnTasks.setOnClickListener(this);
        bi.btnAppointments.setOnClickListener(this);
        bi.btnFollowUps.setOnClickListener(this);
        populateListData();
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

        if (error.contains("Authorization has been denied for this request")) {
            ToastUtils.showErrorToast(context, "Session Expired", "Please Login Again");
            logout();
        } else {
            bi.rvNotifications.setVisibility(View.GONE);
            bi.tvMessage.setVisibility(View.VISIBLE);
        }

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
    public void updateUIList(NotificationModel.Data response) {

        notificationList.clear();
        if (response.taskList.size() > 0) {
            notificationList.addAll(response.taskList);
            recyclerAdapterUtil.notifyDataSetChanged();
            bi.rvNotifications.setVisibility(View.VISIBLE);
            bi.tvMessage.setVisibility(View.GONE);
        } else {
            bi.rvNotifications.setVisibility(View.GONE);
            bi.tvMessage.setVisibility(View.VISIBLE);
        }


    }

}