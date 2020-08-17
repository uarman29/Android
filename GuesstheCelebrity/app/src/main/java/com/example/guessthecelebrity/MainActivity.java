package com.example.guessthecelebrity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    //ImageView image;
    //Button[] choices;

    public int getRandomInteger(int lower, int upper)
    {
        Random rand = new Random();
        return (rand.nextInt(upper - lower + 1) + lower);
    }

    public void choose(View view)
    {

    }

    public void update()
    {

    }

    public class HTMLDownloader extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... urls)
        {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try
            {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                //urlConnection.connect();

                InputStream in  = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while(data != -1)
                {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;
            }
            catch(Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        image = (ImageView) findViewById(R.id.imageView);

        choices = new Button[4];
        choices[0] = (Button) findViewById(R.id.button1);
        choices[1] = (Button) findViewById(R.id.button2);
        choices[2] = (Button) findViewById(R.id.button3);
        choices[3] = (Button) findViewById(R.id.button4);
        */
        String result = "";
        try
        {
            HTMLDownloader task = new HTMLDownloader();
            result = task.execute("https://www.imdb.com/list/ls052283250/").get();
            System.out.println("AYYY");
            System.out.println(result);
            Log.i("DONE","DONE");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}