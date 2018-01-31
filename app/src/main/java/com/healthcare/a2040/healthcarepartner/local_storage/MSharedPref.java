package com.healthcare.a2040.healthcarepartner.local_storage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by SHIVAM on 2/28/2017.
 */
public class MSharedPref {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context mContext;
    protected final int MODE = 0;
    protected final String PREF_NAME = "OBEY.sharedPref";

    public MSharedPref(Context mContext) {
        this.mContext = mContext;
        pref = mContext.getSharedPreferences(PREF_NAME, MODE);
        editor = pref.edit();
    }

    public void setPrefranceValue(String key, boolean isFirstTime) {
        editor.putBoolean(key, isFirstTime);
        editor.commit();
    }

    public void setPrefranceValue(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void setPrefranceValue(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public boolean getPrefranceBooleanValue(String key) {
        return pref.getBoolean(key, false);
    }


    public String getPrefranceStringValue(String key) {
        return pref.getString(key, "Not Found");
    }


    public int getPrefranceIntValue(String key) {
        return pref.getInt(key, -1);
    }


}
