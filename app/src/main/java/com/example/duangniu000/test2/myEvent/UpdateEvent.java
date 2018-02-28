package com.example.duangniu000.test2.myEvent;


public class UpdateEvent extends EventBus {

    private static UpdateEvent observable;

    public static UpdateEvent getInstance() {
        synchronized (UpdateEvent.class) {
            if (observable == null) {
                observable = new UpdateEvent();
            }
            return observable;
        }
    }



    private UpdateEvent() {
        super();
    }

    @Override
    public void notifyStepChange(Notice notice) {
        setChanged();//设置changeFlag
        notifyObservers(notice);//通知观察者
    }
}
