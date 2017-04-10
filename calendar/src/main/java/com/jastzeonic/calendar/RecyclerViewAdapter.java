package com.jastzeonic.calendar;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.List;

/**
 * Created by ptc_02008 on 2017/1/25.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Calendar> items;
    private CalendarEventHandler eventHandler;

    public RecyclerViewAdapter(List<Calendar> items, CalendarEventHandler eventHandler) {
        this.items = items;
        this.eventHandler = eventHandler;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_item, parent, false);
        return new ViewHolder(view, eventHandler);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mCalendarView.getCurrentDate().setTimeInMillis(items.get(position).getTimeInMillis());
        holder.mCalendarView.updateCalendar();

        if (position == 0) {
            Calendar calendar = (Calendar) items.get(position).clone();
            calendar.setTimeInMillis(items.get(position).getTimeInMillis());
            calendar.add(Calendar.MONTH, -1);
            items.add(0, calendar);
            holder.mCalendarView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    notifyItemInserted(0);
                }
            }, 100);

        } else if (position == items.size() - 2) {
            Calendar calendar = (Calendar) items.get(items.size() - 1).clone();
            calendar.add(Calendar.MONTH, 1);
            items.add(calendar);
            holder.mCalendarView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    notifyItemInserted(items.size() - 1);
                }
            }, 100);
        }


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItems(List<Calendar> items) {
        this.items = items;
        this.notifyDataSetChanged();

    }

}
