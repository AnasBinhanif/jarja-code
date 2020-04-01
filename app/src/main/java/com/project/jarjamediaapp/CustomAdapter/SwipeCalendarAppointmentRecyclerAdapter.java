package com.project.jarjamediaapp.CustomAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SwipeCalendarAppointmentRecyclerAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflater;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();
    public Context context;
    Activity activity;
    int pos;
    List<CalendarModel.Data> mData;
    CalendarModel.Data modelData;
    Call<CalendarDetailModel> _call;
    Call<BaseResponse> call;

    public SwipeCalendarAppointmentRecyclerAdapter(Context context, Activity activity, List<CalendarModel.Data> data) {

        mData = data;
        this.context = context;
        this.activity = activity;
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

        GH.getInstance().ShowProgressDialog(activity);

        if (type != null && !type.equalsIgnoreCase("") && type.equalsIgnoreCase("Task")) {
            call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).deleteCalendarTask(GH.getInstance().getAuthorization(), calendarId);

        } else {
            call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).deleteCalendarAppointment(GH.getInstance().getAuthorization(), calendarId);
        }
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                GH.getInstance().HideProgressDialog();
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
                GH.getInstance().HideProgressDialog();
                ToastUtils.showToastLong(context, context.getString(R.string.retrofit_failure));
            }
        });
    }

    private void viewCalendarAppointmentOrTaskDetail(String calendarId, String type, String startDateTime) {

        GH.getInstance().ShowProgressDialog(activity);

        if (type != null && !type.equalsIgnoreCase("") && type.equalsIgnoreCase("Task")) {
            _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getCalendarTaskDetail(GH.getInstance().getAuthorization(), calendarId, startDateTime);

        } else {
            _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getCalendarAppointmentDetail(GH.getInstance().getAuthorization(), calendarId);
        }
        _call.enqueue(new Callback<CalendarDetailModel>() {
            @Override
            public void onResponse(Call<CalendarDetailModel> call, Response<CalendarDetailModel> response) {
                GH.getInstance().HideProgressDialog();
                if (response.isSuccessful()) {

                    CalendarDetailModel calendarDetailModel = response.body();
                    if (calendarDetailModel.getStatus().equals("Success")) {

                        if (type != null && !type.equalsIgnoreCase("") && type.equalsIgnoreCase("Task")) {
                             showDialogForCalendarTaskDetail(context, calendarDetailModel.getData().getList());
                        } else {
                            showDialogForCalendarAppointmentDetail(context, calendarDetailModel.getData().getCalendarData());
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
            public void onFailure(Call<CalendarDetailModel> call, Throwable t) {
                GH.getInstance().HideProgressDialog();
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

                modelData = mData.get(getAdapterPosition());
                deleteCalendarAppointmentOrTaskDetail(modelData.getCalendarId(), modelData.getCalendarType(), swipeLayout);

            });

            frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    modelData = mData.get(getAdapterPosition());
                    viewCalendarAppointmentOrTaskDetail(modelData.getCalendarId(), modelData.getCalendarType(), modelData.getStart());

                }
            });

        }
    }

    private void showDialogForCalendarAppointmentDetail(Context context, CalendarDetailModel.Data.CalendarData calendarDetailModel) {

        try {

            final Dialog dialog = new Dialog(context, R.style.Dialog);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialog_view_calendar_appointment_detail);

            TextView tvEventTitle, tvLead, tvAgents, tvDate, tvTime, tvLocation, tvDetail, tvNotes, tvClose;

            tvEventTitle = dialog.findViewById(R.id.tvEventTitle);
            tvLead = dialog.findViewById(R.id.tvLead);
            tvAgents = dialog.findViewById(R.id.tvAgents);
            tvDate = dialog.findViewById(R.id.tvDate);
            tvLocation = dialog.findViewById(R.id.tvLocation);
            tvDetail = dialog.findViewById(R.id.tvDetails);
            tvTime = dialog.findViewById(R.id.tvTime);
            tvNotes = dialog.findViewById(R.id.tvNotes);

            String[] startTime = calendarDetailModel.getDatedFrom().split("T");
            String[] endTime = calendarDetailModel.getDatedTo().split("T");
            tvEventTitle.setText(calendarDetailModel.getEventTitle() != null ? calendarDetailModel.getEventTitle() : "");
            if (calendarDetailModel.isAllDay) {
                tvTime.setText("All Day");
            } else {
                tvTime.setText(GH.getInstance().formatter(startTime[1], "hh:mm:ss a", "HH:mm:ss") + " - " + GH.getInstance().formatter(endTime[1], "hh:mm:ss a", "HH:mm:ss"));
            }
            tvLocation.setText(calendarDetailModel.getLocation() != null ? calendarDetailModel.getLocation() : "");
            tvDate.setText(GH.getInstance().formatter(startTime[0], "EEE,MMM dd,yyyy", "yyyy-mm-dd") + " - " + GH.getInstance().formatter(endTime[0], "EEE,MMM dd,yyyy", "yyyy-mm-dd"));
            tvLead.setText(calendarDetailModel.getLeadName() != null ? calendarDetailModel.getLeadName() : "");

            if (calendarDetailModel != null && calendarDetailModel.getAgentList().size() > 0) {

                ArrayList<String> arrayList = new ArrayList<>();
                for (int i = 0; i < calendarDetailModel.getAgentList().size(); i++) {
                    arrayList.add(calendarDetailModel.getAgentList().get(i).getAgentName());
                }
                String agentNames = TextUtils.join(" , ", arrayList);
                tvAgents.setText(agentNames);
            }

            // need data from api
            tvDetail.setText(calendarDetailModel.getDesc() != null ? calendarDetailModel.getDesc() : "");
            tvNotes.setText(calendarDetailModel.getNote() != null ? calendarDetailModel.getNote() : "");

            tvClose = dialog.findViewById(R.id.tvClose);
            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            Button btnEdit = dialog.findViewById(R.id.btnEdit);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                    if (calendarDetailModel.getCalendarType() == null) {
                        // data and calendar id will be passed in intent
                        context.startActivity(new Intent(context, AddAppointmentActivity.class)
                                .putExtra("from", "6")
                                .putExtra("calendarId", modelData.getCalendarId())
                                .putExtra("modelData", calendarDetailModel));
                    } else {
                        context.startActivity(new Intent(context, AddCalendarTaskActivity.class)
                                .putExtra("isEdit", true)
                                .putExtra("calendarId", calendarDetailModel.getGmailCalenderId())
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

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void showDialogForCalendarTaskDetail(Context context, CalendarDetailModel.Data.CalendarList calendarDetailModel) {

        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_view_calendar_task_detail);

        TextView tvEventTitle, tvDate, tvTime, tvAttendeesCount, tvDetail, tvEventDetail, tvClose;

        tvEventTitle = dialog.findViewById(R.id.tvEventTitle);
        tvDate = dialog.findViewById(R.id.tvDate);
        tvTime = dialog.findViewById(R.id.tvTime);
        tvAttendeesCount = dialog.findViewById(R.id.tvAttendeesCount);
        tvDetail = dialog.findViewById(R.id.tvDetails);
        tvEventDetail = dialog.findViewById(R.id.tvEventDetails);
        tvClose = dialog.findViewById(R.id.tvClose);

        try {
            tvEventDetail.setPaintFlags(tvEventDetail.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            String[] startTime = calendarDetailModel.getStartTime().split("T");
            String[] endTime = calendarDetailModel.getEndTime().split("T");
            tvEventTitle.setText(calendarDetailModel.getEventTitle() != null ? calendarDetailModel.getEventTitle() : "");
            if (calendarDetailModel.isAllDay != null && calendarDetailModel.isAllDay) {
                tvTime.setText("All Day");
            } else {
                tvTime.setText(GH.getInstance().formatter(startTime[1], "hh:mm:ss a", "HH:mm:ss") + " - " + GH.getInstance().formatter(endTime[1], "hh:mm:ss a", "HH:mm:ss"));
            }
            tvAttendeesCount.setText("0");
            tvDate.setText(GH.getInstance().formatter(startTime[0], "EEE,MMM dd,yyyy", "yyyy-MM-dd") + " - " + GH.getInstance().formatter(endTime[0], "EEE,MMM dd,yyyy", "yyyy-MM-dd"));
            tvDetail.setText(calendarDetailModel.getDescription() != null ? calendarDetailModel.getDescription() : "");

            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            Button btnEdit = dialog.findViewById(R.id.btnEdit);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                    if (calendarDetailModel != null) {
                        if (calendarDetailModel.getCalendarType().equals("Event")) {
                            // data and calendar id will be passed in intent
                            context.startActivity(new Intent(context, AddAppointmentActivity.class)
                                    .putExtra("from", "6")
                                    .putExtra("calendarId", calendarDetailModel.getCalendarEventID())
                                    .putExtra("modelData", calendarDetailModel));
                        } else {
                            context.startActivity(new Intent(context, AddCalendarTaskActivity.class)
                                    .putExtra("isEdit", true)
                                    .putExtra("calendarId", calendarDetailModel.getCalendarEventID())
                                    .putExtra("modelData", calendarDetailModel));
                        }
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

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

}
