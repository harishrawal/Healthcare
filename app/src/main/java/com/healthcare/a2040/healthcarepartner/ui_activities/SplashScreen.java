package com.healthcare.a2040.healthcarepartner.ui_activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.healthcare.a2040.healthcarepartner.R;
import com.healthcare.a2040.healthcarepartner.controllers.LoginController;

public class SplashScreen extends AppCompatActivity {
    private Context mContext;
    private LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mContext = SplashScreen.this;
        loginController = new LoginController(mContext);
        waitThread();
    }

    private void waitThread() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        loginController.navigateInside();
                    }
                });

            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
