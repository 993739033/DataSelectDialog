package com.bbny.qifengwlw.dataselectdialog.PopupWindowUtils;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;


public class OverScrollDecor extends FrameLayout {

    private ViewDragHelper mDragHelper;

    public OverScrollDecor(Context context) {
        this(context, null);
    }

    public OverScrollDecor(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OverScrollDecor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragHelper = ViewDragHelper.create(this, 1.0f, new OverScrollCallBack());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean shouldIntercept = false;
        try {
            shouldIntercept = mDragHelper.shouldInterceptTouchEvent(ev);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shouldIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            mDragHelper.processTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private class OverScrollCallBack extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            MarginLayoutParams params = (MarginLayoutParams) releasedChild.getLayoutParams();
            mDragHelper.smoothSlideViewTo(releasedChild, params.leftMargin, params.topMargin);
            ViewCompat.postInvalidateOnAnimation(OverScrollDecor.this);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return Math.abs(child.getHeight());
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return child.getLeft();
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return child.getTop() + dy / 2;
        }
    }
}