package com.sinarkelas.util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StylingUtil {

    private Typeface typeface;

    public StylingUtil() {
    }

    public void montserratRegularTextview(Context context, TextView textView) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.ttf");
        textView.setTypeface(typeface);
    }

    public void montserratMediumTextview(Context context, TextView textView) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Medium.ttf");
        textView.setTypeface(typeface);
    }

    public void montserratSemiBoldTextview(Context context, TextView textView) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-SemiBold.ttf");
        textView.setTypeface(typeface);
    }

    public void montserratBoldTextview(Context context, TextView textView) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Bold.ttf");
        textView.setTypeface(typeface);
    }

    public void montserratRegularEdittext(Context context, EditText editText) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.ttf");
        editText.setTypeface(typeface);
    }

    public void montserratMediumButton(Context context, Button btn) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Medium.ttf");
        btn.setTypeface(typeface);
    }

}
