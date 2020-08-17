package com.example.multipleactivitydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    ListView nameListView;

    public void goToNextActivity(View view)
    {
        Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
        intent.putExtra("Data to Pass", "AYO CATCH THIS");
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameListView = (ListView) findViewById(R.id.nameList);
        final String[] nameList = {"Jack", "Nick", "Rob", "Sarah"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,nameList);
        nameListView.setAdapter(arrayAdapter);

        nameListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
                intent.putExtra("Name", nameList[i]);
                startActivity(intent);
            }
        });
    }
}