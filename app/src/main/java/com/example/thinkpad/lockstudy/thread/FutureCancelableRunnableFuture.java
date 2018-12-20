package com.example.thinkpad.lockstudy.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

public class FutureCancelableRunnableFuture<T> extends FutureCancelable implements Runnable {
    private RunnableFuture<T> runnableFuture;

    public FutureCancelableRunnableFuture(Callable<T> callable) {
        this.runnableFuture = newTaskFor(callable);
        setFuture(runnableFuture);
    }

    static <T> FutureCancelableRunnableFuture<T> decorate(Callable<T> callable) {
        return new FutureCancelableRunnableFuture(callable);
    }

    private <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new FutureTask<T>(callable);
    }

    @Override
    public void run() {
        try {
            runnableFuture.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            runnableFuture = null;
        }
    }
}
