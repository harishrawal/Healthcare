package com.healthcare.a2040.healthcarepartner.ui_activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.healthcare.a2040.healthcarepartner.R;
import com.healthcare.a2040.healthcarepartner.app_interfaces.WebResultCallback;
import com.healthcare.a2040.healthcarepartner.app_utils.CustomMethods;
import com.healthcare.a2040.healthcarepartner.controllers.LoginController;
import com.healthcare.a2040.healthcarepartner.controllers.SignUpController;

public class RequestConfirmationStatus extends SuperCompatActivity {

    private final String TAG = RequestConfirmationStatus.class.getName();

    private Context mContext;
    private boolean submitSignUpData = false;
    private SignUpController signUpController;
    private LoginController loginController;
    private TextView textConfirmation, textConfirmationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_confirmation_status);

        mContext = RequestConfirmationStatus.this;
        signUpController = new SignUpController(mContext);
        loginController = new LoginController(mContext);

        textConfirmation = findViewById(R.id.textConfirmation);
        textConfirmationBtn = findViewById(R.id.textConfirmationBtn);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            submitSignUpData = bundle.getBoolean("submitData");
        }


        if (submitSignUpData) {

            textConfirmation.setText("We'r submitting your details for verification process. we'll get back soon");

            if (StepperSignupActivity.getUserInfo() != null) {

                showProgressDialog("Signing Up", "Please wait...", false);

                signUpController.submitSignUpDetails(StepperSignupActivity.getUserInfo(), new WebResultCallback() {
                    @Override
                    public void onResult(int SuccessType, String result) {

                        Log.e(TAG, SuccessType + "-->" + result);

                        removeProgressDialog();
                    }
                });

            } else {

                toastMsg("Data Not Found");
            }

        } else {

            verifyOnline();

        }

        textConfirmationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verifyOnline();
            }
        });


        textConfirmationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(mContext, DashboardActivity.class));
                onStop();
            }
        });
    }

    private void verifyOnline() {

        textConfirmation.setText("We'r verifying your details. we'll get back soon");

        showProgressDialog("Verifying...", "Please wait...", false);

        loginController.checkVerificationByServer(new WebResultCallback() {
            @Override
            public void onResult(int SuccessType, String result) {

                Log.e(TAG, SuccessType + "-->" + result);

                removeProgressDialog();
            }
        });
    }

    @Override
    protected void onStop() {
        finish();
        super.onStop();
    }
}
