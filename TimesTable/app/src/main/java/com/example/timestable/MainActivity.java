package com.example.timestable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
{
    Integer[] values = new Integer[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar valueBar = (SeekBar) findViewById(R.id.bar);
        valueBar.setMax(20);
        valueBar.setProgress(1);

        final ListView table = (ListView) findViewById(R.id.table);

        for(int x = 0; x < 100;x++) {
            values[x] = (x + 1);
        }
        final ArrayAdapter<Integer>[] myAdapter = new ArrayAdapter[]{new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, values)};
        table.setAdapter(myAdapter[0]);

        valueBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                for(int x = 0; x < 100;x++) {
                    values[x] = (x + 1) * i;
                }
                myAdapter[0] = new ArrayAdapter<Integer>(getApplicationContext(), android.R.layout.simple_list_item_1, values);
                table.setAdapter(myAdapter[0]);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}