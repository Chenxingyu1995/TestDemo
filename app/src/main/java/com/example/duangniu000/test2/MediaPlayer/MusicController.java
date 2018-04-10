package com.example.duangniu000.test2.MediaPlayer;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.example.duangniu000.test2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MusicController extends RelativeLayout {

    @BindView(R.id.skip_previous)
    Button skipPrevious;
    @BindView(R.id.play)
    ImageView play;
    @BindView(R.id.skip_next)
    Button skipNext;
    @BindView(R.id.progress)
    SeekBar progress;

    public MusicController(Context context) {
        this(context, null);

    }


    public void setListener(OnClickListener listener) {
        play.setOnClickListener(listener);
        skipPrevious.setOnClickListener(listener);
        skipNext.setOnClickListener(listener);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
    }

    public MusicController(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Unbinder bind;

    public void setProgress(int progress) {
        this.progress.setProgress(progress);
    }

    public void setMax(int max) {
        this.progress.setMax(max);
    }


    private void init() {
        inflate(getContext(), R.layout.music_controller, this);
        bind = ButterKnife.bind(this);

    }

    public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener listener) {
        progress.setOnSeekBarChangeListener(listener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        bind.unbind();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }
}
