package com.project.jarjamediaapp.CustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAppointmentsModel;
import com.project.jarjamediaapp.Models.GetUserPermission;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwipeAppointPreviousRecyclerAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflater;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();
    public Context context;
    Activity activity;
    int pos;
    boolean isEditByLead, isPreviousAppoint;
    List<GetAppointmentsModel.Data> mData;

    GetUserPermission userPermission;

    public SwipeAppointPreviousRecyclerAdapter(Context context, Activity activity, List<GetAppointmentsModel.Data> data, boolean isEditByLead, boolean isPreviousAppoint) {

        mData = data;
        this.context = context;
        this.activity = activity;
        this.isEditByLead = isEditByLead;
        this.isPreviousAppoint = isPreviousAppoint;
        mInflater = LayoutInflater.from(context);
        binderHelper.setOpenOnlyOne(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_swipe_appoint_previous_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {
        final ViewHolder holder = (ViewHolder) h;

        pos = position;
        if (mData != null && 0 <= position && position < mData.size()) {

            GetAppointmentsModel.Data modelData = mData.get(position);

            String firstName = modelData.leadsData.firstName + "";
            String lastName = modelData.leadsData.lastName + "";
            String evenTitle = modelData.eventTitle + "";

            if (firstName.equals("null") || firstName.equals("")) {
                firstName = "-";
            }
            if (lastName.equals("null") || lastName.equals("")) {
                lastName = "-";
            }
            if (evenTitle.equals("null") || evenTitle.equals("")) {
                evenTitle = "-";
            }

            holder.tvName.setText(firstName + " " + lastName);
            holder.tvAddress.setText(evenTitle);

            holder.tvInitial.setText(firstName.substring(0, 1) + lastName.substring(0, 1));

            holder.swipeLayout.setSwipeListener(new SwipeRevealLayout.SwipeListener() {
                @Override
                public void onClosed(SwipeRevealLayout view) {

                    view.getParent().requestDisallowInterceptTouchEvent(false);

                }

                @Override
                public void onOpened(SwipeRevealLayout view) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                }

                @Override
                public void onSlide(SwipeRevealLayout view, float slideOffset) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                }
            });


            holder.frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!isPreviousAppoint) {

                        userPermission = GH.getInstance().getUserPermissions();
                        if (userPermission.data.dashboard.get(6).value) {
                            pos = position;
                            String leadID = mData.get(pos).leadAppoinmentID;
                            GetAppointmentsModel.Data modelData = mData.get(pos);
                            context.startActivity(new Intent(context, AddAppointmentActivity.class)
                                    .putExtra("leadID", leadID)
                                    .putExtra("from", "7")
                                    .putExtra("models", modelData));

                        } else {
                            ToastUtils.showToast(context, context.getString(R.string.dashboard_ViewEditAppoint));
                        }

                    }
                }
            });


            // Use ViewBindHelper to restore and save the open/close state of the SwipeRevealView
            // put an unique string id as value, can be any string which uniquely define the data

            binderHelper.bind(holder.swipeLayout, String.valueOf(pos));
            // Bind your data here
            holder.bind();
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null)
            return 0;
        return mData.size();
    }

    public void saveStates(Bundle outState) {
        binderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        binderHelper.restoreStates(inState);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private SwipeRevealLayout swipeLayout;
        TextView tvName, tvAddress, tvEdit, tvDone, tvInitial;
        FrameLayout frameLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            swipeLayout = (SwipeRevealLayout) itemView.findViewById(R.id.swipe_layout);
            swipeLayout.setLockDrag(true);
            frameLayout = itemView.findViewById(R.id.frameLayout);
            tvName = itemView.findViewById(R.id.tvName);
            tvEdit = itemView.findViewById(R.id.tvEdit);
            tvDone = itemView.findViewById(R.id.tvDone);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvInitial = itemView.findViewById(R.id.tvInitial);
        }

        public void bind() {

            tvDone.setOnClickListener(v -> {
                pos = getAdapterPosition();
                String leadID = mData.get(pos).leadAppoinmentID;
                markAsRead(leadID + ",", "true");
            });

            if (isPreviousAppoint) {
                tvDone.setVisibility(View.GONE);
            }

            tvEdit.setOnClickListener(v -> {

                userPermission = GH.getInstance().getUserPermissions();
                if (userPermission.data.dashboard.get(6).value) {
                    pos = getAdapterPosition();
                    String leadID = mData.get(pos).leadAppoinmentID;
                    GetAppointmentsModel.Data modelData = mData.get(pos);
                    if (isEditByLead) {
                        swipeLayout.close(true);
                        context.startActivity(new Intent(context, AddAppointmentActivity.class)
                                .putExtra("leadID", leadID)
                                .putExtra("from", "2")
                                .putExtra("model", modelData));
                    } else {
                        swipeLayout.close(true);
                        context.startActivity(new Intent(context, AddAppointmentActivity.class)
                                .putExtra("leadID", leadID)
                                .putExtra("from", "4")
                                .putExtra("models", modelData));
                    }
                } else {
                    ToastUtils.showToast(context, context.getString(R.string.dashboard_ViewEditAppoint));
                }


            });
        }
    }

    private void markAsRead(String leadID, String state) {
        GH.getInstance().ShowProgressDialog(activity);
        Call<BaseResponse> _callToday;
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).MarkComplete(GH.getInstance().getAuthorization(),
                leadID, state);
        _callToday.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                GH.getInstance().HideProgressDialog();
                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (getAppointmentsModel.getStatus().equals("Success")) {
                        binderHelper.closeLayout(String.valueOf(pos));
                        ToastUtils.showToast(context, "Successfully Done");
                        mData.remove(pos);
                        notifyDataSetChanged();
                    } else {
                        ToastUtils.showToast(context, getAppointmentsModel.message);
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    ToastUtils.showToast(context, error.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                GH.getInstance().HideProgressDialog();
                ToastUtils.showToastLong(context, context.getString(R.string.retrofit_failure));
            }
        });
    }
}