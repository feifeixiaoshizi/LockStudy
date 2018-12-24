package com.example.lib.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 实现DelayExecuteService继承AbstractExecuteService，在原来的基础上扩展延时功能
 */
public class DelayWorker extends AbstractExecuteService implements DelayExecuteService {
    private ScheduledExecutorService mExecutorService;

    public DelayWorker() {
        this.mExecutorService = Executors.newScheduledThreadPool(10);
    }

    public DelayWorker(ScheduledExecutorService mExecutorService) {
        this.mExecutorService = mExecutorService;
    }

    @Override
    public Cancelable execute(Runnable runnable, long delay, TimeUnit timeUnit) {
        FutureCancelableRunnable cancelableRunnable = convert(runnable);
        mExecutorService.schedule(cancelableRunnable, delay, timeUnit);
        return cancelableRunnable;
    }

    @Override
    public Cancelable execute(Runnable runnable, long delay, long perodic,
                              TimeUnit timeUnit) {
        FutureCancelableRunnable cancelableRunnable =
                FutureCancelableRunnable.decorateForPerodic(runnable);
        mExecutorService.scheduleAtFixedRate(cancelableRunnable, delay,
                perodic, timeUnit);
        return cancelableRunnable;
    }

    @Override
    protected void run(Runnable runnable) {
        //实现具体的执行工作
        mExecutorService.submit(runnable);
    }


}
