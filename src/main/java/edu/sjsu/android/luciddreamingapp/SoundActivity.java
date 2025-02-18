package edu.sjsu.android.luciddreamingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SoundActivity extends AppCompatActivity {

    ImageView play, prev, next, imageView;
    TextView songTitle;
    SeekBar mSeekBarTime, mSeekBarVol;
    public static MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);


        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // initializing views

        play = findViewById(R.id.play);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        songTitle = findViewById(R.id.songTitle);
        imageView = findViewById(R.id.imageView);
        mSeekBarTime = findViewById(R.id.seekBarTime);
        mSeekBarVol = findViewById(R.id.seekBarVol);

        // creating an ArrayList to store our songs

        final ArrayList<Integer> songs = new ArrayList<>();

        songs.add(0, R.raw.bird_chirp);
        songs.add(1, R.raw.heavy_rain);
        songs.add(2, R.raw.rain_thunder);
        songs.add(3, R.raw.soft_piano);
        songs.add(4, R.raw.spring_stream);


        // intializing mediaplayer

        mMediaPlayer = MediaPlayer.create(getApplicationContext(), songs.get(currentIndex));

        // seekbar volume

        int maxV = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curV = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mSeekBarVol.setMax(maxV);
        mSeekBarVol.setProgress(curV);

        mSeekBarVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        //above seekbar volume

        play.setOnClickListener(v -> {
            mSeekBarTime.setMax(mMediaPlayer.getDuration());
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
                play.setImageResource(R.drawable.play_btn);
            } else {
                assert mMediaPlayer != null;
                mMediaPlayer.start();
                play.setImageResource(R.drawable.pause_btn);
            }

            songNames();

        });


        next.setOnClickListener(v -> {
            if (mMediaPlayer != null) {
                play.setImageResource(R.drawable.pause_btn);
            }

            if (currentIndex < songs.size() - 1) {
                currentIndex++;
            } else {
                currentIndex = 0;
            }

            assert mMediaPlayer != null;
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
                hasStopped = true;
            }

            mMediaPlayer = MediaPlayer.create(getApplicationContext(), songs.get(currentIndex));

            mMediaPlayer.start();
            hasStopped = false;

            songNames();

        });


        prev.setOnClickListener(v -> {

            if (mMediaPlayer != null) {
                play.setImageResource(R.drawable.pause_btn);
            }

            if (currentIndex > 0) {
                currentIndex--;
            } else {
                currentIndex = songs.size() - 1;
            }
            assert mMediaPlayer != null;
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
                hasStopped = true;
            }

            mMediaPlayer = MediaPlayer.create(getApplicationContext(), songs.get(currentIndex));
            mMediaPlayer.start();
            hasStopped = false;
            songNames();

        });

    }
    public static boolean hasStopped = false;

    // Static method to stop the MediaPlayer
    public static void stopMediaPlayer() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
                hasStopped=true;
            }
            mMediaPlayer = null;
        }
    }
    @SuppressLint("SetTextI18n")
    private void songNames() {
        if (currentIndex == 0) {
            songTitle.setText(R.string.blackbird_chirping);
            imageView.setImageResource(R.drawable.bird);
        }
        if (currentIndex == 1) {
            songTitle.setText(R.string.heavy_rain);
            imageView.setImageResource(R.drawable.heavyrain);
        }
        if (currentIndex == 2) {
            songTitle.setText(R.string.rain_thunder);
            imageView.setImageResource(R.drawable.thunder);
        }
        if (currentIndex == 3) {
            songTitle.setText(R.string.soft_piano);
            imageView.setImageResource(R.drawable.piano);
        }
        if (currentIndex == 4) {
            songTitle.setText("Spring Streams");
            imageView.setImageResource(R.drawable.stream);
        }


        // seekbar duration
        mMediaPlayer.setOnPreparedListener(mp -> {
            mSeekBarTime.setMax(mMediaPlayer.getDuration());
            mMediaPlayer.start();
            hasStopped = false;

        });

        mSeekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mMediaPlayer.seekTo(progress);
                    mSeekBarTime.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        new Thread(() -> {
            while (mMediaPlayer != null) {
                try {
                    if (mMediaPlayer.isPlaying()) {
                        Message message = new Message();
                        message.what = mMediaPlayer.getCurrentPosition();
                        handler.sendMessage(message);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @SuppressLint({"Handler Leak", "HandlerLeak"})
    Handler handler = new Handler () {
        @Override
        public void handleMessage  (Message msg) {
            mSeekBarTime.setProgress(msg.what);
        }
    };
}