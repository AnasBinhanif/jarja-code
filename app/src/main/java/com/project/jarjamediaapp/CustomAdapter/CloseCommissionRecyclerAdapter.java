package com.project.jarjamediaapp.CustomAdapter;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.project.jarjamediaapp.Activities.transactions.TransactionModel;
import com.project.jarjamediaapp.Interfaces.CloseAdapterClickListener;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.DecimalDigitsInputFilter;
import com.project.jarjamediaapp.Utilities.GH;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Akshay Kumar on 11/27/2020.
 */
public class CloseCommissionRecyclerAdapter extends RecyclerView.Adapter<CloseCommissionRecyclerAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private Context context;
    private List<TransactionModel.Data.Agent> dataList;
    private CloseAdapterClickListener closeAdapterClickListener;
    int position = 0;
    TextWatcher textWatcher;

    public CloseCommissionRecyclerAdapter(Context context, List<TransactionModel.Data.Agent> dataList) {
        this.context = context;
        this.dataList = dataList;
        mInflater = LayoutInflater.from(this.context);
        closeAdapterClickListener = (CloseAdapterClickListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_adp_agent_commission, parent, false);
        return new CloseCommissionRecyclerAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.position = position;
        TransactionModel.Data.Agent data = dataList.get(position);

        holder.tvAgentName.setText(data.getAgentName() != null ? data.getAgentName() + "'s Commission" : "");


        holder.atvAgentCommission.setText(data.getCommission() + "");

        //creating input filter to limit the lenght and decimal point upto 2
        InputFilter[] filterArray = new InputFilter[]{new DecimalDigitsInputFilter(10, 2), new InputFilter.LengthFilter(10)};

        holder.atvAgentCommission.setFilters(filterArray);

       /* holder.atvAgentCommission.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    TransactionModel.Data data = dataList.get(position);
                    data.setCommission(Double.valueOf(holder.atvAgentCommission.getText().toString()));
                    dataList.set(position, data);
                }
            }
        });*/

        setTextWatcher(position, holder.atvAgentCommission);

        holder.tvCommisionDate.setText(GH.getInstance().formatter(data.getCommisionDate(), "MM/dd/yyyy", "yyyy-MM-dd'T'HH:mm:ss"));
        holder.tvCommisionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeAdapterClickListener.clickOnDate(position, holder.atvAgentCommission);

            }
        });
    }

    private void setTextWatcher(int position, AutoCompleteTextView autoCompleteTextView) {
        if (textWatcher != null) {
            autoCompleteTextView.removeTextChangedListener(textWatcher);
        }
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    TransactionModel.Data.Agent data = dataList.get(position);
                    data.setCommission(Double.valueOf(autoCompleteTextView.getText().toString()));
                    dataList.set(position, data);
                }
            }
        };
        autoCompleteTextView.addTextChangedListener(textWatcher);
    }


    public void setDataList(List<TransactionModel.Data.Agent> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAgentName;
        AutoCompleteTextView atvAgentCommission;
        TextView tvCommisionDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAgentName = itemView.findViewById(R.id.tvAgentName);
            atvAgentCommission = itemView.findViewById(R.id.atvAgentCommission);
            tvCommisionDate = itemView.findViewById(R.id.atvCommissionDate);
        }
    }
}
