package com.nayan.audiodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    AudioManager audioManager;

    public void play(View view){

        mediaPlayer.start();

    }

    public void pause(View view){

        mediaPlayer.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//we can craete an int called maxVolume which is equal to the maxVolume in the device that the app is runing on we can get it from the audio manager by getStreamMaxVolume//
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        mediaPlayer =MediaPlayer.create(this,R.raw.audidemo);

        SeekBar volumeControl = (SeekBar) findViewById(R.id.volumeSeekBar);

        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(currentVolume);//for seting curent position//

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {   // it listens for the changes in the seek bar ,waits until user moves the seekbar and calls this method (with in the method we define new onseek bar change listener)//
                                                                                            //which gives 3 handy methods//
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                Log.i("seekbar changed",Integer.toString(i));  //we actualy dontwaant to log to be displayed we need to vhange the colume this has been done by defining the audio mangaer in the top so that we can acess in any other method//

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0); // we ned to put int init the dificulty is the int is not from 0 to 100 it varies from device to device,so we need to establish that before setting it//
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final SeekBar scrubSeekBar = (SeekBar) findViewById(R.id.scrubSeekBar);

        scrubSeekBar.setMax(mediaPlayer.getDuration()); //wehave to set to the max value to the length of the audio ,length of ausio is in the media player//

        scrubSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                Log.i("scrub seekbar moved", Integer.toString(i));

                mediaPlayer.seekTo(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //to update the scrub bar as the audio is played so we us timer//
        //timer allowus to do something every time or every min when we are doing something
     new Timer().scheduleAtFixedRate(new TimerTask() {
         @Override
         public void run() {
             //seelbar to be updated for the new value of the curent possition of the media player
             scrubSeekBar.setProgress(mediaPlayer.getCurrentPosition());

         }
     }, 0 , 10000000);

    }
}
