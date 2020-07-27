package com.project.jarjamediaapp.CustomAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetFollowUpsModel;
import com.project.jarjamediaapp.Models.GetUserPermission;
import com.project.jarjamediaapp.Models.ViewFollowUpModel;
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


public class SwipeFollowUpsRecyclerAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflater;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();
    public Context context;
    Activity activity;
    int pos;
    String status = "";
    List<GetFollowUpsModel.Data.FollowUpsList> mData;

    GetUserPermission userPermission;

    public SwipeFollowUpsRecyclerAdapter(Context context, Activity activity, List<GetFollowUpsModel.Data.FollowUpsList> data) {

        mData = data;
        this.context = context;
        this.activity = activity;
        mInflater = LayoutInflater.from(context);
        binderHelper.setOpenOnlyOne(true);


       /* // fro testing
            if (GH.getInstance().getNotificationType().equals("followup")) {
                viewDetail(mData.get(0).dripDetailID, null);
            }*/


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_swipe_followup_due_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {
        final ViewHolder holder = (ViewHolder) h;

        pos = position;
        if (mData != null && 0 <= position && position < mData.size()) {

            GetFollowUpsModel.Data.FollowUpsList modelData = mData.get(position);


            String summary = modelData.summary + "";
            if (summary.equals("null") || summary.equals("")) {
                summary = "-";
            }
            holder.tvAddress.setText(summary);
            try {

                holder.tvName.setText(modelData.leadName + "");
                String[] name = modelData.leadName.split(" ");
                String firstName = name[0];
                String lastName = name[1];
                if (firstName.equals("null") || firstName.equals("")) {
                    firstName = "-";
                }
                if (lastName.equals("null") || lastName.equals("")) {
                    lastName = "-";
                }
                holder.tvInitial.setText(firstName.substring(0, 1) + lastName.substring(0, 1));
            } catch (Exception e) {
                e.printStackTrace();
            }


            holder.frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    viewDetail(mData.get(pos).dripDetailID, holder.swipeLayout);
                }
            });


            holder.swipeLayout.setSwipeListener(new SwipeRevealLayout.SwipeListener() {
                @Override
                public void onClosed(SwipeRevealLayout view) {

                    pos = position;
                    view.getParent().requestDisallowInterceptTouchEvent(false);

                }

                @Override
                public void onOpened(SwipeRevealLayout view) {
                    pos = position;
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                }

                @Override
                public void onSlide(SwipeRevealLayout view, float slideOffset) {
                    pos = position;
                    view.getParent().requestDisallowInterceptTouchEvent(true);
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

    private void markAsRead(String reminderID) {
        GH.getInstance().ShowProgressDialog(activity);

        Call<BaseResponse> _callToday;
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).MarkFollowUpComplete(GH.getInstance().getAuthorization(), reminderID + ",", "true");
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

    private void viewDetail(String dripDetailId, SwipeRevealLayout swipeRevealLayout) {
        GH.getInstance().ShowProgressDialog(activity);
        Call<ViewFollowUpModel> _callToday;
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetFollowUpDetails(GH.getInstance().getAuthorization(), dripDetailId);
        _callToday.enqueue(new Callback<ViewFollowUpModel>() {
            @Override
            public void onResponse(Call<ViewFollowUpModel> call, Response<ViewFollowUpModel> response) {
                GH.getInstance().HideProgressDialog();
                if (response.isSuccessful()) {

                    ViewFollowUpModel getDetails = response.body();
                    if (getDetails.status.equals("Success")) {

                        String wait = getDetails.data.viewDPCStep.wait;
                        String time = getDetails.data.viewDPCStep.sendTime;
                        String note = getDetails.data.viewDPCStep.message;
                        String title = getDetails.data.viewDPCStep.subject;
                        String senType = getDetails.data.viewDPCStep.sentType;
                        String dateTime = getDetails.data.viewDPCStep.sendDateTime;

                        if (swipeRevealLayout != null){

                            swipeRevealLayout.close(true);
                        }

                        showViewFollowUpDialog(context, wait, title, dateTime, time, note, senType);

//                        // for remove shared pref of notificationType
//                        EasyPreference.Builder pref = new EasyPreference.Builder(context);
//                        pref.addString(GH.KEYS.NOTIFICATIONTYPE.name(),"").save();

                    } else {

                        ToastUtils.showToast(context, getDetails.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    ToastUtils.showToast(context, error.message());
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

    public void saveStates(Bundle outState) {
        binderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        binderHelper.restoreStates(inState);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private SwipeRevealLayout swipeLayout;
        TextView tvName, tvAddress, tvView, tvDone, tvInitial;
        FrameLayout frameLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            swipeLayout = (SwipeRevealLayout) itemView.findViewById(R.id.swipe_layout);
            swipeLayout.setLockDrag(true);
            frameLayout = itemView.findViewById(R.id.frameLayout);
            tvName = itemView.findViewById(R.id.tvName);
            tvView = itemView.findViewById(R.id.tvView);
            tvDone = itemView.findViewById(R.id.tvDone);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvInitial = itemView.findViewById(R.id.tvInitial);
        }

        public void bind() {

            tvDone.setOnClickListener(v -> {
                userPermission = GH.getInstance().getUserPermissions();
                if (userPermission.data.dashboard.get(3).value) {
                    pos = getAdapterPosition();
                    markAsRead(mData.get(pos).reminderId);
                } else {
                    ToastUtils.showToast(context, context.getString(R.string.dashboard_EditFollowUps));
                }
            });

            tvView.setOnClickListener(v -> {
                pos = getAdapterPosition();
                viewDetail(mData.get(pos).dripDetailID, swipeLayout);
            });
        }
    }

}