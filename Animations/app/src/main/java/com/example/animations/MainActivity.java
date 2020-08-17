package com.example.animations;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
{
    boolean moneyShow = true;
    public void fade(View view)
    {
        ImageView money = (ImageView) findViewById(R.id.money);
        ImageView cashburn = (ImageView) findViewById(R.id.cashburn);
        /*
        if(moneyShow) {
            money.animate().alpha(0).setDuration(2000);
            cashburn.animate().alpha(1).setDuration(2000);
            moneyShow = false;
        }
        else
        {
            cashburn.animate().alpha(0).setDuration(2000);
            money.animate().alpha(1).setDuration(2000);
            moneyShow = true;
        }*/

        //money.animate().translationXBy(-2000).setDuration(2000);
        //money.animate().translationXBy(-2000).setDuration(2000);
        //money.animate().rotation(1800).alpha(0).setDuration(1000);
        //money.animate().scaleX(.5f).scaleY(.5f).setDuration(2000);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView money = (ImageView) findViewById(R.id.money);
        money.setX(-1000);
        money.animate().rotation(1800).translationXBy(1000).setDuration(2000);

    }
}