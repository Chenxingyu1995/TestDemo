package com.example.duangniu000.test2.myEvent;


import java.util.Vector;

public abstract class EventBus {

    private boolean changed = false;

    private Vector<EventObserver> obs;

    public EventBus() {
        obs = new Vector<>();
    }

    public synchronized void addObserver(EventObserver o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.addElement(o);
        }
    }

    public synchronized void deleteObserver(EventObserver o) {
        obs.removeElement(o);
    }

    public void notifyObservers() {
        notifyObservers(null);
    }

    public void notifyObservers(Notice arg) {

        Object[] arrLocal;

        synchronized (this) {
            if (!hasChanged())
                return;
            arrLocal = obs.toArray();
            clearChanged();
        }

        for (int i = arrLocal.length - 1; i >= 0; i--)
            ((EventObserver) arrLocal[i]).update(arg);
    }

    public synchronized void deleteObservers() {
        obs.removeAllElements();
    }

    protected synchronized void setChanged() {
        changed = true;
    }


    protected synchronized void clearChanged() {
        changed = false;
    }


    public synchronized boolean hasChanged() {
        return changed;
    }

    public synchronized int countObservers() {
        return obs.size();
    }

    public abstract void notifyStepChange(Notice notic);
}
