package com.example.kotlintest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    var count:Int = 0;

    fun reset(view: View)
    {
        count = 0;
        textView.setText(count.toString());
    }

    fun plus1(view: View)
    {
        count++;
        textView.setText(count.toString());
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var textView = findViewById<TextView>(R.id.textView);
    }
}