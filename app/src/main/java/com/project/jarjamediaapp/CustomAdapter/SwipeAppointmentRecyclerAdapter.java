package com.project.jarjamediaapp.CustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentActivity;
import com.project.jarjamediaapp.Activities.user_profile.GetPermissionModel;
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

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwipeAppointmentRecyclerAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflater;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();
    public Context context;
    Activity activity;
    int pos;
    boolean isEditByLead, isPreviousAppoint;
    List<GetAppointmentsModel.Data.Datum> mData;

    GetPermissionModel userPermission;


    public SwipeAppointmentRecyclerAdapter(Context context, Activity activity, List<GetAppointmentsModel.Data.Datum> data, boolean isEditByLead, boolean isPreviousAppoint) {

        mData = data;
        this.context = context;
        this.activity = activity;
        this.isEditByLead = isEditByLead;
        this.isPreviousAppoint = isPreviousAppoint;
        mInflater = LayoutInflater.from(context);
        binderHelper.setOpenOnlyOne(true);

       /* if(GH.getInstance().getNotificationType().equals("apointment")){

            Log.i("indexOfList",""+mData.indexOf(GH.getInstance().getNotificationID()));
            context.startActivity(new Intent(context,AddAppointmentActivity.class));

        }*/


           /* if (GH.getInstance().getNotificationType().equals("apointment")){


                String leadID = mData.get(pos).leadAppoinmentID;
                GetAppointmentsModel.Data.Datum modelData1 = mData.get(0);
                context.startActivity(new Intent(context, AddAppointmentActivity.class)
                        .putExtra("leadID", leadID)
                        .putExtra("from", "4")
                        .putExtra("models", modelData1));


            }

*/

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

            GetAppointmentsModel.Data.Datum modelData = mData.get(position);

            String firstName = modelData.getVtCRMLeadCustom().firstName + "";
            String lastName = modelData.getVtCRMLeadCustom().lastName + "";
            String evenTitle = modelData.eventTitle + "";
            String location = modelData.location + "";

           /* if (firstName.equals("null") || firstName.equals("")) {
                firstName = "";
            }
            if (lastName.equals("null") || lastName.equals("")) {
                lastName = "";
            }*/
            //replace above block of code with below one.
            if (firstName.equals("null") || firstName == null) {
                firstName = "";
            }
            if (lastName.equals("null") || lastName == null) {
                lastName = "";
            }

            if (evenTitle.equals("null") || evenTitle.equals("")) {
                evenTitle = "";
            }

            if (location.equals("null") || evenTitle.equals("")) {

                location = "";
            }

            holder.tvName.setText(firstName + " " + lastName);
            if (!location.equals("")) {
                holder.tvAddress.setText(evenTitle + ", " + location);

            } else {

                holder.tvAddress.setText(evenTitle);

            }

            /*if (!firstName.equals("") && !lastName.equals("")){
                holder.tvInitial.setText(firstName.substring(0, 1) + lastName.substring(0, 1));
            }else {

                if (firstName == null){

                    firstName = "";
                }else if(lastName == null){

                    lastName = "";
                }

                holder.tvInitial.setText(firstName + lastName);
            }*/
            //replacing above block of code with below one
            String a = "", b = "";
            if (!firstName.equals("")) {
                a = firstName.charAt(0) + "";

            }
            if (!lastName.equals("")) {
                b = lastName.charAt(0) + "";
            }
            holder.tvInitial.setText(a + b);
            //end

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
                        Gson gson = new Gson();
                        //   GetPermissionModel  userPermission = GH.getInstance().getUserPermissions();
                        String storedHashMapLeadsString = GH.getInstance().getUserPermissonLead();
                        java.lang.reflect.Type typeLeads = new TypeToken<HashMap<String, Boolean>>() {
                        }.getType();
                        HashMap<String, Boolean> mapLeads = gson.fromJson(storedHashMapLeadsString, typeLeads);

                        if (mapLeads.get("View Appointments")) {
                            pos = position;
                            String leadID = mData.get(pos).leadAppoinmentID;
                            GetAppointmentsModel.Data.Datum modelData = mData.get(pos);
                            String gmailCalenderId = mData.get(pos).gmailCalenderId;
                            if (isEditByLead) {

                                context.startActivity(new Intent(context, AddAppointmentActivity.class)
                                        .putExtra("leadID", leadID)
                                        .putExtra("from", "2")
                                        .putExtra("gmailCalenderId", gmailCalenderId)
                                        .putExtra("models", modelData));

                            } else {
                                context.startActivity(new Intent(context, AddAppointmentActivity.class)
                                        .putExtra("leadID", leadID)
                                        .putExtra("from", "4")
                                        .putExtra("gmailCalenderId", gmailCalenderId)
                                        .putExtra("models", modelData));
                            }

                        } else {
                            ToastUtils.showToast(context, context.getString(R.string.lead_ViewAppoint));
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

                Gson gson = new Gson();
                //   GetPermissionModel  userPermission = GH.getInstance().getUserPermissions();
                String storedHashMapDashboardString = GH.getInstance().getUserPermissonDashboard();
                java.lang.reflect.Type typeDashboard = new TypeToken<HashMap<String, Boolean>>() {
                }.getType();
                HashMap<String, Boolean> mapDashboard = gson.fromJson(storedHashMapDashboardString, typeDashboard);

                if (mapDashboard.get("View Or Edit Appointments")) {
                    pos = getAdapterPosition();
                    String leadID = mData.get(pos).leadAppoinmentID;
                    GetAppointmentsModel.Data.Datum modelData = mData.get(pos);
                    if (isEditByLead) {
                        swipeLayout.close(true);
                        context.startActivity(new Intent(context, AddAppointmentActivity.class)
                                .putExtra("leadID", leadID)
                                .putExtra("from", "2")
                                .putExtra("models", modelData));
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