package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    ListView noteListView;
    SharedPreferences sharedPreferences;
    static ArrayList<String> notes;
    static ArrayAdapter arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.newNote)
        {
            notes.add("");
            arrayAdapter.notifyDataSetChanged();
            try {
                sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(MainActivity.notes)).apply();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
            intent.putExtra("noteId", notes.size() - 1);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteListView = (ListView) findViewById(R.id.noteListView);
        try
        {
            sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
            notes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("notes", null));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if(notes == null || notes.size() == 0)
        {
            notes = new ArrayList<String>();
            notes.add("Example Note");
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);

        noteListView.setAdapter(arrayAdapter);
        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
                intent.putExtra("noteId",i);
                startActivity(intent);
            }
        });

        noteListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                final int itemSelected = i;
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete Note")
                        .setMessage("Are you sure you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                notes.remove(itemSelected);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                try {
                    sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(MainActivity.notes)).apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }
}