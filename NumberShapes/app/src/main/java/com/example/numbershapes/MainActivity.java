package com.example.numbershapes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    class Number
    {
        int value;

        public boolean isTriangular()
        {
            int currentTriangularNumber = 1;
            int currentGap = 2;
            while(currentTriangularNumber <= value)
            {
                if(value == currentTriangularNumber)
                    return true;

                currentTriangularNumber += currentGap;
                currentGap++;
            }
            return false;
        }

        public boolean isSquare()
        {
            if(Math.sqrt(value) == Math.floor(Math.sqrt(value)))
                return true;
            return false;
        }

        public boolean isTriangularAndSquare()
        {
            return isTriangular() && isSquare();
        }
    }

    public void test(View view)
    {
        String message;
        EditText numberEditText = (EditText) findViewById(R.id.editTextNumber);
        if(numberEditText.getText().toString().isEmpty()) {
            message = "Invalid Input";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            return;
        }

        int value = Integer.parseInt(numberEditText.getText().toString());
        Number n = new Number();
        n.value = value;
        if(n.isTriangularAndSquare())
        {
            message = n.value + " is a triangular and a square number";
        }
        else if(n.isTriangular())
        {
            message = n.value + " is a triangular number but not a square number";
        }
        else if(n.isSquare())
        {
            message = n.value + " is a square number but not a triangular number";
        }
        else
        {
            message = n.value + " is neither a triangular nor a square number";
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}