package org.xfort.xrock.rockhttp.callback;

public abstract class RockRunnable<T> implements Runnable {
    T obj;

    public RockRunnable(T obj) {
        this.obj = obj;
    }

    @Override
    public void run() {
        onData(obj);
    }

    public abstract void onData(T obj);

}
