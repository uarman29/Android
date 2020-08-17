package com.example.timerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this,1000);
            }
        };

        handler.post(run);*/

        new CountDownTimer(10000,1000)
        {
            @Override
            public void onTick(long millisecondsUntilDone)
            {
                Log.i("ayo",String.valueOf(millisecondsUntilDone) + " milliseconds remaining");
            }

            @Override
            public void onFinish()
            {
                Log.i("ayo","Done");
            }
        }.start();
    }
}