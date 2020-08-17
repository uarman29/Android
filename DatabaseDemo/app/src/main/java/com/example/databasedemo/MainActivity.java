package com.example.databasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        SQLiteDatabase myDatabase = this.openOrCreateDatabase("Database", MODE_PRIVATE, null);

        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR, age INT(3))");
        myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Nick', 28)");
        myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Sean', 33)");

        Cursor c = myDatabase.rawQuery("SELECT * FROM users", null);
        int nameIndex = c.getColumnIndex("name");
        int ageIndex = c.getColumnIndex("age");

        c.moveToFirst();

        while(!c.isAfterLast())
        {
            Log.i("name", c.getString(nameIndex));
            Log.i("age", String.valueOf(c.getInt(ageIndex)));
            c.moveToNext();
        }
        */

        /*
        SQLiteDatabase myDatabase = this.openOrCreateDatabase("Database", MODE_PRIVATE, null);

        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS events (name VARCHAR, year INT(4))");
        myDatabase.execSQL("INSERT INTO events (name, year) VALUES ('Millenium', 2000)");
        myDatabase.execSQL("INSERT INTO events (name, year) VALUES ('WWI Starts', 1914)");

        Cursor c = myDatabase.rawQuery("SELECT * FROM events", null);

        int nameIndex = c.getColumnIndex("name");
        int yearIndex = c.getColumnIndex("year");

        c.moveToFirst();

        while(!c.isAfterLast())
        {
            System.out.println(c.getString(nameIndex) + " " + c.getInt(yearIndex));
            c.moveToNext();
        }
        */

        SQLiteDatabase myDatabase = this.openOrCreateDatabase("Database", MODE_PRIVATE, null);
        //myDatabase.execSQL("DROP TABLE users");

        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR, age INT(3), id INTEGER PRIMARY KEY)");
        //myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Billy', 18)");
        //myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Will', 14)");
        //myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Nick', 28)");
        //myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Sean', 33)");

        //myDatabase.execSQL("DELETE FROM users WHERE 1 = 1");
        Cursor c = myDatabase.rawQuery("SELECT * FROM users WHERE age >= 18", null);
        int nameIndex = c.getColumnIndex("name");
        int ageIndex = c.getColumnIndex("age");
        int idIndex = c.getColumnIndex("id");

        c.moveToFirst();

        while(!c.isAfterLast())
        {
            Log.i("id", String.valueOf(c.getInt(idIndex)));
            Log.i("name", c.getString(nameIndex));
            Log.i("age", String.valueOf(c.getInt(ageIndex)));
            c.moveToNext();
        }

    }
}