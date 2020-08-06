package com.project.jarjamediaapp.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.koushikdutta.ion.builder.Builders;
import com.project.jarjamediaapp.Activities.add_appointment.AddAppointmentActivity;
import com.project.jarjamediaapp.Activities.add_appointment.Data;
import com.project.jarjamediaapp.Activities.add_appointment.GetAppointmentByIDModel;
import com.project.jarjamediaapp.Activities.add_lead.AddLeadActivity;
import com.project.jarjamediaapp.Activities.add_task.AddTaskActivity;
import com.project.jarjamediaapp.Activities.add_task.GetTaskDetail;
import com.project.jarjamediaapp.Activities.calendar.CalendarActivity;
import com.project.jarjamediaapp.Activities.login.LoginActivity;
import com.project.jarjamediaapp.Activities.notification.NotificationActivity;
import com.project.jarjamediaapp.Activities.open_houses.OpenHousesActivity;
import com.project.jarjamediaapp.Activities.user_profile.UserProfileActivity;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Fragments.DashboardFragments.TabsFragment;
import com.project.jarjamediaapp.Fragments.LeadsFragments.find_leads.FindLeadsFragment;
import com.project.jarjamediaapp.Interfaces.UpdateTitle;
import com.project.jarjamediaapp.Models.GetUserPermission;
import com.project.jarjamediaapp.Models.GetUserProfile;
import com.project.jarjamediaapp.Models.ViewFollowUpModel;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.AppConstants;
import com.project.jarjamediaapp.Utilities.EasyPreference;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, UpdateTitle {

    Context context;
    DrawerLayout drawer;
    public Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    public NavigationView navigationView;
    private String current_fragment_title;
    private ArrayList<String> fragments_added = new ArrayList<>();
    public int mCurrentFragmentId = -1;
    Fragment fragment = null;
    String title = "";
    boolean addToStack = false;
    boolean shouldAnimate = false;
    Menu _menu;
    String picPath = "";
    MenuItem item;
    MenuItem NavigationItem;
    CircleImageView navHeaderImageView;
    TextView navHeaderTextView, tvInitial;
    public static boolean onClick = true;
    private String notificationType;
  //  LocalReceiver myReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = HomeActivity.this;

        initViews();

     //   myReceiver = new LocalReceiver();

        if (getIntent().getExtras() != null) {

            String typeOfNotification = getIntent().getStringExtra("notificationType");
            String notificationID = getIntent().getStringExtra("notificationID");


            switch (typeOfNotification) {
                case "4":

                    notificationType = "apointment";

                   getAppointmentById(notificationID);
                  //  easyPreference.addString(GH.KEYS.NOTIFICATIONTYPE.name(), notificationType).save();

                    break;

                case "3":


                    getFolloUpDetailByID(notificationID);

                    break;

                case "1":


                    notificationType = "task";

                   getTaskDetail(notificationID);

                    break;
                case "2":


                    notificationType = "futureTask";

                    getFutureTaskDetail(notificationID);

                    break;


            }
        }/*else{

            easyPreference.addString(GH.KEYS.NOTIFICATIONTYPE.name(), "").save();
        }*/

    }

    private void initViews() {

        title = getString(R.string.app_name);
        toolbar = findViewById(R.id.toolbar);
        setToolBarTitle(toolbar, getString(R.string.dashboard), false);
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        NavigationMenuView navMenuView = (NavigationMenuView) navigationView.getChildAt(0);

        View headerView = navigationView.getHeaderView(0);
        navHeaderTextView = (TextView) headerView.findViewById(R.id.header_title);
        tvInitial = (TextView) headerView.findViewById(R.id.tvInitial);
        navHeaderImageView = (CircleImageView) headerView.findViewById(R.id.imageView);

        getUserProfileData();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            //To do//
                            return;
                        }
                        // Get the Instance ID token//
                        String token = task.getResult().getToken();
                        Log.d("FCM TOKEN",token);
                       // userAuthenticate(FirebaseInstanceId.getInstance().getToken(),"FCM");
                    }
                });


    }

    private void userAuthenticate(String deviceToken, String network) {

        Call<BaseResponse> _call=NetworkController.getInstance().getRetrofit().create(ApiMethods.class).Authanticate_UserDevice(GH.getInstance().getAuthorization(),deviceToken,network);
        _call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                hideProgressBar();
                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (getAppointmentsModel.getStatus().equalsIgnoreCase("Success")) {


                    } else {

                        Toast.makeText(context, getAppointmentsModel.message, Toast.LENGTH_SHORT).show();

                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);
                    ToastUtils.showToastLong(context, error.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                hideProgressBar();
                ToastUtils.showToastLong(context, getString(R.string.retrofit_failure));
            }
        });
    }

    private void getUserProfileData() {

      //  showProgressBar();
        Call<GetUserProfile> _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).
                getUserProfileData(GH.getInstance().getAuthorization());
        _call.enqueue(new Callback<GetUserProfile>() {
            @Override
            public void onResponse(Call<GetUserProfile> call, Response<GetUserProfile> response) {

                if (response.isSuccessful()) {

                    GetUserProfile getUserProfile = response.body();
                    if (response.body().status.equals("Success")) {

                        easyPreference.addInt(GH.KEYS.AGENT_ID_INT.name(), getUserProfile.data.getAgentData().getAgentID()).save();
                        easyPreference.addString(GH.KEYS.AGENT_ID_CALENDAR.name(), getUserProfile.data.getAgentData().getEncryptedAgentID()).save();
                        easyPreference.addString(GH.KEYS.AGENT_NAME.name(), getUserProfile.data.getAgentData().getAgentName()).save();
                        AppConstants.Keys.UserID = getUserProfile.data.userProfileData.userID;

                        String firstName = getUserProfile.data.userProfileData.firstName + "";
                        String lastName = getUserProfile.data.userProfileData.lastName + "";
                        String fullName = getUserProfile.data.userProfileData.firstName + " " + getUserProfile.data.userProfileData.lastName;
                        easyPreference.addString(GH.KEYS.USER_NAME.name(), fullName).save();
                        easyPreference.addString(GH.KEYS.USER_ID.name(), getUserProfile.data.userProfileData.userID).save();
                        navHeaderTextView.setText(fullName);

                        picPath = getUserProfile.data.userProfileData.picPath;
                        if (picPath != null && !picPath.equals("")) {
                            Glide.with(context)
                                    .load(picPath)
                                    .into(navHeaderImageView);
                        } else {
                            if (firstName.equals("null") || firstName.equals("")) {
                                firstName = "-";
                            }

                            if (lastName.equals("null") || lastName.equals("")) {
                                lastName = "-";
                            }

                            tvInitial.setText(firstName.substring(0, 1) + lastName.substring(0, 1));

                            navHeaderImageView.setVisibility(View.GONE);
                            tvInitial.setVisibility(View.VISIBLE);
                        }
                        getUserPermissions();

                    } else {
                    //    hideProgressBar();
                        Toast.makeText(context, getUserProfile.message, Toast.LENGTH_SHORT).show();
                    }
                } else {

                //    hideProgressBar();
                    ApiError error = ErrorUtils.parseError(response);

                    /*if (error.message().contains("Authorization has been denied for this request")) {
                        ToastUtils.showErrorToast(context, "Session Expired", "Please Login Again");
                        logout();
                    } else {*/
                    ToastUtils.showToastLong(context, error.message());
                }
            }

            @Override
            public void onFailure(Call<GetUserProfile> call, Throwable t) {
              //  hideProgressBar();
                ToastUtils.showToastLong(context, getString(R.string.retrofit_failure));
            }
        });
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
       // LocalBroadcastManager.getInstance(HomeActivity.this).unregisterReceiver(myReceiver);
    }

    @Override
    protected void onResume() {

        super.onResume();


        // checking condition when we coming from leads activity
        if(GH.getInstance().getFragmentStatus() != null){
            if (GH.getInstance().getFragmentStatus().equals("leadsFragment")){

                // for testing
                EasyPreference.Builder pref = new EasyPreference.Builder(context);
                pref.addString(GH.KEYS.FRAGMENTSTATUS.name(),"").save();

            }else {

            //    navigationView.getMenu().getItem(1).setChecked(true);
                getUserProfileData();
            }

        }



//        IntentFilter filter = new IntentFilter("NOTIFICATION_LOCAL_BROADCAST");
//        LocalBroadcastManager.getInstance(HomeActivity.this).registerReceiver(myReceiver, filter);
        // when come from anyactivity to dashboard so highlighted the dash board item in navigation drawer
        // and stop for repeating dialogue in dash board screen
      //  getUserProfileData();
     /*   navigationView.getMenu().getItem(0).setChecked(true);
        getUserProfileData();*/

      /*  navigationView.getMenu().getItem(0).setChecked(true);
        getUserProfileData();*/
        // this logics create double loading of data and progress bar
       /* try {
            navigationView.getMenu().getItem(0).setChecked(true);
            getUserProfileData();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        displayView(item);
        NavigationItem = item;
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void displayView(MenuItem item) {

        int itemId = item.getItemId();
        item.setChecked(true);
        mCurrentFragmentId = itemId;
        GetUserPermission userPermission;

        switch (itemId) {

            case R.id.nav_dashboard:

                userPermission = GH.getInstance().getUserPermissions();
                if (userPermission.data.dashboard.get(0).value) {
                    title = getResources().getString(R.string.dashboard);
                    fragment = TabsFragment.newInstance(title,R.id.nav_dashboard);

                    addToStack = false;
                    shouldAnimate = true;

                } else {
                    ToastUtils.showToast(context, getString(R.string.dashboard_ViewDashboard));
                }

                break;
            case R.id.nav_lead:

                userPermission = GH.getInstance().getUserPermissions();
                if (userPermission.data.lead.get(0).value) {
                    title = getResources().getString(R.string.leads);
                    fragment = FindLeadsFragment.newInstance(title, R.id.nav_lead);
                    addToStack = true;
                    shouldAnimate = true;
                    _menu.findItem(R.id.action_search).setVisible(false);

                } else {

                    ToastUtils.showToast(context, getString(R.string.lead_ViewLeads));
                }

                break;
            case R.id.nav_calendar:
                fragment = null;
                title = getResources().getString(R.string.calendar);
                switchActivity(CalendarActivity.class);

                break;
            case R.id.nav_open_house:
                title = getResources().getString(R.string.openHouses);
                fragment = null;
                switchActivity(OpenHousesActivity.class);
                break;
            case R.id.nav_profile:
                fragment = null;
                title = "Profile";
                switchActivity(UserProfileActivity.class);
                break;
            case R.id.nav_logout:
                logout();
                break;

            default:
                break;
        }
        if (fragment != null) {
            ReplaceFragment(fragment, title, shouldAnimate, addToStack);
        }
    }


    public void logout() {

        AlertDialog alertDialog1;
        alertDialog1 = new AlertDialog.Builder(
                HomeActivity.this).create();

        // Setting Dialog Title
        alertDialog1.setTitle("Alert");

        // Setting Dialog Message
        alertDialog1.setMessage("\nAre You Sure! You Want To Logout ?");

        // Setting Icon to Dialog
        // alertDialog1.setIcon(R.drawable.tick);

        // Setting OK Button
        alertDialog1.setButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog
                // closed
                fragment = null;

                UnAuthenticateUser(FirebaseInstanceId.getInstance().getToken(), "FCM");

            }
        });

        alertDialog1.setButton2("NO", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog
                // closed
                alertDialog1.dismiss();
                // remove selection of logout
                 navigationView.getMenu().getItem(1).setChecked(true);
            }
        });

        // Showing Alert Message
        alertDialog1.show();
    }

    public void ReplaceFragment(Fragment fragment, String title, boolean shouldAnimate, boolean addToStack) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        current_fragment_title = title;
        if (shouldAnimate) {
            ft.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out, android.R.anim.fade_in,
                    android.R.anim.fade_out);
        }
        ft.replace(R.id.fragment_replacer, fragment, title);
        if (addToStack) {
            ft.addToBackStack(title);

        }
        fragments_added.add(title);
        ft.commitAllowingStateLoss();
    }
   /* public void adddFragment(Fragment fragment, String title, boolean shouldAnimate, boolean addToStack) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        current_fragment_title = title;
        if (shouldAnimate) {
            ft.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out, android.R.anim.fade_in,
                    android.R.anim.fade_out);
        }
        ft.add(R.id.fragment_replacer, fragment, title);
        if (addToStack) {
            ft.addToBackStack(title);

        }
        fragments_added.add(title);
        ft.commitAllowingStateLoss();
    }*/


    @Override
    public void updateToolBarTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        _menu = menu;

        item = menu.findItem(R.id.action_notify);

        return true;
    }

    private void getUserPermissions() {

        Call<GetUserPermission> _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).
                GetUserPermission(GH.getInstance().getAuthorization());
        _call.enqueue(new Callback<GetUserPermission>() {
            @Override
            public void onResponse(Call<GetUserPermission> call, Response<GetUserPermission> response) {

                // comment the code for repeating progressbar when a ctivity on resume state
           //    hideProgressBar();
                if (response.isSuccessful()) {

                    GetUserPermission getUserProfile = response.body();
                    if (getUserProfile.status.equals("Success")) {

                        Gson gson = new Gson();
                        String jsonText = gson.toJson(getUserProfile);
                        easyPreference.addString(GH.KEYS.USER_PERMISSIONS.name(), jsonText).save();

                        if (getUserProfile.data.dashboard.get(0).value) {

                            // for just showing first time navigation first item not repeating
                            if (GH.getInstance().getDashboardNavigationStatus()){

                                onNavigationItemSelected(navigationView.getMenu().getItem(1));
                                easyPreference.addBoolean(GH.KEYS.NAVIGATIONSTATUS.name(),false);
                            }




                        } else {

                            ToastUtils.showToast(context,getString(R.string.dashboard_ViewDashboard));
                        }

                    } else {

                        Toast.makeText(context, getUserProfile.message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // comment the code for repeating progressbar when a ctivity on resume state
                 //   hideProgressBar();
                    ApiError error = ErrorUtils.parseError(response);
                    /*if (error.message().contains("Authorization has been denied for this request")) {
                        ToastUtils.showErrorToast(context, "Session Expired", "Please Login Again");
                        logout();`
                    } else {*/
                    ToastUtils.showToastLong(context, error.message());

                }
            }

            @Override
            public void onFailure(Call<GetUserPermission> call, Throwable t) { hideProgressBar();
                ToastUtils.showToastLong(context, getString(R.string.retrofit_failure));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notify) {
            switchActivity(NotificationActivity.class);

            return true;
        }

        if (id == R.id.action_add) {
            switchActivity(AddLeadActivity.class);

            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private Drawable buildCounterDrawable(int count, int backgroundImageId) {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.custom_notification_badge, null);
        view.setBackgroundResource(backgroundImageId);
        TextView textView = (TextView) view.findViewById(R.id.count);
        if (count == 0) {
            View counterTextPanel = view.findViewById(R.id.counterValuePanel);
            counterTextPanel.setVisibility(View.GONE);
        } else {

            textView.setText("" + count);
        }

        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }

    public void showProgressBar() {

        GH.getInstance().ShowProgressDialog(HomeActivity.this);
    }

    public void hideProgressBar() {

        GH.getInstance().HideProgressDialog();
    }

    public void updateNotificationCount(int count) {
        item.setIcon(buildCounterDrawable(count, R.drawable.ic_notification));
    }

    private void UnAuthenticateUser(String deviceToken, String network) {
        // comment code here for repeating progress dialogue
        showProgressBar();
        Call<BaseResponse> _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).UnAuthanticate_UserDevice(GH.getInstance().getAuthorization(), deviceToken, network);
        _call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                hideProgressBar();
                if (response.isSuccessful()) {

                    BaseResponse getAppointmentsModel = response.body();
                    if (getAppointmentsModel.getStatus().equalsIgnoreCase("Success")) {

                        easyPreference.clearAll().save();
                        switchActivity(LoginActivity.class);
                        finish();

                    } else {
                        ToastUtils.showToastLong(context, getAppointmentsModel.message);
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);
                    ToastUtils.showToastLong(context, error.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                hideProgressBar();
                ToastUtils.showToastLong(context, getString(R.string.retrofit_failure));
            }
        });
    }

    public void getAppointmentById(String appointmentID) {

        Call<GetAppointmentByIDModel> _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).GetAppointmentByID(GH.getInstance().getAuthorization(),appointmentID);
        _call.enqueue(new Callback<GetAppointmentByIDModel>() {
            @Override
            public void onResponse(Call<GetAppointmentByIDModel> call, Response<GetAppointmentByIDModel> response) {

                if (response.isSuccessful()) {

                    GetAppointmentByIDModel getAppointmentsModel = response.body();

                    if (getAppointmentsModel.getStatus().equals("Success")) {

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

    public void getTaskDetail(String taskId) {


        Call<GetTaskDetail> apiCall = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).getTaskDetail(GH.getInstance().getAuthorization(), taskId);
        apiCall.enqueue(new Callback<GetTaskDetail>() {
            @Override
            public void onResponse(Call<GetTaskDetail> call, Response<GetTaskDetail> response) {

                if (response.isSuccessful()) {

                    GetTaskDetail getTaskDetail = response.body();

                    if (getTaskDetail.getStatus().equalsIgnoreCase("Success")) {

                        context.startActivity(new Intent(context, AddTaskActivity.class)
                                .putExtra("from", "5")
                                .putExtra("whichTasks", 1)
                                .putExtra("leadID", getTaskDetail.getData().leadID)
                                .putExtra("taskId", taskId));
                       // _view.updateTaskDetail(getTaskDetail);

                    } else {

                        ToastUtils.showToastLong(getApplicationContext(),getTaskDetail.message);
                       /* _view.hideProgressBar();

                        _view.updateUIonFalse(getTaskDetail.message);*/

                    }
                } else {


                    ApiError error = ErrorUtils.parseError(response);
                    ToastUtils.showToastLong(getApplicationContext(),error.Message());

                }
            }

            @Override
            public void onFailure(Call<GetTaskDetail> call, Throwable t) {
                ToastUtils.showToastLong(getApplicationContext(),t.getMessage());
            }
        });

    }
    public void getFutureTaskDetail(String scheduleID) {


        Call<GetTaskDetail> apiCall = NetworkController.getInstance().getRetrofit().create(ApiMethods.class)
                .getFutureTaskDetail(GH.getInstance().getAuthorization(), scheduleID);
        apiCall.enqueue(new Callback<GetTaskDetail>() {
            @Override
            public void onResponse(Call<GetTaskDetail> call, Response<GetTaskDetail> response) {

                if (response.isSuccessful()) {

                    GetTaskDetail getTaskDetail = response.body();

                    if (getTaskDetail.getStatus().equalsIgnoreCase("Success")) {


                        context.startActivity(new Intent(context, AddTaskActivity.class)
                                .putExtra("from", "5")
                                .putExtra("whichTasks", 3)
                                .putExtra("leadID", getTaskDetail.getData().leadID)
                                .putExtra("taskId", scheduleID));

                    } else {


                    }
                } else {

                    ApiError error = ErrorUtils.parseError(response);

                }
            }

            @Override
            public void onFailure(Call<GetTaskDetail> call, Throwable t) {

            }
        });

    }
   /* private void updateUI(Intent intent) {
        // do what you need to do
        ToastUtils.showToastLong(context,"Hello hello");
        ToastUtils.showToastLong(context,""+intent.getStringExtra("notificationType"));
    }

    private class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent);
        }
    }*/


}