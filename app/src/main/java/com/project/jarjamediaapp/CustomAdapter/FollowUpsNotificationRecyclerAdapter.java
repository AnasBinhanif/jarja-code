package com.project.jarjamediaapp.CustomAdapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.project.jarjamediaapp.Activities.notification.FollowUpNotificationModel;
import com.project.jarjamediaapp.Models.ViewFollowUpModel;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowUpsNotificationRecyclerAdapter extends RecyclerView.Adapter<FollowUpsNotificationRecyclerAdapter.MyViewHolder> {


    final LayoutInflater inflater;
    Context context;
    ArrayList<FollowUpNotificationModel.Data> data;


    public FollowUpsNotificationRecyclerAdapter(Context context, ArrayList<FollowUpNotificationModel.Data> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);

    }




    @Override
    public FollowUpsNotificationRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_notifications_layout, parent, false);
        return new FollowUpsNotificationRecyclerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FollowUpsNotificationRecyclerAdapter.MyViewHolder holder, final int position) {

        final FollowUpNotificationModel.Data notificationObj  = data.get(position);

        if(!notificationObj.getIsSeen()){


            holder.CardviewNotification.setCardBackgroundColor(context.getResources().getColor(R.color.colorYellow));

        }



        holder.tvName.setText(notificationObj.getFollowUpsType() != null ? notificationObj.getFollowUpsType() : "N/A");

        holder.tvLeadName.setText(notificationObj.getLeadName() != null ? notificationObj.getLeadName() : "N/A");

        holder.tvContact.setText(notificationObj.getAddress() != null ? notificationObj.getAddress() : "N/A");

        holder.tvEmail.setText(notificationObj.getDripType() != null ? notificationObj.getDripType() : "N/A");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int backgroundColor = holder.CardviewNotification.getCardBackgroundColor().getDefaultColor();
                if (backgroundColor == context.getResources().getColor(R.color.colorYellow)){

                    holder.CardviewNotification.setCardBackgroundColor(context.getResources().getColor(R.color.colorWhite));


                }

                getFolloUpDetailByID(notificationObj.getDripDetailID());

            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvLeadName,tvContact,tvEmail;
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

    private void getFolloUpDetailByID(String dripDetailId) {

        Call<ViewFollowUpModel> _callToday;
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetFollowUpDetails(GH.getInstance().getAuthorization(), dripDetailId);
        _callToday.enqueue(new Callback<ViewFollowUpModel>() {
            @Override
            public void onResponse(Call<ViewFollowUpModel> call, Response<ViewFollowUpModel> response) {
                GH.getInstance().HideProgressDialog();
                if (response.isSuccessful()) {

                    ViewFollowUpModel getDetails = response.body();
                    if (getDetails.status.equals("Success")) {

                        GH.getInstance().HideProgressDialog();
                        String wait = getDetails.data.viewDPCStep.wait;
                        String time = getDetails.data.viewDPCStep.sendTime;
                        String note = getDetails.data.viewDPCStep.message;
                        String title = getDetails.data.viewDPCStep.subject;
                        String senType = getDetails.data.viewDPCStep.sentType;
                        String dateTime = getDetails.data.viewDPCStep.sendDateTime;

                        showViewFollowUpDialog(context, wait, title, dateTime, time, note, senType);


                    } else {

                        ToastUtils.showToast(context, getDetails.message);
                        GH.getInstance().HideProgressDialog();

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    ToastUtils.showToast(context, error.message());
                    GH.getInstance().HideProgressDialog();
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

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
