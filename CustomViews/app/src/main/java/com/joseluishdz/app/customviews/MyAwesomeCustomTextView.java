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

    private Bitmap bitmap;
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    String c1 = "";
    String c2 = "";
    String c3 = "=";
    int w = 0;

    public MyAwesomeCustomTextView(Context context) {
        super(context);
    }

    public MyAwesomeCustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyAwesomeCustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setValues(String from, String to) {
        this.c1 = from;
        this.c2 = to;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        bitmap = BitmapFactory
                .decodeResource(getResources(), R.drawable.bitcoin);

        w = Math.round(convertDpToPixel(32, getContext()));

        Rect src = new Rect(0,0,bitmap.getWidth()-1, bitmap.getHeight()-1);
        Rect dest = new Rect(getPaddingLeft(),getPaddingTop(), w +getPaddingLeft(), w + getPaddingTop());
        canvas.drawBitmap(bitmap, src, dest, null);

        textPaint.setTextSize(convertDpToPixel(16, getContext()));

        Rect textBounds = new Rect();
        textPaint.getTextBounds(c1, 0, c1.length(), textBounds);
        canvas.drawText(c1,getPaddingLeft() + dest.width() + 16, dest.height(), textPaint);

        bitmap = BitmapFactory
                .decodeResource(getResources(), R.drawable.dogecoin);

        Rect dest2 = new Rect(canvas.getWidth() - w - getPaddingRight() ,getPaddingTop(), canvas.getWidth()-getPaddingRight(), w + getPaddingTop());
        canvas.drawBitmap(bitmap , src, dest2, null);

        textPaint.getTextBounds(c2, 0, c2.length(), textBounds);

        canvas.drawText(c2,dest2.left - textBounds.width() -16, dest2.height(), textPaint);

        textPaint.getTextBounds(c3, 0, c3.length(), textBounds);
        canvas.drawText(c3,canvas.getWidth()/2 - textBounds.width()/2, dest2.height(), textPaint);

        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(widthSize, w +getPaddingTop() + getPaddingBottom());
    }

    public static float convertDpToPixel(float dp,Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi/160f);
        return px;
    }
}
