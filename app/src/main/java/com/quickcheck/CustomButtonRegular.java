package com.quickcheck;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class CustomButtonRegular extends Button {

    public CustomButtonRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTransformationMethod(null);

    }
}
