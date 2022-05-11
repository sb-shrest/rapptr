package com.datechnologies.androidtest.animation;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;

/**
 * Screen that displays the D & A Technologies logo.
 * The icon can be moved around on the screen as well as animated.
 * */

public class AnimationActivity extends AppCompatActivity {

    //==============================================================================================
    // Class Properties
    //==============================================================================================

    private View logo, fade;

    private final float OUT = 0;
    private final float IN = 1;
    private final int DURATION = 3000;


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.fade_btn:
                    fade();
                    break;
            }
        }
    };

    private View.OnTouchListener touchListener = new View.OnTouchListener() {

        private float dy, dx;
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if(view == logo) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    dx = view.getX() - motionEvent.getRawX();
                    dy = view.getY() - motionEvent.getRawY();
                    return true;
                }
                else if(motionEvent.getAction() == MotionEvent.ACTION_MOVE) {

                    view.animate().x(dx + motionEvent.getRawX())
                            .y(dy + motionEvent.getRawY())
                            .setDuration(0).start();
                    return true;

                }

            }
            return false;
        }
    };

    private void fade() {
        if(logo != null) {

            MediaPlayer player = MediaPlayer.create(AnimationActivity.this, R.raw.alert);
            player.start();


            Animation out = new AlphaAnimation(IN, OUT);
            out.setDuration(DURATION);
            out.setInterpolator(new LinearInterpolator());

            Animation in = new AlphaAnimation(OUT, IN);
            in.setDuration(DURATION);
            in.setInterpolator(new LinearInterpolator());
            in.setStartOffset(DURATION);

            logo.startAnimation(out);
            logo.startAnimation(in);

        }
    }

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, AnimationActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @Override
    protected void onStart() {
        super.onStart();
        if(fade != null) {
            fade.setOnClickListener(clickListener);
        }
        if(logo != null) {
            logo.setOnTouchListener(touchListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(fade != null) {
            fade.setOnClickListener(null);
        }
        if(logo != null) {
            logo.setOnTouchListener(null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(R.string.anim_header);

        logo = findViewById(R.id.anim_logo);
        fade = findViewById(R.id.fade_btn);

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked

        // TODO: When the fade button is clicked, you must animate the D & A Technologies logo.
        // TODO: It should fade from 100% alpha to 0% alpha, and then from 0% alpha to 100% alpha

        // TODO: The user should be able to touch and drag the D & A Technologies logo around the screen.

        // TODO: Add a bonus to make yourself stick out. Music, color, fireworks, explosions!!!
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logo = null;
        fade = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }
}
