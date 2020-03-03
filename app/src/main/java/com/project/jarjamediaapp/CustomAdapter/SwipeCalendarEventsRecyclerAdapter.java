package com.project.jarjamediaapp.CustomAdapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentActivity;
import com.project.jarjamediaapp.Activities.add_calendar_task.AddCalendarTaskActivity;
import com.project.jarjamediaapp.Activities.calendar.CalendarModel;
import com.project.jarjamediaapp.Activities.calendarDetail.CalendarDetailModel;
import com.project.jarjamediaapp.Base.BaseResponse;
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


public class SwipeCalendarEventsRecyclerAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflater;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();
    public Context context;
    int pos;
    List<CalendarModel.Data> mData;
    CalendarModel.Data modelData;
    Call<CalendarDetailModel> _call;
    Call<BaseResponse> call;

    public SwipeCalendarEventsRecyclerAdapter(Context context, List<CalendarModel.Data> data) {

        mData = data;
        this.context = context;
        mInflater = LayoutInflater.from(context);
        binderHelper.setOpenOnlyOne(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_calendar_event_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {
        final ViewHolder holder = (ViewHolder) h;

        pos = position;
        if (mData != null && 0 <= position && position < mData.size()) {

            modelData = mData.get(position);

            holder.tvTitle.setText(modelData.getTitle() != null ? modelData.getTitle() : "");
            holder.tvCount.setText(modelData.getCount() != null ? String.valueOf(modelData.getCount()) : "0");


            holder.frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    viewCalendarAppointmentOrTaskDetail(modelData.getCalendarId(), modelData.getCalendarType(), modelData.getStart());

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

    private void deleteCalendarAppointmentOrTaskDetail(String calendarId, String type, SwipeRevealLayout swipeRevealLayout) {

        GH.getInstance().ShowProgressDialog(context);

        if (type != null && !type.equalsIgnoreCase("") && type.equalsIgnoreCase("Task")) {
            call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).deleteCalendarTask(GH.getInstance().getAuthorization(), calendarId);

        } else {
            call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).deleteCalendarAppointment(GH.getInstance().getAuthorization(), calendarId);
        }
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                GH.getInstance().HideProgressDialog(context);
                if (response.isSuccessful()) {

                    BaseResponse calendarDetailModel = response.body();
                    if (calendarDetailModel.getStatus().equals("Success")) {
                        if (mData != null && mData.size() > 0) {

                            ToastUtils.showToast(context, calendarDetailModel.getMessage() + "");
                            mData.remove(pos);
                            notifyDataSetChanged();
                            swipeRevealLayout.close(true);

                        }
                    } else {
                        ToastUtils.showToast(context, calendarDetailModel.getMessage());
                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    ToastUtils.showToast(context, error.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                GH.getInstance().HideProgressDialog(context);
                ToastUtils.showToastLong(context, context.getString(R.string.retrofit_failure));
            }
        });
    }

    private void viewCalendarAppointmentOrTaskDetail(String calendarId, String type, String startDateTime) {

        GH.getInstance().ShowProgressDialog(context);

        if (type != null && !type.equalsIgnoreCase("") && type.equalsIgnoreCase("Task")) {
            _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getCalendarTaskDetail(GH.getInstance().getAuthorization(), calendarId, startDateTime);

        } else {
            _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getCalendarAppointmentDetail(GH.getInstance().getAuthorization(), calendarId);
        }
        _call.enqueue(new Callback<CalendarDetailModel>() {
            @Override
            public void onResponse(Call<CalendarDetailModel> call, Response<CalendarDetailModel> response) {
                GH.getInstance().HideProgressDialog(context);
                if (response.isSuccessful()) {

                    CalendarDetailModel calendarDetailModel = response.body();
                    if (calendarDetailModel.getStatus().equals("Success")) {
                        showDialogForEditView(context, calendarDetailModel.getCalendarData());
                    } else {
                        ToastUtils.showToast(context, calendarDetailModel.getMessage());
                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    ToastUtils.showToast(context, error.message());
                }
            }

            @Override
            public void onFailure(Call<CalendarDetailModel> call, Throwable t) {
                GH.getInstance().HideProgressDialog(context);
                ToastUtils.showToastLong(context, context.getString(R.string.retrofit_failure));
            }
        });
    }

    public void saveStates(Bundle outState) {
        binderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        binderHelper.restoreStates(inState);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private SwipeRevealLayout swipeLayout;
        TextView tvTitle, tvDelete, tvCount;
        FrameLayout frameLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            swipeLayout = (SwipeRevealLayout) itemView.findViewById(R.id.swipe_layout);
            swipeLayout.setLockDrag(true);
            frameLayout = itemView.findViewById(R.id.frameLayout);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCount = itemView.findViewById(R.id.tvCount);
            tvDelete = itemView.findViewById(R.id.tvDelete);
        }

        public void bind() {

            tvDelete.setOnClickListener(v -> {

                pos = getAdapterPosition();
                deleteCalendarAppointmentOrTaskDetail(modelData.getCalendarId(), modelData.getCalendarType(), swipeLayout);

            });

        }
    }

    private void showDialogForEditView(Context context, CalendarDetailModel.CalendarData calendarDetailModel) {

        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_view_calendar_detail);

        TextView tvEventTitle, tvLead, tvAgents, tvDate, tvTime, tvLocation, tvDetail, tvNotes, tvClose;

        tvEventTitle = dialog.findViewById(R.id.tvEventTitle);
        tvLead = dialog.findViewById(R.id.tvLead);
        tvAgents = dialog.findViewById(R.id.tvAgents);
        tvDate = dialog.findViewById(R.id.tvDate);
        tvLocation = dialog.findViewById(R.id.tvLocation);
        tvDetail = dialog.findViewById(R.id.tvDetails);
        tvTime = dialog.findViewById(R.id.tvTime);
        tvNotes = dialog.findViewById(R.id.tvNotes);

        try {
            tvEventTitle.setText(calendarDetailModel.getEventTitle() != null ? calendarDetailModel.getEventTitle() : "");
            if (calendarDetailModel.isAllDay) {
                tvTime.setText("All Day");
            } else {
                tvLocation.setText(calendarDetailModel.getLocation() != null ? calendarDetailModel.getLocation() : "");
                tvNotes.setText(calendarDetailModel.getDescription() != null ? calendarDetailModel.getDescription() : "");
                String[] startTime = calendarDetailModel.getStartTime().split("T");
                String[] endTime = calendarDetailModel.getEndTime().split("T");
                tvTime.setText(GH.getInstance().formatter(startTime[1], "hh:mm:ss a", "HH:mm:ss") + " - " + GH.getInstance().formatter(endTime[1], "hh:mm:ss a", "HH:mm:ss"));
                tvDate.setText(GH.getInstance().formatter(startTime[0], "EEE,MMM dd,yyyy", "yyyy-mm-dd") + " - " + GH.getInstance().formatter(endTime[0], "EEE,MMM dd,yyyy", "yyyy-mm-dd"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvClose = dialog.findViewById(R.id.tvClose);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnEdit = dialog.findViewById(R.id.btnEdit);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                if (calendarDetailModel.getCalendarType().equals("Event")) {
                    // data and calendar id will be passed in intent
                    context.startActivity(new Intent(context, AddAppointmentActivity.class)
                            .putExtra("from", "6")
                            .putExtra("modelData", calendarDetailModel));
                } else {
                    context.startActivity(new Intent(context, AddCalendarTaskActivity.class)
                            .putExtra("isEdit", true)
                            .putExtra("modelData", calendarDetailModel));
                }
            }
        });

        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }


}
