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

    private int tenMinutes = 600000;
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
                leftTime = millisUntilFinished;
                long min = (millisUntilFinished / (oneSecond * 60));
                long sec = ((millisUntilFinished / oneSecond) % 60);
                String formattedTime = String.format("%02d:%02d", min, sec);
                textView.setText(formattedTime);
            }

            @Override
            public void onFinish() {
                Log.i("Count down finished ", "0 seconds left");
                // play sound
                mediaPlayer.start();
            }
        }.start();
    }

    public void timerPause() {
        timer.cancel();
    }

    public void resumeTimer() {
        timerStart(leftTime);
    }

    public void adjustSeekBar() {
        seekBar.setMax(tenMinutes);
        seekBar.setProgress(Integer.valueOf((int) leftTime));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

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
