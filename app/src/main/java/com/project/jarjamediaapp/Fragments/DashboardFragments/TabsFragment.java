package com.project.jarjamediaapp.Fragments.DashboardFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.project.jarjamediaapp.Base.BaseFragment;
import com.project.jarjamediaapp.CustomAdapter.ViewPagerAdapter;
import com.project.jarjamediaapp.Fragments.DashboardFragments.appointments.FragmentAppointment;
import com.project.jarjamediaapp.Fragments.DashboardFragments.followups.FollowUpFragment;
import com.project.jarjamediaapp.Fragments.DashboardFragments.tasks.TasksFragment;
import com.project.jarjamediaapp.Fragments.FragmentLifeCycle;
import com.project.jarjamediaapp.Interfaces.UpdateTitle;
import com.project.jarjamediaapp.R;

public class TabsFragment extends BaseFragment {

    Context context;
    TabLayout tabLayout;
    ViewPager viewPager;
    private Menu menu;

    ViewPagerAdapter adapter;

    View view;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_tabs, container, false);

            initViews(view);
        }
//        return inflater.inflate(R.layout.fragment_tabs, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initViews(view);
    }

    @Override
    public void onDestroyView() {
        if (viewPager.getParent() != null) {
            ((ViewGroup) viewPager.getParent()).removeView(view);
        }
        super.onDestroyView();
    }

    private void initViews(View view) {


      /*  String str = this.getArguments().getString("title");
        ToastUtils.showToastLong(context,str);*/
        if (viewPager == null) {
            viewPager = view.findViewById(R.id.viewPager);
            setupViewPager(viewPager);
            tabLayout = view.findViewById(R.id.tabLayout);
            tabLayout.setupWithViewPager(viewPager);
        }

    }

    private void setupViewPager(ViewPager viewPager) {


        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(FragmentAppointment.newInstance(getResources().getString(R.string.dashboard), "", false), getResources().getString(R.string.appointment));
        adapter.addFragment(FollowUpFragment.newInstance(getResources().getString(R.string.dashboard), "", false), getResources().getString(R.string.follow_up));
        adapter.addFragment(TasksFragment.newInstance(getResources().getString(R.string.dashboard), "", false), getResources().getString(R.string.tasks));

        viewPager.setAdapter(adapter);



      /*  // fro testing
        if (GH.getInstance().getNotificationType().equals("followup")){


            viewPager.setCurrentItem(1);


        }else if(GH.getInstance().getNotificationType().equals("task")){

            viewPager.setCurrentItem(2);

        }*/



       /* viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        });*/
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        this.menu = menu;
        getActivity().getMenuInflater().inflate(R.menu.home, menu);
        menu.findItem(R.id.action_notify).setVisible(true);
    }

    @Override
    public void onResume() {
        super.onResume();


    }


}
