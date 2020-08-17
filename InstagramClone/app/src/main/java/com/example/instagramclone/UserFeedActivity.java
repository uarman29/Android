package com.example.instagramclone;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UserFeedActivity extends AppCompatActivity
{
    ListView feedListView;
    ConstraintLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);
        setTitle("User Feed");

        layout = findViewById(R.id.layout);
        feedListView = findViewById(R.id.feedListView);
        final ArrayList<Bitmap> images = new ArrayList<Bitmap>();
        final ImageAdapter imageAdapter = new ImageAdapter(this, images);
        feedListView.setAdapter(imageAdapter);
        Intent intent  = getIntent();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Image");
        query.whereEqualTo("username", intent.getStringExtra("username"));
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e)
            {
                if(e == null && objects.size() > 0)
                {
                    for(ParseObject object: objects)
                    {
                        ParseFile file = object.getParseFile("image");

                        file.getDataInBackground(new GetDataCallback()
                        {
                            @Override
                            public void done(byte[] data, ParseException e)
                            {
                                if(e == null && data != null)
                                {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                    images.add(bitmap);
                                    imageAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }
                else
                {
                    e.printStackTrace();
                }
            }
        });

    }

    public class ImageAdapter extends ArrayAdapter<Bitmap> {

        private Context mContext;
        private List<Bitmap> imageList;

        public ImageAdapter(@NonNull Context context, ArrayList<Bitmap> list) {
            super(context, 0 , list);
            mContext = context;
            imageList = list;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItem = convertView;

            if(listItem == null)
                listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

            Bitmap currentImage = imageList.get(position);

            ImageView image = (ImageView)listItem.findViewById(R.id.imageView);
            image.setImageBitmap(currentImage);

            return listItem;
        }
    }
}