package com.jastzeonic.calendar;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jastzeonic.calendar.layoutmangers.ViewPagerLayoutManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class CalendarListDialog {

    public static final String TAG = "CalendarListDialog";

    private Context context;
    private Dialog dialog;


    public CalendarListDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context, R.style.NewDialog);
        dialog.setContentView(R.layout.dialog_calendar);

    }


    public void callCalendarView(TextView startDate, final TextView setTextView) {

        final RecyclerView mRecyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_view);
        final TextView mTextViewYear = (TextView) dialog.findViewById(R.id.calendar_date_year_display);
        final TextView mTextViewMonth = (TextView) dialog.findViewById(R.id.calendar_date_month_display);

        mRecyclerView.setLayoutManager(new ViewPagerLayoutManager(context, ViewPagerLayoutManager.HORIZONTAL, false));


        List<Calendar> items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, i);
            items.add(calendar);
        }
        mRecyclerView.setAdapter(new RecyclerViewAdapter(items, new CalendarEventHandler() {
            @Override
            public void onDayPress(Calendar date) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

                String selectTime = sdf.format(date.getTime());
                setTextView.setText(selectTime);
                dialog.dismiss();
            }
        }));
        mRecyclerView.setNestedScrollingEnabled(false);
//
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, int scrollState) {

                switch (scrollState) {

                    case RecyclerView.SCROLL_STATE_SETTLING:
                        int year = ((ViewHolder) mRecyclerView.findViewHolderForLayoutPosition(((ViewPagerLayoutManager) mRecyclerView.getLayoutManager()).getCurrentPosition())).mCalendarView.getCurrentDate().get(Calendar.YEAR);
                        int month = ((ViewHolder) mRecyclerView.findViewHolderForLayoutPosition(((ViewPagerLayoutManager) mRecyclerView.getLayoutManager()).getCurrentPosition())).mCalendarView.getCurrentDate().get(Calendar.MONTH);
                        Log.v("CalendarListDialog", "year:" + year);
                        Log.v("CalendarListDialog", "month:" + month);
                        mTextViewYear.setText(year + "年");
                        mTextViewMonth.setText((month + 1) + "月");


                        Log.v("CalendarListDialog", "position:" + ((ViewPagerLayoutManager) recyclerView.getLayoutManager()).getCurrentPosition());
                        break;

                }


            }

        });

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
//                        Log.v("CalendarListDialog", "position:" + ((ViewPagerLayoutManager) recyclerView.getLayoutManager()).getCurrentPosition());
        mTextViewYear.setText(year + "年");
        mTextViewMonth.setText((month + 1) + "月");


        dialog.show();

    }

    private boolean setCalendarOfTheDayBegin(Calendar calendar) {

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return true;
    }


}
