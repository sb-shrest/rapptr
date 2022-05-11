package com.datechnologies.androidtest;

import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.datechnologies.androidtest.animation.AnimationActivity;
import com.datechnologies.androidtest.chat.ChatActivity;
import com.datechnologies.androidtest.login.LoginActivity;

/**
 * The main screen that lets you navigate to all other screens in the app.
 *
 */

public class MainActivity extends AppCompatActivity {

    private final int SPLASH_EXIT = 3000;
    private final String FIRST_TAG = "first";

    private Runnable changeLayout = new Runnable() {
        @Override
        public void run() {

            ActionBar actionBar = getSupportActionBar();
            if(actionBar != null) {
                actionBar.show();
            }
            setTitle(R.string.activity_main_title);
            setContentView(R.layout.activity_main);
        }
    };

    private Runnable exitSplash = new Runnable() {
        @Override
        public void run() {
            runOnUiThread(changeLayout);
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle args) {
        args.putBoolean(FIRST_TAG, false);
        super.onSaveInstanceState(args);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler handler = new Handler(Looper.getMainLooper());

        if(savedInstanceState == null || savedInstanceState.getBoolean(FIRST_TAG, true)) {


            ActionBar actionBar = getSupportActionBar();
            if(actionBar != null) {
                actionBar.hide();
            }

            setContentView(R.layout.activity_splash);

            handler.postDelayed(exitSplash, SPLASH_EXIT);
        }
        else {
            handler.post(exitSplash);
        }


        /**
         * =========================================================================================
         * INSTRUCTIONS
         * =========================================================================================
         *
         * 1. UI must work on Android phones of multiple sizes. Do not worry about Android Tablets.
         *
         * 2. Use this starter project as a base and build upon it. It is ok to remove some of the
         *    provided code if necessary.
         *
         * 3. Read the additional 'TODO' comments throughout the codebase, they will guide you.
         *
         * 3. Please take care of the bug(s) we left for you in the project as well.
         *
         * Thank you and Good luck. -  D & A Technologies
         * =========================================================================================
         */

        // TODO: Make the UI look like it does in the mock-up
        // TODO: Add a ripple effect when the buttons are clicked
    }

    //==============================================================================================
    // Button Click Methods
    //==============================================================================================

    public void onChatClicked(View v)
    {
        ChatActivity.start(this);
    }

    public void onLoginClicked(View v)
    {
        LoginActivity.start(this);
    }

    public void onAnimationClicked(View v)
    {
        AnimationActivity.start(this);
    }
}
