package com.jastzeonic.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;

public class SimpleCalendarView extends LinearLayout {
    // for logging
    private static final String LOGTAG = "Calendar_View";

    // how many days to show, defaults to six weeks, 42 days
    private static final int DAYS_COUNT = 42;

    // default date format
    private static final String DATE_FORMAT = "MMM yyyy";

    // default date format
    private static final String DATE_FORMAT_YEAR = "yyyy";

    private static final String DATE_FORMAT_MONTH = "MMM";


    // date format
    private String dateFormat;

    // current displayed month
    private Calendar currentDate = Calendar.getInstance();

    // date min which user can select
    private Calendar minDate;

    // date max which user can select
    private Calendar maxDate;

    //event handling
    private EventHandler eventHandler = null;

    // internal components
    private ImageView btnPrevYear;
    private ImageView btnNextYear;
    private TextView txtDateYear;
    private ImageView buttonPrevMonth;
    private ImageView buttonNextMonth;
    private TextView txtDateMonth;

    // month-season association (northern hemisphere, sorry australia :)
    int[] monthSeason = new int[]{2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};

    public SimpleCalendarView(Context context) {
        super(context);
    }

    public SimpleCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context, attrs);
    }

    public SimpleCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
    }

    /**
     * Load control xml layout
     */
    private void initControl(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.control_calendar, this);

        loadDateFormat(attrs);
        assignUiElements();
        assignClickHandlers();

        updateCalendar();
    }

    private void loadDateFormat(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.SimpleCalendarView);

        try {
            // try to load provided date format, and fallback to default otherwise
            dateFormat = ta.getString(R.styleable.SimpleCalendarView_dateFormat);
            if (dateFormat == null)
                dateFormat = DATE_FORMAT;
        } finally {
            ta.recycle();
        }
    }

    private void assignUiElements() {
        // layout is inflated, assign local variables to components
        btnPrevYear = (ImageView) findViewById(R.id.calendar_prev_year_button);
        btnNextYear = (ImageView) findViewById(R.id.calendar_next_year_button);
        txtDateYear = (TextView) findViewById(R.id.calendar_date_year_display);
        buttonPrevMonth = (ImageView) findViewById(R.id.calendar_prev_month_button);
        buttonNextMonth = (ImageView) findViewById(R.id.calendar_next_month_button);
        txtDateMonth = (TextView) findViewById(R.id.calendar_date_month_display);

    }

    private void assignClickHandlers() {
        // add one month and refresh UI
        btnNextYear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.YEAR, 1);
                updateCalendar();
            }
        });

        // subtract one month and refresh UI
        btnPrevYear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.YEAR, -1);
                updateCalendar();
            }
        });

        buttonNextMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, 1);
                updateCalendar();
            }
        });

        // subtract one month and refresh UI
        buttonPrevMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, -1);
                updateCalendar();
            }
        });

    }


    public void resetCurrentDate() {
        currentDate = Calendar.getInstance();
    }


    /**
     * Check input date before Max Date if maxDate not null
     */
    private boolean isTheDateBeforeMaxDate(Calendar date) {
        // if minDate is null,then forget it.
        return maxDate == null || date.before(maxDate);


    }


    /**
     * Check input date after Min Date if maxDate not null
     */
    private boolean isTheDateAfterMinDate(Calendar date) {
        // if minDate is null,then forget it.
        return minDate == null || date.after(minDate);


    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar() {
        updateCalendar(null);
    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar(HashSet<Calendar> events) {
        ArrayList<Calendar> cells = new ArrayList<>();
        Calendar calendar = (Calendar) currentDate.clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < DAYS_COUNT) {
            //because  direct put the calendar into list will make all list same.
            //so need to use the copy.
            cells.add((Calendar) calendar.clone());
            calendar.add(Calendar.DATE, 1);
        }

        // update grid

        // update title
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YEAR, Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat(DATE_FORMAT_MONTH, Locale.getDefault());

        txtDateYear.setText(sdf.format(currentDate.getTime()));
        txtDateMonth.setText(sdf2.format(currentDate.getTime()));

//        // set header color according to current season
//        int month = currentDate.get(Calendar.MONTH);
//        int season = monthSeason[month];
//        int color = rainbow[season];
//
//        header.setBackgroundColor(ContextCompat.getColor(this.getContext(), color));
    }

    /**
     * Assign event handler to be passed needed events
     */
    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    /**
     * This interface defines what events to be reported to
     * the outside world
     */
    public interface EventHandler {
        void onDayLongPress(Calendar date);
    }

    /**
     * Set the max Date which user can select.
     */
    public void setMaxDate(Calendar maxDate) {
        this.maxDate = maxDate;
    }


    /**
     * Set the min Date which user can select.
     */
    public void setMinDate(Calendar minDate) {
        this.minDate = minDate;
    }


    /**
     * Set the max Date which user can select.
     */
    public void setMaxDate(long maxDateMillionSecond) {
        maxDate = Calendar.getInstance();
        maxDate.setTimeInMillis(maxDateMillionSecond);
    }


    /**
     * Set the min Date which user can select.
     */
    public void setMinDate(long minDateMillionSecond) {
        minDate = Calendar.getInstance();
        minDate.setTimeInMillis(minDateMillionSecond);
    }
}
