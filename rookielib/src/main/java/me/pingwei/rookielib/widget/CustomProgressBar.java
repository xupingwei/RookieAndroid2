package me.pingwei.rookielib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import me.pingwei.rookielib.utils.DisplayHelper;


/**
 * Created by xupingwei on 2015/5/27.
 */
public class CustomProgressBar extends ProgressBar {
    String text;
    Paint mPaint;
    private Context mContext;

    public CustomProgressBar(Context context) {
        super(context);
        this.mContext = context;
        System.out.println("1");
        initText();
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        System.out.println("2");
        initText();
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        System.out.println("3");
        initText();
    }

    @Override
    public synchronized void setSecondaryProgress(int secondaryProgress) {
        setText(secondaryProgress);
        super.setSecondaryProgress(secondaryProgress);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //this.setText();
        Rect rect = new Rect();
        this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
        int x = (getWidth() / 2) - rect.centerX();

        int y = (getHeight() / 2) - rect.centerY();
        canvas.drawText(this.text, x, y, this.mPaint);
    }

    //初始化，画笔
    private void initText() {
        this.mPaint = new Paint();
//        this.mPaint.setTypeface(FontsUtils.getTypeface(mContext));
        this.mPaint.setColor(Color.WHITE);
        this.mPaint.setTextSize(DisplayHelper.dip2px(mContext, 14));
    }

    //  private void setText() {
    //      setText(this.getProgress());
    //  }

    //设置文字内容
    private void setText(int progress) {
//        int i = (progress * 1) / this.getMax();
        this.text = String.valueOf(progress) + "%完整度";
    }
}