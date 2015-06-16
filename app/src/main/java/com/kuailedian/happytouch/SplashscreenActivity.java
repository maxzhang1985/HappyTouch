package com.kuailedian.happytouch;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.AnimationUtils;

public class SplashscreenActivity extends ActionBarActivity {

    private final int SPLASH_DISPLAY_LENGHT = 1800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashscreenActivity.this,MyBaseActivity.class);
                SplashscreenActivity.this.startActivity(mainIntent);
                overridePendingTransition(R.anim.ac_transition_unzoom_in, R.anim.abc_fade_out);
                SplashscreenActivity.this.finish();
            }

        }, SPLASH_DISPLAY_LENGHT);


    }


}
