package com.jastzeonic.calendar.layoutmangers;

/**
 * Created by ptc_02008 on 2017/1/25.
 */


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class ViewPagerLayoutManager extends AbstractPagerLLM<ViewPagerLayoutManager> {

    public ViewPagerLayoutManager(Context context) {
        super(context);
    }

    public ViewPagerLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ViewPagerLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }


    @Override
    public int getPosition(View view) {
        return super.getPosition(view);
    }
}
