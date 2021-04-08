package com.project.jarjamediaapp.CustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.project.jarjamediaapp.Models.CommunicationModel;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;

import java.util.List;


public class SwipeCommunicationRecyclerAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflater;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();
    public Context context;
    Activity activity;
    int pos;
    String status = "";
    List<CommunicationModel.Data> modelList;

    public static boolean isEditable = false;
    String agentString = "";


    public SwipeCommunicationRecyclerAdapter(Context context, Activity activity, List<CommunicationModel.Data> modelList) {

        this.context = context;
        this.activity = activity;
        this.modelList = modelList;
        mInflater = LayoutInflater.from(context);
        binderHelper.setOpenOnlyOne(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_communication_rv2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int position) {
        final ViewHolder holder = (ViewHolder) h;

        pos = position;
        if (modelList != null && 0 <= position && position < modelList.size()) {

            CommunicationModel.Data communicationModel = modelList.get(position);

            String date = GH.getInstance().formatter(modelList.get(position).getDate(), "dd/MM/yyyy", "yyyy-MM-dd'T'hh:mm:ss");
            holder.tvDate.setText(date);
            String time = GH.getInstance().formatter(modelList.get(position).getDate(), "hh:mm a", "yyyy-MM-dd'T'hh:mm:ss");
            holder.tvTime.setText(time);

            if (communicationModel.getType().equalsIgnoreCase("Sent")) {

                holder.ivIcon.setImageResource(R.drawable.ic_envelope_solid);
                holder.tvHeading.setText(communicationModel.getHtml());
                holder.tvSubHeading.setText(communicationModel.getSubject());

            } else if (communicationModel.getType().equalsIgnoreCase("Text")) {
                holder.ivIcon.setImageResource(R.drawable.ic_mobile_solid);
                holder.tvHeading.setText(communicationModel.getHtml());
                holder.tvSubHeading.setText(communicationModel.getMsg());

            } else if (communicationModel.getType().equalsIgnoreCase("Call")) {

                holder.ivIcon.setImageResource(R.drawable.ic_phone_square_alt_solid);
                holder.tvHeading.setText("Call to " + communicationModel.getSentAt());

                if (!TextUtils.isEmpty(communicationModel.getCallDuration())) {
                    holder.tvSubHeading.setText("Call duration: " + communicationModel.getCallDuration() + " minute(s)");
                } else {
                    holder.tvSubHeading.setText(communicationModel.getMsg());
                }

            } else if (communicationModel.getType().equalsIgnoreCase("SocialMedia")) {

                holder.ivIcon.setImageResource(R.drawable.ic_comment_dots_solid);
                holder.tvHeading.setText("Social Media");
                holder.tvSubHeading.setText(communicationModel.getMsg());

            } else if (communicationModel.getType().equalsIgnoreCase("Inperson")) {
                holder.ivIcon.setImageResource(R.drawable.ic_users_solid);
                holder.tvHeading.setText("In-person");
                holder.tvSubHeading.setText(communicationModel.getMsg());
            } else if (communicationModel.getType().equalsIgnoreCase("Others")) {
                holder.ivIcon.setImageResource(R.drawable.ic_lightbulb_regular);
                holder.tvHeading.setText("Other");
                holder.tvSubHeading.setText(communicationModel.getMsg());
            }else if (communicationModel.getType().equalsIgnoreCase("notes")){
                holder.tvHeading.setText("notes");
                holder.ivIcon.setImageResource(R.drawable.ic_calendar);
                holder.tvSubHeading.setText("");
            }



            holder.frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });


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

            // Use ViewBindHelper to restore and save the open/close state of the SwipeRevealView
            // put an unique string id as value, can be any string which uniquely define the data

//            binderHelper.bind(holder.swipeLayout, String.valueOf(pos));
            // Bind your data here
//            holder.bind();
        }

        binderHelper.bind(holder.swipeLayout, String.valueOf(pos));
        holder.bind();
    }

    @Override
    public int getItemCount() {
        if (modelList == null) {
            return 0;
        } else {
            return modelList.size();
        }
    }

    public void setData(List<CommunicationModel.Data> dataList) {
        this.modelList = dataList;
        notifyDataSetChanged();
    }

    public void saveStates(Bundle outState) {
        binderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        binderHelper.restoreStates(inState);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private SwipeRevealLayout swipeLayout;
        TextView tvDate, tvHeading, tvTime, tvSubHeading;
        ImageView ivIcon;
        FrameLayout frameLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            swipeLayout = (SwipeRevealLayout) itemView.findViewById(R.id.swipe_layout);
            swipeLayout.setLockDrag(true);
            frameLayout = itemView.findViewById(R.id.frameLayout);


            tvTime = itemView.findViewById(R.id.tvTime);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvHeading = itemView.findViewById(R.id.tvMessage);
            tvTime = itemView.findViewById(R.id.tvTime);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvSubHeading = itemView.findViewById(R.id.tvDuration);
        }

        public void bind() {

/*
            lnEdit.setOnClickListener(v -> {

                isEditable = true;
                pos = getAdapterPosition();
                Intent intent = new Intent(context, AddNotesActivity.class);
                intent.putExtra("Note", mData.get(pos));
                context.startActivity(intent);

            });

            lnDelete.setOnClickListener(v -> {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Confirmation");
                alertDialog.setMessage("Are you sure you want to delete this Note ?");
                alertDialog.setPositiveButton(android.R.string.yes, (dialog, which) -> {

                    pos = getAdapterPosition();
                    String noteID = mData.get(pos).encryptedNoteID;
                    callDeleteNote(noteID, swipeLayout);
                    dialog.dismiss();

                });
                alertDialog.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });

                alertDialog.show();
            });*/


        }
    }
/*
    private void callDeleteNote(String encryptedNoteID, SwipeRevealLayout swipeLayout) {

        GH.getInstance().ShowProgressDialog(activity);
        Call<BaseResponse> _callToday;
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).DeleteNote(GH.getInstance().getAuthorization(),
                encryptedNoteID);
        _callToday.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                GH.getInstance().HideProgressDialog();
                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (getAppointmentsModel.getStatus().equals("Success")) {

                        ToastUtils.showToast(context, "Successfully Deleted");
                        mData.remove(pos);
                        swipeLayout.close(true);
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

    private void callStickyNote(String encryptedNoteID, String encryptedLeadid, Boolean isSticky) {
        GH.getInstance().ShowProgressDialog(activity);
        Call<BaseResponse> _callToday;
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).Make_Lead_Note_Sticky(GH.getInstance().getAuthorization(),
                encryptedNoteID, encryptedLeadid, isSticky);
        _callToday.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                GH.getInstance().HideProgressDialog();
                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (getAppointmentsModel.getStatus().equals("Success")) {

                        ToastUtils.showToast(context, "Successfully Done");
                        ((NotesActivity) context).refreshData();

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
    }*/



}
