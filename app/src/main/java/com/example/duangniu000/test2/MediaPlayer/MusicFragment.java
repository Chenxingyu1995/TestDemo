package com.example.duangniu000.test2.MediaPlayer;


import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.duangniu000.test2.Adaper.AbstractAdapter;
import com.example.duangniu000.test2.Adaper.ViewHolder.BaseHolder;
import com.example.duangniu000.test2.Fragment.BaseFragment;
import com.example.duangniu000.test2.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MusicFragment extends BaseFragment implements MediaPlayer.OnCompletionListener, ServiceConnection,
        MusicPlayer.MusicPlayerListener, MediaPlayer.OnErrorListener,
        View.OnClickListener {


    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.controller)
    MusicController controller;

    private int playIndex;

    private MusicAdapter adapter;

    private int State = STOP;

    public static final int PLAYING = 0;
    public static final int STOP = 1;
    public static final int PAUSE = 2;

    private Player playerService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private AudioManager am;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ui_music, container, false);
        unbinder = ButterKnife.bind(this, view);
        Intent intent = new Intent(getContext(), PlayerService.class);
        getContext().bindService(intent, this, Context.BIND_AUTO_CREATE);
        am = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        controller.setListener(this);


        AudioManager.OnAudioFocusChangeListener listener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                Log.e("onAudioFocusChange", focusChange + "");

                if (AudioManager.AUDIOFOCUS_LOSS == focusChange) {

                }

                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_GAIN:
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                        break;

                }

            }
        };
        am.requestAudioFocus(listener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        if (service != null) {
            Log.e("onServiceConnected", "on");
            PlayerService.MBinder mBinder = (PlayerService.MBinder) service;
            playerService = mBinder.getService();
            bindService();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.e("onServiceDisconnected", "off");
    }

    @Override
    public void onBindingDied(ComponentName name) {
        Log.e("onBindingDied", "died");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unbindService(this);
    }

    private void init() {
        adapter = new MusicAdapter(getActivity());
        List<Music> musicList = new ArrayList<>();
        ContentResolver resolver = getActivity().getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));   //音乐id
            String title = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));//音乐标题
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));//艺术家
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));//时长
            long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));  //文件大小
            String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));  //文件路径
            int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));//是否为音乐
            Music music = new Music();
            music.setArtist(artist);
            music.setSize(size);
            music.setCountTime(duration);
            music.setPath(url);
            music.setTitle(title);
            musicList.add(music);
        }

        cursor.close();
        adapter.addAll(musicList);
        adapter.setOnItemClickListener(new AbstractAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, BaseHolder holder) {
                playIndex = pos;
                startMusic(playIndex);
            }
        });
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        startMusic(++playIndex);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        State = STOP;
        return false;
    }

    @Override
    public void onPlayState(MediaPlayer player, int currentPosition, int duration) {
        controller.setProgress(currentPosition);
    }

    @Override
    public void stop(MediaPlayer player) {
        State = STOP;
        Log.e("stop", "stop");
    }

    @Override
    public void start(MediaPlayer player) {
        Log.e("start", "start");
        State = PLAYING;
        controller.setMax(player.getDuration());
    }

    @Override
    public void pause(MediaPlayer player) {
        State = PAUSE;
        Log.e("pause", "pause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        State = STOP;
        unbinder.unbind();
    }

    private void bindService() {
        playerService.getMusicPlayer().addListener(this);
        playerService.getMusicPlayer().setOnErrorListener(this);
        playerService.getMusicPlayer().setOnCompletionListener(this);
        controller.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (State == PLAYING) {
                    int progress = seekBar.getProgress();
                    playerService.seek(progress);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skip_previous:
                startMusic(--playIndex);
                break;
            case R.id.play:
                play();
                break;
            case R.id.skip_next:
                startMusic(++playIndex);
                break;
        }
    }

    private void startMusic(int playIndex) {
        if (playIndex >= adapter.getItemCount()) {
            playIndex = 0;
        }

        if (playIndex < 0) {
            playIndex = adapter.getItemCount() - 1;
        }
        playerService.play(adapter.getList().get(playIndex));
        adapter.setPlayIndex(playIndex);
    }

    private void play() {
        switch (State) {
            case STOP:
                startMusic(playIndex);
                break;
            case PAUSE:
                playerService.reStart();
                break;
            case PLAYING:
                playerService.pause();
                break;
        }
    }


}
