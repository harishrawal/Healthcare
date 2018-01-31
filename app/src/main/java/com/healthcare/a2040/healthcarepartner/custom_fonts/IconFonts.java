package com.healthcare.a2040.healthcarepartner.custom_fonts;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by ABC on 6/16/2016.
 */
public class IconFonts {

    public static Typeface getTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "font/fontawesome_webfont.ttf");
    }



}
