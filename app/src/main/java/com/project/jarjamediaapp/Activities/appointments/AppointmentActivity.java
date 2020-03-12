package com.project.jarjamediaapp.Activities.appointments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentActivity;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Fragments.DashboardFragments.appointments.FragmentAppointment;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityAppointmentsBinding;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Response;

public class AppointmentActivity extends BaseActivity implements View.OnClickListener, AppointmentContract.View {

    ActivityAppointmentsBinding bi;
    Context context = AppointmentActivity.this;
    AppointmentPresenter presenter;
    String leadID = "", leadName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_appointments);
        presenter = new AppointmentPresenter(this);
        leadID = getIntent().getStringExtra("leadID");
        leadName = getIntent().getStringExtra("leadName");
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.appointment), true);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initViews() {

        Fragment fragment = FragmentAppointment.newInstance("Appointments", leadID, true);
        ReplaceFragment(fragment, "Appointments", true, false);
    }

    public void ReplaceFragment(Fragment fragment, String title, boolean shouldAnimate, boolean addToStack) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (shouldAnimate) {
            ft.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out, android.R.anim.fade_in,
                    android.R.anim.fade_out);
        }
        ft.replace(R.id.fragment_replacer, fragment, title);
        if (addToStack) {
            ft.addToBackStack(title);
        }
        ft.commitAllowingStateLoss();
    }

    @Override
    public void updateUI(Response<BaseResponse> response) {


    }

    @Override
    public void updateUIonFalse(String message) {

        ToastUtils.showToastLong(context, message);

    }

    @Override
    public void updateUIonError(String error) {

        ToastUtils.showToastLong(context, error);
    }

    @Override
    public void updateUIonFailure() {

        ToastUtils.showToastLong(context, getString(R.string.retrofit_failure));
    }

    @Override
    public void showProgressBar() {

        GH.getInstance().ShowProgressDialog(AppointmentActivity.this);
    }

    @Override
    public void hideProgressBar() {

        GH.getInstance().HideProgressDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem item = menu.findItem(R.id.action_add);
        item.setVisible(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {

            leadID = getIntent().getStringExtra("leadID");
            Map<String, String> appointMap = new HashMap<>();
            appointMap.put("leadID", leadID);
            appointMap.put("leadName", leadName);
            appointMap.put("from","1");
            switchActivityWithIntentString(AddAppointmentActivity.class, (HashMap<String, String>) appointMap);

            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("resume123","On Resume Called");
    }
}
