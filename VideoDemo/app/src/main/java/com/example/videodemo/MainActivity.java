package com.example.videodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VideoView myVid = (VideoView) findViewById(R.id.videoView);
        myVid.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.demovideo);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(myVid);
        myVid.setMediaController(mediaController);
        myVid.start();
    }
}