package com.project.jarjamediaapp.CustomAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
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
import com.project.jarjamediaapp.Activities.calendarDetail.CalendarAppointmentDetailModel;
import com.project.jarjamediaapp.Activities.calendarDetail.CalendarTaskDetailModel;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.EasyPreference;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;

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
    Call<CalendarAppointmentDetailModel> _call;
    Call<CalendarTaskDetailModel> _cCall;
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
            String date = "";
            if (modelData.getStart() != null || modelData.getStart().equalsIgnoreCase("Null")
                    || !modelData.getStart().equals("")) {
                date = GH.getInstance().formatter(modelData.getStart(), "dd", "yyyy-MM-dd'T'HH:mm:ss");
            }
            holder.tvTitle.setText(modelData.getTitle() != null ? modelData.getTitle() : "");
            holder.tvCount.setText(date);



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
                          /*  mData.remove(pos);
                            notifyItemRemoved(pos);
                            notifyItemRangeChanged(pos, mData.size());*/
                            mData.remove(pos);
                            notifyDataSetChanged();
                            EasyPreference.Builder pref = new EasyPreference.Builder(context);
                            pref.addInt(GH.KEYS.CALENDERUPDATELIST.name(),pos).save();
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

    private void viewCalendarAppointmentDetail(String calendarId, String startDateTime) {

        GH.getInstance().ShowProgressDialog(activity);

        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getCalendarAppointmentDetail(GH.getInstance().getAuthorization(), calendarId, startDateTime);

        _call.enqueue(new Callback<CalendarAppointmentDetailModel>() {
            @Override
            public void onResponse(Call<CalendarAppointmentDetailModel> call, Response<CalendarAppointmentDetailModel> response) {
                GH.getInstance().HideProgressDialog();
                if (response.isSuccessful()) {

                    CalendarAppointmentDetailModel calendarAppointmentDetailModel = response.body();
                    if (calendarAppointmentDetailModel.getStatus().equals("Success")) {

                        showDialogForCalendarAppointmentDetail(context, calendarAppointmentDetailModel.getData().getCalendarData());

                    } else {
                        ToastUtils.showToast(context, calendarAppointmentDetailModel.getMessage());
                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    ToastUtils.showToast(context, error.message());
                }
            }

            @Override
            public void onFailure(Call<CalendarAppointmentDetailModel> call, Throwable t) {
                GH.getInstance().HideProgressDialog();
                ToastUtils.showToastLong(context, context.getString(R.string.retrofit_failure));
            }
        });
    }

    private void viewCalendarTaskDetail(String calendarId, String startDateTime) {

        GH.getInstance().ShowProgressDialog(activity);

        _cCall = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getCalendarTaskDetail(GH.getInstance().getAuthorization(), calendarId, startDateTime);

        _cCall.enqueue(new Callback<CalendarTaskDetailModel>() {
            @Override
            public void onResponse(Call<CalendarTaskDetailModel> call, Response<CalendarTaskDetailModel> response) {
                GH.getInstance().HideProgressDialog();
                if (response.isSuccessful()) {

                    CalendarTaskDetailModel calendarTaskDetailModel = response.body();
                    if (calendarTaskDetailModel.getStatus().equals("Success")) {

                        showDialogForCalendarTaskDetail(context, calendarTaskDetailModel.getData());

                    } else {
                        ToastUtils.showToast(context, calendarTaskDetailModel.getMessage());

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    ToastUtils.showToast(context, error.message());

                }
            }

            @Override
            public void onFailure(Call<CalendarTaskDetailModel> call, Throwable t) {
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
                    if (modelData.getCalendarType() != null && !modelData.getCalendarType().equalsIgnoreCase("") && modelData.getCalendarType().equalsIgnoreCase("Task")) {
                        viewCalendarTaskDetail(modelData.getCalendarId(), modelData.getStart());
                    } else {

                        viewCalendarAppointmentDetail(modelData.getCalendarId(), modelData.getStart());

                    }


                }
            });

        }
    }

    private void showDialogForCalendarAppointmentDetail(Context context, CalendarAppointmentDetailModel.Data.CalendarData calendarDetailModelForTask) {

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

            if (calendarDetailModelForTask.getDatedTo() != null && calendarDetailModelForTask.getDatedFrom() != null) {

                String[] startTime = calendarDetailModelForTask.getDatedFrom().split("T");
                String[] endTime = calendarDetailModelForTask.getDatedTo().split("T");
                if (calendarDetailModelForTask.isAllDay) {
                    tvTime.setText("All Day");
                } else {
                    tvTime.setText(GH.getInstance().formatter(startTime[1], "hh:mm a", "HH:mm:ss") + " - " +
                            GH.getInstance().formatter(endTime[1], "hh:mm a", "HH:mm:ss"));
                }
                tvDate.setText(GH.getInstance().formatter(startTime[0], "EEE, MMM dd, yyyy", "yyyy-MM-dd") + " - " +
                        GH.getInstance().formatter(endTime[0], "EEE, MMM dd, yyyy", "yyyy-MM-dd"));
            }
            tvEventTitle.setText(calendarDetailModelForTask.getEventTitle() != null ? calendarDetailModelForTask.getEventTitle() : "");
            tvLocation.setText(calendarDetailModelForTask.getLocation() != null ? calendarDetailModelForTask.getLocation() : "");
            tvLead.setText(calendarDetailModelForTask.getLeadName() != null ? calendarDetailModelForTask.getLeadName() : "");

            if (calendarDetailModelForTask.getAgentList() != null) {
                String agentNames = "";
                if (calendarDetailModelForTask.getAgentList().size() > 0) {
                    for (int i = 0; i < calendarDetailModelForTask.getAgentList().size(); i++) {
                        if (agentNames.equals("")) {
                            agentNames = calendarDetailModelForTask.getAgentList().get(i).getAgentName();
                        } else {
                            agentNames = agentNames + "," + calendarDetailModelForTask.getAgentList().get(i).getAgentName();
                        }
                    }
                    tvAgents.setText(agentNames);
                }
            }

            tvDetail.setText(calendarDetailModelForTask.getDesc() != null ? calendarDetailModelForTask.getDesc() : "");
            tvNotes.setText(calendarDetailModelForTask.getNote() != null ? calendarDetailModelForTask.getNote() : "");

            tvClose = dialog.findViewById(R.id.tvClose);
            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            Button btnEdit = dialog.findViewById(R.id.btnEdit);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();


                    Log.i("calenderId",modelData.getCalendarId());
                    // data and calendar id will be passed in intent
                    context.startActivity(new Intent(context, AddAppointmentActivity.class)
                            .putExtra("from", "6")
                            .putExtra("calendarId", modelData.getCalendarId())
                            .putExtra("modelData", calendarDetailModelForTask));

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

    private void showDialogForCalendarTaskDetail(Context context, CalendarTaskDetailModel.Data calendarDetailModel) {

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
            if (calendarDetailModel != null) {
                tvEventDetail.setPaintFlags(tvEventDetail.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                if (calendarDetailModel.startTime != null && calendarDetailModel.startTime != null) {

                    calendarDetailModel.setStartTime(calendarDetailModel.startTime);
                    String[] startTime = calendarDetailModel.getStartTime().split(" ");
                    if (calendarDetailModel.isAllDay != null && calendarDetailModel.isAllDay) {
                        tvTime.setText("All Day");
                    } else {
                        tvTime.setText(GH.getInstance().formatter(startTime[1], "hh:mm", "HH:mm:ss")+" "+startTime[2]);
                    }
                    tvDate.setText(GH.getInstance().formatter(startTime[0], "EEE, MMM dd, yyyy", "MM/dd/yyyy"));
                }
                tvEventTitle.setText(calendarDetailModel.getEventTitle() != null ? calendarDetailModel.getEventTitle() : "");
                tvAttendeesCount.setText("0");
                tvDetail.setText(calendarDetailModel.getDescription() != null ? calendarDetailModel.getDescription() : "");
            }

            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            Button btnEdit = dialog.findViewById(R.id.btnEdit);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                    if (calendarDetailModel != null) {

                        context.startActivity(new Intent(context, AddCalendarTaskActivity.class)
                                .putExtra("isEdit", true)
                                .putExtra("calendarId", calendarDetailModel.userCalenderID)
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

}
