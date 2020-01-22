package com.project.jarjamediaapp.customCalendar.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.customCalendar.adapters.CalendarViewPagerAdapter;
import com.project.jarjamediaapp.customCalendar.interfaces.OnCalendarScrolledListener;
import com.project.jarjamediaapp.customCalendar.interfaces.OnDateSelectedListener;
import com.project.jarjamediaapp.customCalendar.objects.CalendarMonth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CustomCalendarView extends FrameLayout implements View.OnClickListener {

    private ViewPager mViewPager;
    private OnCalendarScrolledListener mPageListener;
    private CalendarViewPagerAdapter mViewPagerAdapter;
    private OnDateSelectedListener mListener;

    public CustomCalendarView(@NonNull Context context) {
        super(context);
        init();
    }

    public CustomCalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomCalendarView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_custom_calendar, this);
        mViewPager = (ViewPager) findViewById(R.id.activity_main_view_pager);
        buildCalendarView();
    }

    public void setOnDateSelectedListener(OnDateSelectedListener listener) {
        mViewPagerAdapter.setOnDateSelectedListener(listener);
    }

    public void setOnPageScrolled(OnCalendarScrolledListener listener) {
        mPageListener=listener;
    }

    @Override
    public void onClick(View view) {
    }

    private void buildCalendarView() {
        List<CalendarMonth> list = new ArrayList<>();
        CalendarMonth today = new CalendarMonth(Calendar.getInstance());

        list.add(new CalendarMonth(today, -2));
        list.add(new CalendarMonth(today, -1));
        list.add(today);
        list.add(new CalendarMonth(today, 1));
        list.add(new CalendarMonth(today, 2));

        mViewPagerAdapter = new CalendarViewPagerAdapter(list, mViewPager);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setCurrentItem(2);
        //mPagerTextMonth.setText(mViewPagerAdapter.getItemPageHeader(2));
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            int position = mViewPager.getCurrentItem();
            mPageListener.OnPageScrolled(mViewPager, position, mViewPagerAdapter.getItemPageHeader(position));
            //mPagerTextMonth.setText(mViewPagerAdapter.getItemPageHeader(position));

            // current item is the first item in the list
            if (state == ViewPager.SCROLL_STATE_IDLE && position == 1) {
                addPrev();
            }

            // current item is the last item in the list
            if (state == ViewPager.SCROLL_STATE_IDLE && position == mViewPagerAdapter.getCount() - 2) {
                addNext();
            }

        }
    };

    private void addNext() {
        CalendarMonth month = mViewPagerAdapter.getItem(mViewPagerAdapter.getCount() - 1);
        mViewPagerAdapter.addNext(new CalendarMonth(month, 1));
        mViewPagerAdapter.addNext(new CalendarMonth(month, 2));
    }

    private void addPrev() {
        CalendarMonth month = mViewPagerAdapter.getItem(0);
        mViewPagerAdapter.addPrev(new CalendarMonth(month, -1));
        mViewPagerAdapter.addPrev(new CalendarMonth(month, -2));
    }

}
