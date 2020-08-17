package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.sharedpreferences", Context.MODE_PRIVATE);

        ArrayList<String> peeps = new ArrayList<String>();
        peeps.add("Fido");
        peeps.add("Sarah");
        peeps.add("Jones");

        try {
            sharedPreferences.edit().putString("peeps", ObjectSerializer.serialize(peeps)).apply();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("BRUH whAT");
        }

        ArrayList<String> newPeeps = new ArrayList<>();
        try {
            newPeeps = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("peeps", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("AYOOO",newPeeps.toString());
        //sharedPreferences.edit().putString("username","nick34").apply();
        //String username = sharedPreferences.getString("username","");
        //Log.i("username",username);
    }
}