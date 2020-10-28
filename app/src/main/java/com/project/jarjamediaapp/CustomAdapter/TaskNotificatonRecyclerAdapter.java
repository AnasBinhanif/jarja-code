package com.project.jarjamediaapp.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.project.jarjamediaapp.Activities.add_task.AddTaskActivity;
import com.project.jarjamediaapp.Activities.add_task.GetTaskDetail;
import com.project.jarjamediaapp.Activities.notification.AppointmentNotificationModel;
import com.project.jarjamediaapp.Activities.notification.NotificationActivity;
import com.project.jarjamediaapp.Activities.notification.TaskNotificationModel;
import com.project.jarjamediaapp.Activities.tasks.TasksActivity;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskNotificatonRecyclerAdapter extends RecyclerView.Adapter<TaskNotificatonRecyclerAdapter.MyViewHolder> {


    final LayoutInflater inflater;
    Context context;
    ArrayList<TaskNotificationModel.TaskList> data;


    public TaskNotificatonRecyclerAdapter(Context context, ArrayList<TaskNotificationModel.TaskList> data) {
        this.context = context;
        this.data = data;
        sortData();
        inflater = LayoutInflater.from(context);

    }


    @Override
    public TaskNotificatonRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_notifications_layout, parent, false);
        return new TaskNotificatonRecyclerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskNotificatonRecyclerAdapter.MyViewHolder holder, final int position) {

        TaskNotificationModel.TaskList notificationObj = data.get(position);

        if (!notificationObj.getIsSeen()) {


            holder.CardviewNotification.setCardBackgroundColor(context.getResources().getColor(R.color.colorYellow));

        }


        holder.tvName.setText(notificationObj.getTaskName() != null ? notificationObj.getTaskName() : "N/A");


        holder.tvLeadName.setText(notificationObj.getDescription());


        holder.tvContact.setText(notificationObj.getTaskType());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int backgroundColor = holder.CardviewNotification.getCardBackgroundColor().getDefaultColor();
                if (backgroundColor == context.getResources().getColor(R.color.colorYellow)) {

                    holder.CardviewNotification.setCardBackgroundColor(context.getResources().getColor(R.color.colorWhite));
                    data.get(position).setIsSeen(true);

                }
                getTaskDetail(notificationObj.getEncryptedTaskID());
                //  GH.getInstance().ShowProgressDialog((NotificationActivity)context);


            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void sortData() {
        Collections.sort(this.data, new Comparator<TaskNotificationModel.TaskList>() {
            @Override
            public int compare(TaskNotificationModel.TaskList t1, TaskNotificationModel.TaskList t2) {
                return Boolean.compare(t1.getIsSeen(), t1.getIsSeen());
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvLeadName, tvContact, tvEmail;
        CardView CardviewNotification;


        private MyViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvContact = itemView.findViewById(R.id.tvContact);
            tvLeadName = itemView.findViewById(R.id.tvLeadName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            CardviewNotification = itemView.findViewById(R.id.cardview_notification);


        }
    }

    public void getTaskDetail(String taskId) {


        Call<GetTaskDetail> apiCall = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getTaskDetail(GH.getInstance().getAuthorization(), taskId);
        apiCall.enqueue(new Callback<GetTaskDetail>() {
            @Override
            public void onResponse(Call<GetTaskDetail> call, Response<GetTaskDetail> response) {
                Log.d("onResponse: ", response.message());
                if (response.isSuccessful()) {

                    GetTaskDetail getTaskDetail = response.body();

                    if (getTaskDetail.getStatus().equalsIgnoreCase("Success")) {

                        GH.getInstance().HideProgressDialog();

                        //****added by akshay to sort list on basis of seen or unseen
                        Collections.sort(data, new Comparator<TaskNotificationModel.TaskList>() {
                            @Override
                            public int compare(TaskNotificationModel.TaskList t1, TaskNotificationModel.TaskList t2) {
                                return Boolean.compare(t1.getIsSeen(), t1.getIsSeen());
                            }
                        });
                        notifyDataSetChanged();
                        //**********
                        context.startActivity(new Intent(context, AddTaskActivity.class)
                                .putExtra("from", "3")
                                .putExtra("whichTasks", 1)
                                .putExtra("leadID", getTaskDetail.getData().leadID)
                                .putExtra("taskId", taskId));
                        // _view.updateTaskDetail(getTaskDetail);


                    } else {

                        ToastUtils.showToastLong(context, getTaskDetail.message);
                       /* _view.hideProgressBar();

                        _view.updateUIonFalse(getTaskDetail.message);*/

                    }
                } else {


                    ApiError error = ErrorUtils.parseError(response);
                    ToastUtils.showToastLong(context, error.Message());

                }
            }

            @Override
            public void onFailure(Call<GetTaskDetail> call, Throwable t) {
                ToastUtils.showToastLong(context, t.getMessage());
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}