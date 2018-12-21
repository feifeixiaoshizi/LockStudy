package com.example.thinkpad.lockstudy.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

public class FutureCancelableRunnable extends FutureCancelable implements Runnable {
    private Runnable runnable;
    private boolean isPerodic;

    public FutureCancelableRunnable(Runnable runnable) {
        RunnableFuture<Void> runnableFuture = newTaskFor(runnable, null);
        this.runnable = runnableFuture;
        setFuture(runnableFuture);
    }

    public FutureCancelableRunnable(Runnable runnable, boolean isPerodic) {
        PerodicFuture<Void> runnableFuture = newTaskForPerodic(runnable, null);
        this.runnable = runnableFuture;
        this.isPerodic = isPerodic;
        setFuture(runnableFuture);
    }

    private  <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return new FutureTask<T>(runnable, value);
    }

    private  <T> PerodicFuture<T> newTaskForPerodic(Runnable runnable, T value) {
        return new PerodicFuture<T>(runnable, value);
    }

    /**
     * 继承FutureTask重写run（）方法调用runAndReset（）方法，
     * 让PerodicFuture可以重复执行，避免执行完毕后Callable为空。
     * @param <V>
     */
    private class PerodicFuture<V> extends FutureTask<V> {

        public PerodicFuture(Callable<V> callable) {
            super(callable);
        }

        public PerodicFuture(Runnable runnable, V v) {
            super(runnable, v);
        }

        @Override
        public void run() {
            //调用runAndReset()让该任务可以重复执行
            runAndReset();
        }

    }


    static FutureCancelableRunnable decorate(Runnable runnable) {
        return new FutureCancelableRunnable(runnable);
    }

    static FutureCancelableRunnable decorateForPerodic(Runnable runnable) {
        return new FutureCancelableRunnable(runnable, true);
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
