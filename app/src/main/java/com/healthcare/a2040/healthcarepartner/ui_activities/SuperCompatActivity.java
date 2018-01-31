package com.healthcare.a2040.healthcarepartner.ui_activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

import com.healthcare.a2040.healthcarepartner.app_utils.CustomMethods;

/**
 * Created by Amit-PC on 1/17/2018.
 */

public class SuperCompatActivity extends AppCompatActivity {
    public Context mctx;
    public CustomMethods mcmethods;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mctx = SuperCompatActivity.this;
        mcmethods = new CustomMethods(mctx);
    }

    public void setTitle(String titleText) {
        getSupportActionBar().setTitle(titleText);
    }

    public void setTitleWithBackButton(String titleText) {
        getSupportActionBar().setTitle(titleText);
        setBackButton();
    }

    public void setBackButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void enableViews(View... views) {
        for (View v0 : views) {
            v0.setVisibility(View.VISIBLE);
        }

    }

    public void toastMsg(String msgText) {
        mcmethods.msgbox(msgText);
    }

    public void disableViews(View... views) {
        for (View v0 : views) {
            v0.setVisibility(View.GONE);
        }
    }

    private ProgressDialog mProgressDialog;

    public void showProgressDialog(@Nullable String textTitle, @Nullable String bodyText, final boolean isRequestCancelable) {
        try {
            if (isFinishing()) {
                return;
            }
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(mctx);
                mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                // mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                mProgressDialog.setCancelable(isRequestCancelable);
                //mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.setOnKeyListener(new Dialog.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_CAMERA || keyCode == KeyEvent.KEYCODE_SEARCH) {
                            return true; //
                        } else if (keyCode == KeyEvent.KEYCODE_BACK && isRequestCancelable) {
                            Log.e("Ondailogback", "cancel dailog");
                            // RequestManager.cancelRequest();
                            dialog.dismiss();
                            return true;
                        }
                        return false;
                    }
                });
            }
            if (textTitle != null) {
                mProgressDialog.setTitle(textTitle);
            }
            if (bodyText != null) {
                mProgressDialog.setMessage(bodyText);
            }


            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
                //mProgressDialog.setContentView(new ProgressBar(this));
            }
        } catch (Exception e) {

        }
    }

    public void removeProgressDialog() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {

        }
    }


}
