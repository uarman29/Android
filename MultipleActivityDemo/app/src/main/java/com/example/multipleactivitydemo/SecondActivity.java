package com.example.multipleactivitydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity
{
    TextView nameTextView;

    public void goBackToMain(View view)
    {
        //Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        //startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        nameTextView = (TextView) findViewById(R.id.nameTextView);
        Intent intent = getIntent();
        String recieved = intent.getStringExtra("Name");
        nameTextView.setText(recieved);
    }
}