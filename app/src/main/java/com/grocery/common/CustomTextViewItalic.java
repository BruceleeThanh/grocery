package com.grocery.common;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by thuyetpham94 on 03/10/2016.
 */
public class CustomTextViewItalic extends AppCompatTextView {

    public CustomTextViewItalic(Context context) {
        super(context);
        setFont();
    }

    public CustomTextViewItalic(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public CustomTextViewItalic(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    private void setFont() {
        Typeface textViewTypeface = Utils.getUtmHelveItalicTypeFace(getContext());
        setTypeface(textViewTypeface);
    }
}
