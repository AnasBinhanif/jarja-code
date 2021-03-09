package com.project.jarjamediaapp.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.jarjamediaapp.Models.CommunicationModel;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;

import java.util.List;

public class CommunicationAdapter extends RecyclerView.Adapter<CommunicationAdapter.ViewHolder> {

    Context context;
    LayoutInflater layoutInflater;
    List<CommunicationModel.Data> modelList;

    public CommunicationAdapter(Context context, List<CommunicationModel.Data> modelList) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public CommunicationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_communication_rv, parent, false);
        return new CommunicationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunicationAdapter.ViewHolder holder, int position) {

        String date = GH.getInstance().formatter(modelList.get(position).getDate(),"dd/MM/yyyy","yyyy-MM-dd'T'hh:mm:ss.sss");
        holder.tvDate.setText(date);
        String time = GH.getInstance().formatter(modelList.get(position).getDate(),"hh:mm a","yyyy-MM-dd'T'hh:mm:ss.sss");
        holder.tvTime.setText(time);

        holder.tvMessage.setText(modelList.get(position).getHtml());



    }

    @Override
    public int getItemCount() {
        if (modelList != null) {
            return modelList.size();
        }
        return 0;
    }

    public void setData(List<CommunicationModel.Data> dataList){
        this.modelList = dataList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate, tvMessage,tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate) ;
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvTime = itemView.findViewById(R.id.tvTime);
        }

    }
}
