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
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.jarjamediaapp.Activities.add_task.AddTaskActivity;
import com.project.jarjamediaapp.Activities.user_profile.GetPermissionModel;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetTasksModel;
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

public class SwipeTasksRecyclerAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflater;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();
    public Context context;
    int pos;
    boolean isEditByLead, isFutureTask;
    Activity activity;
    List<GetTasksModel.Data.TaskList> mData;
    GetPermissionModel userPermission;
    String firstnameInitial = "", lastnameInitial = "";

    public SwipeTasksRecyclerAdapter(Context context, Activity activity, List<GetTasksModel.Data.TaskList> data, boolean isEditByLead, boolean isFutureTask) {

        mData = data;
        this.context = context;
        this.activity = activity;
        this.isFutureTask = isFutureTask;
        this.isEditByLead = isEditByLead;
        mInflater = LayoutInflater.from(context);
        binderHelper.setOpenOnlyOne(true);

     /*   // fro testing
        if (GH.getInstance().getNotificationType().equals("task")) {

            editTask(0, null);
        }*/


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_swipe_tasks_due_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {
        final ViewHolder holder = (ViewHolder) h;
//        pos = position;
        pos = h.getAdapterPosition();
        if (mData != null && 0 <= position && position < mData.size()) {

            GetTasksModel.Data.TaskList modelData = mData.get(position);

            holder.tvTaskType.setText(modelData.type);
            holder.tvName.setText(modelData.name + " for " + modelData.agentName);
            holder.tvAddress.setText(modelData.firstName + " " + modelData.lastName);

            String firstName = modelData.firstName;
            String lastName = modelData.lastName;
            if (firstName.equals("null") || firstName == null) {
                firstName = "";
            }
            if (lastName.equals("null") || lastName == null) {
                lastName = "";
            }

            firstnameInitial = "";
            lastnameInitial = "";

            if (!firstName.equals("")) {
                firstnameInitial = firstName.charAt(0) + "";
            }
            if (!lastName.equals("")) {
                lastnameInitial = lastName.charAt(0) + "";
            }
            holder.tvInitial.setText(firstnameInitial + lastnameInitial);
            //end

           /* try {
                holder.tvInitial.setText(firstName.substring(0, 1) + lastName.substring(0, 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
*/
            holder.frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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

    private void markAsRead(String taskID) {

        GH.getInstance().ShowProgressDialog(activity);

        Call<BaseResponse> _callToday;
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).MarkTaskComplete(GH.getInstance().getAuthorization(),
                taskID + ",", "true");
        _callToday.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                GH.getInstance().HideProgressDialog();
                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (getAppointmentsModel.getStatus().equals("Success")) {

                        binderHelper.closeLayout(String.valueOf(pos));
                        ToastUtils.showToast(context, "Done");
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

    public void saveStates(Bundle outState) {
        binderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        binderHelper.restoreStates(inState);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private SwipeRevealLayout swipeLayout;
        TextView tvName, tvAddress, tvEdit, tvDone, tvInitial, tvTaskType;
        FrameLayout frameLayout;
        Gson gson = new Gson();

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
            tvTaskType = itemView.findViewById(R.id.tvTaskType);
        }

        public void bind() {

            tvDone.setOnClickListener(v -> {
                pos = getAdapterPosition();
                String taskID = mData.get(pos).taskID;
                markAsRead(taskID);
            });

            if (isFutureTask) {
                tvDone.setVisibility(View.GONE);
            }
            tvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //   GetPermissionModel  userPermission = GH.getInstance().getUserPermissions();
                    String storedHashMapLeadsString = GH.getInstance().getUserPermissonLead();
                    java.lang.reflect.Type typeLeads = new TypeToken<HashMap<String, Boolean>>() {
                    }.getType();
                    HashMap<String, Boolean> mapLeads = gson.fromJson(storedHashMapLeadsString, typeLeads);

                    if (mapLeads.get("View Tasks From Leads")) {

                        pos = getAdapterPosition();
                        editTask(pos, swipeLayout);

                    } else {

                        ToastUtils.showToast(context, context.getString(R.string.lead_ViewLeads));
                    }

                }
            });
            frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //   GetPermissionModel  userPermission = GH.getInstance().getUserPermissions();
                    String storedHashMapLeadsString = GH.getInstance().getUserPermissonLead();
                    java.lang.reflect.Type typeLeads = new TypeToken<HashMap<String, Boolean>>() {
                    }.getType();
                    HashMap<String, Boolean> mapLeads = gson.fromJson(storedHashMapLeadsString, typeLeads);

                    if (mapLeads.get("View Tasks From Leads")) {

                        pos = getAdapterPosition();
                        editTask(pos, swipeLayout);

                    } else {

                        ToastUtils.showToast(context, context.getString(R.string.lead_ViewLeads));
                    }
                }
            });

        }
    }

    private void editTask(int pos, SwipeRevealLayout swipeLayout) {
       /* Gson gson = new Gson();
        //   GetPermissionModel  userPermission = GH.getInstance().getUserPermissions();
        String storedHashMapDashboardString = GH.getInstance().getUserPermissonDashboard();
        java.lang.reflect.Type typeDashboard = new TypeToken<HashMap<String, Boolean>>(){}.getType();
        HashMap<String, Boolean> mapDashboard = gson.fromJson(storedHashMapDashboardString, typeDashboard);

        if (mapDashboard.get("Edit Task")) {*/

        String leadID = mData.get(pos).leadID;
        String taskID = mData.get(pos).taskID;
        String scheduleID = mData.get(pos).scheduleID;
        if (isEditByLead) {
            if (swipeLayout != null) {
                swipeLayout.close(true);
            }

            if (!isFutureTask) {
                if (taskID == null || taskID.equals("null") || taskID.equals("")) {
                    Toast.makeText(context, "Task ID Not Found", Toast.LENGTH_SHORT).show();
                } else {


                    context.startActivity(new Intent(context, AddTaskActivity.class)
                            .putExtra("from", "5")
                            .putExtra("whichTasks", 1)
                            .putExtra("leadID", leadID)
                            .putExtra("taskId", taskID));
                }
            } else {

                if (scheduleID == null || scheduleID.equals("null") || scheduleID.equals("")) {
                    Toast.makeText(context, "Task ID Not Found", Toast.LENGTH_SHORT).show();
                } else {


                    context.startActivity(new Intent(context, AddTaskActivity.class)
                            .putExtra("from", "5")
                            .putExtra("whichTasks", 3)
                            .putExtra("leadID", leadID)
                            .putExtra("taskId", scheduleID));
                }
            }
        } else {

            if (swipeLayout != null) {
                swipeLayout.close(true);
            }
            // from overdue task
            if (!isFutureTask) {
                if (taskID == null || taskID.equals("null") || taskID.equals("")) {
                    Toast.makeText(context, "Task ID Not Found", Toast.LENGTH_SHORT).show();
                } else {
                    context.startActivity(new Intent(context, AddTaskActivity.class)
                            .putExtra("from", "3")
                            .putExtra("whichTasks", 1)
                            .putExtra("taskId", taskID));
                }
            } else {

                if (scheduleID == null || scheduleID.equals("null") || scheduleID.equals("")) {
                    Toast.makeText(context, "Task ID Not Found", Toast.LENGTH_SHORT).show();
                } else {

                    context.startActivity(new Intent(context, AddTaskActivity.class)
                            .putExtra("from", "3")
                            .putExtra("whichTasks", 3)
                            .putExtra("taskId", scheduleID));
                }


            }
        }
      /*  } else {

            ToastUtils.showToast(context, context.getString(R.string.dashboard_EditTask));
        }*/
    }

}
