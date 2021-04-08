package com.project.jarjamediaapp.CustomAdapter;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebBackForwardList;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.project.jarjamediaapp.Models.CommunicationModel;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;

import java.util.List;

public class CommunicationAdapter extends RecyclerView.Adapter<CommunicationAdapter.ViewHolder> {

    Context context;
    LayoutInflater layoutInflater;
    List<CommunicationModel.Data> modelList;
    ViewBinderHelper viewBinderHelper;
    Dialog dialog;

    public CommunicationAdapter(Context context, List<CommunicationModel.Data> modelList) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.modelList = modelList;
        viewBinderHelper = new ViewBinderHelper();

    }

    @NonNull
    @Override
    public CommunicationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_communication_rv2, parent, false);
        return new CommunicationAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CommunicationAdapter.ViewHolder holder, int position) {
        int post = position;

        CommunicationModel.Data communicationModel = modelList.get(post);

        if (!TextUtils.isEmpty(communicationModel.getDate())) {
            String date = GH.getInstance().formatter(modelList.get(post).getDate(), "MM/dd/yyyy", "yyyy-MM-dd'T'hh:mm:ss");
            holder.tvDate.setText(date);
//            holder.tvDate.setText(String.valueOf(post));
            String time = GH.getInstance().formatter(modelList.get(post).getDate(), "hh:mm a", "yyyy-MM-dd'T'hh:mm:ss");
            holder.tvTime.setText(time);
        }

        viewBinderHelper.bind(holder.swipeLayout, String.valueOf(post));
        viewBinderHelper.setOpenOnlyOne(true);

        viewBinderHelper.lockSwipe(String.valueOf(post));

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

        if (communicationModel.getType().equalsIgnoreCase("Sent") || communicationModel.getType().equalsIgnoreCase("imbox")) {

            viewBinderHelper.unlockSwipe(String.valueOf(post));

            holder.ivIcon.setImageResource(R.drawable.ic_envelope_solid);
            holder.tvMessage.setText(communicationModel.getHtml());
            holder.tvSubMessage.setText(communicationModel.getSubject());

        } else if (communicationModel.getType().equalsIgnoreCase("Text")) {
            holder.ivIcon.setImageResource(R.drawable.ic_mobile_solid);
            holder.tvMessage.setText(communicationModel.getHtml());
            holder.tvSubMessage.setText(communicationModel.getMsg());

        } else if (communicationModel.getType().equalsIgnoreCase("Call")) {

            holder.ivIcon.setImageResource(R.drawable.ic_phone_square_alt_solid);
            holder.tvMessage.setText("Call to " + communicationModel.getSentAt());
            if (!TextUtils.isEmpty(communicationModel.getCallDuration())) {
                holder.tvSubMessage.setText("Call duration: " + communicationModel.getCallDuration() + " minute(s)");
            } else {
                holder.tvSubMessage.setText(communicationModel.getMsg());
            }
        } else if (communicationModel.getType().equalsIgnoreCase("SocialMedia")) {
            holder.ivIcon.setImageResource(R.drawable.ic_comment_dots_solid);
            holder.tvMessage.setText("Social Media");
            holder.tvSubMessage.setText(communicationModel.getMsg());
        } else if (communicationModel.getType().equalsIgnoreCase("Inperson")) {
            holder.ivIcon.setImageResource(R.drawable.ic_users_solid);
            holder.tvMessage.setText("In-person");
            holder.tvSubMessage.setText(communicationModel.getMsg());
        } else if (communicationModel.getType().equalsIgnoreCase("Others")) {
            holder.ivIcon.setImageResource(R.drawable.ic_lightbulb_regular);
            holder.tvMessage.setText("Other");
            holder.tvSubMessage.setText(communicationModel.getMsg());
        } else if (communicationModel.getType().equalsIgnoreCase("log")) {
            holder.tvMessage.setText("Log");
            holder.ivIcon.setImageResource(R.drawable.ic_calendar);
            holder.tvSubMessage.setText("");
        }

        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (communicationModel.getType().equalsIgnoreCase("Sent") || communicationModel.getType().equalsIgnoreCase("imbox")) {
                    showEmailDialog(communicationModel.getEmailSendFrom(), communicationModel.getEmailSendTo(),
                            communicationModel.getEmailSubject(), communicationModel.getEmailBody());

                }
            }
        });

        holder.lnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showEmailDialog(communicationModel.getEmailSendFrom(), communicationModel.getEmailSendTo(),
                        communicationModel.getEmailSubject(), communicationModel.getEmailBody());
            }
        });


    }

    @Override
    public int getItemCount() {
        if (modelList != null) {
            return modelList.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setData(List<CommunicationModel.Data> dataList) {

        this.modelList = dataList;
        notifyDataSetChanged();
    }

    private void showEmailDialog(String emailSendFrom, String emailSendTo, String emailSubject, String emailBody) {

        if (dialog == null || !dialog.isShowing()) {
            dialog = new Dialog(context, R.style.Dialog);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_show_email2);

            AutoCompleteTextView atvFrom = dialog.findViewById(R.id.atvFrom);
            atvFrom.setText(emailSendFrom);
            TextView tvTo = dialog.findViewById(R.id.tvToAgent);
            tvTo.setText(emailSendTo);
            ImageView ivClose = dialog.findViewById(R.id.ivClose);
            AutoCompleteTextView atvCC = dialog.findViewById(R.id.atvCC);
            AutoCompleteTextView atvBCC = dialog.findViewById(R.id.atvBCC);
            AutoCompleteTextView atvSubject = dialog.findViewById(R.id.atvSubject);
            atvSubject.setText(emailSubject);

            MultiAutoCompleteTextView mAtvBody = dialog.findViewById(R.id.atvBody);
//            mAtvBody.setText(emailBody);
//            mAtvBody.setText(Html.fromHtml(emailBody));
//            lnAgent = dialog.findViewById(R.id.lnAgent);

            WebView webView = dialog.findViewById(R.id.webview);
            final String mimeType = "text/html";
            final String encoding = "UTF-8";
//            webView.getSettings().setLoadWithOverviewMode(true);
//            webView.getSettings().setUseWideViewPort(true);
//            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
            if (TextUtils.isEmpty(emailBody)){
                emailBody = "";
            }
            Log.d( "showEmailDialog: ","<style>img{display: inline;height: auto;max-width: 100%;}</style>" + emailBody);
            webView.loadDataWithBaseURL("", "<style>img{display: inline;height: auto;max-width: 100%;}</style>" + emailBody, mimeType, encoding, "");

            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });

            dialog.show();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate, tvMessage, tvTime, tvSubMessage, tvView;
        ImageView ivIcon;
        SwipeRevealLayout swipeLayout;
        FrameLayout frameLayout;
        LinearLayout lnView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvTime = itemView.findViewById(R.id.tvTime);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvSubMessage = itemView.findViewById(R.id.tvDuration);

            swipeLayout = itemView.findViewById(R.id.swipe_layout);
            swipeLayout.setLockDrag(true);
            frameLayout = itemView.findViewById(R.id.frameLayout);

            tvView = itemView.findViewById(R.id.tvView);
            lnView = itemView.findViewById(R.id.lnView);
        }

    }
}
