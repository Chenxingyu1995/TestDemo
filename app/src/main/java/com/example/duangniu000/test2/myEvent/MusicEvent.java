package com.example.duangniu000.test2.myEvent;


public class MusicEvent extends EventBus {

    private static MusicEvent event;

    public static MusicEvent build() {
        synchronized (MusicEvent.class) {
            if (event == null) {
                event = new MusicEvent();
            }
        }
        return event;

    }

    public void post(Notice notice) {
        notifyStepChange(notice);
    }

    @Override
    public void notifyStepChange(Notice notice) {
        setChanged();
        notifyObservers(notice);
    }
}
