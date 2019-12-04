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

    public static final String LOG_TAG = "MainActivity";

    private SeekBar seekBar;

    // 10 minutes = 600 seconds
    private final static  int MAX = 600;
    // 5 minutes = 300 seconds
    private final static int PROGRESS = 300;
    // 1 second = 1000 milliseconds
    private final static int SECOND = 1000;

    private TextView timerTextView;

    private ToggleButton toggleButton;

    private CountDownTimer timer;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekbar);
        seekBar.setMax(MAX);
        seekBar.setProgress(PROGRESS);

        timerTextView = findViewById(R.id.timer_text_view);
        toggleButton = findViewById(R.id.toggle_button);

        mediaPlayer = MediaPlayer.create(this, R.raw.beep);

        updateCounterFromSeekbar();

        setupTimer();

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    startTimer();
                } else {
                    pauseTimer();
                }
            }
        });
    }

    public void updateTimer(int i){
        int min = i / 60;
        int sec = i - (min * 60);
        String formattedTime = String.format("%02d:%02d", min, sec);
        timerTextView.setText(formattedTime);
    }

    public void updateCounterFromSeekbar() {

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void  setupTimer() {
        timer = new CountDownTimer(seekBar.getProgress() * SECOND, SECOND) {
            @Override
            public void onTick(long l) {
                Log.i("Count down started", String.valueOf(l/SECOND));
                //Log.i("Count down started", String.valueOf(seekBar.getProgress()));
                updateTimer((int) l / SECOND);
            }

            @Override
            public void onFinish() {
                Log.i("Count down finished ", "0 seconds left");
                // play sound
                mediaPlayer.start();
            }
        };
    }

    public void startTimer() {
        timer.start();
    }

    public void pauseTimer() {
        timer.cancel();
    }
}


