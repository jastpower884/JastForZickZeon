package com.jastzeonic.jastforzickzeon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jastzeonic.calendar.CalendarDialog;
import com.jastzeonic.calendar.ScrollingCalendarDialog;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    CalendarDialog mCalendarDialog;
    ScrollingCalendarDialog mScrollingCalendarDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {

        mCalendarDialog = new CalendarDialog(this);
        mCalendarDialog.callCalendarView(null, (TextView) view);

    }

    public void onClick2(View view) {

        if (mScrollingCalendarDialog == null) {
            mScrollingCalendarDialog = new ScrollingCalendarDialog(this);
        }
        mScrollingCalendarDialog.callCalendarView(null, new ScrollingCalendarDialog.CallBack() {
            @Override
            public boolean onDateClick(String clickDate, Calendar calender) {

                return true;
            }
        });

    }
}
