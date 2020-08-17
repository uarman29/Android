package com.example.higherorlower;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    int answer;
    public void generateRandomNumber(int lower, int upper)
    {
        Random rand = new Random();
        answer = rand.nextInt(upper - lower + 1) + lower;
    }
    public void guess(View view)
    {
        EditText guessEditText = (EditText) findViewById(R.id.editTextNumber);
        String guessString = guessEditText.getText().toString();
        int guessValue = Integer.parseInt(guessString);

        String message;
        if(guessValue == answer)
        {
            message = "You got it! Try Again!";
            generateRandomNumber(1, 20);
        }
        else if(guessValue < answer)
        {
            message = "Higher!";
        }
        else
        {
            message = "Lower!";
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateRandomNumber(1, 20);
    }
}