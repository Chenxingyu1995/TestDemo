package com.example.duangniu000.test2.MediaPlayer;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;


import java.io.IOException;


@SuppressLint("Registered")
public class PlayerService extends Service implements Player , AudioManager.OnAudioFocusChangeListener  {

    private MusicPlayer musicPlayer;

    private MBinder mBinder = new MBinder();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    @Override
    public void pause() {
        musicPlayer.pause();
    }

    @Override
    public void stop() {
        musicPlayer.stop();
    }

    @Override
    public void reStart() {
        if (musicPlayer != null && !musicPlayer.isPlaying()) {
            musicPlayer.start();
        }
    }

    @Override
    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }

    @Override
    public boolean isPlaying() {
        return musicPlayer != null && musicPlayer.isPlaying();
    }

    @Override
    public void seek(int progress) {
        musicPlayer.seekTo(progress);
    }

    @Override
    public void onAudioFocusChange(int focusChange) {

    }

    class MBinder extends Binder {

        Player getService() {
            return PlayerService.this;
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        musicPlayer = new MusicPlayer();
        musicPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        musicPlayer.onDestroy();
    }

    @Override
    public void play(Music music) {
        if (musicPlayer.isPlaying()) {
            musicPlayer.stop();
        }
        musicPlayer.reset();

        if (music == null) return;
        String path = (music).getPath();

        try {
            musicPlayer.setDataSource(path);
            musicPlayer.prepare();
            musicPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
