package com.jastzeonic.calendar;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;


public class ScrollingCalendarDialog {


    public interface CallBack {
        boolean onDateClick(String clickDate, Calendar calender);
    }

    private static final String DATE_FORMAT_YEAR = "yyyy";
    private static final String DATE_FORMAT_MONTH = "MMM";

    public static final String TAG = "ScrollingCalendarDialog";

    private Context context;

    public ScrollingCalendarDialog(Context context) {
        this.context = context;


    }

    private int currentPosition;

    public void callCalendarView(CallBack callBack) {
        callCalendarView(null, callBack);

    }

    public void callCalendarView(String startDate, final CallBack callBack) {
        callCalendarView(startDate, null, null, callBack);

    }

    public void callCalendarView(String startDate, String minDate, String maxDate, CallBack callBack) {

        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat sdfResource = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        try {
            currentDate.setTime(sdfResource.parse(startDate));
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }

        Calendar minCalender = null;
        if (minDate != null) {
            try {
                minCalender = Calendar.getInstance();
                minCalender.setTime(sdfResource.parse(minDate));

            } catch (ParseException | NullPointerException e) {
                e.printStackTrace();
            }
        }

        Calendar maxCalender = null;
        if (maxDate != null) {

            try {
                maxCalender = Calendar.getInstance();
                maxCalender.setTime(sdfResource.parse(maxDate));

            } catch (ParseException | NullPointerException e) {
                e.printStackTrace();
            }

        }


        callCalendarView(currentDate, minCalender, maxCalender, callBack);

    }


    public void callCalendarView(Calendar currentDate, Calendar minDate, Calendar maxDate, final CallBack callBack) {


        final Dialog dialog = new Dialog(context, R.style.NewDialog);
        dialog.setContentView(R.layout.dialog_calendar);
        final RecyclerView mRecyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_view);
        final TextView mTextViewYear = (TextView) dialog.findViewById(R.id.calendar_date_year_display);
        final TextView mTextViewMonth = (TextView) dialog.findViewById(R.id.calendar_date_month_display);

        LinearSnapHelper helper = new LinearSnapHelper() {
            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
                View centerView = findSnapView(layoutManager);
                if (centerView == null)
                    return RecyclerView.NO_POSITION;


                int position = layoutManager.getPosition(centerView);
                Log.v("ScrollingCalendarDialog", "centerView.getId():" + position);
                int targetPosition = -1;
                if (layoutManager.canScrollHorizontally()) {
                    Log.v("ScrollingCalendarDialog", "velocityX:" + velocityX);
                    if (velocityX < 0) {
                        targetPosition = position - 1;
                    } else {
                        targetPosition = position + 1;
                    }
                }

                if (layoutManager.canScrollVertically()) {
                    if (velocityY < 0) {
                        targetPosition = position - 1;
                    } else {
                        targetPosition = position + 1;
                    }
                }

                final int firstItem = 0;
                final int lastItem = layoutManager.getItemCount() - 1;
                targetPosition = Math.min(lastItem, Math.max(targetPosition, firstItem));


                currentPosition = targetPosition;
                return targetPosition;
            }

        };
        helper.attachToRecyclerView(mRecyclerView);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));


        List<Calendar> items = getCalendars(currentDate.get(Calendar.MONTH));

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(items, new CalendarEventHandler() {
            @Override
            public void onDayPress(Calendar date) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

                String selectTime = sdf.format(date.getTime());
                if (callBack.onDateClick(selectTime, date)) {
                    dialog.dismiss();
                }
            }
        });

        recyclerViewAdapter.setMinDate(minDate);
        recyclerViewAdapter.setMaxDate(maxDate);


        mRecyclerView.setAdapter(recyclerViewAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, int scrollState) {

                switch (scrollState) {

                    case RecyclerView.SCROLL_STATE_SETTLING:

                        mRecyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ViewHolder mViewHolder = ((ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(currentPosition));
                                if (mViewHolder != null) {
                                    Calendar currentDate = mViewHolder.mCalendarView.getCurrentDate();
                                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YEAR, Locale.getDefault());
                                    SimpleDateFormat sdf2 = new SimpleDateFormat(DATE_FORMAT_MONTH, Locale.getDefault());

                                    mTextViewYear.setText(sdf.format(currentDate.getTime()));
                                    mTextViewMonth.setText((sdf2.format(currentDate.getTime())));
                                }
                            }
                        }, 100);


                        break;

                }


            }

        });


        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YEAR, Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat(DATE_FORMAT_MONTH, Locale.getDefault());

        mTextViewYear.setText(sdf.format(currentDate.getTime()));
        mTextViewMonth.setText((sdf2.format(currentDate.getTime())));

        dialog.show();

    }

    @NonNull
    private List<Calendar> getCalendars(int month) {
        List<Calendar> items = new LinkedList<>();
        for (int i = 0; i < 3; i++) {

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, month);
            calendar.add(Calendar.MONTH, i);
            items.add(calendar);
        }
        return items;
    }

    private boolean setCalendarOfTheDayBegin(Calendar calendar) {

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return true;
    }


}
