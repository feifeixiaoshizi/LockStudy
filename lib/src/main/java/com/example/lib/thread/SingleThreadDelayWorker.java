package com.example.lib.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SingleThreadDelayWorker extends AbstractExecuteService implements DelayExecuteService {
    private ScheduledExecutorService mExecutorService = Executors.newScheduledThreadPool(1);

    @Override
    public Cancelable execute(Runnable runnable, long delay, TimeUnit timeUnit) {
        FutureCancelableRunnable cancelableRunnable = FutureCancelableRunnable.decorate(runnable);
        Future future = mExecutorService.schedule(cancelableRunnable, delay, timeUnit);
        cancelableRunnable.setFuture(future);
        return cancelableRunnable;
    }

    @Override
    public Cancelable execute(Runnable runnable, long delay, long perodic, TimeUnit timeUnit) {
        FutureCancelableRunnable cancelableRunnable = FutureCancelableRunnable.decorate(runnable);
        Future future = mExecutorService.scheduleAtFixedRate(cancelableRunnable, delay, perodic, timeUnit);
        cancelableRunnable.setFuture(future);
        return cancelableRunnable;
    }

    @Override
    protected Future run(Runnable runnable) {
        return mExecutorService.submit(runnable);
    }

    @Override
    protected <T> Future<T> run(Callable<T> callable) {
        return mExecutorService.submit(callable);
    }
}
