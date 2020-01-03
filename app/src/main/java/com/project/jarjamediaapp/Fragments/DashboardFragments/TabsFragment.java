package com.project.jarjamediaapp.Fragments.DashboardFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.project.jarjamediaapp.Base.BaseFragment;
import com.project.jarjamediaapp.CustomAdapter.ViewPagerAdapter;
import com.project.jarjamediaapp.Fragments.DashboardFragments.appointments.FragmentAppointment;
import com.project.jarjamediaapp.Fragments.DashboardFragments.followups.FollowUpFragment;
import com.project.jarjamediaapp.Fragments.DashboardFragments.tasks.TasksFragment;
import com.project.jarjamediaapp.Fragments.FragmentLifeCycle;
import com.project.jarjamediaapp.R;

public class TabsFragment extends BaseFragment {

    Context context;
    TabLayout tabLayout;
    ViewPager viewPager;
    private MenuItem jobMatchesMenuItem;
    private AppCompatImageView matchesActionView;
    View view;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;

    public TabsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public static TabsFragment newInstance(String fragment_title, int position) {
        TabsFragment tabsFragment = new TabsFragment();
        Bundle args = new Bundle();
        args.putString("title", fragment_title);
        args.putInt("position", position);
        tabsFragment.setArguments(args);
        return tabsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tabs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    private void initViews(View view) {
        viewPager = view.findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {

        final ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(FragmentAppointment.newInstance(getResources().getString(R.string.dashboard)), getResources().getString(R.string.appointment));
        adapter.addFragment(FollowUpFragment.newInstance(getResources().getString(R.string.dashboard)), getResources().getString(R.string.follow_up));
        adapter.addFragment(TasksFragment.newInstance(getResources().getString(R.string.dashboard)), getResources().getString(R.string.tasks));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            int currentPosition = 0;

            @Override
            public void onPageSelected(int newPosition) {

                FragmentLifeCycle fragmentToHide = (FragmentLifeCycle) adapter.getItem(currentPosition);
                fragmentToHide.onPauseFragment();
                FragmentLifeCycle fragmentToShow = (FragmentLifeCycle) adapter.getItem(newPosition);
                fragmentToShow.onResumeFragment();
                currentPosition = newPosition;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
