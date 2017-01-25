package com.jastzeonic.calendar.layoutmangers;

import android.content.Context;
import android.util.AttributeSet;


public class SnapperLinearLayoutManager extends AbstractSnapperLLM<SnapperLinearLayoutManager> {


    public SnapperLinearLayoutManager(Context context) {
        super(context);

    }

    public SnapperLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    public SnapperLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }



}