package com.jastzeonic.jastforzickzeon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jastzeonic.calendar.CalendarDialog;

public class MainActivity extends AppCompatActivity {


    CalendarDialog mCalendarDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {

        mCalendarDialog = new CalendarDialog(this);
        mCalendarDialog.callCalendarView(null, (TextView) view);

    }
}
