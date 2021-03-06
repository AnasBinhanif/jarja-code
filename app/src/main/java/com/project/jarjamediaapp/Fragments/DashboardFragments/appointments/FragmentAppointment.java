package com.project.jarjamediaapp.Fragments.DashboardFragments.appointments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.paris.Paris;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.jarjamediaapp.Activities.HomeActivity;
import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentActivity;
import com.project.jarjamediaapp.Activities.open_houses.UploadImageModel;
import com.project.jarjamediaapp.Activities.user_profile.GetPermissionModel;
import com.project.jarjamediaapp.Base.BaseFragment;
import com.project.jarjamediaapp.CustomAdapter.SwipeAppointmentRecyclerAdapter;
import com.project.jarjamediaapp.Fragments.FragmentLifeCycle;
import com.project.jarjamediaapp.Models.GetAppointmentsModel;
import com.project.jarjamediaapp.Models.GetUserPermission;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.FragmentAppointmentBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAppointment extends BaseFragment implements FragmentLifeCycle, AppointmentContract.View, View.OnClickListener {

    Context context;
    FragmentAppointmentBinding bi;
    AppointmentPresenter presenter;
    SwipeAppointmentRecyclerAdapter swipeAppointmentRecyclerAdapter;
    boolean isFromActivity, isTabClick = true;
    String leadID = "";
    List<GetAppointmentsModel.Data.Datum> appointmentList;
    List<GetAppointmentsModel.Data> leadAppointmentList;
    GetPermissionModel userPermission;
    String buttonType = "T";
    int page = 1;
    int totalPages;
    boolean onResume;
    View view;

    public FragmentAppointment() {
        // Required empty public constructor
    }

    public static FragmentAppointment newInstance(String fragment_title, String leadID, boolean fromActivity) {
        FragmentAppointment fragmentAppointment = new FragmentAppointment();
        Bundle args = new Bundle();
        args.putString("title", fragment_title);
        args.putString("leadID", leadID);
        args.putBoolean("isFromActivity", fromActivity);
        fragmentAppointment.setArguments(args);
        return fragmentAppointment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        bi = DataBindingUtil.inflate(inflater, R.layout.fragment_appointment, container, false);
        presenter = new AppointmentPresenter(this);
        presenter.initScreen();

        onResume = true;
        page = 1;
        isTabClick = true;
        if (appointmentList.size() != 0) {
            appointmentList.clear();
        }
        hitApi();
        return bi.getRoot();
    }


    public FragmentAppointment getInstance() {
        return this;
    }

    @Override
    public void setupViews() {

        initViews();

    }

    @SuppressLint("RestrictedApi")
    private void initViews() {

        appointmentList = new ArrayList<>();
        isFromActivity = this.getArguments().getBoolean("isFromActivity");
        if (isFromActivity) {
            leadID = this.getArguments().getString("leadID");
            bi.tvTitle.setVisibility(View.GONE);
            bi.fbAddAppoint.setVisibility(View.GONE);
        }

        bi.btnToday.setOnClickListener(this);
        bi.btnUpcoming.setOnClickListener(this);
        bi.btnPrevious.setOnClickListener(this);
        bi.fbAddAppoint.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        bi.rvAppointments.setLayoutManager(layoutManager);
        bi.rvAppointments.setItemAnimator(new DefaultItemAnimator());


        userPermission = GH.getInstance().getUserPermissions();

        bi.rvAppointments.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    Log.d("scroll", "scroll down");
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    // Load more if we have reach the end to the recyclerView
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        Log.d("scroll", "last item");
                        if (totalPages > appointmentList.size()) {
                            page++;
                            try {
                                isTabClick = false;
                                hitApi();
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                            Log.d("scroll", "More to come");
                        }
                    }
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        if (easyPreference.getBoolean(getResources().getString(R.string.refresh_appointment), false)) {
            onResume = true;
            page = 1;
            isTabClick = true;
            if (appointmentList.size() != 0) {
                appointmentList.clear();
            }
            hitApi();
            easyPreference.addBoolean(getResources().getString(R.string.refresh_appointment), false).save();

        }

    }

    @Override
    public void updateAppointmentUI(GetAppointmentsModel response) {

        appointmentList.addAll(response.getData().getData());

        if (!isFromActivity) {
            totalPages = response.getData().getTotalRecordCount() != null ? response.getData().getTotalRecordCount() : 0;
        }
        if (appointmentList.size() == 0) {

            bi.tvNoRecordFound.setVisibility(View.VISIBLE);
            bi.rvAppointments.setVisibility(View.GONE);

        } else {
            bi.tvNoRecordFound.setVisibility(View.GONE);
            bi.rvAppointments.setVisibility(View.VISIBLE);
            if (isTabClick == true) {
                swipeAppointmentRecyclerAdapter = new SwipeAppointmentRecyclerAdapter(context, getActivity(), appointmentList, isFromActivity, false);
                bi.rvAppointments.setAdapter(swipeAppointmentRecyclerAdapter);
            } else {
                if (swipeAppointmentRecyclerAdapter != null) {
                    swipeAppointmentRecyclerAdapter.notifyDataSetChanged();

                }
            }
        }
        if (buttonType.equalsIgnoreCase("T") && onResume) {
            if (!isFromActivity) {
                getNotificationCount();
            }
        } else {
            hideProgressBar();
        }


    }

    @Override
    public void updateUIonFalse(String message) {

        bi.tvNoRecordFound.setVisibility(View.VISIBLE);
        bi.rvAppointments.setVisibility(View.GONE);
        ToastUtils.showToastLong(context, message);
        if (buttonType.equalsIgnoreCase("T") && onResume) {
            if (!isFromActivity) {
                getNotificationCount();
            }
        } else {
            hideProgressBar();
        }

    }

    @Override
    public void updateUIonError(String error) {

        /*if (error.contains("Authorization has been denied for this request")) {
            ToastUtils.showErrorToast(context, "Session Expired", "Please Login Again");
            logout();
        } else {*/
        ToastUtils.showToastLong(context, error);

    }

    @Override
    public void updateUIonFailure() {

        try {
            ToastUtils.showToastLong(context, getString(R.string.retrofit_failure));
        } catch (Exception e) {
            Log.i("Exception", "Exception in Context");
        }


    }

    @Override
    public void showProgressBar() {
        GH.getInstance().ShowProgressDialog(getActivity());
    }

    @Override
    public void hideProgressBar() {
        GH.getInstance().HideProgressDialog();
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.fbAddAppoint:


                if (HomeActivity.onClick) {
                    Map<String, String> map = new HashMap<>();
                    map.put("from", "5");
                    switchActivityWithIntentString(context, AddAppointmentActivity.class, (HashMap<String, String>) map);
                    HomeActivity.onClick = false;
                }
                break;

            case R.id.btnToday:

                page = 1;
                appointmentList.clear();
                isTabClick = true;
                buttonType = "T";
                if (isFromActivity) {
                    presenter.getLeadTodayAppointments(leadID, page);
                } else {
                    Gson gson = new Gson();
                    //   GetPermissionModel  userPermission = GH.getInstance().getUserPermissions();
                    String storedHashMapDashboardString = GH.getInstance().getUserPermissonDashboard();
                    java.lang.reflect.Type typeDashboard = new TypeToken<HashMap<String, Boolean>>() {
                    }.getType();
                    HashMap<String, Boolean> mapDashboard = gson.fromJson(storedHashMapDashboardString, typeDashboard);

                    if (mapDashboard.get("View Or Edit Appointments")) {

                        onResume = false;
                        presenter.getTodayAppointments(page);

                    } else {
                        ToastUtils.showToast(context, getString(R.string.dashboard_ViewEditAppoint));
                    }
                }
                Paris.style(bi.btnToday).apply(R.style.TabButtonYellowLeft);
                Paris.style(bi.btnUpcoming).apply(R.style.TabButtonTranparentMiddle);
                Paris.style(bi.btnPrevious).apply(R.style.TabButtonTranparentRight);

                break;

            case R.id.btnUpcoming:

                page = 1;
                isTabClick = true;
                appointmentList.clear();
                buttonType = "U";
                if (isFromActivity) {
                    presenter.getLeadUpcomingAppointments(leadID, page);
                } else {

                    if (userPermission.getData().getDashboard().get(6).getValue()) {
                        presenter.getUpcomingAppointments(page);
                    } else {

                        ToastUtils.showToast(context, getString(R.string.dashboard_ViewEditAppoint));
                    }

                }

                Paris.style(bi.btnToday).apply(R.style.TabButtonTranparentLeft);
                Paris.style(bi.btnUpcoming).apply(R.style.TabButtonYellowMiddle);
                Paris.style(bi.btnPrevious).apply(R.style.TabButtonTranparentRight);

                break;

            case R.id.btnPrevious:

                page = 1;
                appointmentList.clear();
                isTabClick = true;
                buttonType = "P";
                if (isFromActivity) {

                    presenter.getLeadPreviousAppointments(leadID, page);

                } else {

                    if (userPermission.getData().getDashboard().get(6).getValue()) {
                        presenter.getPreviousAppointments(page);
                    } else {
                        ToastUtils.showToast(context, getString(R.string.dashboard_ViewEditAppoint));
                    }
                }

                Paris.style(bi.btnToday).apply(R.style.TabButtonTranparentLeft);
                Paris.style(bi.btnUpcoming).apply(R.style.TabButtonTranparentMiddle);
                Paris.style(bi.btnPrevious).apply(R.style.TabButtonYellowRight);


                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {

        Log.d("resume", "On Resume Called");

    }

    private void hitApi() {

        if (isFromActivity) {
            switch (buttonType) {
                case "T": {
                    presenter.getLeadTodayAppointments(leadID, page);
                }
                break;
                case "U": {
                    presenter.getLeadUpcomingAppointments(leadID, page);
                }
                break;
                case "P": {
                    presenter.getLeadPreviousAppointments(leadID, page);
                }
                break;
            }

        } else {
            switch (buttonType) {
                case "T": {
                    if (userPermission.getData().getDashboard().get(6).getValue()) {
                        presenter.getTodayAppointments(page);
                    } else {
                        ToastUtils.showToast(context, getString(R.string.dashboard_ViewEditAppoint));
                    }
                    Log.d("bt", buttonType);
                }
                break;
                case "U": {
                    presenter.getUpcomingAppointments(page);
                    Log.d("bt", buttonType);
                }
                break;
                case "P": {
                    presenter.getPreviousAppointments(page);
                    Log.d("bt", buttonType);
                }
                break;
            }
        }
    }


    private void getNotificationCount() {

        Call<UploadImageModel> _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).
                getNotificationCount(GH.getInstance().getAuthorization());
        _call.enqueue(new Callback<UploadImageModel>() {
            @Override
            public void onResponse(Call<UploadImageModel> call, Response<UploadImageModel> response) {

                hideProgressBar();
                if (response.isSuccessful()) {

                    UploadImageModel getUserProfile = response.body();
                    if (getUserProfile.status.equals("Success")) {
                        hideProgressBar();
                        if (getUserProfile.getData() != null && !getUserProfile.getData().equalsIgnoreCase("")) {
                            ((HomeActivity) context).updateNotificationCount(Integer.parseInt(getUserProfile.getData()));
                        }
                    } else {

                        Toast.makeText(context, getUserProfile.message, Toast.LENGTH_SHORT).show();
                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    /*if (error.message().contains("Authorization has been denied for this request")) {
                        ToastUtils.showErrorToast(context, "Session Expired", "Please Login Again");
                        logout();
                    } else {*/
                    ToastUtils.showToastLong(context, error.message());

                }
            }

            @Override
            public void onFailure(Call<UploadImageModel> call, Throwable t) {

                hideProgressBar();
                ToastUtils.showToastLong(context, getString(R.string.retrofit_failure));
            }
        });
    }

}