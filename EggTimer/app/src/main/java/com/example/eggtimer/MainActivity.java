package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    SeekBar timeSeek;
    TextView timeText;
    int timeSet;
    CountDownTimer timer;
    MediaPlayer mediaPlayer;

    public void startStop(View view)
    {
        if(timeSeek.isEnabled())
        {
            timeSeek.setEnabled(false);
            ((Button)view).setText("STOP");
            timer = new CountDownTimer(timeSet * 1000,1000)
            {
                @Override
                public void onTick(long l) {
                    timeSet -= 1;
                    updateTime(timeSet);
                }

                @Override
                public void onFinish()
                {
                    if (mediaPlayer != null){
                        if (mediaPlayer.isPlaying()||mediaPlayer.isLooping()) {
                            mediaPlayer.stop();
                        }
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.bell);
                    mediaPlayer.start();
                }
            }.start();
        }
        else
        {
            ((Button)view).setText("START");
            timer.cancel();
            if (mediaPlayer != null){
                if (mediaPlayer.isPlaying()||mediaPlayer.isLooping()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.release();
                mediaPlayer = null;
            }
            timeSeek.setEnabled(true);
        }
    }

    public void updateTime(int time)
    {
        String timeString = String.valueOf((int)Math.floor(time / 60)) + " : " + String.format("%02d",time % 60);
        timeText.setText(timeString);
        timeSet = time;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeSeek = (SeekBar) findViewById(R.id.seekBar);
        timeText = (TextView) findViewById(R.id.textView);

        timeSeek.setMax(1200);
        timeSeek.setProgress(180);
        updateTime(180);

        timeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                updateTime(i);
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