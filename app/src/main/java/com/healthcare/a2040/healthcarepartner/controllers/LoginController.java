package com.healthcare.a2040.healthcarepartner.controllers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.healthcare.a2040.healthcarepartner.app_interfaces.WebResultCallback;
import com.healthcare.a2040.healthcarepartner.app_utils.AppConstants;
import com.healthcare.a2040.healthcarepartner.app_utils.CustomMethods;
import com.healthcare.a2040.healthcarepartner.local_storage.MSharedPref;
import com.healthcare.a2040.healthcarepartner.ui_activities.DashboardActivity;
import com.healthcare.a2040.healthcarepartner.ui_activities.LoginActivity;
import com.healthcare.a2040.healthcarepartner.ui_activities.RequestConfirmationStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amit-PC on 1/19/2018.
 */

public class LoginController {
    private Context mContext;
    private CustomMethods mCustomMethods;
    private MSharedPref mSharedPref;

    public LoginController(Context ctx) {
        this.mContext = ctx;
        this.mCustomMethods = new CustomMethods(mContext);
        this.mSharedPref = new MSharedPref(mContext);
    }

    public boolean checkLogin() {

        if (mSharedPref.getPrefranceBooleanValue(AppConstants.S_IS_LOGGED_IN)) {
            return true;

        }
        return false;
    }

    public boolean checkVerifiedLocal() {

        if (mSharedPref.getPrefranceBooleanValue(AppConstants.S_IS_VERIFIED)) {
            return true;

        }
        return false;
    }

    public void navigateInside() {

        if (checkLogin()) {

            if (checkVerifiedLocal()) {
                Intent intent = new Intent(mContext, DashboardActivity.class);
                mContext.startActivity(intent);

            } else {

                Intent intent = new Intent(mContext, RequestConfirmationStatus.class);
                intent.putExtra("submitData", false);
                mContext.startActivity(intent);
            }

        } else {

            // mContext.startService(new Intent(mContext, MyFirebaseInstanceIDService.class));

            Intent intent = new Intent(mContext, LoginActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            mContext.startActivity(intent);

        }
    }


    public void checkVerificationByServer(final WebResultCallback callBacks) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.URL_SignUp,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callBacks.onResult(AppConstants.SUCCESS, response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callBacks.onResult(AppConstants.FAILURE, error.toString());
                        Log.e("error", error.toString());

                    }
                }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", mSharedPref.getPrefranceStringValue(AppConstants.S_USER_ID));

                Log.e("params", params.toString());
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);

    }

}
