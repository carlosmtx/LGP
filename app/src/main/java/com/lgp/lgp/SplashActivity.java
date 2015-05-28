package com.lgp.lgp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class SplashActivity extends Activity {

    private static final long TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread timer = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(TIME);
                } catch (InterruptedException e) {

                }finally {
                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainIntent);
                }
            }
        };
        timer.start();

    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
