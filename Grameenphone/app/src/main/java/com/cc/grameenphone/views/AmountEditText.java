package com.cc.grameenphone.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Rajkiran on 10/30/2015.
 */
public class AmountEditText extends EditText {


    public AmountEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
        protected void onSelectionChanged(int selStart, int selEnd) {
            if (selStart == 0 || selStart == 1)
                setSelection(getText().length());
            super.onSelectionChanged(selStart, selEnd);
        }
    }

