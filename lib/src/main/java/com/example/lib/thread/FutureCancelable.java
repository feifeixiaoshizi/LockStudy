package com.example.lib.thread;

import java.util.concurrent.Future;

/**
 * Created by ThinkPad on 2018/12/18.
 */

public class FutureCancelable<T> implements Cancelable {
    protected Future<T> future;
    protected volatile boolean isCanceled;

    protected void setFuture(Future<T> future) {
        this.future = future;
    }

    @Override
    public void cancel() {
        isCanceled = future.cancel(true);
    }

    @Override
    public boolean isCanceled() {
        return isCanceled;
    }

    public Future<T> getFuture() {
        return future;
    }

}
