package com.project.jarjamediaapp.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.project.jarjamediaapp.Activities.add_filters.AddFiltersActivity;
import com.project.jarjamediaapp.Activities.add_lead.AddLeadActivity;
import com.project.jarjamediaapp.Activities.open_houses.Open_HousesActivity;
import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Fragments.DashboardFragments.TabsFragment;
import com.project.jarjamediaapp.Fragments.LeadsFragments.find_leads.FindLeadsFragment;
import com.project.jarjamediaapp.Interfaces.UpdateTitle;
import com.project.jarjamediaapp.R;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, UpdateTitle {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
                _menu.findItem(R.id.action_search).setVisible(false);
                break;
            case R.id.nav_calendar:
                fragment=null;
                title = getResources().getString(R.string.calendar);
                break;
            case R.id.nav_open_house:
                title = getResources().getString(R.string.openHouses);
                fragment=null;
                switchActivity(Open_HousesActivity.class);
                break;
            case R.id.nav_tasks:
                fragment=null;
                title = getResources().getString(R.string.task);
                break;
            case R.id.nav_logout:
                fragment=null;
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

        _menu=menu;

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