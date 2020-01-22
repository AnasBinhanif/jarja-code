package com.project.jarjamediaapp.customCalendar.views;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.project.jarjamediaapp.ProjectApplication;
import com.project.jarjamediaapp.customCalendar.objects.CalendarDate;
import com.project.jarjamediaapp.R;


public class CalendarDayView extends LinearLayout {

    private CalendarDate mCalendarDate;
    private TextView mTextDay;
    private View mLayoutBackground;

    public CalendarDayView(Context context, CalendarDate calendarDate) {
        super(context);
        mCalendarDate = calendarDate;
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_calendar_day, this);
        mLayoutBackground = findViewById(R.id.view_calendar_day_layout_background);
        mTextDay = (TextView) findViewById(R.id.view_calendar_day_text);
        mTextDay.setText("" + mCalendarDate.getDay());
    }

    public CalendarDate getDate() {
        return mCalendarDate;
    }

    public void setThisMothTextColor() {
        mTextDay.setTextColor(ContextCompat.getColor(getContext(), R.color.colorGreyTextDark));
    }

    public void setOtherMothTextColor() {
        mTextDay.setVisibility(View.GONE);
        mLayoutBackground.setVisibility(View.GONE);
    }

    public void setPurpleSolidOvalBackground() {
        mTextDay.setTextColor(ProjectApplication.getInstance().getResources().getColor(R.color.colorPrimaryDark));
        mLayoutBackground.setBackgroundResource(R.drawable.oval_purple_solid);
    }

    public void unsetPurpleSolidOvalBackground() {
        mLayoutBackground.setBackgroundResource(R.drawable.oval_black_solid);
        mTextDay.setTextColor(ProjectApplication.getInstance().getResources().getColor(R.color.colorGreyTextDark));
    }

}
