package com.project.jarjamediaapp.Activities.calendar;

public class CalendarLabel {

    Integer dateFormat ;
    Integer count ;

    public Integer getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(Integer dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public CalendarLabel(Integer dateFormat, Integer count) {
        this.dateFormat = dateFormat;
        this.count = count;
    }
}
