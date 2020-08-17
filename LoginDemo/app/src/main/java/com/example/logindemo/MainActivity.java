package com.example.logindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public void login(View view)
    {
        EditText userNameEditText = (EditText) findViewById(R.id.editTextUserName);
        EditText passwordEditText = (EditText) findViewById(R.id.editTextPassword);

        Log.i("Values","Username: " + userNameEditText.getText().toString() + "\n Password: "+ passwordEditText.getText().toString());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}