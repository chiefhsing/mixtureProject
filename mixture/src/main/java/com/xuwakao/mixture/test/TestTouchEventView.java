package com.xuwakao.mixture.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.xuwakao.mixture.framework.utils.MLog;

/**
 * Created by xujiexing on 13-11-12.
 */
public class TestTouchEventView extends View {
    private static final String TAG = "TestTouchEventView";

    public TestTouchEventView(Context context) {
        super(context);
    }

    public TestTouchEventView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestTouchEventView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        MLog.verbose(TAG, "onTouchEvent return " + super.onTouchEvent(event) + " action = " + event.getAction());
        return true;
    }
}
