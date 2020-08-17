package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void convert(View view)
    {
        EditText dollarEditText = (EditText) findViewById(R.id.editTextDollars);

        String amountInDollars = dollarEditText.getText().toString();
        double amountInPounds = Double.parseDouble(amountInDollars) * 0.766162;
        String amountInPoundsString = String.format("%.2f", amountInPounds);
        Toast.makeText(this, "$" + amountInDollars + " is " + amountInPoundsString + " pounds", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}