package com.jastzeonic.jastforzickzeon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jastzeonic.calendar.CalendarDialog;
import com.jastzeonic.calendar.CalendarListDialog;

public class MainActivity extends AppCompatActivity {


    CalendarDialog mCalendarDialog;
    CalendarListDialog mCalendarListDialog;

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

        mCalendarListDialog = new CalendarListDialog(this);
        mCalendarListDialog.callCalendarView(null, (TextView) view);

    }
}
