package com.joseluishdz.app.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by drac94 on 7/12/17.
 */

public class MyAwesomeTextView extends android.support.v7.widget.AppCompatTextView {
    public MyAwesomeTextView(Context context) {
        super(context);
    }

    public MyAwesomeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyAttributes(context, attrs);
    }

    public MyAwesomeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyAttributes(context, attrs);
    }

    private void applyAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyAwesomeTextView);
        String fontName = a.getString(R.styleable.MyAwesomeTextView_matw_font);
        try {
            Typeface font = Typeface.createFromAsset(getResources().getAssets(), "fonts/" + fontName + ".otf");
            if (font != null) {
                this.setTypeface(font);
            }
        } catch (RuntimeException e) {

        }
        a.recycle();
    }
}
