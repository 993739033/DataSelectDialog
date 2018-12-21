package com.bbny.qifengwlw.dataselectdialog.PopupWindowUtils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2018/12/2.
 */
//能够滑动
public class DragMoveLayout extends LinearLayout {
    private ViewDragHelper mDragHelper;
    private int Drag_Up_MAX = 200;//向上滑动的最大值
    private float Min_Speed;
    private int height;
    private int width;
    private int top;

    private SlideListener mListener;//打开关闭回调

    void setSlideListener(SlideListener listener) {
        mListener = listener;
    }


    public DragMoveLayout(@NonNull Context context) {
        super(context);
        initView();
    }

    public DragMoveLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DragMoveLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mDragHelper = ViewDragHelper.create(this, 0.8f, new ViewDragCallback());//0.8敏感度
        Min_Speed = getResources().getDisplayMetrics().density * 400;//400dp
    }


    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
            if (mListener != null) {
                if (getChildAt(0) == null) return;
                int h = getChildAt(0).getTop();
                mListener.onDragProgress(height - h);
            }
        } else if (getChildAt(0) == null) return;
        int h = getChildAt(0).getTop();
        if (h >= height) {
            Log.d("h", "computeScroll: " + h + ":" + height);
            if (mListener != null) {
                mListener.onClosed();
            }
        }
        super.computeScroll();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = getMeasuredHeight();//closeableView 高度
        width = getMeasuredWidth();//closeableView 宽度
        top = getPaddingTop();//获取paddingtop值
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("onlayout", "onLayout: " + changed);
    }

    private void dismiss(View view, float yvel) {
        mDragHelper.settleCapturedViewAt(0, height);//模仿手指滑动到最初位置 最下方
        postInvalidate();
    }

    private void show(View view, float yvel) {
        mDragHelper.settleCapturedViewAt(0, top);//模仿手指滑动到最初位置 最下方
        postInvalidate();
    }

    /**
     * Callback
     */
    private class ViewDragCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            if (child == DragMoveLayout.this.getChildAt(0)) {
                return true;
            }
            return false;
        }//必须返回true 才能进行其他处理


        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {//竖直拖拽时回调
            return Math.max(top, DragMoveLayout.this.getTop() - Drag_Up_MAX);//返回实际滑动的数值 此处可做滑动范围限制
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {//拖拽位置改变回调
            if (mListener != null) {
                mListener.onDragProgress(height - top);
            }

            if (height - top < 10 && mListener != null) {
                mDragHelper.smoothSlideViewTo(changedView, 0, height);//关闭dialog
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {//滑动结束  回调值为速率
            show(releasedChild, yvel);
        }
    }

    /**
     * set listener
     */
    interface SlideListener {
        void onClosed();

        void onDragProgress(int top);
    }
}
