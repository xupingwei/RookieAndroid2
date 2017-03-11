package me.pingwei.rookielib.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import me.pingwei.rookielib.utils.LoggerUtils;


/**
 * Created by Administrator on 2015/9/7.
 */
public class CustomScrollView extends NestedScrollView {
    private GestureDetector mGestureDetector;
    private int Scroll_height = 0;
    private int view_height = 0;
    protected Field scrollView_mScroller;
    private static final String TAG = "CustomScrollView";
    private OnScrollListener onScrollListener;
    // 滑动距离及坐标
    private float xDistance, yDistance, xLast, yLast;
    private Context mContext;

    public CustomScrollView(Context context) {
        this(context, null);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
        setFadingEdgeLength(0);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;

                if (xDistance > yDistance) {
                    LoggerUtils.e("yDistance=---------------" + yDistance);
                    return false;
                }
        }

        return super.onInterceptTouchEvent(ev);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (onScrollListener != null) {
            onScrollListener.onScroll(l, t, oldl, oldt);
            LoggerUtils.e("t--------------------" + t);
            LoggerUtils.e("l--------------------" + l);
            LoggerUtils.e("oldl--------------------" + oldl);
            LoggerUtils.e("oldt--------------------" + oldt);
        }
        boolean stop = false;
        if (Scroll_height - view_height == t) {
            stop = true;
        }

        if (t == 0 || stop == true) {
            try {
                if (scrollView_mScroller == null) {
                    scrollView_mScroller = getDeclaredField(this, "mScroller");
                }

                Object ob = scrollView_mScroller.get(this);
                if (ob == null || !(ob instanceof Scroller)) {
                    return;
                }
                Scroller sc = (Scroller) ob;
                sc.abortAnimation();

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    private void stopAnim() {
        try {
            if (scrollView_mScroller == null) {
                scrollView_mScroller = getDeclaredField(this, "mScroller");
            }

            Object ob = scrollView_mScroller.get(this);
            if (ob == null) {
                return;
            }
            Method method = ob.getClass().getMethod("abortAnimation");
            method.invoke(ob);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    // Return false if we're scrolling in the x direction
    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (Math.abs(distanceY) > Math.abs(distanceX)) {
                return true;
            }
            return false;
        }
    }

    /**
     * 设置滚动接口
     *
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }


    /**
     * 滚动的回调接口
     */
    public interface OnScrollListener {
        /**
         * 回调方法， 返回MyScrollView滑动的Y方向距离
         *
         * @param l
         * @param t
         * @param oldl
         * @param oldt 、
         */
        public void onScroll(int l, int t, int oldl, int oldt);
    }


    @Override
    public int computeVerticalScrollRange() {
        Scroll_height = super.computeVerticalScrollRange();
        return Scroll_height;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed == true) {
            view_height = b - t;
        }
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        if (focused != null && focused instanceof WebView) {
            return;
        }
        super.requestChildFocus(child, focused);
    }

    /**
     * 获取一个对象隐藏的属性，并设置属性为public属性允许直接访问
     *
     * @return {@link Field} 如果无法读取，返回null；返回的Field需要使用者自己缓存，本方法不做缓存�?
     */
    public static Field getDeclaredField(Object object, String field_name) {
        Class<?> cla = object.getClass();
        Field field = null;
        for (; cla != Object.class; cla = cla.getSuperclass()) {
            try {
                field = cla.getDeclaredField(field_name);
                field.setAccessible(true);
                return field;
            } catch (Exception e) {

            }
        }
        return null;
    }
}
