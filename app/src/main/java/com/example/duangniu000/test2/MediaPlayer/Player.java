package com.example.duangniu000.test2.MediaPlayer;


public interface Player {

    void play(Music music);

    void pause();

    void stop();

    void reStart();

    MusicPlayer getMusicPlayer();

    boolean isPlaying();


    void seek(int progress);
}
