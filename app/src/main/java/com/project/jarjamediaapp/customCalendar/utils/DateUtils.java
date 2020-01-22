package com.project.jarjamediaapp.customCalendar.utils;


import com.project.jarjamediaapp.ProjectApplication;
import com.project.jarjamediaapp.R;

import java.util.Calendar;

public class DateUtils {

    /**
     * Return a string with the month name like it appears in the Julian and Gregorian calendars.
     *
     * @param month value is 0-based: 0 for January, 11 for December.
     * @return the month name like it appears in the Julian and Gregorian calendars as a string.
     */
    public static String monthToString(int month) {
        switch (month) {
            case Calendar.JANUARY:
                return ProjectApplication.getInstance().getString(R.string.january);

            case Calendar.FEBRUARY:
                return ProjectApplication.getInstance().getString(R.string.february);

            case Calendar.MARCH:
                return ProjectApplication.getInstance().getString(R.string.march);

            case Calendar.APRIL:
                return ProjectApplication.getInstance().getString(R.string.april);

            case Calendar.MAY:
                return ProjectApplication.getInstance().getString(R.string.may);

            case Calendar.JUNE:
                return ProjectApplication.getInstance().getString(R.string.june);

            case Calendar.JULY:
                return ProjectApplication.getInstance().getString(R.string.july);

            case Calendar.AUGUST:
                return ProjectApplication.getInstance().getString(R.string.august);

            case Calendar.SEPTEMBER:
                return ProjectApplication.getInstance().getString(R.string.september);

            case Calendar.OCTOBER:
                return ProjectApplication.getInstance().getString(R.string.october);

            case Calendar.NOVEMBER:
                return ProjectApplication.getInstance().getString(R.string.november);

            case Calendar.DECEMBER:
                return ProjectApplication.getInstance().getString(R.string.december);
        }
        return "";
    }

    /**
     * Return a string with the day name like it appears in the Julian and Gregorian calendars.
     *
     * @param day is the day of the week value.
     * @return the day of week name like it appears in the Julian and Gregorian calendars as a string.
     */
    public static String dayOfWeekToString(int day) {
        switch (day) {
            case Calendar.SUNDAY:
                return ProjectApplication.getInstance().getString(R.string.sunday);

            case Calendar.MONDAY:
                return ProjectApplication.getInstance().getString(R.string.monday);

            case Calendar.TUESDAY:
                return ProjectApplication.getInstance().getString(R.string.tuesday);

            case Calendar.WEDNESDAY:
                return ProjectApplication.getInstance().getString(R.string.wednesday);

            case Calendar.THURSDAY:
                return ProjectApplication.getInstance().getString(R.string.thursday);

            case Calendar.FRIDAY:
                return ProjectApplication.getInstance().getString(R.string.friday);

            case Calendar.SATURDAY:
                return ProjectApplication.getInstance().getString(R.string.saturday);
        }
        return "";
    }
}
