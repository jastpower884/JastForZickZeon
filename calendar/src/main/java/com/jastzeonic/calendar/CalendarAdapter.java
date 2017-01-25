package com.jastzeonic.calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

/**
 * Created by ptc_02008 on 2017/1/25.
 */
public class CalendarAdapter extends ArrayAdapter<Calendar> {
    private CalendarView calendarView;
    // days with events
    private HashSet<Calendar> eventDays;

    // for view inflation
    private LayoutInflater inflater;

    public CalendarAdapter(CalendarView calendarView, Context context, ArrayList<Calendar> days, HashSet<Calendar> eventDays) {
        super(context, R.layout.control_calendar_day, days);
        this.calendarView = calendarView;
        this.eventDays = eventDays;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // day in question
        Calendar date = getItem(position);
        int day = date.get(Calendar.DATE);
        int month = date.get(Calendar.MONTH);
        int year = date.get(Calendar.YEAR);

        // today
        Calendar today = Calendar.getInstance();

        // inflate item if it does not exist yet
        if (view == null)
            view = inflater.inflate(R.layout.control_calendar_day, parent, false);

        // if this day has an event, specify event image
//            view.setBackgroundResource(0);
        if (eventDays != null) {
            for (Calendar eventDate : eventDays) {
                if (eventDate.get(Calendar.DAY_OF_MONTH) == day &&
                        eventDate.get(Calendar.MONTH) == month &&
                        eventDate.get(Calendar.YEAR) == year) {
                    // mark this day for event
                    view.setBackgroundResource(R.drawable.reminder);
                    break;
                }
            }
        }

        // clear styling
        ((TextView) view).setTypeface(null, Typeface.NORMAL);
        ((TextView) view).setTextColor(ContextCompat.getColor(this.getContext(), R.color.greyed_out));

        if (month == calendarView.getCurrentDate().get(Calendar.MONTH) && year == calendarView.getCurrentDate().get(Calendar.YEAR)
                && calendarView.isTheDateBeforeMaxDate(date) && calendarView.isTheDateAfterMinDate(date)) {
            // if this day is outside current month, grey it out
            ((TextView) view).setTextColor(Color.BLACK);
        }

        if (month == today.get(Calendar.MONTH) && year == today.get(Calendar.YEAR) && day == today.get(Calendar.DATE)) {
            // if it is today, set it to blue/bold
            ((TextView) view).setTypeface(null, Typeface.BOLD);
            ((TextView) view).setTextColor(ContextCompat.getColor(this.getContext(), R.color.today));
        }

        // set text
        ((TextView) view).setText(String.valueOf(date.get(Calendar.DATE)));

        return view;
    }
}
