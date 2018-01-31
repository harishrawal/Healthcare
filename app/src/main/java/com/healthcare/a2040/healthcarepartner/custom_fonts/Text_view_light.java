package com.healthcare.a2040.healthcarepartner.custom_fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Study India on 3/2/2016.
 */
public class Text_view_light extends android.support.v7.widget.AppCompatTextView {
    public Text_view_light(Context context, AttributeSet set) {
        super(context,set);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"font/SourceSansPro_Light.ttf"));
    }
}
