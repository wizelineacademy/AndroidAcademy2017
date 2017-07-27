package com.joseluishdz.app.customviews;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by drac94 on 7/12/17.
 */

public class MyAwesomeCustomTextView extends View {

    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    String text = "Hello World!";

    public MyAwesomeCustomTextView(Context context) {
        super(context);
    }

    public MyAwesomeCustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyAwesomeCustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        try {
            Typeface font = Typeface.createFromAsset(getResources().getAssets(), "fonts/warsawgothic.otf");
            if (font != null) {
                textPaint.setTypeface(font);
            }
        } catch (RuntimeException e) {

        }
        textPaint.setTextSize(convertDpToPixel(20, getContext()));
        textPaint.setColor(getResources().getColor(R.color.colorAccent));

        Rect textBounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), textBounds);
        canvas.drawText(text, getPaddingLeft(), getPaddingTop() + textBounds.height(), textPaint);

        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int desiredWidth = measureWidth(widthMeasureSpec);
        int desiredHeight = measureHeight(heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {  // match_parent
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) { // wrap_content
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }

    private int measureWidth(int measureSpec) {
        int size = getPaddingLeft() + getPaddingRight();
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        size += bounds.width();
        return resolveSizeAndState(size, measureSpec, 0);
    }

    private int measureHeight(int measureSpec) {
        int size = getPaddingTop() + getPaddingBottom();
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        size += bounds.height();
        return resolveSizeAndState(size, measureSpec, 0);
    }

    public static float convertDpToPixel(float dp,Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi/160f);
        return px;
    }
}
