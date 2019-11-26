package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private ToggleButton toggleButton;
    private TextView textView;
    private CountDownTimer timer;
    private long leftTime;
    private MediaPlayer mediaPlayer;

    // time in milliseconds = 10 minutes
    private int tenMinutes = 600000;
    // time in milliseconds = 1 second
    private int oneSecond = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekbar);
        toggleButton = findViewById(R.id.toggle_button);
        textView = findViewById(R.id.timer_text_view);
        mediaPlayer = MediaPlayer.create(this, R.raw.beep);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    timerStart(tenMinutes);
                } else {
                    timerPause();
                }
            }
        });

        adjustSeekBar();
    }

    public void timerStart(long timeLengthMilli) {
        timer = new CountDownTimer(tenMinutes, oneSecond) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.i("Count down started", String.valueOf(millisUntilFinished/oneSecond));
                // convert long to int
                // convert milliseconds to seconds by /1000
                updateTimer((int) millisUntilFinished /1000);
            }

            @Override
            public void onFinish() {
                Log.i("Count down finished ", "0 seconds left");
                // play sound
                mediaPlayer.start();
            }
        }.start();
    }

    public void updateTimer(int secondsLeft) {
        int min = secondsLeft / 60;
        int sec = secondsLeft - (min * 60);
        String formattedTime = String.format("%02d:%02d", min, sec);
        textView.setText(formattedTime);
    }

    public void timerPause() {
        timer.cancel();
    }

    public void resumeTimer() {
        timerStart(leftTime);
    }

    public void adjustSeekBar() {
        // 600 seconds = 10 min
        seekBar.setMax(600);
        // 30 seconds
        seekBar.setProgress(600);

        // what to update if the seekBar changes
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               updateTimer(progress);
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
