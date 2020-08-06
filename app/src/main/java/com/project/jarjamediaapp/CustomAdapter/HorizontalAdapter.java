package com.project.jarjamediaapp.CustomAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.jarjamediaapp.Activities.all_leads.AllLeadsActivity;
import com.project.jarjamediaapp.Activities.open_houses.AddOpenHousesActivity;
import com.project.jarjamediaapp.Activities.open_houses.GetAllOpenHousesModel;
import com.project.jarjamediaapp.Activities.open_houses.GetTimeFrameModel;
import com.project.jarjamediaapp.Activities.open_houses.OpenHousesActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.Methods;
import com.project.jarjamediaapp.Utilities.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder> {

    Context context;
    private List<GetAllOpenHousesModel.Data.OpenHouse> data;
    String openHouseType = "";
    Dialog dialog;
    String firstName = "", lastName = "", preQual = "", withAgent = "", timeFrame = "",
            email = "", phone = "", houseSell = "", priceRange = "";
    AutoCompleteTextView atvFirstName, atvLastName, atvPhoneNumber, atvEmail,
            atvPlan, atvApprove, atvSell, atvPay, atvDeal;
    Button btnSave, btnCancel;
    ArrayList<String> arrayListPlanId, arrayListPlanName, arrayListPayId, arrayListPayName, arrayList;
    ArrayAdapter<String> arrayAdapterPlanName, arrayAdapterPayName, arrayAdapter;
    Call<GetTimeFrameModel> _call;
    boolean _preQual = false, _withAgent = false, _houseSell = false;
    int pos;
    boolean mFormatting; // this is a flag which prevents the  stack overflow.
    int mAfter;

    public HorizontalAdapter(Context context, List<GetAllOpenHousesModel.Data.OpenHouse> data, String openHouseType) {

        this.context = context;
        this.data =  data;
       /* // reverse arraylist
        Collections.reverse(this.data);*/
        this.openHouseType = openHouseType;

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
        pos = position;
        // Data binding
        try {

            String startDateTime = data.get(position).getOpenHouseDate();
            String endDateTime = data.get(position).getOpenHouseEndDate();

            holder.tvStartDateTime.setText(GH.getInstance().formatter(startDateTime, "MM-dd-yyyy hh:mm a", "yyyy-MM-dd'T'HH:mm:ss"));
            holder.tvEndDateTime.setText(GH.getInstance().formatter(endDateTime, "MM-dd-yyyy hh:mm a", "yyyy-MM-dd'T'HH:mm:ss"));

            holder.tvAddress.setText(data.get(position).getStreetName());
            holder.tvCityPostal.setText(data.get(position).getCity()+" , "+data.get(position).getState());
            holder.tvLeadsCount.setText(data.get(position).getLeadCount() != 0 ? "Leads " + data.get(position).getLeadCount() : "Leads 0");
            String image = data.get(position).getImgURL();
            if (!image.equalsIgnoreCase("")) {
                Glide.with(context).load(image).into(holder.ivHouse);
            } else {
                holder.ivHouse.setImageResource(R.drawable.open_house);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rl;
        private CardView cardView;
        private ImageView ivHouse;
        private TextView tvStartDateTime, tvEndDateTime, tvAddress, tvCityPostal, tvLeadsCount;
        private Button btnEdit;

        public ViewHolder(View itemView) {
            super(itemView);

            // UI Binding
            rl = itemView.findViewById(R.id.rl);
            cardView = itemView.findViewById(R.id.cardView);
            ivHouse = (ImageView) itemView.findViewById(R.id.ivHouse);
            tvStartDateTime = itemView.findViewById(R.id.tvStartDateTime);
            tvEndDateTime = itemView.findViewById(R.id.tvEndDateTime);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvCityPostal = itemView.findViewById(R.id.tvCityPostal);
            tvLeadsCount = itemView.findViewById(R.id.tvLeadsCount);
            btnEdit = itemView.findViewById(R.id.btnEdit);

            btnEdit.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {


                    if (openHouseType.equals("upcoming")) {
                        GetAllOpenHousesModel.Data.OpenHouse openHouse = data.get(getAdapterPosition());

                        // passing open house object to edit screen.
                        Intent i = new Intent(context,AddOpenHousesActivity.class);
                        i.putExtra("editLeadsObj",openHouse);

                        context.startActivity(i);
                    } else if (openHouseType.equals("past")) {

                        GetAllOpenHousesModel.Data.OpenHouse openHouse = data.get(getAdapterPosition());

                        // passing open house object to edit screen.
                        Intent i = new Intent(context,AddOpenHousesActivity.class);
                        i.putExtra("editLeadsObj",openHouse);

                        context.startActivity(i);
                    }

                }
            });

            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (openHouseType.equals("upcoming")) {
                        GetAllOpenHousesModel.Data.OpenHouse openHouse = data.get(getAdapterPosition());
                        showAddLeadDialog(context, String.valueOf(openHouse.propertyId), getAdapterPosition());
                    } else if (openHouseType.equals("past")) {

                        if (data.get(getAdapterPosition()).getLeadCount() != 0) {
                            GetAllOpenHousesModel.Data.OpenHouse openHouse = data.get(getAdapterPosition());
                            Intent intent = new Intent(context, AllLeadsActivity.class);
                            intent.putExtra("type", 1);
                            intent.putExtra("propertyID", String.valueOf(openHouse.propertyId));
                            context.startActivity(intent);
                        } else {
                            ToastUtils.showToast(context, "No Leads Founds");
                        }
                    }
                }
            });

            ivHouse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (openHouseType.equals("upcoming")) {
                        GetAllOpenHousesModel.Data.OpenHouse openHouse = data.get(getAdapterPosition());
                        showAddLeadDialog(context, String.valueOf(openHouse.propertyId), pos);
                    } else if (openHouseType.equals("past")) {

                        if (data.get(getAdapterPosition()).getLeadCount() != 0) {
                            GetAllOpenHousesModel.Data.OpenHouse openHouse = data.get(getAdapterPosition());
                            Intent intent = new Intent(context, AllLeadsActivity.class);
                            intent.putExtra("type", 1);
                            intent.putExtra("propertyID", String.valueOf(openHouse.propertyId));
                            context.startActivity(intent);
                        } else {
                            ToastUtils.showToast(context, "No Leads Founds");
                        }
                    }
                }
            });


        }

    }

    public void showAddLeadDialog(Context context, String propertyId, int pos) {

        dialog = new Dialog(context, R.style.Dialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_open_house_add_lead_dialog);

        atvFirstName = dialog.findViewById(R.id.atvFirstName);
        atvLastName = dialog.findViewById(R.id.atvLastName);
        atvPhoneNumber = dialog.findViewById(R.id.atvPhoneNumber);
        atvEmail = dialog.findViewById(R.id.atvEmail);

        atvPlan = dialog.findViewById(R.id.tvPlan);
        atvApprove = dialog.findViewById(R.id.tvApprove);
        atvSell = dialog.findViewById(R.id.tvSell);
        atvPay = dialog.findViewById(R.id.tvPay);
        atvDeal = dialog.findViewById(R.id.tvDeal);

        btnSave = dialog.findViewById(R.id.btnSave);
        btnCancel = dialog.findViewById(R.id.btnCancel);

        arrayList = new ArrayList<>();
        arrayList.add("Nothing Selected");
        arrayList.add("Yes");
        arrayList.add("No");
        arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, arrayList);

        atvPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mAfter = after;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mFormatting) {
                    mFormatting = true;
                    // using US or RU formatting...
                    if (mAfter != 0) // in case back space ain't clicked...
                    {
                        String num = s.toString();
                        String data = PhoneNumberUtils.formatNumber(num, "US");
                        if (data != null) {
                            s.clear();
                            s.append(data);
                            Log.i("Number", data);//8 (999) 123-45-67 or +7 999 123-45-67
                        }

                    }
                    mFormatting = false;
                }
            }
        });

        atvPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GH.getInstance().hideKeyboard(context, (Activity) context);
                atvPlan.setAdapter(arrayAdapterPlanName);
                atvPlan.showDropDown();
            }
        });
        atvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GH.getInstance().hideKeyboard(context, (Activity) context);
                atvPay.setAdapter(arrayAdapterPayName);
                atvPay.showDropDown();
            }
        });
        atvSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GH.getInstance().hideKeyboard(context, (Activity) context);
                atvSell.setAdapter(arrayAdapter);
                atvSell.showDropDown();
            }
        });
        atvApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GH.getInstance().hideKeyboard(context, (Activity) context);
                atvApprove.setAdapter(arrayAdapter);
                atvApprove.showDropDown();
            }
        });
        atvDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GH.getInstance().hideKeyboard(context, (Activity) context);
                atvDeal.setAdapter(arrayAdapter);
                atvDeal.showDropDown();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // dismiss dialogue on api integration
                firstName = atvFirstName.getText().toString();
                lastName = atvLastName.getText().toString();
                email = atvEmail.getText().toString();
                phone = atvPhoneNumber.getText().toString();
                timeFrame = atvPlan.getText().toString();
                priceRange = atvPay.getText().toString();

                houseSell = atvSell.getText().toString();
                preQual = atvApprove.getText().toString();
                withAgent = atvDeal.getText().toString();

                if (priceRange.equalsIgnoreCase("")) {
                    priceRange = "Nothing Selected";
                }
                if (timeFrame.equalsIgnoreCase("")) {
                    timeFrame = "Nothing Selected";
                }
                if (houseSell.equalsIgnoreCase("Yes")) {
                    _houseSell = true;
                } else {
                    _houseSell = false;
                }
                if (preQual.equalsIgnoreCase("Yes")) {
                    _preQual = true;
                } else {
                    _preQual = false;
                }
                if (withAgent.equalsIgnoreCase("Yes")) {
                    _withAgent = true;
                } else {
                    _withAgent = false;
                }
                JSONObject obj = new JSONObject();
                try {
                    obj.put("propertyId", Integer.valueOf(propertyId));
                    obj.put("firstName", firstName);
                    obj.put("priceRange", priceRange);
                    obj.put("houseSell", _houseSell);
                    obj.put("timeFrame", timeFrame);
                    obj.put("lastName", lastName);
                    obj.put("withAgent", _withAgent);
                    obj.put("primaryEmail", email);
                    obj.put("preQual", _preQual);
                    obj.put("primaryPhone", phone);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("json", obj.toString());
                if (isValidate()) {
                    sendDataToServer(Integer.valueOf(propertyId), pos);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ((OpenHousesActivity) context).hitApiForRefresh(pos);
            }
        });

        getTimeFrame();

    }

    private void getTimeFrame() {

        GH.getInstance().ShowProgressDialog((Activity) context);
        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getTimeFrame(GH.getInstance().getAuthorization());
        _call.enqueue(new Callback<GetTimeFrameModel>() {
            @Override
            public void onResponse(Call<GetTimeFrameModel> call, Response<GetTimeFrameModel> response) {

                if (response.isSuccessful()) {
                    GetTimeFrameModel getTimeFrameModel = response.body();
                    if (getTimeFrameModel.getStatus().equals("Success")) {
                        arrayListPlanName = new ArrayList<>();
                        if (getTimeFrameModel != null) {
                            for (int i = 0; i < getTimeFrameModel.data.size(); i++) {
                                arrayListPlanName.add(getTimeFrameModel.getData().get(i).getTimeFrame());
                            }
                            arrayAdapterPlanName = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, arrayListPlanName);
                            getAmount();
                        }

                    } else {
                        GH.getInstance().HideProgressDialog();
                        ToastUtils.showToast(context, getTimeFrameModel.message);
                    }
                } else {
                    GH.getInstance().HideProgressDialog();
                    ApiError error = ErrorUtils.parseError(response);
                    ToastUtils.showToast(context, error.message());
                }
            }

            @Override
            public void onFailure(Call<GetTimeFrameModel> call, Throwable t) {
                GH.getInstance().HideProgressDialog();
                ToastUtils.showToastLong(context, context.getString(R.string.retrofit_failure));
            }
        });
    }

    private void getAmount() {

        _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getAmountRange(GH.getInstance().getAuthorization());
        _call.enqueue(new Callback<GetTimeFrameModel>() {
            @Override
            public void onResponse(Call<GetTimeFrameModel> call, Response<GetTimeFrameModel> response) {
                GH.getInstance().HideProgressDialog();
                if (response.isSuccessful()) {

                    GetTimeFrameModel getTimeFrameModel = response.body();
                    if (getTimeFrameModel.getStatus().equals("Success")) {
                        dialog.show();
                        arrayListPayName = new ArrayList<>();
                        if (getTimeFrameModel != null) {
                            for (int i = 0; i < getTimeFrameModel.data.size(); i++) {
                                arrayListPayName.add(getTimeFrameModel.getData().get(i).getValue());
                            }
                            arrayAdapterPayName = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, arrayListPayName);
                        }
                    } else {
                        ToastUtils.showToast(context, getTimeFrameModel.message);
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    ToastUtils.showToast(context, error.message());
                }
            }

            @Override
            public void onFailure(Call<GetTimeFrameModel> call, Throwable t) {
                GH.getInstance().HideProgressDialog();
                ToastUtils.showToastLong(context, context.getString(R.string.retrofit_failure));
            }
        });

    }

    private void sendDataToServer(int propertyId, int pos) {

        GH.getInstance().ShowProgressDialog((Activity) context);
        Call<BaseResponse> _callToday;
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).addLeadViaOpenHouse(GH.getInstance().getAuthorization(),
                propertyId, firstName, lastName, email, phone, _withAgent, timeFrame, _houseSell, _preQual, priceRange);
        _callToday.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                GH.getInstance().HideProgressDialog();
                if (response.isSuccessful()) {

                    BaseResponse baseResponse = response.body();
                    if (baseResponse.getStatus().equals("Success")) {
                        // clear data
                        clearData();
                        ToastUtils.showToast(context, baseResponse.message);
                    } else {
                        ToastUtils.showToast(context, baseResponse.message);
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

    private boolean isValidate() {

        if (Methods.isEmpty(atvFirstName) && Methods.isEmpty(atvLastName)) {
            ToastUtils.showToast(context, "Please enter name");
            atvFirstName.requestFocus();
            return false;
        }
        if (Methods.isEmpty(atvEmail)) {
            ToastUtils.showToast(context, "Please enter email");
            atvEmail.requestFocus();
            return false;
        }
        if (Methods.isInValidEmail(atvEmail)) {
            ToastUtils.showToast(context, "Please enter valid email");
            atvEmail.requestFocus();
            return false;
        }
        if (Methods.isEmpty(atvPhoneNumber)) {
            ToastUtils.showToast(context, "Please enter phone number");
            atvPhoneNumber.requestFocus();
            return false;
        }
        if (!atvPhoneNumber.getText().toString().equalsIgnoreCase("") &&
                atvPhoneNumber.getText().toString().length() < 14) {
            ToastUtils.showToast(context, "Please enter valid phone number");
            atvPhoneNumber.requestFocus();
            return false;
        }
        /*if (Methods.isEmpty(atvOpenHouseEndDate)) {
            ToastUtils.showToast(context, R.string.error_end_time);
            atvOpenHouseEndDate.requestFocus();
            return false;
        }*/

        return true;
    }

    private void clearData(){

        atvFirstName.setText("");
        atvLastName.setText("");
        atvPhoneNumber.setText("");
        atvEmail.setText("");
        atvPlan.setText("");
        atvApprove.setText("");
        atvSell.setText("");
        atvPay.setText("");
        atvDeal.setText("");

    }

}

