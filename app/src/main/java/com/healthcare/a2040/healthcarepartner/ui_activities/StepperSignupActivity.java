package com.healthcare.a2040.healthcarepartner.ui_activities;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.healthcare.a2040.healthcarepartner.R;
import com.healthcare.a2040.healthcarepartner.fragments.SIgnUpPartOne;
import com.healthcare.a2040.healthcarepartner.fragments.SIgnUpPartTwo;
import com.healthcare.a2040.healthcarepartner.obj_holders.FragmentHolder;
import com.healthcare.a2040.healthcarepartner.obj_holders.UserInformationHolder;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import java.util.ArrayList;

public class StepperSignupActivity extends SuperCompatActivity {

    //private SectionsPagerAdapter mSectionsPagerAdapter;
    private Context mContext;
    private ArrayList<FragmentHolder> fragmentHolders;
    private static UserInformationHolder userInformationHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepper_signup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = StepperSignupActivity.this;

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
//        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        StepperLayout stepperLayout = findViewById(R.id.stepperLayout);


        fragmentHolders = new ArrayList<>();

        FragmentHolder fragmentHolder = new FragmentHolder();
        fragmentHolder.setFragment(SIgnUpPartOne.newInstance());// Fragment should have implementation of Step Interface
        fragmentHolder.setFragmentTitle("Basic Details");

        fragmentHolders.add(fragmentHolder);

        FragmentHolder fragmentHolder2 = new FragmentHolder();
        fragmentHolder2.setFragment(SIgnUpPartTwo.newInstance());// Fragment should have implementation of Step Interface
        fragmentHolder2.setFragmentTitle("Document Upload");

        fragmentHolders.add(fragmentHolder2);

        stepperLayout.setTabNavigationEnabled(true);
//        stepperLayout.set
        stepperLayout.setAdapter(new MyStepperAdapter(getSupportFragmentManager(), mContext, fragmentHolders));
        stepperLayout.setListener(new StepperLayout.StepperListener() {
            @Override
            public void onCompleted(View completeButton) {

            }

            @Override
            public void onError(VerificationError verificationError) {

            }

            @Override
            public void onStepSelected(int newStepPosition) {

                setTitle(fragmentHolders.get(newStepPosition).getFragmentTitle());

            }

            @Override
            public void onReturn() {

            }
        });

        setTitle(fragmentHolders.get(stepperLayout.getCurrentStepPosition()).getFragmentTitle());
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public static UserInformationHolder getUserInfo() {
        return userInformationHolder;
    }

    public static void setUserInfo(UserInformationHolder userInfo) {
        userInformationHolder = userInfo;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stepper_signup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class MyStepperAdapter extends AbstractFragmentStepAdapter {
        private ArrayList<FragmentHolder> fragments;

        public MyStepperAdapter(FragmentManager fm, Context context, ArrayList<FragmentHolder> fragList) {
            super(fm, context);
            this.fragments = fragList;
        }

        @Override
        public Step createStep(int position) {

//
//            final SIgnUpPartOne step = SIgnUpPartOne.newInstance();
//            Bundle b = new Bundle();
////            b.putInt(CURRENT_STEP_POSITION_KEY, position);
//            step.setArguments(b);
//            return step;

            FragmentHolder fragmentHolder = fragments.get(position);
            return (Step) fragmentHolder.getFragment();


        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @NonNull
        @Override
        public StepViewModel getViewModel(@IntRange(from = 0) int position) {
            //Override this method to set Step title for the Tabs, not necessary for other stepper types

//            FragmentHolder fragmentHolder = fragments.get(position);
//            return new StepViewModel.Builder(context)
//                    .setTitle(fragmentHolder.getFragmentTitle()) //can be a CharSequence instead
//                    .create();

            FragmentHolder fragmentHolder = fragments.get(position);
            StepViewModel.Builder builder = new StepViewModel.Builder(context)
                    .setTitle(fragmentHolder.getFragmentTitle());
            switch (position) {
                case 0:
                    builder
                            .setEndButtonLabel("Upload Doc.")
                            .setEndButtonLabel("Next")
                            .setBackButtonLabel("Cancel")
                            .setNextButtonEndDrawableResId(R.drawable.ic_arrow_forward_black_24dp)
                            .setBackButtonStartDrawableResId(StepViewModel.NULL_DRAWABLE);
                    break;
                case 1:
                    builder
                            .setEndButtonLabel("I'm done")
                            .setBackButtonLabel("Go back")
                            .setBackButtonStartDrawableResId(R.drawable.ic_arrow_back_black_24dp);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported position: " + position);
            }

            return builder.create();
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return SIgnUpPartOne.newInstance();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
