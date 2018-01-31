package com.healthcare.a2040.healthcarepartner.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.healthcare.a2040.healthcarepartner.R;
import com.healthcare.a2040.healthcarepartner.app_utils.CustomMethods;
import com.healthcare.a2040.healthcarepartner.controllers.SignUpController;
import com.healthcare.a2040.healthcarepartner.obj_holders.UserInformationHolder;
import com.healthcare.a2040.healthcarepartner.ui_activities.StepperSignupActivity;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

/**
 * Created by Amit-PC on 1/18/2018.
 */

public class SIgnUpPartOne extends Fragment implements BlockingStep {
    private CustomMethods mCustomMethods;

    private Context mContext;
    private EditText userNameF, userNameL, userEmail, userPassword;
    String name, email, password;

    private SignUpController signUpController;


    public SIgnUpPartOne() {
    }


    public static SIgnUpPartOne newInstance() {
        SIgnUpPartOne fragment = new SIgnUpPartOne();
        Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_sign_up, container, false);
        mContext = getActivity();
        signUpController = new SignUpController(mContext);
        mCustomMethods = new CustomMethods(mContext);

        userNameF = rootView.findViewById(R.id.idNameR1);
        userNameL = rootView.findViewById(R.id.idNameR1);
        userEmail = rootView.findViewById(R.id.idemailR);

        return rootView;
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {

        if (signUpController.validateSignUpStep1(userNameF.getText().toString(), userNameL.getText().toString())) {

            UserInformationHolder userInfo = new UserInformationHolder();
            userInfo.setUserNameF(userNameF.getText().toString());
            userInfo.setUserNameL(userNameL.getText().toString());
            StepperSignupActivity.setUserInfo(userInfo);

            callback.goToNextStep();

        } else {

            mCustomMethods.msgbox("Put all details");
        }

        callback.getStepperLayout().hideProgress();


    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
        callback.getStepperLayout().hideProgress();

    }
}