package com.jastzeonic.calendar;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ptc_02008 on 2017/1/25.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    CalendarView mCalendarView;

    public ViewHolder(View itemView, CalendarEventHandler eventHandler) {
        super(itemView);
        mCalendarView = (CalendarView) itemView.findViewById(R.id.calendar_view);
        mCalendarView.setCalendarEventHandler(eventHandler);


    }
}
