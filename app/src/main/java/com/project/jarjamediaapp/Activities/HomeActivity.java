package com.project.jarjamediaapp.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.project.jarjamediaapp.Activities.add_lead.AddLeadActivity;
import com.project.jarjamediaapp.Activities.calendar.CalendarActivity;
import com.project.jarjamediaapp.Activities.login.LoginActivity;
import com.project.jarjamediaapp.Activities.open_houses.Open_HousesActivity;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Fragments.DashboardFragments.TabsFragment;
import com.project.jarjamediaapp.Fragments.LeadsFragments.find_leads.FindLeadsFragment;
import com.project.jarjamediaapp.Interfaces.UpdateTitle;
import com.project.jarjamediaapp.Models.GetUserProfile;
import com.project.jarjamediaapp.Networking.ApiError;
import com.project.jarjamediaapp.Networking.ApiMethods;
import com.project.jarjamediaapp.Utilities.AppConstants;
import com.project.jarjamediaapp.Networking.ErrorUtils;
import com.project.jarjamediaapp.Networking.NetworkController;
import com.project.jarjamediaapp.R;
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

    CircleImageView navHeaderImageView;
    TextView navHeaderTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = HomeActivity.this;

        initViews();

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
        navHeaderImageView = (CircleImageView) headerView.findViewById(R.id.imageView);

        getUserProfileData();

        onNavigationItemSelected(navigationView.getMenu().getItem(0));
    }

    private void getUserProfileData() {

        GH.getInstance().ShowProgressDialog(context);
        Call<GetUserProfile> _call = NetworkController.getInstance().getRetrofit().create(ApiMethods.class).
                getUserProfileData(GH.getInstance().getAuthorization());
        _call.enqueue(new Callback<GetUserProfile>() {
            @Override
            public void onResponse(Call<GetUserProfile> call, Response<GetUserProfile> response) {

                GH.getInstance().HideProgressDialog(context);
                if (response.isSuccessful()) {

                    GetUserProfile getUserProfile = response.body();
                    if (response.body().status.equals("Success")) {

                        AppConstants.Keys.UserID = getUserProfile.data.userProfile.userID;

                        String fullName = getUserProfile.data.userProfile.firstName + " " + getUserProfile.data.userProfile.lastName;
                        navHeaderTextView.setText(fullName);

                        picPath = AppConstants.HTTP.BASE_IMAGE_URL + getUserProfile.data.userProfile.picPath;

                        Glide.with(context)
                                .load(picPath)
                                .into(navHeaderImageView);

                    } else {

                        Toast.makeText(context, getUserProfile.message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ApiError error = ErrorUtils.parseError(response);

                    if (error.message().contains("Authorization has been denied for this request"))
                    {
                        ToastUtils.showErrorToast(HomeActivity.this,"Session Expired","Please Login Again");
                        easyPreference.clearAll();
                        switchActivity(LoginActivity.class);
                    }else{
                        ToastUtils.showToastLong(context, error.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetUserProfile> call, Throwable t) {
                GH.getInstance().HideProgressDialog(context);
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
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displayView(item);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void displayView(MenuItem item) {

        int itemId = item.getItemId();
        item.setChecked(true);
        mCurrentFragmentId = itemId;

        switch (itemId) {

            case R.id.nav_dashboard:
                title = getResources().getString(R.string.dashboard);
                fragment = TabsFragment.newInstance(title, R.id.nav_dashboard);
                addToStack = false;
                shouldAnimate = true;
                break;
            case R.id.nav_lead:
                title = getResources().getString(R.string.leads);
                fragment = FindLeadsFragment.newInstance(title, R.id.nav_lead);
                addToStack = false;
                shouldAnimate = true;
                _menu.findItem(R.id.action_search).setVisible(false);
                break;
            case R.id.nav_calendar:
                fragment = null;
                title = getResources().getString(R.string.calendar);
                switchActivity(CalendarActivity.class);
                break;
            case R.id.nav_open_house:
                title = getResources().getString(R.string.openHouses);
                fragment = null;
                switchActivity(Open_HousesActivity.class);
                break;
            case R.id.nav_tasks:
                fragment = null;
                title = getResources().getString(R.string.task);
                break;
            case R.id.nav_logout:
                fragment = null;
                easyPreference.clearAll().save();
                switchActivity(LoginActivity.class);
                finish();
                break;

            default:
                break;
        }
        if (fragment != null) {
            ReplaceFragment(fragment, title, shouldAnimate, addToStack);
            //ForBaseFragmentActivityLogic
            //pushFragments("Home", fragment, shouldAnimate, addToStack);
        }
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

    @Override
    public void updateToolBarTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        _menu = menu;

        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(true);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setVisibility(View.VISIBLE);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            switchActivity(AddLeadActivity.class);

            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}