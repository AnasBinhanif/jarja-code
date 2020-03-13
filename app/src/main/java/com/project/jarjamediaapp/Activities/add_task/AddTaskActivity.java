package com.project.jarjamediaapp.Activities.add_task;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentModel;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetAgentsModel;
import com.project.jarjamediaapp.Models.GetLeadTitlesModel;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityAddTaskBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTaskActivity extends BaseActivity implements AddTaskContract.View, View.OnClickListener {

    ActivityAddTaskBinding bi;
    Context context = AddTaskActivity.this;
    AddTaskPresenter presenter;

    RecyclerAdapterUtil recyclerAdapterUtil;
    RecyclerView recyclerSearch;

    ArrayList<String> arrayListViaText = new ArrayList<>();
    ArrayList<String> arrayListViaValue = new ArrayList<>();
    ArrayList<String> arrayListReminderText = new ArrayList<>();
    ArrayList<String> arrayListReminderValue = new ArrayList<>();

    ArrayList<GetAgentsModel.Data> agentList;
    ArrayList<GetLeadTitlesModel.Data> nameList;
    ArrayList<Integer> selectedIdsList = new ArrayList<>();
    ArrayList<MultiSelectModel> searchListItems;
    String startDate = "", endDate = "", reminder = "0", via = "", leadId = "", type = "", reoccur = "", agentId = "";
    String agentIdsString = "", leadName = "", taskId = "";
    MultiSelectModel agentModel;
    boolean isEdit;
    boolean isReminderClicked = false, isViaClicked = false, isTypeClicked = false, isRecurClicked = false;
    int month, year, day;
    Calendar newCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_add_task);
        presenter = new AddTaskPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.add_task), true);

    }


    @Override
    public void initViews() {

        presenter.getAgentNames();
        presenter.getReminder();
        presenter.getVia();
        presenter.getType();
        presenter.getRecur();

        bi.btnSave.setOnClickListener(this);
        bi.btnCancel.setOnClickListener(this);
        bi.tvName.setOnClickListener(this);
        bi.tvAssignTo.setOnClickListener(this);
        bi.tvStartDate.setOnClickListener(this);
        bi.tvEndDate.setOnClickListener(this);
        bi.atvType.setOnClickListener(this);
        bi.atvRecur.setOnClickListener(this);
        bi.atvReminder.setOnClickListener(this);
        bi.atvVia.setOnClickListener(this);

        bi.cbEndDate.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b) {
                endDate = "";
                bi.tvEndDate.setText("");
                bi.tvEndDate.setVisibility(View.GONE);
            } else {
                bi.tvEndDate.setVisibility(View.VISIBLE);
            }

        });

        // 1 from Add Task by Lead Id
        // 2 from Update Task Lead Id
        // 3 from Update Task
        // 4 from Add Task
        String id = getIntent().getStringExtra("from");
        switch (id) {
            case "1": {
                leadId = getIntent().getStringExtra("leadID");
                leadName = getIntent().getStringExtra("leadName");
                bi.tvName.setText(leadName);
                bi.tvName.setEnabled(false);
            }
            break;
            case "2": {
                leadId = getIntent().getStringExtra("leadID");
                leadName = getIntent().getStringExtra("leadName");
                taskId = getIntent().getStringExtra("taskId");
                bi.tvName.setText(leadName);
                bi.tvName.setEnabled(false);
                isEdit = true;
                // hit api for task detail
                presenter.getTaskDetail(taskId);

            }
            break;
            case "3": {
                bi.tvName.setEnabled(true);
                isEdit = true;
                leadId = "";
                taskId = getIntent().getStringExtra("taskId");
                presenter.getTaskDetail(taskId);
                // hit api for task detail
            }
            break;
            case "4": {

                bi.tvName.setEnabled(true);
                leadId = "";
                isEdit = true;

            }
            break;
        }

        newCalendar = Calendar.getInstance();
        year = newCalendar.get(Calendar.YEAR);
        month = newCalendar.get(Calendar.MONTH);
        day = newCalendar.get(Calendar.DAY_OF_MONTH);

    }

    @Override
    public void updateUI(GetAgentsModel response) {

        agentList = new ArrayList<>();
        searchListItems = new ArrayList<>();
        agentList = response.data;
        for (GetAgentsModel.Data model : agentList) {
            searchListItems.add(new MultiSelectModel(model.agentID, model.agentName, model.encryptedAgentID));
        }

    }

    @Override
    public void updateUIListForReminders(AddAppointmentModel response) {

        arrayListReminderText = new ArrayList<>();
        arrayListReminderValue = new ArrayList<>();

        for (int i = 0; i < response.getData().size(); i++) {

            arrayListReminderText.add(response.getData().get(i).getText());
            arrayListReminderValue.add(response.getData().get(i).getValue());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, arrayListReminderText);
        bi.atvReminder.setAdapter(arrayAdapter);

        bi.atvReminder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.getVia();
                reminder = arrayListReminderValue.get(position);
                if (bi.atvReminder.getText().toString().contains("None")) {
                    bi.atvVia.setVisibility(View.GONE);
                    bi.lblVia.setVisibility(View.GONE);
                } else {
                    bi.atvVia.setVisibility(View.VISIBLE);
                    bi.lblVia.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public void updateUIListForVia(AddAppointmentModel response) {

        arrayListViaText = new ArrayList<>();
        arrayListViaValue = new ArrayList<>();

        for (int i = 0; i < response.getData().size(); i++) {

            arrayListViaText.add(response.getData().get(i).getText());
            arrayListViaValue.add(response.getData().get(i).getValue());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, arrayListViaText);
        bi.atvVia.setAdapter(arrayAdapter);

        bi.atvVia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                via = arrayListViaValue.get(position);
            }
        });

    }

    @Override
    public void updateUIListForType(AddAppointmentModel response) {

        ArrayList<String> arrayListViaText = new ArrayList<>();
        ArrayList<String> arrayListViaValue = new ArrayList<>();

        for (int i = 0; i < response.getData().size(); i++) {

            arrayListViaText.add(response.getData().get(i).getText());
            arrayListViaValue.add(response.getData().get(i).getValue());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, arrayListViaText);
        bi.atvType.setAdapter(arrayAdapter);

        bi.atvType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                type = arrayListViaValue.get(position);
            }
        });



    }

    @Override
    public void updateUIListForReoccur(AddAppointmentModel response) {

        ArrayList<String> arrayListViaText = new ArrayList<>();
        ArrayList<String> arrayListViaValue = new ArrayList<>();

        for (int i = 0; i < response.getData().size(); i++) {

            arrayListViaText.add(response.getData().get(i).getId());
            arrayListViaValue.add(response.getData().get(i).getType());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, arrayListViaValue);
        bi.atvRecur.setAdapter(arrayAdapter);

        bi.atvRecur.setOnItemClickListener((parent, view, position, id) -> {

            reoccur = arrayListViaText.get(position);
            if (position != 0) {
                bi.lnEndDate.setVisibility(View.VISIBLE);
            } else {
                bi.lnEndDate.setVisibility(View.GONE);
            }

        });
    }

    @Override
    public void updateTaskDetail(GetTaskDetail response) {

        // data bind
        GetTaskDetail ft = response;
        retrieveData(ft);

    }

    private void retrieveData(GetTaskDetail taskDetail) {

        bi.atvNameTask.setText(taskDetail.data.name);
        bi.atvDescription.setText(taskDetail.data.description);
        bi.tvName.setText(taskDetail.data.firstName);
        bi.atvType.setText(taskDetail.data.type);
        bi.atvType.setText(taskDetail.data.type, false);
        bi.atvRecur.setText(taskDetail.data.recur, false);
        bi.atvReminder.setText(arrayListReminderText.get(arrayListReminderValue.indexOf(String.valueOf(taskDetail.data.interval))), false);
        bi.atvVia.setText(arrayListViaText.get(arrayListViaValue.indexOf(taskDetail.data.viaReminder)), false);
        String startDate = GH.getInstance().formatter(taskDetail.data.startDate, "MM-dd-yyyy", "dd/MM/yyyy hh:mm:ss a");
        String endDate = GH.getInstance().formatter(taskDetail.data.endDate, "MM-dd-yyyy", "dd/MM/yyyy hh:mm:ss a");
        bi.tvStartDate.setText(startDate);
        bi.tvEndDate.setText(endDate);
        if (taskDetail.data.agents.size() != 0) {

            if (bi.lnAgent.getChildCount() > 0) {
                bi.lnAgent.removeAllViews();
            }
            selectedIdsList = new ArrayList<>();

            for (GetTaskDetail.Data.Agent name : taskDetail.data.agents) {

                View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                TextView textView = child.findViewById(R.id.txtDynamic);
                textView.setText(String.valueOf(name.agentName));
                bi.lnAgent.addView(child);
                //selectedIdsList.add(name.);
            }
        }
    }

    private void callAddTask() {

        String id = "1";
        String agentIds = agentIdsString;
        String leadStringID = leadId + "";
        String isAssignNow = bi.tvName.getText().toString().equals("") ? "false" : "true";
        String monthType = "";
        String scheduleID = "0";
        String name = bi.atvNameTask.getText().toString() + "";
        String desc = bi.atvDescription.getText().toString() + "";
        int scheduleRecurID = !reoccur.equals("") ? Integer.valueOf(reoccur) : 0;
        String type = this.type;
        String datedFrom = startDate;
        String datedto = endDate;
        String recurDay = "";
        String recureWeek = "";
        String noOfWeek = "";
        String dayOfWeek = "";
        String dayOfMonth = "";
        String weekNo = "";
        String monthOfYear = "";
        String nextRun = "";
        String isEndDate = bi.cbEndDate.isChecked() ? "false" : "true";
        String reminderDate = "";
        int interval = !reminder.equals("") ? Integer.valueOf(reminder) : 0;
        String isSend = "";
        String viaReminder = via;
        String propertyId = "";
        String propertyAddress = bi.atvAddProperty.getText().toString() + "";

        presenter.addTask(id, agentIds, leadStringID, isAssignNow, monthType, scheduleID, name, desc, scheduleRecurID, type, datedFrom, datedto, recurDay, recureWeek, noOfWeek,
                dayOfWeek, dayOfMonth, weekNo, monthOfYear, nextRun, isEndDate, reminderDate, interval, isSend, viaReminder, propertyId, propertyAddress);

    }

    private void type() {

        if (!isTypeClicked) {
            bi.atvType.showDropDown();
            isTypeClicked = true;
        } else {
            isTypeClicked = false;
            bi.atvType.dismissDropDown();
        }

    }

    private void recur() {

        if (!isRecurClicked) {
            bi.atvRecur.showDropDown();
            isRecurClicked = true;
        } else {
            isRecurClicked = false;
            bi.atvRecur.dismissDropDown();
        }

    }

    private void reminder() {

        if (!isReminderClicked) {
            bi.atvReminder.showDropDown();
            isReminderClicked = true;
        } else {
            isReminderClicked = false;
            bi.atvReminder.dismissDropDown();
        }

    }

    private void via() {

        if (!isViaClicked) {
            bi.atvVia.showDropDown();
            isViaClicked = true;
        } else {
            isViaClicked = false;
            bi.atvVia.dismissDropDown();
        }

    }

    @Override
    public void onClick(View view) {

        super.onClick(view);
        switch (view.getId()) {
            case R.id.tvName:
                showSearchDialog(context);
                break;
            case R.id.tvStartDate:
                showDateDialog(bi.tvStartDate, true);
                break;
            case R.id.tvEndDate:
                showDateDialog(bi.tvEndDate, false);
                break;
            case R.id.tvAssignTo:
                showAgentDialog();
                break;
            case R.id.btnSave:
                callAddTask();
                break;
            case R.id.btnCancel:
                finish();
                break;
            case R.id.atvReminder:
                reminder();
                break;
            case R.id.atvVia:
                via();
                break;
            case R.id.atvType:
                type();
                break;
            case R.id.atvRecur:
                recur();
                break;
        }
    }

    private void showAgentDialog() {

        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title("Select Agents") //setting title for dialog
                .titleSize(25)
                .positiveText("Done")
                .negativeText("Cancel")
                .setMinSelectionLimit(1) //you can set minimum checkbox selection limit (Optional)
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        //will return list of selected IDS
                        selectedIdsList = new ArrayList<>();
                        selectedIdsList = selectedIds;
                        if (bi.lnAgent.getChildCount() > 0) {
                            bi.lnAgent.removeAllViews();
                        }
                        for (String name : selectedNames) {

                            View child = getLayoutInflater().inflate(R.layout.custom_textview, null);
                            TextView textView = child.findViewById(R.id.txtDynamic);
                            textView.setText(name);
                            bi.lnAgent.addView(child);
                        }
                        agentModel = new MultiSelectModel(selectedIds.get(0), selectedNames.get(0));
                        Log.e("DataString", dataString);
                    }

                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, ArrayList<String> selectedEncyrptedIds, String commonSeperatedData) {

                        agentIdsString = "";
                        if (selectedEncyrptedIds != null || selectedEncyrptedIds.size() != 0) {
                            for (String i : selectedEncyrptedIds) {

                                if (agentIdsString.equals("")) {
                                    agentIdsString = i;
                                } else {
                                    agentIdsString = agentIdsString + "," + i;
                                }
                            }
                        } else {
                            ToastUtils.showToast(context, "No EncryptedID Found");
                        }
                    }

                    @Override
                    public void onCancel() {
                    }
                });

        if (selectedIdsList.size() != 0) {
            multiSelectDialog.preSelectIDsList(selectedIdsList);
            multiSelectDialog.multiSelectList(searchListItems);
        } else {
            multiSelectDialog.multiSelectList(searchListItems);
        }

        multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");

    }

    public void showSearchDialog(Context context) {

        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_search_dialog);

        EditText edtQuery = dialog.findViewById(R.id.edtQuery);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        recyclerSearch = dialog.findViewById(R.id.recyclerSearch);

        tvTitle.setText("Name of Contact");
        edtQuery.requestFocus();
        if (edtQuery.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        edtQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                getLeadByText(editable.toString(), dialog);

            }
        });

        dialog.show();
    }

    private void showDateDialog(TextView textView, boolean isStart) {

        final Calendar newCalendar = Calendar.getInstance();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("MM-dd-yyyy");
        DatePickerDialog StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int years, int monthOfYear, int dayOfMonth) {

                year = years;
                month = monthOfYear;
                day = dayOfMonth;
                newCalendar.set(year, month, day);

                if (isStart) {
                    startDate = dateFormatter.format(newCalendar.getTime());
                } else {
                    endDate = dateFormatter.format(newCalendar.getTime());
                }

                textView.setText(dateFormatter2.format(newCalendar.getTime()));

            }

        }, year, month, day);
        if (isStart) {
            StartTime.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        }else{
            newCalendar.set(year, month, day);
            StartTime.getDatePicker().setMinDate(newCalendar.getTime().getTime() - 1000);
        }
        StartTime.show();
    }

    private void setRecyclerSearch(Dialog dialog) {

        recyclerSearch.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerSearch.setItemAnimator(new DefaultItemAnimator());
        recyclerSearch.addItemDecoration(new DividerItemDecoration(recyclerSearch.getContext(), 1));
        recyclerAdapterUtil = new RecyclerAdapterUtil(context, nameList, R.layout.custom_search_layout);
        recyclerAdapterUtil.addViewsList(R.id.tvName);

        recyclerAdapterUtil.addOnDataBindListener((Function4<View, GetLeadTitlesModel.Data, Integer, Map<Integer, ? extends View>, Unit>) (view, nameList, integer, integerMap) -> {

            TextView tvName = (TextView) integerMap.get(R.id.tvName);
            tvName.setText(nameList.name);

            return Unit.INSTANCE;
        });

        recyclerSearch.setAdapter(recyclerAdapterUtil);

        recyclerAdapterUtil.addOnClickListener((Function2<GetLeadTitlesModel.Data, Integer, Unit>) (list, integer) -> {

            bi.tvName.setText(nameList.get(integer).name);
            leadId = nameList.get(integer).leadID;
            dialog.dismiss();

            return Unit.INSTANCE;
        });
    }

    @Override
    public void updateUI(Response<BaseResponse> response) {

        if (response.body().getStatus().equals("Success")) {
            ToastUtils.showToast(context, "Added Successfully");
            finish();
        }

    }

    @Override
    public void updateUIonFalse(String message) {

        ToastUtils.showToastLong(context, message);

    }

    @Override
    public void updateUIonError(String error) {

        if (error.contains("Authorization has been denied for this request")) {
            ToastUtils.showErrorToast(context, "Session Expired", "Please Login Again");
            logout();
        } else {
            ToastUtils.showToastLong(context, error);
        }
    }

    @Override
    public void updateUIonFailure() {

        ToastUtils.showToastLong(context, getString(R.string.retrofit_failure));
    }

    @Override
    public void showProgressBar() {

        GH.getInstance().ShowProgressDialog(AddTaskActivity.this);
    }

    @Override
    public void hideProgressBar() {

        GH.getInstance().HideProgressDialog();
    }

    private void getLeadByText(String query, Dialog dialog) {
        Call<GetLeadTitlesModel> _callToday;
        _callToday = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetLeadTitlesModel(GH.getInstance().getAuthorization(), query);
        _callToday.enqueue(new Callback<GetLeadTitlesModel>() {
            @Override
            public void onResponse(Call<GetLeadTitlesModel> call, Response<GetLeadTitlesModel> response) {
                GH.getInstance().HideProgressDialog();
                if (response.isSuccessful()) {

                    GetLeadTitlesModel getAppointmentsModel = response.body();
                    if (getAppointmentsModel.status.equals("Success")) {

                        nameList = new ArrayList<>();
                        nameList.addAll(getAppointmentsModel.data);
                        setRecyclerSearch(dialog);

                    } else {

                        ToastUtils.showToast(context, getAppointmentsModel.message);

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    ToastUtils.showToast(context, error.message());
                }
            }

            @Override
            public void onFailure(Call<GetLeadTitlesModel> call, Throwable t) {
                ToastUtils.showToastLong(context, context.getString(R.string.retrofit_failure));
            }
        });
    }

}
