package com.project.jarjamediaapp.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Fragments.DashboardFragments.TabsFragment;
import com.project.jarjamediaapp.Fragments.LeadsFragments.find_leads.FindLeadsFragment;
import com.project.jarjamediaapp.R;

import java.util.ArrayList;


public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();

    }

    private void initViews() {

        title = getString(R.string.app_name);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        NavigationMenuView navMenuView = (NavigationMenuView) navigationView.getChildAt(0);

        onNavigationItemSelected(navigationView.getMenu().getItem(0));
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
                break;
            case R.id.nav_calendar:
                title = getResources().getString(R.string.calendar);
                break;
            case R.id.nav_open_house:
                title = getResources().getString(R.string.openHouses);
                break;
            case R.id.nav_tasks:
                title = getResources().getString(R.string.task);
                break;
            case R.id.nav_logout:
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

}
