package com.example.hackernews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    ListView articlesListView;
    ArrayList<String> titles;
    ArrayList<String> urls;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        titles = new ArrayList<String>();
        urls = new ArrayList<String>();
        processTopStoryIDS();

        articlesListView = (ListView) findViewById(R.id.articlesListView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titles);
        articlesListView.setAdapter(arrayAdapter);

        articlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = new Intent(getApplicationContext(), ViewArticle.class);
                intent.putExtra("articleID", urls.get(i));
                startActivity(intent);
            }
        });


    }

    public void processTopStoryIDS()
    {
        DownloadTask task =  new DownloadTask();
        try
        {
            String ids = task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty").get();
            JSONArray jsonArray = new JSONArray(ids);

            int numberOfItems = 10;
            String articleID;
            String articleInfo;
            JSONObject article;

            if(jsonArray.length() < numberOfItems)
                numberOfItems = jsonArray.length();

            for(int i = 0; i < numberOfItems;i++)
            {
                articleID = jsonArray.getString(i);
                task =  new DownloadTask();
                articleInfo = task.execute("https://hacker-news.firebaseio.com/v0/item/" + articleID + ".json?print=pretty").get();
                article = new JSONObject(articleInfo);

                if(!article.isNull("title") && !article.isNull("url"))
                {
                    titles.add(article.getString("title"));
                    urls.add(article.getString("url"));
                }
            }
            arrayAdapter.notifyDataSetChanged();
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

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
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }


}