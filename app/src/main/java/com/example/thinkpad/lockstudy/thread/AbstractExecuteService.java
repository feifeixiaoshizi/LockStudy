package com.example.thinkpad.lockstudy.thread;

import java.util.concurrent.Callable;

/**
 * ExecuteService的基类实现execute的功能，父类主要负责装饰，子类负责具体的执行功能
 */
public abstract class AbstractExecuteService implements ExecuteService {

    @Override
    public Cancelable execute(Runnable runnable) {
        FutureCancelableRunnable cancelable = convert(runnable);
        run(cancelable);
        return cancelable;
    }

    @Override
    public <V> FutureCancelable<V> execute(Callable<V> callable) {
        FutureCancelableRunnableFuture<V> cancelable = convert(callable);
        run(cancelable);
        return cancelable;
    }

    protected FutureCancelableRunnable convert(Runnable runnable) {
        FutureCancelableRunnable cancelableRunnable = FutureCancelableRunnable
                .decorate(runnable);
        return cancelableRunnable;
    }

    protected <T> FutureCancelableRunnableFuture<T> convert(Callable<T> callable) {
        FutureCancelableRunnableFuture<T> cancelableRunnable = FutureCancelableRunnableFuture.decorate(callable);
        return cancelableRunnable;
    }

    protected abstract void run(Runnable runnable);

}
