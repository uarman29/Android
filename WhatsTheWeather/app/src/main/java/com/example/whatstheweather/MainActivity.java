package com.example.whatstheweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity
{
    EditText enterCity;
    TextView weather;

    public void getWeather(View view)
    {
        try
        {
            DownloadTask task = new DownloadTask();
            String encodedCityName = URLEncoder.encode(enterCity.getText().toString(),"UTF-8");
            task.execute("https://openweathermap.org/data/2.5/weather?q=" + encodedCityName +"&appid=439d4b804bc8187953eb36d2a8c26a02");
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(enterCity.getWindowToken(),0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            weather.setText("Invalid Input");
        }
    }

    public class DownloadTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... urls)
        {
            try
            {
                String result = "";
                URL url;
                HttpURLConnection urlConnection;
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while(data != -1)
                {
                    result += (char) data;
                    data = reader.read();
                }

                return result;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return "";
            }
        }

        @Override
        protected void onPostExecute(String s)
        {
            try
            {
                super.onPostExecute(s);
                JSONObject jsonObject = new JSONObject(s);
                String weatherInfo = jsonObject.getString("weather");

                String weatherText = "";
                JSONArray arr = new JSONArray(weatherInfo);
                for(int i = 0; i < arr.length();i++)
                {
                    JSONObject jsonPart = arr.getJSONObject(i);
                    weatherText += jsonPart.getString("main") + ": " + jsonPart.getString("description") + "\n";
                }
                weather.setText(weatherText);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                weather.setText("Invalid Input");
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enterCity = (EditText) findViewById(R.id.enterCity);
        weather = (TextView) findViewById(R.id.weather);
    }
}