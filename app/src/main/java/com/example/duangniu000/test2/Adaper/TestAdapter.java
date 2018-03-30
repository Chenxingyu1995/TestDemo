package com.example.duangniu000.test2.Adaper;


import android.app.Activity;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.duangniu000.test2.Adaper.ViewHolder.BaseHolder;
import com.example.duangniu000.test2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestAdapter extends AbstractAdapter<String> {


    public TestAdapter(Activity fragment) {
        super(fragment);
        for (int i = 0; i < 25; i++) {
            list.add(String.valueOf(i));
        }
    }

    @Override
    public BaseHolder<String> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TextHolder(inflate(R.layout.item_simple_text, parent, false), getActivity());
    }

    private static class TextHolder extends BaseHolder<String> {

        private TextView textView;
        private CountDownTimer timer;

        public TextHolder(View itemView, Activity activity) {
            super(itemView, activity);
            textView = itemView.findViewById(R.id.text);
        }

        @Override
        public void onBindViewHolder(String o) {

            if (timer != null) {
                timer.cancel();
            }

            int position = getAdapterPosition();
            if (position % 2 == 0) {
                textView.setText(o);
            } else {

                long currentTimeMillis = getTime();
                String time1 = second2Time((int) (currentTimeMillis / 1000));
                textView.setText(time1);
                timer = new CountDownTimer(getTime(), 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        long currentTimeMillis = getTime();
                        String time1 = second2Time((int) (currentTimeMillis / 1000));
                        textView.setText(time1);
                    }

                    @Override
                    public void onFinish() {

                    }
                };

                timer.start();
            }

        }

        private long getTime() {
//        String endTime = stake.getEndTime();
            String time = "2018年03月16日 09:21:35";
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
            Date date = null;
            try {
                date = format.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date == null) {
                date = new Date();
            }
            final long dateTime = date.getTime();
            return dateTime - System.currentTimeMillis();
        }

        private String second2Time(int datal) {
            int hours = datal / 3600;
            int minute = (datal / 60) - (hours * 60);
            int second = datal - (hours * 60 * 60) - (minute * 60);
            String s_hours;
            String s_minute;
            String s_second;
            if (hours < 10) {
                s_hours = "0" + String.valueOf(hours);
            } else {
                s_hours = String.valueOf(hours);
            }

            if (minute < 10) {
                s_minute = "0" + String.valueOf(minute);
            } else {
                s_minute = String.valueOf(minute);
            }

            if (second < 10) {
                s_second = "0" + String.valueOf(second);
            } else {
                s_second = String.valueOf(second);
            }

            return s_hours + ":" + s_minute + ":" + s_second;
        }
    }


}
