package com.example.memorableplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    ListView locationListView;
    static ArrayList<String> locations = new ArrayList<String>();;
    static ArrayList<LatLng> locationCoords = new ArrayList<LatLng>();
    static ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationListView = (ListView) findViewById(R.id.locationListView);
        locations.clear();
        locationCoords.clear();

        try
        {
            SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.memorableplaces", Context.MODE_PRIVATE);
            locations = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("locations", ObjectSerializer.serialize(new ArrayList<String>())));

            ArrayList<String> latitudes = new ArrayList<String>();
            ArrayList<String> longitudes = new ArrayList<String>();
            latitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("latitudes", ObjectSerializer.serialize(new ArrayList<String>())));
            longitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("longitudes", ObjectSerializer.serialize(new ArrayList<String>())));

            for(int i = 0; i < locations.size(); i++)
                locationCoords.add(new LatLng(Double.parseDouble(latitudes.get(i)),Double.parseDouble(longitudes.get(i))));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if(locations.size() == 0) {
            locations.add("Add a new place");
            locationCoords.add(new LatLng(0, 0));
        }

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,locations);
        locationListView.setAdapter(arrayAdapter);

        locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                intent.putExtra("placeNumberSelected", i);
                startActivity(intent);
            }
        });

    }
}