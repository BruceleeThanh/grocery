package com.grocery.common;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class CustomTextviewBold extends AppCompatTextView {

    public CustomTextviewBold(Context context) {
        super(context);
        setFont();
    }

    public CustomTextviewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public CustomTextviewBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    private void setFont() {
        Typeface textViewTypeface = Utils.getUtmHelveBoldTypeFace(getContext());
        setTypeface(textViewTypeface);
    }
}
