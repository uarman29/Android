package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.io.IOException;

public class NoteEditorActivity extends AppCompatActivity
{
    EditText editNote;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        editNote = (EditText) findViewById(R.id.editNote);
        Intent intent = getIntent();

        final int noteId = intent.getIntExtra("noteId",-1);
        if(noteId != -1)
        {
            String currentNote = MainActivity.notes.get(noteId);
            editNote.setText(currentNote);

            editNote.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
                {
                    MainActivity.notes.set(noteId, charSequence.toString());
                    MainActivity.arrayAdapter.notifyDataSetChanged();

                    SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
                    try {
                        sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(MainActivity.notes)).apply();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

    }
}