package com.project.jarjamediaapp.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.jarjamediaapp.Activities.open_houses.GetAllOpenHousesModel;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;

import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder> {

    Context context;
    private List<GetAllOpenHousesModel.Data.OpenHouse> data;


    public HorizontalAdapter(Context context, List<GetAllOpenHousesModel.Data.OpenHouse> data) {

        this.context = context;
        this.data = data;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.custom_open_house_layout, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // Data binding
        try {

            Glide.with(context).load(data.get(position).getImgURL()).into(holder.ivHouse);
            holder.tvStartDateTime.setText(GH.getInstance().formatDate(data.get(position).getOpenHouseDate()));
            holder.tvEndDateTime.setText(GH.getInstance().formatDate(data.get(position).getOpenHouseEndDate()));
            holder.tvAddress.setText(data.get(position).getStreetName() + " , " + data.get(position).getState());
            holder.tvCityPostal.setText(data.get(position).getCity());
            holder.tvLeadsCount.setText(data.get(position).getLeadCount());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private ImageView ivHouse;
        private TextView tvStartDateTime, tvEndDateTime, tvAddress, tvCityPostal, tvLeadsCount;

        public ViewHolder(View itemView) {
            super(itemView);

            // UI Binding
            cardView = itemView.findViewById(R.id.cardView);
            ivHouse = (ImageView) itemView.findViewById(R.id.ivHouse);
            tvStartDateTime = itemView.findViewById(R.id.tvStartDateTime);
            tvEndDateTime = itemView.findViewById(R.id.tvEndDateTime);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvCityPostal = itemView.findViewById(R.id.tvCityPostal);
            tvLeadsCount = itemView.findViewById(R.id.tvLeadsCount);

        }

    }
}

