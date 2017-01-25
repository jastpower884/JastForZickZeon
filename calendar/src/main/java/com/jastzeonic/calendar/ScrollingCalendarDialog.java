package com.jastzeonic.calendar;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class ScrollingCalendarDialog {


    private static final String DATE_FORMAT_YEAR = "yyyy";
    private static final String DATE_FORMAT_MONTH = "MMM";

    public static final String TAG = "ScrollingCalendarDialog";

    private Context context;
    private Dialog dialog;


    public ScrollingCalendarDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context, R.style.NewDialog);
        dialog.setContentView(R.layout.dialog_calendar);

    }

    private int currentPosition;

    public void callCalendarView(TextView startDate, final TextView setTextView) {

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

        Calendar currentDate = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YEAR, Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat(DATE_FORMAT_MONTH, Locale.getDefault());

        mTextViewYear.setText(sdf.format(currentDate.getTime()));
        mTextViewMonth.setText((sdf2.format(currentDate.getTime())));


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
