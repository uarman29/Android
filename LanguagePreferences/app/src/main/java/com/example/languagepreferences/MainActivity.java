package com.example.languagepreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    TextView languageTextView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            case R.id.English:
                setLanguage("English");
                return true;

            case R.id.Japanese:
                setLanguage("Japanese");
                return true;

            default:
                return false;
        }
    }

    public void setLanguage(String language)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("preferredLanguage", language).apply();
        languageTextView.setText(language);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        languageTextView = (TextView) findViewById(R.id.languageTextView);
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        String preferredLanguage = sharedPreferences.getString("preferredLanguage",null);

        if(preferredLanguage ==  null || preferredLanguage.length() == 0)
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.star_on)
                    .setTitle("Preferred Language")
                    .setMessage("Please select your preferred Language")
                    .setPositiveButton("English", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            setLanguage("English");
                        }
                    })
                    .setNegativeButton("Japanese", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            setLanguage("Japanese");
                        }
                    })
                    .show();
        }
        else
        {
            setLanguage(preferredLanguage);
        }
    }
}