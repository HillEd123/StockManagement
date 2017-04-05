package com.edward.stockmanagement.VIEWS;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Edward on 2017/04/05.
 */

    public class NonSwipableViewPager extends ViewPager {

        public NonSwipableViewPager(Context context) {
            super(context);
        }

        public NonSwipableViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent arg0) {
            // Never allow swiping to switch between pages
            return false;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            // Never allow swiping to switch between pages
            return false;
        }
    }



