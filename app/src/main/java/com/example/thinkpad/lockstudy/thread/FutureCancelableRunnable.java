package com.example.thinkpad.lockstudy.thread;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

public class FutureCancelableRunnable extends FutureCancelable implements Runnable {
    private Runnable runnable;

    public FutureCancelableRunnable(Runnable runnable) {
        RunnableFuture<Void> runnableFuture = newTaskFor(runnable,null);
        this.runnable = runnableFuture;
        setFuture(runnableFuture);
    }

    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return new FutureTask<T>(runnable, value);
    }



    static FutureCancelableRunnable decorate(Runnable runnable) {
        return new FutureCancelableRunnable(runnable);
    }

    @Override
    public void run() {
        try {
            runnable.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           // cancel();
        }
    }
}
