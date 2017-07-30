package com.wizeline.cryptoconverter.conversionList.listItem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;

import com.wizeline.cryptoconverter.R;
import com.wizeline.cryptoconverter.data.model.Conversion;
import com.wizeline.cryptoconverter.mvvm.ViewModel;

/**
 * Created by Nicole Terc on 7/27/17.
 */

public class ListItemViewModel extends ViewModel {
    Conversion item;
    Context context;

    public ListItemViewModel(Context context) {
        super(null);
        this.context = context;
    }

    public void setItem(Conversion item) {
        this.item = item;
        notifyChange();
    }

    public Conversion getItem() {
        return item;
    }

    public SpannableString getChangeText(){
        if(item == null){
            return new SpannableString("");
        }
        return getFormattedChange(item.getChange(), item.getPriceDisplay());
    }

    private SpannableString getFormattedChange(Double change, String price) {
        int iconRes;
        int colorRes;

        if (change > 0) { //positive change
            iconRes = R.drawable.ic_arrow_up;
            colorRes = R.color.colorChangeUp;
        } else if (change < 0) { //negative change
            iconRes = R.drawable.ic_arrow_down;
            colorRes = R.color.colorChangeDown;
        } else { //no change
            iconRes = R.drawable.ic_no_change;
            colorRes = R.color.colorText;
        }

        Drawable iconDrawable = context.getResources().getDrawable(iconRes);
        iconDrawable.setBounds(0, 0, iconDrawable.getIntrinsicWidth(), iconDrawable.getIntrinsicHeight());
        int changeColor = context.getResources().getColor(colorRes);

        SpannableString formattedChange = new SpannableString(" " + price);
        formattedChange.setSpan(new ForegroundColorSpan(changeColor), 0, price.length() + 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        formattedChange.setSpan(new ImageSpan(iconDrawable, DynamicDrawableSpan.ALIGN_BOTTOM), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return formattedChange;
    }
}
