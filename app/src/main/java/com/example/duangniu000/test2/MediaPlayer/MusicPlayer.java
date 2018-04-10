package com.example.duangniu000.test2.MediaPlayer;


import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayer extends MediaPlayer {


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                if (listeners != null) {
                    for (MusicPlayerListener listener : listeners) {
                        listener.onPlayState(MusicPlayer.this, getCurrentPosition(), getDuration());
                    }
                }
                handler.sendEmptyMessageDelayed(1, 500);
            }

            return false;
        }
    });

    @Override
    public void prepare() throws IOException, IllegalStateException {
        super.prepare();
    }

    @Override
    public void start() throws IllegalStateException {
        super.start();
        handler.sendEmptyMessage(1);
        if (listeners != null) {
            for (MusicPlayerListener listener : listeners) {
                listener.start(this);
            }
        }
    }

    @Override
    public void stop() throws IllegalStateException {
        super.stop();
        handler.removeCallbacksAndMessages(null);
        if (listeners != null) {
            for (MusicPlayerListener listener : listeners) {
                listener.stop(this);
            }
        }
    }

    @Override
    public void pause() throws IllegalStateException {
        super.pause();
        handler.removeCallbacksAndMessages(null);
        if (listeners != null) {
            for (MusicPlayerListener listener : listeners) {
                listener.pause(this);
            }
        }
    }

    @Override
    public void release() {
        super.release();
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public int getCurrentPosition() {
        return super.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return super.getDuration();
    }


    private List<MusicPlayerListener> listeners = new ArrayList<>();

    public void addListener(MusicPlayerListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public boolean removeListener(MusicPlayerListener listener) {
        return listeners.remove(listener);
    }

    public interface MusicPlayerListener {

        void onPlayState(MediaPlayer player, int currentPosition, int duration);

        void stop(MediaPlayer player);

        void start(MediaPlayer player);

        void pause(MediaPlayer player);

    }

    public abstract class MusicPlayerListener2 implements MusicPlayerListener {
        @Override
        public void onPlayState(MediaPlayer player, int currentPosition, int duration) {

        }

        @Override
        public void stop(MediaPlayer player) {

        }

        @Override
        public void start(MediaPlayer player) {

        }

        @Override
        public void pause(MediaPlayer player) {

        }
    }


    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        stop();
        release();
        handler = null;
    }

}
