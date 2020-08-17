package com.example.basicphrases;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    MediaPlayer mediaPlayer;
    public void play(View view)
    {
        if (mediaPlayer != null){
            if (mediaPlayer.isPlaying()||mediaPlayer.isLooping()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }

        String tag = view.getTag().toString();
        mediaPlayer = MediaPlayer.create(this,getResources().getIdentifier(tag,"raw",getPackageName()));
        mediaPlayer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}