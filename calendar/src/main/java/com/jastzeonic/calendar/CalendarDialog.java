package com.jastzeonic.calendar;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class CalendarDialog {

    private Context context;
    private Dialog dialog;


    public CalendarDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context, R.style.NewDialog);
        dialog.setContentView(R.layout.dialog_calendar_selection);

    }


    public void callCalendarView(TextView startDate, final TextView setTextView) {
        SimpleCalendarView customSimpleCalendarView = (SimpleCalendarView) dialog.findViewById(R.id.calendar_view);


        Calendar maxDate = Calendar.getInstance();

        Calendar minDate = Calendar.getInstance();


        if (startDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            try {
                maxDate.setTime(sdf.parse(startDate.getText().toString()));
                minDate.setTime(sdf.parse(startDate.getText().toString()));

                setCalendarOfTheDayBegin(minDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        setCalendarOfTheDayBegin(maxDate);
        setCalendarOfTheDayBegin(minDate);


        setCalendarOfTheDayBegin(maxDate);
        setCalendarOfTheDayBegin(minDate);

        maxDate.add(Calendar.DAY_OF_MONTH, (365 * 100));

        minDate.add(Calendar.DAY_OF_MONTH, -(365 * 100));


        final long minDateTime = minDate.getTimeInMillis();

        final long maxDateTime = maxDate.getTimeInMillis();


        customSimpleCalendarView.setMinDate(minDateTime);
        customSimpleCalendarView.setMaxDate(maxDateTime);

        customSimpleCalendarView.setEventHandler(new SimpleCalendarView.EventHandler() {
            @Override
            public void onDayLongPress(Calendar calendar) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

                String selectTime = sdf.format(calendar.getTime());
                setTextView.setText(selectTime);
//                editAccountInfo(selectTime, "", "");

                dialog.dismiss();


            }
        });

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
