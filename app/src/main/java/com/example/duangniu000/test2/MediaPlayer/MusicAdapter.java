package com.example.duangniu000.test2.MediaPlayer;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.duangniu000.test2.Adaper.AbstractAdapter;
import com.example.duangniu000.test2.Adaper.ViewHolder.BaseHolder;
import com.example.duangniu000.test2.R;

public class MusicAdapter extends AbstractAdapter<Music> {

    @Override
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        super.setOnItemClickListener(onItemClickListener);
    }

    public MusicAdapter(Activity fragment) {
        super(fragment);
    }

    @Override
    public BaseHolder<Music> onCreateViewHolder(ViewGroup parent, int viewType) {
        MusicHolder holder = new MusicHolder(inflate(R.layout.item_music, parent, false), getActivity());
        holder.setListener(onItemClickListener);
        return holder;
    }

    public void setPlayIndex(int playIndex) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (i == playIndex) {
                list.get(i).setPlay(true);
                notifyItemChanged(i);
            } else {
                list.get(i).setPlay(false);
                notifyItemChanged(i);
            }
        }
    }


    class MusicHolder extends BaseHolder<Music> {

        private TextView title;
        private TextView singer;
        private TextView time;
        private View root;

        private OnItemClickListener listener;


        public void setListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        MusicHolder(View itemView, Activity activity) {
            super(itemView, activity);
            title = itemView.findViewById(R.id.title);
            singer = itemView.findViewById(R.id.singer);
            time = itemView.findViewById(R.id.time);
            root = itemView.findViewById(R.id.root);
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(getAdapterPosition(), MusicHolder.this);
                    }
                }
            });


        }

        @Override
        public void onBindViewHolder(Music object) {
            title.setText(object.getTitle());
            singer.setText(object.getArtist());
            long countLong = object.getCountTime() / 1000;

            if (object.isPlay()) {
                root.setBackgroundColor(Color.parseColor("#4ed5c7"));
            } else {
                root.setBackgroundColor(Color.parseColor("#ffffff"));
            }

            time.setText(getTime((int) countLong));
        }


    }

    //根据秒数转化为时分秒   00:00:00
    private static String getTime(int second) {
        if (second < 10) {
            return "00:0" + second;
        }
        if (second < 60) {
            return "00:" + second;
        }
        if (second < 3600) {
            int minute = second / 60;
            second = second - minute * 60;
            if (minute < 10) {
                if (second < 10) {
                    return "0" + minute + ":0" + second;
                }
                return "0" + minute + ":" + second;
            }
            if (second < 10) {
                return minute + ":0" + second;
            }
            return minute + ":" + second;
        }
        int hour = second / 3600;
        int minute = (second - hour * 3600) / 60;
        second = second - hour * 3600 - minute * 60;
        if (hour < 10) {
            if (minute < 10) {
                if (second < 10) {
                    return "0" + hour + ":0" + minute + ":0" + second;
                }
                return "0" + hour + ":0" + minute + ":" + second;
            }
            if (second < 10) {
                return "0" + hour + minute + ":0" + second;
            }
            return "0" + hour + minute + ":" + second;
        }
        if (minute < 10) {
            if (second < 10) {
                return hour + ":0" + minute + ":0" + second;
            }
            return hour + ":0" + minute + ":" + second;
        }
        if (second < 10) {
            return hour + minute + ":0" + second;
        }
        return hour + minute + ":" + second;
    }


}
