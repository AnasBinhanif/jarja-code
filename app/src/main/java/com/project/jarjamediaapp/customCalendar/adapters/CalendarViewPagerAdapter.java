package com.project.jarjamediaapp.customCalendar.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.project.jarjamediaapp.customCalendar.interfaces.OnDateSelectedListener;
import com.project.jarjamediaapp.customCalendar.interfaces.OnDayViewClickListener;
import com.project.jarjamediaapp.customCalendar.objects.CalendarDate;
import com.project.jarjamediaapp.customCalendar.objects.CalendarMonth;
import com.project.jarjamediaapp.customCalendar.views.CalendarDayView;
import com.project.jarjamediaapp.customCalendar.views.CalendarMonthView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.view.View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION;


public class CalendarViewPagerAdapter extends PagerAdapter implements OnDayViewClickListener {

    private ViewPager mViewPager;
    private List<CalendarMonth> mData;
    private CalendarDate mSelectedDate;
    private OnDateSelectedListener mListener;

    public CalendarViewPagerAdapter(List<CalendarMonth> list, ViewPager viewPager) {
        mData = list;
        mViewPager = viewPager;
        mSelectedDate = new CalendarDate(Calendar.getInstance());
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        CalendarMonth month = mData.get(position);
        CalendarMonthView monthView = new CalendarMonthView(container.getContext());
        monthView.setSelectedDate(mSelectedDate);
        monthView.setOnDayViewClickListener(this);
        monthView.buildView(month);
        (container).addView(monthView, 0);
        monthView.setTag(month);
        return monthView;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        View view = (View) object;
        CalendarMonth month = (CalendarMonth) view.getTag();
        int position = mData.indexOf(month);

        if (position >= 0) {
            return position;
        } else {
            return POSITION_NONE;
        }
    }

    public void addNext(CalendarMonth month) {
        mData.add(month);
        notifyDataSetChanged();
    }

    public void addPrev(CalendarMonth month) {
        mData.add(0, month);
        notifyDataSetChanged();
    }

    public String getItemPageHeader(int position) {
        return mData.get(position).toString();
    }

    public CalendarMonth getItem(int position) {
        return mData.get(position);
    }

    public void setOnDateSelectedListener(OnDateSelectedListener listener) {
        mListener = listener;

        if (mListener != null) {
            mListener.onDateSelected(new CalendarDate(mSelectedDate));
        }
    }

    @Override
    public void onDayViewClick(CalendarDayView view) {
        // unset old selection
        decorateSelection(mSelectedDate.toString(), false);

        // set new selection
        mSelectedDate = view.getDate();
        decorateSelection(mSelectedDate.toString(), true);

        if (mListener != null) {
            mListener.onDateSelected(new CalendarDate(mSelectedDate));
        }
    }

    private void decorateSelection(String tag, boolean isSelected) {
        ArrayList<View> output = new ArrayList<>();
        mViewPager.findViewsWithText(output, tag, FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        for (View outputView : output) {
            CalendarDayView dayView = (CalendarDayView) outputView;
            if (isSelected) {
                dayView.setPurpleSolidOvalBackground();
            } else {
                dayView.unsetPurpleSolidOvalBackground();
            }

        }
    }
}
