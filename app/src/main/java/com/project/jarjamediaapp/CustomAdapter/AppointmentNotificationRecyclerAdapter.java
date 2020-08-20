package com.project.jarjamediaapp.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentActivity;
import com.project.jarjamediaapp.Activities.add_appointment.Data;
import com.project.jarjamediaapp.Activities.add_appointment.GetAppointmentByIDModel;
import com.project.jarjamediaapp.Activities.notification.AppointmentNotificationModel;
import com.project.jarjamediaapp.Activities.notification.NotificationActivity;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.databinding.ActivityTagsBindingImpl;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentNotificationRecyclerAdapter extends RecyclerView.Adapter<AppointmentNotificationRecyclerAdapter.MyViewHolder> {


    final LayoutInflater inflater;
    Context context;
    ArrayList<AppointmentNotificationModel.FollowUpsList> data;


    public AppointmentNotificationRecyclerAdapter(Context context, ArrayList<AppointmentNotificationModel.FollowUpsList> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);

    }




    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_notifications_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final AppointmentNotificationModel.FollowUpsList notificationObj  = data.get(position);

        if(!notificationObj.getIsSeen()){


            holder.CardviewNotification.setCardBackgroundColor(context.getResources().getColor(R.color.colorYellow));

        }

        holder.tvName.setText(notificationObj.getEventTitle() != null ? notificationObj.getEventTitle() : "N/A");


        String firstName = notificationObj.getVtCRMLeadCustom().getFirstName();
        String lastName = notificationObj.getVtCRMLeadCustom().getLastName();
        if (firstName != null && lastName != null) {
            holder.tvLeadName.setText(firstName + " " + lastName);
        } else if (firstName == null && lastName != null) {
            holder.tvLeadName.setText(lastName);
        } else if (firstName != null && lastName == null) {
            holder.tvLeadName.setText(firstName);
        } else if (firstName == null && lastName == null) {
            holder.tvLeadName.setText("N/A");
        }


        holder.tvContact.setText(notificationObj.getVtCRMLeadCustom().getPrimaryPhone() != null ? notificationObj.getVtCRMLeadCustom().getPrimaryPhone() : "N/A");

        holder.tvEmail.setText(notificationObj.getVtCRMLeadCustom().getPrimaryEmail() != null ? notificationObj.getVtCRMLeadCustom().getPrimaryEmail() : "N/A");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int backgroundColor = holder.CardviewNotification.getCardBackgroundColor().getDefaultColor();
                if (backgroundColor == context.getResources().getColor(R.color.colorYellow)){

                    holder.CardviewNotification.setCardBackgroundColor(context.getResources().getColor(R.color.colorWhite));


                }
                getAppointmentById(notificationObj.getLeadAppoinmentID());

                GH.getInstance().ShowProgressDialog((NotificationActivity)context);
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

    public void getAppointmentById(String appointmentID) {

        Call<GetAppointmentByIDModel> _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetAppointmentByID(GH.getInstance().getAuthorization(),appointmentID);
        _call.enqueue(new Callback<GetAppointmentByIDModel>() {
            @Override
            public void onResponse(Call<GetAppointmentByIDModel> call, Response<GetAppointmentByIDModel> response) {

                if (response.isSuccessful()) {

                    GetAppointmentByIDModel getAppointmentsModel = response.body();

                    if (getAppointmentsModel.getStatus().equals("Success")) {

                        GH.getInstance().HideProgressDialog();
                        // from notifcation screen when tap on notification item
                        Data models = getAppointmentsModel.getData();
                        context.startActivity(new Intent(context, AddAppointmentActivity.class)
                                .putExtra("leadID", models.getLeadID())
                                .putExtra("from", "7")
                                .putExtra("leadName",models.getEventTitle())
                                .putExtra("models", models));
                        //_view.updateUI(getAppointmentsModel);

                    } else {

                       /* _view.hideProgressBar();
                        _view.updateUIonFalse(getAppointmentsModel.message);*/

                    }
                } else {

//                    _view.hideProgressBar();
//                    ApiError error = ErrorUtils.parseError(response);
//                    _view.updateUIonError(error.message());
                }
            }

            @Override
            public void onFailure(Call<GetAppointmentByIDModel> call, Throwable t) {
               /* _view.hideProgressBar();
                _view.updateUIonFailure();*/
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

    @Override
    public void onViewRecycled(MyViewHolder holder) {
        super.onViewRecycled(holder);
    }
}

