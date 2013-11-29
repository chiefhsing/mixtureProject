package com.xuwakao.mixture.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;

import com.xuwakao.mixture.R;
import com.xuwakao.mixture.framework.utils.MLog;

/**
 * Created by xujiexing on 13-11-11.
 */
public class VideoDragLayout extends ViewGroup {
    private static final String TAG = "VideoDragLayout";
    private int mHeaderId;
    private int mFooterId;

    private int mHeaderHeight;
    private int mFooterHeight;
    private int mViewWidth;
    private int mViewHeight;

    private ViewDragHelper mDragHelper;
    private VelocityTracker mVelocityTracker;
    private View mHeader;
    private View mFooter;

    private float mOriginalX;
    private float mOriginalY;
    private float mPreX;
    private float mPreY;
    private float mPostX;
    private float mPostY;

    private float mVelocityY;
    private float mVelocityX;

    private int minHeaderWidth;
    private int minHeaderHeight;

    private LayoutParams mFinalLayoutParams;

    /**
     * Header view offsetY
     */
    private float mPositionOffsetY;
    private float mOffsetX;

    public VideoDragLayout(Context context) {
        super(context);
        initialize();
    }

    public VideoDragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public VideoDragLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    @Override
    protected void onFinishInflate() {
        MLog.verbose(TAG, "VideoDrag onFinishInflate");
        super.onFinishInflate();

        mHeader = findViewById(R.id.headerId);
        mFooter = findViewById(R.id.footerId);
    }

    /**
     * Do initialization work
     */
    private void initialize() {
        mDragHelper = ViewDragHelper.create(this, callback);
    }

    private float mCurrentExpectHeight;
    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return false;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            float finalChangedHeight = mHeaderHeight - mFinalLayoutParams.height;
            float finalOffsetY = mFinalLayoutParams.topMargin;
            mCurrentExpectHeight = finalChangedHeight * (mPositionOffsetY / finalOffsetY);
            MLog.verbose(TAG, "changedView = "+ changedView + ", mCurrentExpectHeight = " + mCurrentExpectHeight);
        }
    };

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() == 2) {
            for (int i = 0; i < getChildCount(); i++) {
                final View child = getChildAt(i);
                if (child.getVisibility() != View.GONE) {
                    final LayoutParams lp = (LayoutParams) child.getLayoutParams();
//                    MLog.verbose(TAG, "onLayout " + lp.toString() + " and mPositionOffsetY = " + mPositionOffsetY);

                    if (lp.position == LayoutParams.POSITION_ABOVE) {
                        child.layout((int) mOffsetX, (int) mPositionOffsetY, (int) (mOffsetX + child.getMeasuredWidth()), (int) (mHeaderHeight + mPositionOffsetY));
                    } else if (lp.position == LayoutParams.POSITION_BELOW) {
                        child.layout(0, (int) mPositionOffsetY + mHeaderHeight, child.getMeasuredWidth(), (int) (mPositionOffsetY + mViewHeight));
                    } else {
                        throw new IllegalStateException("positon should not be POSITION_NONE");
                    }
                }
            }
        } else {
            throw new IllegalStateException("childCount must be two");
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);

        if (getChildCount() == 2) {
            for (int i = 0; i < getChildCount(); i++) {
                final View child = getChildAt(i);
                if (child.getVisibility() != View.GONE) {
                    // Measure the child.
                    measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);

                    final LayoutParams lp = (LayoutParams) child.getLayoutParams();
//                    MLog.verbose(TAG, "onMeasure " + lp.toString() + " and mPositionOffsetY = " + mPositionOffsetY);

                    final int width = child.getMeasuredWidth();
                    final int height = child.getMeasuredHeight();

                    /**mViewWidth only initialize once*/
                    mViewWidth = mViewWidth <= 0 ? width : mViewWidth;
                    if (lp.position == LayoutParams.POSITION_ABOVE) {
//                        MLog.verbose(TAG, "header height = " + height);
                        /**mHeaderHeight only initialize once*/
                        mHeaderHeight = mHeaderHeight <= 0 ? height : mHeaderHeight;
                    } else if (lp.position == LayoutParams.POSITION_BELOW) {
                        /**mFooterHeight only initialize once*/
                        mFooterHeight = mFooterHeight <= 0 ? heightMeasureSize - mHeaderHeight : mFooterHeight;
//                        MLog.verbose(TAG, "footer height = " + mFooterHeight);
                    } else {
                        throw new IllegalStateException("positon should not be POSITION_NONE");
                    }
                }
            }

            /**mViewHeight only initialize once*/
            mViewHeight = mViewHeight <=0 ? mFooterHeight + mHeaderHeight : mViewHeight;

            mFinalLayoutParams = new LayoutParams(mViewWidth / 2, mHeaderHeight / 2);
            mFinalLayoutParams.setMargins(mViewWidth - mFinalLayoutParams.width , mViewHeight - mFinalLayoutParams.height, 0 ,0);
        } else {
            throw new IllegalStateException("childCount must be two");
        }

        setMeasuredDimension(widthMeasureSize, heightMeasureSize);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mOriginalX = mPreX = ev.getRawX();
                mOriginalY = mPreY = ev.getRawY();
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        MLog.debug(TAG, "onTouchEvent action = " + action);

        int index = event.getActionIndex();
        int pointerId = event.getPointerId(index);

        int currentTop = mHeader.getTop();
        int currentBottom = mHeader.getBottom();

        mPostX = event.getRawX();
        mPostY = event.getRawY();

        float eventOffsetY = mPostY - mPreY;
        mPositionOffsetY = eventOffsetY + mPositionOffsetY;

        if (currentTop <= 0 && action == MotionEvent.ACTION_MOVE && mPositionOffsetY < 0) {
            resetPositionYZero();
            return false;
        }

        if (currentBottom >= mViewHeight && action == MotionEvent.ACTION_MOVE && eventOffsetY > 0) {
            resetPositionYFull();
            return false;
        }

//        Rect rect = new Rect();
//        mHeader.getHitRect(rect);
//
//        if (!rect.contains((int) mPostX, (int) mPostY)) {
//            mPreY = mPostY;
//            return false;
//        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if(mVelocityTracker == null) {
                    // Retrieve a new VelocityTracker object to watch the velocity of a motion.
                    mVelocityTracker = VelocityTracker.obtain();
                }
                else {
                    // Reset the velocity tracker back to its initial state.
                    mVelocityTracker.clear();
                }
                // Add a user's movement to the tracker.
                mVelocityTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.addMovement(event);
                mVelocityTracker.computeCurrentVelocity(1000);
                mVelocityY = VelocityTrackerCompat.getYVelocity(mVelocityTracker,pointerId);
                mVelocityX = VelocityTrackerCompat.getXVelocity(mVelocityTracker,pointerId);

                int changeY = (int) Math.abs(mPostY - mPreY);
                if (changeY > 1 /**|| Math.abs(mPostX - mPreX) > 5**/) {
//                    MLog.verbose(TAG, "onTouchEvent moving, mPositionOffsetY = " + mPositionOffsetY);
                    if (mDragHelper.smoothSlideViewTo(mHeader, (int) (mHeader.getLeft() + mOffsetX), (int) (mHeader.getBottom() + mPositionOffsetY))) {
                    }
                    mPreY = mPostY;
                    mPreX = mPostX;
                    requestLayout();
                    invalidate();
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                MLog.debug(TAG, "ACITON_UP is being called, currentTop = " + currentTop + ", currentBottom = " + currentBottom);
                if(currentTop < 0){
                    resetPositionYZero();
                }

                if(currentBottom > mViewHeight){
                    resetPositionYFull();
                }

                MLog.verbose(TAG, "X velocity: " + mVelocityX);
                MLog.verbose(TAG, "Y velocity: " + mVelocityY);

                if(mVelocityY > 3000){
                    resetPositionYFull();
                }

                if(mVelocityY < -3000){
                    resetPositionYZero();
                }

                break;
            case MotionEvent.ACTION_CANCEL:
                // Return a VelocityTracker object back to be re-used by others.
                mVelocityTracker.recycle();
                break;
            default:
                break;
        }
        return true;
    }

    private void resetPositionYFull() {
        mPreY = mPostY;
        mPositionOffsetY = mFooterHeight;
        mDragHelper.smoothSlideViewTo(mHeader, 0, (int) mPositionOffsetY);
        requestLayout();
        invalidate();
    }

    /**
     * Move header to the initilized postion [0, 0]
     */
    private void resetPositionYZero() {
        mPreY = mPostY;
        mPositionOffsetY = 0;
        mDragHelper.smoothSlideViewTo(mHeader, 0, (int) mPositionOffsetY);
        requestLayout();
        invalidate();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new VideoDragLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    /**
     * Custom per-child layout information.
     */
    public static class LayoutParams extends MarginLayoutParams {

        public static int POSITION_NONE = 0;
        public static int POSITION_ABOVE = 1;
        public static int POSITION_BELOW = 2;

        public int position = POSITION_NONE;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);

            // Pull the layout param values from the layout XML during
            // inflation.  This is not needed if you don't care about
            // changing the layout behavior in XML.
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.DragVideoStyle);
            position = a.getInt(R.styleable.DragVideoStyle_layout_position, position);
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        @Override
        public String toString() {
            return "[width = " + width + ", height = " + height + ", position = " + position + "]";
        }
    }
}
