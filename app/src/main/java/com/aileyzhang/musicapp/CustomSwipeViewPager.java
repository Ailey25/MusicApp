package com.aileyzhang.musicapp;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Ailey on 2018-01-20.
 */

public class CustomSwipeViewPager extends ViewPager {

    private Boolean isSwipeEnabled = false;

    public CustomSwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setIsSwipeEnabled(Boolean b) {
        isSwipeEnabled = b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isSwipeEnabled) {
            return super.onInterceptTouchEvent(ev);
        }
            return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isSwipeEnabled) {
            return super.onTouchEvent(ev);
        }
        return false;
    }
}
