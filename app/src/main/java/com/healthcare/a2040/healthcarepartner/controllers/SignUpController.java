package com.healthcare.a2040.healthcarepartner.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.healthcare.a2040.healthcarepartner.app_interfaces.CallBacks;
import com.healthcare.a2040.healthcarepartner.app_interfaces.WebResultCallback;
import com.healthcare.a2040.healthcarepartner.app_utils.AppConstants;
import com.healthcare.a2040.healthcarepartner.obj_holders.UserInformationHolder;
import com.healthcare.a2040.healthcarepartner.ui_activities.SignUpActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amit-PC on 1/18/2018.
 */

public class SignUpController {
    private Context mContext;

    public SignUpController(Context context) {
        this.mContext = context;
    }

    public boolean validateSignUpStep1(String... parametsrs) {
        for (String parm : parametsrs) {
            if (parm == null || parm.length() < 3) {
                return false;
            }
        }
        return true;
    }

    public void submitSignUpDetails(final UserInformationHolder userHolder, final WebResultCallback callBacks) {

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
                params.put("email", userHolder.getUserNameF());
                params.put("email", userHolder.getUserNameL());
                params.put("email", userHolder.getUserMobile());
                params.put("email", userHolder.getUserEMail());
                params.put("email", userHolder.getUserQualfication());
                params.put("email", userHolder.getUserSpec());
                params.put("email", userHolder.getUserCity());
                params.put("email", userHolder.getUserIMAReg());
                params.put("email", userHolder.getUserRegisCouncil());
                params.put("email", userHolder.getUserYearPassing());
                params.put("email", userHolder.getAadharCardPath());
                params.put("email", userHolder.getAddressPrrofPath());
                params.put("email", userHolder.getVoterIdPath());
                Log.e("params", params.toString());
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }
}
